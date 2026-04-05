package com.ecommerce.dto.orderline;

public record OrderLineResponse(
        Integer id,
        double quantity
) { }