package edu.bbardi_softwaredesign.clinic.user.repository;

import edu.bbardi_softwaredesign.clinic.user.model.ERole;
import edu.bbardi_softwaredesign.clinic.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}