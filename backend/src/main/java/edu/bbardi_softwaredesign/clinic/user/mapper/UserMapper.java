package edu.bbardi_softwaredesign.clinic.user.mapper;

import edu.bbardi_softwaredesign.clinic.user.model.ERole;
import edu.bbardi_softwaredesign.clinic.user.model.Role;
import edu.bbardi_softwaredesign.clinic.user.model.User;
import edu.bbardi_softwaredesign.clinic.user.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "roles", target = "roles", qualifiedByName = "RoleToString")
    UserDTO toDto(User user);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "StringToRole")
    User fromDto(UserDTO user);

    @Named("StringToRole")
    public static Set<Role> mapStringToRole(Set<String> value) {
        new Role();
        return value.stream()
                .map(role -> Role.builder().name(ERole.valueOf(role)).build())
                .collect(Collectors.toSet());
    }

    @Named("RoleToString")
    public static Set<String> mapRoleToString(Set<Role> value) {
        new Role();
        return value.stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toSet());
    }
}