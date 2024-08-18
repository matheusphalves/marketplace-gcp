package com.marketplace.services;

import com.marketplace.domain.product.ProductDTO;
import com.marketplace.domain.category.Category;
import com.marketplace.domain.product.Product;
import com.marketplace.domain.product.ProductResponseDTO;
import com.marketplace.domain.product.ProductUpdateDTO;
import com.marketplace.domain.product.exceptions.ProductNotFoundException;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.exceptions.UserNotFoundException;
import com.marketplace.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Matheus Alves
 */
@Service
public class ProductService {

    private final ProductRepository repository;

    private final CategoryService categoryService;

    private final UserService userService;

    public ProductService(
            ProductRepository categoryRepository,
            CategoryService categoryService,
            UserService userService
    ) {

        this.repository = categoryRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public List<ProductResponseDTO> getAll() {
        return this.repository.findAll()
                .stream().map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getTitle(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory().getId(),
                        product.getUser().getId())
                ).collect(Collectors.toList());
    }

    public ProductResponseDTO create(ProductDTO productDTO) {

        Category category = this.categoryService
                .findById(productDTO.categoryId());

        User user = this.userService
                .findById(productDTO.userId());

        Product product = new Product(productDTO);
        product.setCategory(category);
        product.setUser(user);

        product = this.repository.save(product);

        return new ProductResponseDTO(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getUser().getId()
        );
    }

    public Product findById(UUID id) {
        return this.repository
                .findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(String.format("Product not found for id=%s", id))
                );
    }

    public ProductResponseDTO update(UUID id, ProductUpdateDTO productDTO) {

        Product product = this.findById(id);

        if(!productDTO.title().isEmpty())
            product.setTitle(productDTO.title());

        if(!productDTO.description().isEmpty())
            product.setDescription(productDTO.description());

        if(productDTO.price() != null)
            product.setPrice(productDTO.price());

        if(productDTO.userId() != null) {
            product.setUser(
                    this.userService.findById(productDTO.userId())
            );
        }

        if(productDTO.categoryId() != null) {
            product.setCategory(
                    this.categoryService.findById(productDTO.categoryId())
            );
        }

        product = this.repository.save(product);

        return new ProductResponseDTO(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getUser().getId()
        );
    }

    public void delete(UUID id) {

        Product product = this.findById(id);

        this.repository.delete(product);
    }
}
