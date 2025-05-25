package com.WaterSupplyNotification.User.controller;

import java.util.List;

import com.WaterSupplyNotification.User.entity.LoginRequest;
import com.WaterSupplyNotification.User.entity.Users;
import com.WaterSupplyNotification.User.service.UserService;

import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService=userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users user)
    {
        Users saveUser = userService.saveUser(user);
        return ResponseEntity.ok(saveUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request)
    {
        String token = userService.loginUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(token);
    }

    @GetMapping("{id}")
    public ResponseEntity<Users> getUser(@PathVariable Long id)
    {
        Optional<Users> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<Users>> getUsersByLocation(@PathVariable String locationId)
    {
        List<Users> users = userService.findByLocationId(locationId);
       return ResponseEntity.ok(users);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<Users> updateUser(@RequestBody Users user)
    {
        Users updateUser=userService.updateUser(user);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id)
    {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with ID " + id + " deleted successfully.");
    }

}
