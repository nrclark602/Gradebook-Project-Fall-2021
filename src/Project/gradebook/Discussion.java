/*

* Assignment: Gradebook Project Part 2

* Name: Nicholas Clark

*/
package Project.gradebook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Project.exception.GradebookEmptyException;

public class Discussion implements AssignmentInterface{

	private double score;
	private char letter; 
	private String name;
	private LocalDate dueDate;
	private String type;
	private String reading;
	
	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * This function prints all of the readings for each discussion
	 */
	public void printDReadings(ArrayList<AssignmentInterface> grades) throws GradebookEmptyException {
		//Check if the gradebook is empty
		if (grades.isEmpty()) {
			throw new GradebookEmptyException();  
		}
		String allReadings = "";
		//Loop through all discussions to get readings
		for(AssignmentInterface a : grades) {
			if(a.getType().equals("Discussion")) {
				Discussion temp = new Discussion();
				temp = (Discussion) a;
				//Format string
				if(allReadings.isEmpty()) {
					allReadings = temp.getReading();
				}
				else {
					allReadings = allReadings + ", " + temp.getReading();
				}
			}
		}
		//Check to make sure there is a reading in the gradebook
		if(allReadings.isEmpty()) {
			System.out.println("There are no readings in the gradebook\n");
		}
		else {
			System.out.println("All associated readings: " + allReadings + "\n");
		}
	}
	
	public String getReading() {
		return reading;
	}
	
	public void setReading(String reading) {
		this.reading = reading;
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
						+ getLetter() + ", Due Date: " + date +  ", Readings: " + getReading();
		return string;
	}
}
