package com.marketplace.controllers.rest;

import com.marketplace.domain.user.UserDTO;
import com.marketplace.domain.user.UserResponseDTO;
import com.marketplace.domain.user.UserUpdateResponseDTO;
import com.marketplace.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/api/users/")
@Tag(name = "Users", description = "Management of Users.")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/")
    @Operation(summary = "Creates a new User.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema()) }) }
    )
    public ResponseEntity<UserResponseDTO> create(
                @Valid @RequestBody UserDTO userDTO) {

        UserResponseDTO user = service.create(userDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update the data of an existing User.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) }
    )
    public ResponseEntity<UserUpdateResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody UserDTO userDTO) {

        UserUpdateResponseDTO user = service.update(id, userDTO);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieves a User by id if exists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserResponseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<UserResponseDTO> get(@PathVariable UUID id) {

        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/")
    @Operation(
            summary = "Returns all registered users.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserResponseDTO.class), mediaType = "application/json") }),
    })
    public ResponseEntity<List<UserResponseDTO>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a User if exists.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<Object> delete(@PathVariable UUID id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

}
