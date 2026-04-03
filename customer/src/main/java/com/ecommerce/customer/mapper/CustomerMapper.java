package com.ecommerce.customer.mapper;

import com.ecommerce.customer.dto.CustomerRequest;
import com.ecommerce.customer.dto.CustomerResponse;
import com.ecommerce.customer.entity.Customer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request) {
        if (request == null) {
            return null;
        }
        return Customer.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

    public Customer updateCustomer(CustomerRequest customerRequest, Customer customer)
    {
        if(StringUtils.isNotBlank(customerRequest.firstname())) {
            customer.setFirstname(customerRequest.firstname());
        }
        if(StringUtils.isNotBlank(customerRequest.lastname())) {
            customer.setFirstname(customerRequest.lastname());
        }
        if(StringUtils.isNotBlank(customerRequest.email())) {
            customer.setFirstname(customerRequest.email());
        }
        if(customerRequest.address() != null) {
            customer.setAddress(customerRequest.address());
        }

        return customer;
    }
}