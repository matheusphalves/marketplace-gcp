package com.marketplace.domain.user;

import com.marketplace.domain.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String emailAddress;

    @OneToMany(mappedBy = "user")
    private Set<Product> products;

    public User(UserDTO userDTO) {
        this.name = userDTO.name();
        this.emailAddress = userDTO.emailAddress();
    }

}
