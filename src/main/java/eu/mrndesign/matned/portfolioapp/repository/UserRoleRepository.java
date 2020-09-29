package eu.mrndesign.matned.portfolioapp.repository;

import eu.mrndesign.matned.portfolioapp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select case when count(ur)> 0 then true else false end from UserRole ur where lower(ur.roleName) like lower(?1)")
    boolean roleExists(String role);

    @Query("select ur from UserRole ur where lower(ur.roleName) = lower(?1)")
    UserRole findByRoleName(String userRole);
}
