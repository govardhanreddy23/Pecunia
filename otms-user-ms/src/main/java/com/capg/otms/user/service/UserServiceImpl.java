package com.capg.otms.user.service;


	import java.net.URI;
	import java.net.URISyntaxException;
	import java.util.List;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
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
			return repo.getByUserName(userName);
			}

	}



