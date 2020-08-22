package com.capg.otms.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
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
	public ResponseEntity<List<Test>> fetchAllTests(){	
		return new ResponseEntity<>(testRepo.findAll(),HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Test> getTest(long testId) {
		
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(testRepo.getOne(testId),HttpStatus.OK);
	}

  @Override
 @Transactional
  public ResponseEntity<Test> addtest (Test test) {
	  return new ResponseEntity<>(testRepo.save(test),HttpStatus.OK);
  }	
  
	@Override
	@Transactional
	public ResponseEntity deleteTest(long testId) {
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		testRepo.deleteById(testId);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@Override
	@Transactional
	public ResponseEntity<Test> updateTest(Test newTestData,long testId) {
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Test test=testRepo.getOne(testId);		
		test.setTestTitle(newTestData.getTestTitle());
		test.setTestDuration(newTestData.getTestDuration());
		test.setTestQuestions(newTestData.getTestQuestions());
		test.setTestTotalMarks(newTestData.getTestTotalMarks());
		test.setTestMarksScored(newTestData.getTestMarksScored());
		test.setCurrentQuestion(newTestData.getCurrentQuestion());
		test.setStartTime(newTestData.getStartTime());
		test.setEndTime(newTestData.getEndTime());
		return new ResponseEntity<>(testRepo.save(test),HttpStatus.OK);
}

	@Override
	public ResponseEntity<Double> calculateTotalMarks(long testId) {
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		double score=0;
		Test test = testRepo.getOne(testId);
		List<Long> qIds = new ArrayList(test.getTestQuestions());
		for(int i=0; i<qIds.size();i++) {
			Question q = rt.getForObject("http://localhost:8030/question/id/"+qIds.get(i), Question.class);
			if(q==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			else {
			score = score + q.getMarksScored();
		}}
		return new ResponseEntity<>(score,HttpStatus.OK);
	}
	@Override
	public ResponseEntity<Question> fetchQuestion(long questionId) {
		try {
		Question question = rt.getForObject("http://localhost:8030/question/id/"+questionId, Question.class);
		if(question==null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(question,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	@Override
	public ResponseEntity<List<Question>> getTestQuestions(long testId) {
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Test test = testRepo.getOne(testId);
		List<Long> qIds = new ArrayList(test.getTestQuestions());
		List<Question> questions = new ArrayList<>();
		for(int i=0; i<qIds.size();i++) {
			Question q = rt.getForObject("http://localhost:8030/question/id/"+qIds.get(i), Question.class);
			if(q==null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
			else {
				questions.add(q);
			}
		}
		return new ResponseEntity<>(questions,HttpStatus.OK);
	}
	@Override
	public ResponseEntity<Test> setTestQuestions(long testId, Set<Long> qIds) {
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
		Test test = testRepo.getOne(testId);
		List<Long> qIds2 = new ArrayList(qIds);
		for(int i=0; i<qIds2.size();i++) {
			Question q = rt.getForObject("http://localhost:8030/question/id/"+qIds2.get(i), Question.class);
			if(q==null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		test.setTestQuestions(qIds);
		
		return new ResponseEntity<>(testRepo.save(test),HttpStatus.OK);
		}catch(HttpClientErrorException e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
public ResponseEntity<Test> assignQuestion(long testId, long questionId) {
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Test test = testRepo.getOne(testId);
		test.getTestQuestions().add(questionId);
		 return new ResponseEntity<>(testRepo.save(test),HttpStatus.OK);
	}
}