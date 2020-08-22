package com.capg.otms.user.service;

	import java.net.URISyntaxException;
	import java.util.List;

	import org.springframework.web.client.RestClientException;

	import com.capg.otms.user.model.Question;
	import com.capg.otms.user.model.Test;
	import com.capg.otms.user.model.User;

	public interface IUserService {
		
		User addUser(User user);
		User deleteUser(long userId);
		User updateUser(User user);
		User getUser(long userId);
		List<User> getAllUsers();
		User getUserByName(String userName);
		Test deleteTest(long testId) throws RestClientException, URISyntaxException;
		Test updateTest(Test test) throws RestClientException, URISyntaxException;
		Test addTest(Test test);
		Question addQuestions(long testId, Question question) throws RestClientException, URISyntaxException;
		Question deleteQuestions(long testId, long QuestionId) throws RestClientException, URISyntaxException;
		Question updateQuestions(long testId, long questionId, Question question)
				throws RestClientException, URISyntaxException;
		
	}



