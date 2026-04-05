package com.ecommerce.service;

import com.ecommerce.client.customer.CustomerClient;
import com.ecommerce.client.product.ProductClient;
import com.ecommerce.dto.order.OrderRequest;
import com.ecommerce.dto.orderline.OrderLineRequest;
import com.ecommerce.dto.product.PurchaseRequest;
import com.ecommerce.exception.BusinessException;
import com.ecommerce.mapper.OrderMapper;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(OrderRequest request)
    {
        //check if the customer exists
        var customer = this.customerClient.getCustomerById(request.customerId())
                .orElseThrow( () ->
                                new BusinessException("Cannot create order:: No customer exists with the provided ID"
                                ));

        //purchase the products
        this.productClient.purchaseProduct(request.products());

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

        return null;
    }
}
