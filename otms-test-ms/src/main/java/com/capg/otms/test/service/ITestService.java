package com.capg.otms.test.service;

import java.util.List;
import java.util.Set;

import com.capg.otms.test.model.Question;
import com.capg.otms.test.model.Test;
public interface ITestService{
	
		public Test addtest(Test test);
		public Test updateTest(Test test);
		public Test deleteTest(long testId);
		public Test getTest(long testId);
		public List<Test> fetchAllTests();
		double calculateTotalMarks(long testId);
		List<Question> getTestQuestions(long testId);
		Test setTestQuestions(long testId, Set<Long> qIds);
	}