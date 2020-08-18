package com.capg.otms.question.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capg.otms.question.exception.QuestionNotFoundException;
import com.capg.otms.question.model.Question;
import com.capg.otms.question.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements IQuestionService {
	@Autowired(required = true)
	QuestionRepository questionRepo;
	
	@Override
	public List<Question> getListOfQuestions() {
		// TODO Auto-generated method stub
		return questionRepo.findAll();
	}
	
	@Override
	public Question getQuestionById(long QuestionId) {
		if(!questionRepo.existsById(QuestionId)) {
			throw new QuestionNotFoundException("Question with id : ["+QuestionId+"] Not Found"); 
		}
		return questionRepo.getOne(QuestionId);
	}
	
	@Override
	@Transactional
	public Question addQuestion(Question question) {
		return questionRepo.save(question);
	}
	
	@Override
	@Transactional
	public Question deleteQuestion(long questionId) {
		Question deletedQuestion = questionRepo.getOne(questionId);
		//return !questionRepo.existsById(questionId);
		questionRepo.deleteById(questionId);
		return deletedQuestion;
	}
	
	@Override
	@Transactional
	public Question updateOption(Question newQuestion,long questionId) {
		Question question=questionRepo.getOne(questionId);
		if(question!=null) {
			question.setChosenAnswer(newQuestion.getChosenAnswer());
		}
		questionRepo.save(question);
		return question;
	}
	@Override
	@Transactional
	public Question updateQuestion(Question newQuestion,long questionId) {
		Question question=questionRepo.getOne(questionId);
		if(question!=null) {
			question.setQuestionTitle(newQuestion.getQuestionTitle());
			question.setQuestionOptions(newQuestion.getQuestionOptions());
			question.setQuestionMarks(newQuestion.getQuestionMarks());
			question.setQuestionAnswer(newQuestion.getQuestionAnswer());
		}
		questionRepo.save(question);
		return question;
	}
}