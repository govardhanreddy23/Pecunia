package com.capg.otms.user.repo;


	import org.springframework.data.jpa.repository.JpaRepository;

	import com.capg.otms.user.model.User;

	public interface UserRepo extends JpaRepository<User, Long> {
		
		User getByUserName(String userName);
	
	}
