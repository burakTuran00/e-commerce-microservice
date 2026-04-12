package com.ecommerce.customer.controller;

import com.ecommerce.customer.dto.CustomerRequest;
import com.ecommerce.customer.dto.CustomerResponse;
import com.ecommerce.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        customerService.updateCustomer(customerRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("id") String id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("exist/{id}")
    public ResponseEntity<Boolean> existCustomerById(@PathVariable("id") String id) {
        return ResponseEntity.ok(customerService.existCustomerById(id));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("id") String id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }
}
