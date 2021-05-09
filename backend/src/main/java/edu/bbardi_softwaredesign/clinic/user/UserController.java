package edu.bbardi_softwaredesign.clinic.user;


import edu.bbardi_softwaredesign.clinic.user.model.dto.UserDTO;
import edu.bbardi_softwaredesign.clinic.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.bbardi_softwaredesign.clinic.UrlMapping.ENTITY;
import static edu.bbardi_softwaredesign.clinic.UrlMapping.USER;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {
    private final UserManagementService userManagementService;

    @GetMapping
    public List<UserDTO> findAllUsers() {
        return userManagementService.findAll();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userManagementService.createUser(user);
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id) {
        userManagementService.deleteUser(id);
    }

    @PatchMapping(ENTITY)
    public UserDTO editUser(@PathVariable Long id, @RequestBody UserDTO user) {
        return userManagementService.editUser(id, user);
    }
}
