package com.capg.otms.test.controller;

import java.time.LocalDateTime;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.capg.otms.test.model.Question;
import com.capg.otms.test.model.Test;
import com.capg.otms.test.service.TestService;


@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	 TestService service;
	
	@PostConstruct
	public void init() {
	Set<Long> questions=new HashSet<>();
	//questions.addAll(Arrays.asList(109L,101L,102L));
	Test test=new Test( "spring Test", LocalTime.of(1, 30), questions, 100, 0, 1L, LocalDateTime.of(2020, 05,2, 14, 0), LocalDateTime.of(2020, 05,2, 15, 30));
	service.addtest(test);
	}
	
	@GetMapping("/id/{testId}")
	public ResponseEntity<Test> getTest(@PathVariable long testId){
	return service.getTest(testId);	
	}
	@GetMapping("/all")
	public ResponseEntity<List<Test>> getAllTests(){
	return service.fetchAllTests();	
	}
	@PostMapping("/add")
	public ResponseEntity<Test> addTest(@RequestBody Test test){
		return service.addtest(test);
	}
	@PutMapping("/update/{testId}")
	public ResponseEntity<Test> updateTest(@RequestBody Test test,@PathVariable long testId){
		return service.updateTest(test ,testId);
	}	
	@PutMapping("/assign/{testId}/question/{questionId}")
	public ResponseEntity<Test> assignQuestion(@PathVariable long testId, @PathVariable long questionId) {
		return service.assignQuestion(testId, questionId);
	}
	
	@DeleteMapping("delete/id/{testId}")
	public ResponseEntity<Test> deleteTest(@PathVariable long testId){
	return service.deleteTest(testId);
	}
	@GetMapping("/calculate/{testId}")
	public ResponseEntity<Double> calculateTotalMarks(@PathVariable long testId){
		return service.calculateTotalMarks(testId);
	}
	@GetMapping("/question/{questionId}")
	public ResponseEntity<Question> fetchQuestion(@PathVariable long questionId){
		return service.fetchQuestion(questionId);
	}
	@PutMapping("/setTestQuestions/testId/{testId}")
	public ResponseEntity<Test> setTestQuestions(@PathVariable long testId, @RequestBody Set<Long> qIds){
		return service.setTestQuestions(testId, qIds);
	}
	@GetMapping("/questions/{testId}")
	public ResponseEntity<List<Question>> getTestQuestions(@PathVariable long testId){
		return service.getTestQuestions(testId);
	}
}