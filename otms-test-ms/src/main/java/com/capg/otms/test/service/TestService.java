package com.capg.otms.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.capg.otms.test.exception.TestNotFoundException;
import com.capg.otms.test.model.Question;
import com.capg.otms.test.model.TestBean;
import com.capg.otms.test.repository.ITestJpaRepo;

@Service
public class TestService implements ITestService{
	
	@Autowired(required = true)
	ITestJpaRepo testRepo;
	
	@Autowired
	RestTemplate rt;
	
	double score;
	
	@Override
	public ResponseEntity<List<TestBean>> fetchAllTests(){	
		return new ResponseEntity<>(testRepo.findAll(),HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<TestBean> getTest(long testId) {
		
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(testRepo.getOne(testId),HttpStatus.OK);
	}

  @Override
 @Transactional
  public ResponseEntity<TestBean> addtest (TestBean testBean) {
	  return new ResponseEntity<>(testRepo.save(testBean),HttpStatus.OK);
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
	public ResponseEntity<TestBean> updateTest(TestBean newTestData,long testId) {
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		TestBean testBean=testRepo.getOne(testId);		
		testBean.setTestTitle(newTestData.getTestTitle());
		testBean.setTestDuration(newTestData.getTestDuration());
		testBean.setTestQuestions(newTestData.getTestQuestions());
		testBean.setTestTotalMarks(newTestData.getTestTotalMarks());
		testBean.setTestMarksScored(newTestData.getTestMarksScored());
		testBean.setCurrentQuestion(newTestData.getCurrentQuestion());
		testBean.setStartTime(newTestData.getStartTime());
		testBean.setEndTime(newTestData.getEndTime());
		return new ResponseEntity<>(testRepo.save(testBean),HttpStatus.OK);
}

	@Override
	public ResponseEntity<Double> calculateTotalMarks(long testId) {
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		double score=0;
		TestBean testBean = testRepo.getOne(testId);
		List<Long> qIds = new ArrayList(testBean.getTestQuestions());
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
		//System.out.println(question);
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
		TestBean testBean = testRepo.getOne(testId);
		List<Long> qIds = new ArrayList(testBean.getTestQuestions());
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
	public ResponseEntity<TestBean> setTestQuestions(long testId, Set<Long> qIds) {
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
		TestBean testBean = testRepo.getOne(testId);
		List<Long> qIds2 = new ArrayList(qIds);
		for(int i=0; i<qIds2.size();i++) {
			Question q = rt.getForObject("http://localhost:8030/question/id/"+qIds2.get(i), Question.class);
			if(q==null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		testBean.setTestQuestions(qIds);
		
		return new ResponseEntity<>(testRepo.save(testBean),HttpStatus.OK);
		}catch(HttpClientErrorException e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
public ResponseEntity<TestBean> assignQuestion(long testId, long questionId) {
		if(!testRepo.existsById(testId)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		TestBean testBean = testRepo.getOne(testId);
		testBean.getTestQuestions().add(questionId);
		 return new ResponseEntity<>(testRepo.save(testBean),HttpStatus.OK);
	}
}