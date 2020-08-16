package com.capg.otms.user.model;

    import java.util.List;

	import javax.persistence.ElementCollection;
	import javax.persistence.Entity;
	import javax.persistence.Id;

	@Entity
	public class Question {
		@Id
		private long questionId;
		@ElementCollection
		private List<String> questionOptions;
		private String questionTitle;
		private int questionAnswer;
		private double questionMarks;
		private int choosenAnswer;
		private double marksScored;
		
		public Question() {
			// TODO Auto-generated constructor stub
		}

		public Question(long questionId, List<String> questionOptions, String questionTitle, int questionAnswer,
				double questionMarks, int choosenAnswer, double marksScored) {
			super();
			this.questionId = questionId;
			this.questionOptions = questionOptions;
			this.questionTitle = questionTitle;
			this.questionAnswer = questionAnswer;
			this.questionMarks = questionMarks;
			this.choosenAnswer = choosenAnswer;
			this.marksScored = marksScored;
		}

		public long getQuestionId() {
			return questionId;
		}

		public void setQuestionId(long questionId) {
			this.questionId = questionId;
		}

		public List<String> getQuestionOptions() {
			return questionOptions;
		}

		public void setQuestionOptions(List<String> questionOptions) {
			this.questionOptions = questionOptions;
		}

		public String getQuestionTitle() {
			return questionTitle;
		}

		public void setQuestionTitle(String questionTitle) {
			this.questionTitle = questionTitle;
		}

		public int getQuestionAnswer() {
			return questionAnswer;
		}

		public void setQuestionAnswer(int questionAnswer) {
			this.questionAnswer = questionAnswer;
		}

		public double getQuestionMarks() {
			return questionMarks;
		}

		public void setQuestionMarks(double questionMarks) {
			this.questionMarks = questionMarks;
		}

		public int getChoosenAnswer() {
			return choosenAnswer;
		}

		public void setChoosenAnswer(int choosenAnswer) {
			this.choosenAnswer = choosenAnswer;
		}

		public double getMarksScored() {
			return marksScored;
		}

		public void setMarksScored(double marksScored) {
			this.marksScored = marksScored;
		}

		@Override
		public String toString() {
			return "Question [questionId=" + questionId + ", questionOptions=" + questionOptions + ", questionTitle="
					+ questionTitle + ", questionAnswer=" + questionAnswer + ", questionMarks=" + questionMarks
					+ ", choosenAnswer=" + choosenAnswer + ", marksScored=" + marksScored + "]";
		}
		
		
	}



