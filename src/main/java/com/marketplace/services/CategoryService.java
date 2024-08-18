package com.marketplace.services;

import com.marketplace.domain.category.Category;
import com.marketplace.domain.category.CategoryDTO;
import com.marketplace.domain.category.CategoryResponseDTO;
import com.marketplace.domain.category.exceptions.CategoryNotFoundException;
import com.marketplace.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Matheus Alves
 */
@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    public List<CategoryResponseDTO> getAll() {
        return this.repository.findAll()
                .stream()
                .map(category -> new CategoryResponseDTO(
                        category.getId(), category.getTitle(), category.getDescription()))
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO create(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO);
        category = this.repository.save(category);

        return new CategoryResponseDTO(category.getId(), category.getTitle(), category.getDescription());
    }

    public Category findById(UUID id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(
                        String.format("Category not found for id=%s", id))
                );
    }

    public Optional<Category> getById(String id) {
        return this.repository.findById(UUID.fromString(id));
    }

    public CategoryResponseDTO update(UUID id, CategoryDTO categoryDTO) {

        Category category = this.findById(id);

        if(!categoryDTO.title().isEmpty())
            category.setTitle(categoryDTO.title());

        if(!categoryDTO.description().isEmpty())
            category.setDescription(categoryDTO.description());

        category = this.repository.save(category);

        return new CategoryResponseDTO(category.getId(), category.getTitle(), category.getDescription());
    }

    public void delete(UUID id) {

        Category category = this.findById(id);

        this.repository.delete(category);
    }

}
