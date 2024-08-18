package com.marketplace.domain.category;

import com.marketplace.domain.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Product> products;

    public Category (CategoryDTO categoryDTO) {
        this.title = categoryDTO.title();
        this.description = categoryDTO.description();
    }
}
