package fr.leojadenadir.projetandroid.model.repositories;

import fr.leojadenadir.projetandroid.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
}
