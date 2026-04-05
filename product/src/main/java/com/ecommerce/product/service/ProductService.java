package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductPurchaseRequest;
import com.ecommerce.product.dto.ProductPurchaseResponse;
import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest productRequest) {
        var product = mapper.ToEntity(productRequest);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProduct(ProductPurchaseRequest productPurchaseRequest) {
        return null;
    }

    public ProductResponse getProductById(Integer productId) {
        return repository.findById(Long.valueOf(productId))
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
    }

    public List<ProductResponse> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .toList();
    }
}
