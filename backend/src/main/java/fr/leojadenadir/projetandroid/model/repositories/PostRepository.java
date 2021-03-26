package fr.leojadenadir.projetandroid.model.repositories;

import fr.leojadenadir.projetandroid.model.entities.Post;
import fr.leojadenadir.projetandroid.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,String> {

    Iterable<Post> findPostsByAuthorOrderByCreatedDateDesc(User author);

}
