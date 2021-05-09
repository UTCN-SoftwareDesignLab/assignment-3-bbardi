package edu.bbardi_softwaredesign.clinic.user.service;


import edu.bbardi_softwaredesign.clinic.user.mapper.UserMapper;
import edu.bbardi_softwaredesign.clinic.user.model.ERole;
import edu.bbardi_softwaredesign.clinic.user.model.Role;
import edu.bbardi_softwaredesign.clinic.user.model.User;
import edu.bbardi_softwaredesign.clinic.user.model.dto.UserDTO;
import edu.bbardi_softwaredesign.clinic.user.repository.RoleRepository;
import edu.bbardi_softwaredesign.clinic.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    private Set<Role> mapRoles(Set<String> roles){
        return roles.stream()
                .map(role ->
                        roleRepository.findByName(ERole.valueOf(role))
                                .orElseThrow(()-> new EntityNotFoundException("Unable to find role"))
                )
                .collect(Collectors.toSet());
    }

    private void verifyDataUnique(User actUser, UserDTO user) {
        if (!actUser.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (!actUser.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
    }

    private void getPasswordFromDB(User user) {
        User DBUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Unable to find user"));
        user.setPassword(DBUser.getPassword());
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }


    public UserDTO createUser(UserDTO user) {
        User actUser = userMapper.fromDto(user);
        actUser.setRoles(mapRoles(user.getRoles()));
        if (userRepository.existsByUsername(actUser.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        if (userRepository.existsByEmail(actUser.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        actUser.setPassword(encoder.encode(actUser.getPassword()));
        actUser.setFullName(user.getFullName());
        return userMapper.toDto(userRepository.save(actUser));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO editUser(Long id, UserDTO user) {
        User actUser = userRepository.findById(id).orElseThrow(()->new EntityExistsException("User not found"));
        actUser.setRoles(mapRoles(user.getRoles()));
        verifyDataUnique(actUser,user);
        actUser.setEmail(user.getEmail());
        actUser.setUsername(user.getUsername());
        actUser.setFullName(user.getFullName());
        if (!user.getPassword().equals("")) {
            actUser.setPassword(encoder.encode(user.getPassword()));
        }
        return userMapper.toDto(userRepository.save(actUser));
    }
}
