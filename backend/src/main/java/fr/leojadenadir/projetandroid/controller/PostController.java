package fr.leojadenadir.projetandroid.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.leojadenadir.projetandroid.model.entities.Post;
import fr.leojadenadir.projetandroid.model.entities.User;
import fr.leojadenadir.projetandroid.model.repositories.PostRepository;
import fr.leojadenadir.projetandroid.model.repositories.UserRepository;

@RestController
@RequestMapping("posts")
public class PostController {

	private PostRepository postRepository;
	private UserRepository userRepository;

	public PostController(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@PostMapping("")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public Post createPost(@RequestBody Post post, Principal userInfo) {
		User user = userRepository.findByUsername(userInfo.getName());
		post.setAuthor(user);
		LocalDateTime now = LocalDateTime.now();
		post.setCreatedDate(now);

		postRepository.save(post);

		return post;
	}

	@GetMapping("")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Post> getAllPosts() {
		return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
	}

	@GetMapping("/myposts")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Post> getMyPosts(Principal userInfo) {
		User user = userRepository.findByUsername(userInfo.getName());
		return postRepository.findPostsByAuthorOrderByCreatedDateDesc(user);
	}

	@GetMapping("/{idUser}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Post> getPostsByUserId(@PathVariable("idUser") String idUser) {
		Optional<User> user = userRepository.findById(idUser);
		return postRepository.findPostsByAuthorOrderByCreatedDateDesc(user.get());
	}

	@DeleteMapping("/{idPost}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePostById(@PathVariable("idPost") String idPost) {
		postRepository.deleteById(idPost);
	}
	
	@DeleteMapping("")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllPosts() {
		postRepository.deleteAll();
	}
}