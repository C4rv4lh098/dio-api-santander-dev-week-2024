package me.dio.controller;

import io.swagger.v3.oas.annotations.Operation;
import me.dio.controller.dto.UserDto;
import me.dio.domain.model.User;
import me.dio.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Retorna todos Usuários | Get all users", description = "Retorna lista com todos Usuários | Retrieve a list of all registered users")
    public ResponseEntity<List<UserDto>> findUsers() {
        List<UserDto> users = userService.findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna usuário pelo id | Get a user by ID", description = "Retorna um usuário específico | Retrieve a specific user based on its ID")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserDto(user));
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário \n Create a new user", description = "Criar novo usuário e retorna os dados do usuário criado \n Create a new user and return the created user's data")
    public ResponseEntity<UserDto> create(@RequestBody User userToCreate) {
        User userCreated = userService.create(userToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(new UserDto(userCreated));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário \n Update a user", description = "Atualiza informações de usuário existente \n Update the data of an existing user based on its ID")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto userDto){
        User user = userService.update(id, userDto.toModel());
        return ResponseEntity.ok(new UserDto(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletarusuário | Delete a user", description = "Apaga usuário exixtente \n Delete an existing user based on its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
