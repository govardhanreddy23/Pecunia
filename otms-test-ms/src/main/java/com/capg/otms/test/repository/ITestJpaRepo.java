package com.capg.otms.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capg.otms.test.model.Test;

@Repository
public interface ITestJpaRepo extends JpaRepository<Test, Long>{
	
}