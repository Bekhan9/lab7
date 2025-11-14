package com.example.lab7.service;

import com.example.lab7.dto.ProductDto;
import com.example.lab7.model.Product;
import com.example.lab7.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductDto> getAllProducts() {
        return repository.findAll().stream()
                .map(ProductDto::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductDto.toDto(product);
    }

    public ProductDto createProduct(ProductDto dto) {
        Product entity = ProductDto.toEntity(dto);
        Product saved = repository.save(entity);
        return ProductDto.toDto(saved);
    }

    public ProductDto updateProduct(Long id, ProductDto dto) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());
        existing.setDescription(dto.getDescription());

        Product updated = repository.save(existing);
        return ProductDto.toDto(updated);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
