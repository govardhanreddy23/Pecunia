package com.capg.otms.test.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;

import com.capg.otms.test.model.Question;
import com.capg.otms.test.model.Test;
public interface ITestService{
	
		public ResponseEntity<Test> addtest(Test test);
		public ResponseEntity<Test> updateTest(Test test,long testId);
		public ResponseEntity deleteTest(long testId);
		public ResponseEntity<Test>  getTest(long testId);
		public ResponseEntity<List<Test>> fetchAllTests();
		ResponseEntity<Double> calculateTotalMarks(long testId);
		ResponseEntity<List<Question>> getTestQuestions(long testId);
		ResponseEntity<Test> setTestQuestions(long testId, Set<Long> qIds);
		ResponseEntity<Question> fetchQuestion(long questionId);
		public ResponseEntity<Test> assignQuestion(long testId, long questionId);
	}