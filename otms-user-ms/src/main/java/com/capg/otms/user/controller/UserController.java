package com.capg.otms.user.controller;


	import java.net.URISyntaxException;
	import java.util.List;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	@CrossOrigin(origins = "http://localhost:4200")
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
		
		@GetMapping("/users/all")
		public List<User> getAllUsers() {
			return service.getAllUsers();
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
		
		@PostMapping("/admin/add/test")
		public Test addTest(@RequestBody Test test) {
			return service.addTest(test);
		}
	@PutMapping("/admin/update/test/{testId}")
		public Test updateTest(@RequestBody Test test, @PathVariable long testId) throws RestClientException, URISyntaxException {
			return service.updateTest(test,testId);
		}
	@DeleteMapping("/admin/delete/test/id/{testId}")
		public Test deleteTest(@PathVariable long testId) throws RestClientException, URISyntaxException {
			return service.deleteTest(testId);
		}
	
	@PostMapping("/add/question/test-id/{testId}")
	public Question addQuestion(@PathVariable long testId, @RequestBody Question question) throws RestClientException, URISyntaxException
	{
		return service.addQuestions(testId, question);
	}
	
	@PutMapping("/update/question/{testId}/{questionId}")
	public Question updateQuestion(@PathVariable long testId, @PathVariable long questionId,@RequestBody Question question) throws RestClientException, URISyntaxException
	{
		return service.updateQuestions(testId, questionId,question);
	}
	
	
   @DeleteMapping("/delete/question/{testId}/{questionId}")
	public Question deleteQuestion(@PathVariable long testId, @PathVariable long questionId) throws RestClientException, URISyntaxException
	{
		return service.deleteQuestions(testId, questionId);
	}

		@GetMapping("/user/test/result/{testId}")
		public double getResult(@PathVariable long testId) {
			return service.getResult(testId);

		}
		@GetMapping("/user/admin/validate/{userName}/{userPassword}")
		public ResponseEntity<Boolean> validateAdmin(@PathVariable String userName, @PathVariable String userPassword) {
			boolean valid =  service.validateAdmin(userName,userPassword);
			return new ResponseEntity<Boolean>(valid,HttpStatus.ACCEPTED);
		}
		@GetMapping("/user/validate/{userName}/{userPassword}")
		public ResponseEntity<Boolean> validateUser(@PathVariable String userName, @PathVariable String userPassword) {
			boolean valid =  service.validateUser(userName,userPassword);
			return new ResponseEntity<Boolean>(valid, HttpStatus.ACCEPTED);
		}
		@GetMapping("/test/questions/{testId}")
		public List<Question> getTestQuestions(@PathVariable long testId){
			return service.getTestQuestions(testId);
		}
		@GetMapping("/users/question/{questionId}/validate/{chosenAnswer}")
		public ResponseEntity<Double> validateQuestion(@PathVariable long questionId, @PathVariable int chosenAnswer){
			double result = service.validateQuestion(questionId,chosenAnswer);
			return new ResponseEntity<Double>(result,HttpStatus.ACCEPTED);
		}

	}