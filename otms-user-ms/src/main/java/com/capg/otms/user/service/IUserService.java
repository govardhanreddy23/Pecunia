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
		
		User getUserByName(String userName);
		
		
		}



