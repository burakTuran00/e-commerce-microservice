package com.ecommerce.kafka;

import com.ecommerce.dto.customer.CustomerResponse;
import com.ecommerce.dto.product.PurchaseResponse;
import com.ecommerce.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
