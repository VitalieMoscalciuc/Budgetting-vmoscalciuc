package com.vmoscalciuc.budget.service;

import com.vmoscalciuc.budget.model.Role;
import com.vmoscalciuc.budget.model.User;
import com.vmoscalciuc.budget.model.dto.UserDto;
import com.vmoscalciuc.budget.repository.impl.RoleRepositoryImpl;
import com.vmoscalciuc.budget.repository.impl.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService  {

    private final UserRepositoryImpl userRepositoryImpl;
    private final RoleRepositoryImpl roleRepositoryImpl;
    private final PasswordEncoder passwordEncoder;


    public User saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setBalance(userDto.getBalance());
        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepositoryImpl.findByName("USER").orElseThrow(() -> new RuntimeException("Role not found"));;
        user.setRoles(Arrays.asList(role));
        userRepositoryImpl.save(user);
        return convertDtoToEntity(userDto);
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private User convertDtoToEntity(UserDto userDto){
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public void updateUserBalance(Long userId, Double updatedAmount){
        System.out.println("updated amount"+updatedAmount);
        userRepositoryImpl.updateUserBalance(userId,updatedAmount);
    };
    public void addToUserBalance(Long userId, Double updatedAmount){
        System.out.println("updated amount"+updatedAmount);
        userRepositoryImpl.addToUserBalance(userId,updatedAmount);
    };




    public Optional<User> findByEmail(String email) {
        System.out.println("finding by email from userService");
        return userRepositoryImpl.findByEmail(email);
    }
//    @Override
//    public List<UserDto> findAllUsers() {
//        List<User> users = userRepository.findAll();
//        return users.stream().map((user) -> convertEntityToDto(user))
//                .collect(Collectors.toList());
//    }

}
