package com.capg.otms.test.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.capg.otms.test.exception.TestNotFoundException;
import com.capg.otms.test.model.Test;
import com.capg.otms.test.repository.ITestJpaRepo;

@Service
public class TestService implements ITestService{
	
	@Autowired(required = true)
	ITestJpaRepo testRepo;
	
	double score;
	
	@Override
	public List<Test> fetchAllTests(){	
		return testRepo.findAll();
	}
	
	@Override
	public Test getTest(long testId) {
		
		if(!testRepo.existsById(testId)) {
			throw new TestNotFoundException("Test with id : ["+testId+"] Not Found"); 
		}
		return testRepo.getOne(testId);
	}

  @Override
 @Transactional
  public Test addtest (Test test) {
	  System.out.println(test);
	  if(testRepo.existsById(test.getTestId())) {
		 throw new RuntimeException("Test Already Exits");
	  }
	 return testRepo.save(test);
  }	
  
	@Override
	@Transactional
	public Test deleteTest(long testId) {
		Test deletedTest=testRepo.getOne(testId);
		testRepo.deleteById(testId);
		return deletedTest;
	}
	
	@Transactional
	public Test updateTest(Test newTestData) {
		Test test=testRepo.getOne(newTestData.getTestId());		
		test.setTestTitle(newTestData.getTestTitle());
		test.setTestDuration(newTestData.getTestDuration());
		test.setTestQuestions(newTestData.getTestQuestions());
		test.setTestTotalMarks(newTestData.getTestTotalMarks());
		test.setTestMarksScored(newTestData.getTestMarksScored());
		test.setCurrentQuestion(newTestData.getCurrentQuestion());
		test.setStartTime(newTestData.getStartTime());
		test.setEndTime(newTestData.getEndTime());
		testRepo.save(test);
		return test;
}
	
	
}