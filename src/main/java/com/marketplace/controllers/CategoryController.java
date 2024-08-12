package com.marketplace.controllers;

import com.marketplace.domain.category.CategoryDTO;
import com.marketplace.domain.category.CategoryResponseDTO;
import com.marketplace.services.CategoryService;
import com.marketplace.domain.category.Category;
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
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Management of Categories.")
public class CategoryController {

    private CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }


    @PostMapping("/")
    @Operation(summary = "Creates a new Category.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema()) }) }
    )
    public ResponseEntity<CategoryResponseDTO> create(
            @RequestBody CategoryDTO categoryDTO) {

        CategoryResponseDTO category = this.service.create(categoryDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.id())
                .toUri();

        return ResponseEntity.created(location).body(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update the data of an existing Category.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) }
    )
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable("id") UUID id, @RequestBody CategoryDTO categoryDTO) {

        CategoryResponseDTO category = this.service.update(id, categoryDTO);

        return ResponseEntity.ok().body(category);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a Category if exists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {

        this.service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    @Operation(
            summary = "Returns all registered categories.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Category.class), mediaType = "application/json") }),
    })
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {

        List<CategoryResponseDTO> categories = this.service.getAll();

        return ResponseEntity.ok().body(categories);
    }

}
