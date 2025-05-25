package com.WaterSupplyNotification.User.service;

import java.util.List;

import com.WaterSupplyNotification.User.config.JWTUtil;
import com.WaterSupplyNotification.User.entity.Users;
import com.WaterSupplyNotification.User.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

   private UserRepository userRepository;
   private PasswordEncoder passwordEncoder;
   private JWTUtil jwtUtil;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
    }

    public String loginUser(String email,String password)
    {
        Optional<Users> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        if(optionalUser.isEmpty())
        {
            throw new RuntimeException("User not found");
        }
        Users user=optionalUser.get();
        if(!passwordEncoder.matches(password,user.getPassword()))
        {
            throw new RuntimeException("Password doesn't match");
        }
        return jwtUtil.generateToken(user.getEmail(),user.getRole());
    }

    public Users saveUser(Users user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<Users> getUserById(long id)
    {
        return userRepository.findById(id);
    }

    public List<Users> findByLocationId(String locationId)
    {
        return userRepository.findByLocationId(locationId);
    }

    public Users updateUser(Users user)
    {
        Users existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update user details
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setLocationId(user.getLocationId());
        existingUser.setMobileNo(user.getMobileNo());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id)
    {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
