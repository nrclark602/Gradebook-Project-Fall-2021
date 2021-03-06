/*

* Assignment: Gradebook Project Part 2

* Name: Nicholas Clark

*/
package Project.gradebook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Project.exception.GradebookEmptyException;

public class Quiz implements AssignmentInterface{

	private double score;
	private char letter; 
	private String name;
	private LocalDate dueDate;
	private String type;
	private int numOfQues;
	
	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * This function prints the average quiz questions in the gradebook
	 */
	public void printQQuesAvg(ArrayList<AssignmentInterface> grades) throws GradebookEmptyException {
		//Checks if the gradebook is empty
		if (grades.isEmpty()) {
			throw new GradebookEmptyException();  
		}
		//Get total number of quiz questions
		int totalQues = 0, numOfQuiz = 0;
		for(AssignmentInterface a : grades) {
			if(a.getType().equals("Quiz")) {
				Quiz temp = new Quiz();
				temp = (Quiz) a;
				totalQues = totalQues + temp.getNumOfQues();
				++numOfQuiz;
			}
		}
		//Check to make sure there is a quiz in the gradebook
		if(numOfQuiz == 0) {
			System.out.println("There are no quizes in the gradebook.\n");
		}
		else {
			double avg = (double)totalQues/(double)numOfQuiz;
			System.out.println("Average quiz questions: " + avg + "\n");
		}
	}
	public int getNumOfQues() {
		return numOfQues;
	}
	
	public void setNumOfQues(int questions) {
		this.numOfQues = questions;
	}
	
	@Override
	public double getScore() {
		return score;
	}

	@Override
	public char getLetter() {
		return letter;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public LocalDate getDueDate() {
		return dueDate;
	}

	@Override
	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public void setLetter(char letter) {
		this.letter = letter;
		
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
		
	}
	
	@Override
	public String getType() {
		return type;
	}
	@Override
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String date =  dtf.format(getDueDate());
		String string = "Type: " + getType() + ", Name: " + getName() + ", Score: " + getScore() + ", Letter: " 
	                    + getLetter() + ", Due Date: " + date +  ", Number of Questions: " + getNumOfQues();
		return string;
	}
}
