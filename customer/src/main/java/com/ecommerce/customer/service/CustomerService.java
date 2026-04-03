package com.ecommerce.customer.service;

import com.ecommerce.customer.dto.CustomerRequest;
import com.ecommerce.customer.dto.CustomerResponse;
import com.ecommerce.customer.exception.CustomerNotFoundException;
import com.ecommerce.customer.mapper.CustomerMapper;
import com.ecommerce.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public String createCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.save(customerMapper.toCustomer(customerRequest));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                "Customer with id %s not found".formatted(customerRequest.id())
        ));

        customerRepository.save(customerMapper.updateCustomer(customerRequest, customer));
    }

    public List<CustomerResponse> getCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .toList();
    }

    public CustomerResponse getCustomerById(String id) {
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer with id %s not found".formatted(id)
                ));
        return customerMapper.fromCustomer(customer);
    }

    public Boolean existCustomerById(String id) {
        return customerRepository
                .findById(id)
                .isPresent();
    }

    public void deleteCustomerById(String id) {
        customerRepository.deleteById(id);
    }
}
