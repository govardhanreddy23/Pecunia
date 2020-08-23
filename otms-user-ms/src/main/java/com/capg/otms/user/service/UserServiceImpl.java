package com.capg.otms.user.service;


	import java.net.URI;
	import java.net.URISyntaxException;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Set;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.stereotype.Service;
	import org.springframework.web.client.RestClientException;
	import org.springframework.web.client.RestTemplate;
	import org.springframework.web.server.ResponseStatusException;

	import com.capg.otms.user.model.Question;
	import com.capg.otms.user.model.Test;
	import com.capg.otms.user.model.User;
	import com.capg.otms.user.repository.IUserRepo;

	@Service
	public class UserServiceImpl implements IUserService {
	@Autowired
		IUserRepo repo;
	@Autowired
		RestTemplate rt;
	@Override
		public User addUser(User user) {
			// TODO Auto-generated method stub
			return repo.save(user);
		}

		@Override
		public User deleteUser(long userId) {
			// TODO Auto-generated method stub
			User deletedUser = repo.getOne(userId);
			repo.deleteById(userId);
			return deletedUser;
		}

		@Override
		public User updateUser(User newUser) {
			// TODO Auto-generated method stub
			User user = repo.getOne(newUser.getUserId());
			user.setUserName(newUser.getUserName());
			user.setUserPassword(newUser.getUserPassword());
			user.setUserTest(newUser.getUserTest());
			user.setAdmin(newUser.isAdmin());
			return user;
		}

		@Override
		public User getUser(long userId) {
			// TODO Auto-generated method stub
			return repo.getOne(userId);
			}
		@Override
		public List<User> getAllUsers() {
			// TODO Auto-generated method stub
			return repo.findAll();
		}
		
		@Override
		public User getUserByName(String userName) {
			// TODO Auto-generated method stub
			return repo.getUserByName(userName);
			}
		
		public boolean assignTest(long userId, long testId) {
			// TODO Auto-generated method stub
			User user=repo.getOne(userId);
			if(user==null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
			user.setUserTest(testId);
			return true;
			
		}
		
		@Override
		public Test deleteTest(long testId) throws RestClientException, URISyntaxException {
			// TODO Auto-generated method stub
			Test test = rt.getForObject("http://localhost:8020/test/id/"+testId,Test.class);
			//test = rt.postForObject("http://localhost:8020/test/delete/id/", test, Test.class);
			rt.delete(new URI("http://localhost:8020/test/delete/id/"+testId));
			return test;
		}
		
		@Override
		public Test updateTest(Test test, long testId) throws RestClientException, URISyntaxException {
			// TODO Auto-generated method stub
			if(test!=null) {
				rt.put(new URI("http://localhost:8020/test/update/"+testId), test);
			}
			return test;
		}
		
		@Override
		public Test addTest(Test test) {
			// TODO Auto-generated method stub
			return rt.postForObject("http://localhost:8020/test/add", test, Test.class);
		}
		
		@Override
		public Question addQuestions(long testId, Question question) throws RestClientException, URISyntaxException {
			// TODO Auto-generated method stub
			Question q=rt.postForObject("http://localhost:8030/question/add", question, Question.class);
			rt.put(new URI("http://localhost:8020/test/assign/"+testId+"/question/"+q.getQuestionId()),null);
			return q;
		}
		
		@Override
		public Question updateQuestions(long testId, long questionId,Question question) throws RestClientException, URISyntaxException {
			
			Question updateQuestion = rt.getForObject("http://localhost:8030/question/id/"+questionId, Question.class);
			if(question.getQuestionId()==updateQuestion.getQuestionId()) {
				
			rt.put(new URI("http://localhost:8030/question/update/"+questionId),updateQuestion);
			rt.put(new URI("http://localhost:8020/test/assign/"+testId+"/question/"+questionId),null);
			}
			return question;
		}
		
		@Override
		public Question deleteQuestions(long testId, long questionId) throws RestClientException, URISyntaxException {
		
			Test test = rt.getForObject("http://localhost:8020/test/id/"+testId,Test.class);
			Question question = rt.getForObject("http://localhost:8030/question/id/"+questionId, Question.class);
			if(test!=null) {
				rt.delete(new URI("http://localhost:8030/question/delete/id/"+questionId));
				//test.getTestQuestions().remove(question.getQuestionId());
			}
			test.getTestQuestions().remove(questionId);
			rt.put(new URI("http://localhost:8020/test/update/"+testId), test);
			return question;
		}
		@Override
		public boolean validateAdmin(String userName, String userPassword) {

			boolean valid=false;
			User user=repo.getUserByName(userName);
			System.out.println(user);
			if(user.getUserPassword().contentEquals(userPassword) && user.isAdmin()) {
				valid=true;
			}
			return valid;
		}
		@Override
		public boolean validateUser(String userName, String userPassword) {

			boolean valid=false;
			User user=repo.getUserByName(userName);
			System.out.println(user);
			if(user.getUserPassword().contentEquals(userPassword) && !user.isAdmin()) {
				valid=true;
			}
			return valid;
		}
		@Override
		public double getResult(long testId) {
			Test test = rt.getForObject("http://localhost:8020/test/id/"+testId,Test.class);
			double result =0;
			result = rt.getForObject("http://localhost:8020/test/calculate/"+testId,Double.class);
			test.setTestMarksScored(result);
			return test.getTestMarksScored();
		}

		@Override
		public List<Question> getTestQuestions(long testId) {
			Test test = rt.getForObject("http://localhost:8020/test/id/"+testId, Test.class);
			List<Long> qIds = new ArrayList(test.getTestQuestions());
			List<Question> questions = new ArrayList<>();
			for(int i=0; i<qIds.size();i++) {
				Question q = rt.getForObject("http://localhost:8030/question/id/"+qIds.get(i), Question.class);
				questions.add(q);

			}
			return questions;
		}
	}