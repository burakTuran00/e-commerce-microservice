package com.ecommerce.dto.customer;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {

}
