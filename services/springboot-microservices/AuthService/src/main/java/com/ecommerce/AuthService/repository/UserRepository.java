package com.ecommerce.AuthService.repository;

import com.ecommerce.AuthService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user
     * @return an Optional containing the user if found, or empty if not
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user
     * @return an Optional containing the user if found, or empty if not
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds users by their role.
     * This assumes a many-to-many relationship between User and Role entities.
     * The query may need to be adjusted depending on the actual structure of your entities.
     *
     * @param role the name of the role to filter by
     * @return a list of users who have the specified role
     */
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :role")
    List<User> findByRole(String role);

    /**
     * Checks if a user with the given email already exists.
     *
     * @param email the email to check for existence
     * @return true if a user with the email exists, otherwise false
     */
    boolean existsByEmail(String email);
}
