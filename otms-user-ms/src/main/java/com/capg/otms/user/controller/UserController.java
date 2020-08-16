package com.capg.otms.user.controller;

    import java.net.URISyntaxException;
	import java.util.List;
    import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RestController;
	import org.springframework.web.client.RestClientException;

	import com.capg.otms.user.model.Question;
	import com.capg.otms.user.model.Test;
	import com.capg.otms.user.model.User;
	import com.capg.otms.user.service.IUserService;

	@RestController
	public class UserController {
		@Autowired
		IUserService service;
		
		
		
		
		@PostMapping("/users/add")
		public User addUser(@RequestBody User user){
			return service.addUser(user);
		}
		
		@PutMapping("/users/update")
		public User updateUser(@RequestBody User user){
			return service.updateUser(user);
		}
		
		@DeleteMapping("/users/delete/id/{userId}")
		public User deleteUser(@PathVariable long userId){
			return service.deleteUser(userId);
		}
		
		@GetMapping("/users/user-id/{userId}")
		public User getUser(@PathVariable long userId) {
			return service.getUser(userId);
		}
		
		
		
		
		
		@GetMapping("/admin/message")
		public String getMessage() {
			return "Hello Admin";
			}
		
		@GetMapping("/users/user-name/{userName}")
		public User getUserByName(@PathVariable String userName) {
			return service.getUserByName(userName);
		}
		
		@GetMapping("/user/message")
		public String getPrivateMessage() {
			return "Hello Users";
			}
		
	}



