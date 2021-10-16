/*

* Assignment: Gradebook Project Part 1

* Name: Nicholas Clark

*/
package Project.gradebook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Project.exception.GradebookEmptyException;

public class Program implements AssignmentInterface{

	private double score;
	private char letter; 
	private String name;
	private LocalDate dueDate;
	private String type;
	private String concept;
	
	/* Parameters: 	grades - AssignmentInterface[] that holds all of the grades in the gradebook
	 * 				size - integer that contains the size of the current gradebook
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * This function prints all programming concepts in the gradebook
	 */
	public void printPConcepts(AssignmentInterface[] grades, int size) throws GradebookEmptyException {
		//Check if the gradebook is empty
		if (size == 0) {
			throw new GradebookEmptyException();  
		}
		String allConcepts = "";
		//Loop through all programs in the gradebook
		for(int i = 0; i < size; ++i) {
			if(grades[i].getType() == "Program") {
				Program temp = new Program();
				temp = (Program) grades[i];
				//Format string
				if(allConcepts.isEmpty()) {
					allConcepts = temp.getConcept();
				}
				else {
					allConcepts = allConcepts + ", " + temp.getConcept();
				}
			}
		}
		//Check to make sure there is program in the gradebook
		if(allConcepts.isEmpty()) {
			System.out.println("There are no programs in the gradebook\n");
		}
		else {
			System.out.println("All concepts: " + allConcepts + "\n");
		}
	}
	
	public String getConcept() {
		return concept;
	}
	
	public void setConcept(String concept) {
		this.concept = concept;
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
						+ getLetter() + ", Due Date: " + date +  ", Concepts: " + getConcept();
		return string;
	}
}
