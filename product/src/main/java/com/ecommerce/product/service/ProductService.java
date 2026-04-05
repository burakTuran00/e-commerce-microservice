package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductPurchaseRequest;
import com.ecommerce.product.dto.ProductPurchaseResponse;
import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;
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

    public Integer createProduct(ProductRequest productRequest) {W
        return null;
    }


    public List<ProductPurchaseResponse> purchaseProduct(ProductPurchaseRequest productPurchaseRequest) {
        return null;
    }

    public @Nullable ProductResponse getProductById(Integer productId) {
    }

    public @Nullable List<ProductResponse> getAllProducts() {
    }
}
