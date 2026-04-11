package com.ecommerce.service;

import com.ecommerce.client.customer.CustomerClient;
import com.ecommerce.client.payment.PaymentClient;
import com.ecommerce.client.product.ProductClient;
import com.ecommerce.dto.order.OrderRequest;
import com.ecommerce.dto.order.OrderResponse;
import com.ecommerce.dto.orderline.OrderLineRequest;
import com.ecommerce.dto.payment.PaymentRequest;
import com.ecommerce.dto.product.PurchaseRequest;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.kafka.OrderConfirmation;
import com.ecommerce.kafka.OrderProducer;
import com.ecommerce.mapper.OrderMapper;
import com.ecommerce.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest request)
    {
        //check if the customer exists
        var customer = this.customerClient.getCustomerById(request.customerId())
                .orElseThrow( () ->
                                new BusinessException("Cannot create order:: No customer exists with the provided ID"
                                ));

        //purchase the products
        var purchaseProducts = this.productClient.purchaseProduct(request.products());

        //persist the order in the database and return the order id
        var order = orderRepository.save(orderMapper.toOrder(request));

        for(PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        //todo: start payment process
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                request.reference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        // send the order confirmation email notifaction-ms (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts.orElseThrow()
                )
        );
        return order.getId();
    }


    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return this.orderRepository.findById(Long.valueOf(orderId))
                .map(this.orderMapper::fromOrder)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(
                                "No order found with the provided ID: %d", orderId)
                        ));
    }
}
