package com.capg.otms.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.capg.otms.test.exception.TestNotFoundException;
import com.capg.otms.test.model.Question;
import com.capg.otms.test.model.Test;
import com.capg.otms.test.repository.ITestJpaRepo;

@Service
public class TestService implements ITestService{
	
	@Autowired(required = true)
	ITestJpaRepo testRepo;
	
	@Autowired
	RestTemplate rt;
	
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

	@Override
	public double calculateTotalMarks(long testId) {
		// TODO Auto-generated method stub
		double score=0;
		Test test = testRepo.getOne(testId);
		List<Long> qIds = new ArrayList(test.getTestQuestions());
		for(int i=0; i<qIds.size();i++) {
			Question q = rt.getForObject("http://localhost:8030/question/id/"+qIds.get(i), Question.class);
			score = score + q.getMarksScored();
		}
		return score;
	}
	public Question fetchQuestion(long questionId) {
		Question question = rt.getForObject("http://localhost:8030/question/id/"+questionId, Question.class);
		return question;
	}
	@Override
	public List<Question> getTestQuestions(long testId) {
		// TODO Auto-generated method stub
		Test test = testRepo.getOne(testId);
		List<Long> qIds = new ArrayList(test.getTestQuestions());
		List<Question> questions = new ArrayList<>();
		for(int i=0; i<qIds.size();i++) {
			Question q = rt.getForObject("http://localhost:8030/question/id/"+qIds.get(i), Question.class);
			try {
			questions.add(q);
			}
			catch(Exception e) {
				System.err.println("Question unavailable to add with id:"+qIds.indexOf(q));
			}
			//score = score + q.getMarksScored();
		}
		return questions;
	}
	@Override
	public Test setTestQuestions(long testId, Set<Long> qIds) {
		Test test = testRepo.getOne(testId);
		test.setTestQuestions(qIds);
		testRepo.save(test);
		return test;
	}
}