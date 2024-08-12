package com.marketplace.controllers;

import com.marketplace.domain.product.ProductDTO;
import com.marketplace.domain.product.ProductResponseDTO;
import com.marketplace.domain.product.ProductUpdateDTO;
import com.marketplace.services.ProductService;
import com.marketplace.domain.product.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * @author Matheus Alves
 */
@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Management of Products.")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/")
    @Operation(summary = "Creates a new Product.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema()) }) }
    )
    public ResponseEntity<ProductResponseDTO> create(
            @RequestBody ProductDTO productDTO) {

        ProductResponseDTO product = this.service.create(productDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.id())
                .toUri();

        return ResponseEntity.created(location).body(product);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update the data of an existing Product.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) }
    )
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable("id") UUID id, @RequestBody ProductUpdateDTO productDTO) {

        ProductResponseDTO product = this.service.update(id, productDTO);

        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a Product if exists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {

        this.service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    @Operation(
            summary = "Returns all registered products.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
    })
    public ResponseEntity<List<ProductResponseDTO>> getAll() {

        List<ProductResponseDTO> products = this.service.getAll();

        return ResponseEntity.ok().body(products);
    }

}
