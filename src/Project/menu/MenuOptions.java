/*

* Assignment: Gradebook Project Part 1

* Name: Nicholas Clark

*/
package Project.menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Project.exception.GradebookEmptyException;
import Project.exception.GradebookFullException;
import Project.exception.InvalidGradeException;
import Project.gradebook.AssignmentInterface;
import Project.gradebook.Discussion;
import Project.gradebook.Program;
import Project.gradebook.Quiz;

public class MenuOptions {
	//This function prints the menu options to the console
	public void displayMenu() {
        System.out.println("COMMAND MENU - Please type the number that correspondes to the action you would like:");
        System.out.println("1. Add Grades                - Add grades to gradebook");
        System.out.println("2. Remove Grades             - Remove grades from gradebook");
        System.out.println("3. Print Grades              - Print all current grades in gradebook");
        System.out.println("4. Print Average             - Print average of all current grades in gradebook");
        System.out.println("5. Print Max/Min Score       - Print the highest and lowest scores in the gradebook");
        System.out.println("6. Print Quiz Ques Average   - Print average number of quiz questions");
        System.out.println("7. Print Discussion Readings - Print all associated discussion readings");
        System.out.println("8. Print Program Concepts    - Print all program concepts");
        System.out.println("9. Quit                      - Exit the program\n");
	}
	

	/* Parameters: 	grades - AssignmentInterface[] that holds all of the grades in the gradebook
	 * 				size - integer that contains the size of the current gradebook
	 * 				input - Scanner object to get input from the user
	 * Exception:	Throws a GradebookFullException if the gradebook is full		
	 * This function adds a new grade to the gradebook and asks the user for the grade's
	 * type, name, score, letter grade, and due date. Also includes type specific information
	 * depending on the inputed type (quiz, discussion, or program)
	 */
	public void addGrades(AssignmentInterface[] grades, int size, Scanner input) throws GradebookFullException  {
		//Throw exception if gradebook is full
		if (grades.length == size) {
			 throw new GradebookFullException();    
		}
		System.out.println("Type 1 if your assignemnt is a quiz, 2 if your assignment is a discussion, "
							+ "or 3 if your assignment is a program");
		int type = 0, typeCheck = 0;
		//Attempt to see if added grade is a quiz, discussion, or program and throw an exception if 
		//inputed number is not an integer or not between 1-3
		while(typeCheck == 0 ) {
			try {
				type = input.nextInt();
				if(type <= 3 && type >= 1) {
					typeCheck = 1;
					input.nextLine();
				}
				else {
					System.out.println("Invalid assignment selected. Please try again.");
					System.out.println("Type 1 if your assignemnt is a quiz, 2 if your assignment is a "
										+ "discussion, or 3 if your assignment is a program");
					input.nextLine();
				}
			} catch (InputMismatchException e){
				System.out.println("Invalid assignment selected. Please try again.");
				System.out.println("Type 1 if your assignemnt is a quiz, 2 if your assignment is a "
									+ "discussion, or 3 if your assignment is a program");
				input.nextLine();
			}
		}
		System.out.println("Please enter the grade's 1. Name, 2. Score, 3. Letter, 4. Due Date\n");
		System.out.println("Name: ");
		String name = input.nextLine();
		System.out.println("Score: ");
		double score = 0, doubCheck = 0;
		//Atempt to get grade's score and throw an exception if inputed number is not an double
		while (doubCheck == 0) {
			try {
				score = input.nextDouble();
				//Check if score is postive (can be greater than 100 due to extra credit)
				if (score < 0) {
		    		System.out.println("The number you entered is an invalid score (cannot be negative). Please try again." );
		    		System.out.println("Score: ");
		 			input.nextLine();
				}
				else {
					doubCheck = 1;
				}
		    } catch (InputMismatchException e){
		    		System.out.println("The number you entered is an invalid score. Please try again." );
		    		System.out.println("Score: ");
		 			input.nextLine();
		    }
		}
		input.nextLine();
		System.out.println("Letter: (A-F)");
		char letter = 0;
		int charCheck = 0;
		//Attempt to get a grade's letter grade and continues until inputed number is between A-F
		while (charCheck == 0) {
			letter = input.next().charAt(0);
			if (letter >= 'A' && letter <= 'F') {
				charCheck = 1;
			}
			else {
				System.out.println("The letter you entered is not in the grading range of A-F. Please try again.");
	    		System.out.println("Letter: (A-F)");
	 			input.nextLine();
			}
		}
		input.nextLine();
		System.out.println("Due Date: (MM/dd/yyyy)");
		//Set format of the due date
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    int dateCheck = 0;
	    LocalDate date = null;
	    //Attempt to get due date and throw an exception if inputed due date is in the wrong format
	    while(dateCheck == 0) {
	    	try {
	    	    String stringDate = input.nextLine();
	    		date = LocalDate.parse(stringDate, dtf);
	    		dateCheck = 1;
	    	}catch (DateTimeParseException e) {
	    		System.out.println("Incorrect Date format. Please try again");
	    		System.out.println("Due Date: (MM/dd/yyyy)");
	    	}
	    }
	    //Determine if the added grade is a quiz, discussion, or program and set variables for each
		if(type == 1) {
			Quiz quiz = new Quiz();
			quiz.setName(name);
			quiz.setScore(score);
			quiz.setLetter(letter);
		    quiz.setDueDate(date);
		    quiz.setType("Quiz");
			System.out.println("Please enter the number of quiz questions");
		    int quizCheck = 0, numQuiz = 0; 
		    //Attempt to get number of quiz questions and throws an exception if an integer is not inputed
			while(quizCheck == 0) {
				try {
					numQuiz = input.nextInt();
					if (numQuiz < 0) {
			    		System.out.println("Invalid quiz questioned entered (cannot be negative). Please try again." );
						System.out.println("Please enter the number of quiz questions");
			 			input.nextLine();
					}
					else {
				        quizCheck = 1;
					}
				} catch (InputMismatchException e){
					System.out.println("Invalid quiz questioned entered. Please try again.");
					System.out.println("Please enter the number of quiz questions");
					input.nextLine();
				}
			}
			quiz.setNumOfQues(numQuiz);
			//Add quiz grade to gradebook
			grades[size] = quiz;
		}
		else if (type == 2) {
			Discussion disc = new Discussion();
			disc.setName(name);
			disc.setScore(score);
			disc.setLetter(letter);
		    disc.setDueDate(date);
		    disc.setType("Discussion");
		    //Get discussion reading from user
			System.out.println("Please enter the reading associated with the discussion");
			String reading = input.nextLine();
			disc.setReading(reading);
			//Add discussion grade to gradebook
			grades[size] = disc;
		}
		else {
			Program prog = new Program();
			prog.setName(name);
			prog.setScore(score);
			prog.setLetter(letter);
		    prog.setDueDate(date);
		    prog.setType("Program");
		    //Get programming concept from user
			System.out.println("Please enter the concept the program is testing you on");
			String concept = input.nextLine();
			prog.setConcept(concept);
			//Add program grade to gradebook
			grades[size] = prog;
		}
	}
	
	/* Parameters: 	grades - AssignmentInterface[] that holds all of the grades in the gradebook
	 * 				size - integer that contains the size of the current gradebook
	 * 				maxLength - integer that contains the max length of the gradebook
	 * 	 			input - Scanner object to get input from the user
	 * Return:		AssignmentInterface array with removed grade
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * 				Throws a InvalidGradeException if the inputed name does not exist in the gradebook
	 * This function removes the first instance of a grade from the grade book based on an
	 * inputed name by the user
	 */
	public AssignmentInterface[] removeGrades(AssignmentInterface[] grades, int size, int maxLength, Scanner input) throws InvalidGradeException, GradebookEmptyException {
		//Checks if the gradebook is empty
		if (size == 0) {
			throw new GradebookEmptyException();  
		}
		System.out.println("Please type the name of the assignment you want to remove");
		//Get name of grade to be removed
		String remove = input.nextLine();
		AssignmentInterface[] temp = new AssignmentInterface[maxLength];
		Boolean removeCheck = false;
		//Loop through gradebook and copy current gradebook into temp
		for(int i = 0, j = 0; i < size; ++i) {
			//If the name of a grade does not equal the inputed grade, copy to temp
			if(!grades[i].getName().equals(remove)) {
		            temp[j++] = grades[i];  
			}
			else {
				//If the inputed name is the same as a grade, do not copy it to temp
				if (!removeCheck) {
					removeCheck = true;
				}
				//If the inputed name is the same as a grade, but it is not the first
				//instance, continue copying
				else {
					temp[j++] = grades[i];
				}
			}
		}
		//Check if inputed name is not in the gradebook
		if(!removeCheck) {
			 throw new InvalidGradeException();    
		}
		return temp;
	}
	
	/* Parameters: 	grades - AssignmentInterface[] that holds all of the grades in the gradebook
	 * 				size - integer that contains the size of the current gradebook
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * This function print all the grades in the gradebook
	 */
	public void printGrades(AssignmentInterface[] grades, int size) throws GradebookEmptyException {
		//Checks if the gradebook is empty
		if (size == 0) {
			throw new GradebookEmptyException();  
		}
		System.out.println("All assignemnts in the gradebook:");
		for (int i = 0; i < size; i++) {
			System.out.println(grades[i].toString());
		}
		System.out.println("\n");
	}
	
	/* Parameters: 	grades - AssignmentInterface[] that holds all of the grades in the gradebook
	 * 				size - integer that contains the size of the current gradebook
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * This function prints the average score of all the grades
	 */
	public void printAvgScore(AssignmentInterface[] grades, int size) throws GradebookEmptyException {
		//Checks if the gradebook is empty
		if (size == 0) {
			throw new GradebookEmptyException();  
		}
		double total = 0;
		//Get total combined score
		for(int i = 0; i < size; i++) {
			total = total + grades[i].getScore();
		}
		double avg = total/((double) size);
		System.out.println("Average Score: " + avg + "\n");
	}
	
	/* Parameters: 	grades - AssignmentInterface[] that holds all of the grades in the gradebook
	 * 				size - integer that contains the size of the current gradebook
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * This function prints the lowest and highest score in the gradebook. If there is only one grade, the lowest 
	 * and highest grade are the same.
	 */
	public void printMinMax(AssignmentInterface[] grades, int size) throws GradebookEmptyException {
		//Checks if the gradebook is empty
		if (size == 0) {
			throw new GradebookEmptyException();  
		}
		//Lowest and highest score are the same
		if(size == 1) {
			System.out.println("Lowest Grade: " + grades[0].getScore() + ", Highest Grade: " + grades[0].getScore() + "\n");
		}
		else {
			double min = 100, max = 0;
			//Loop to find max and min score
			for(int i = 0; i < size; ++i) {
				if(grades[i].getScore() < min) {
					min = grades[i].getScore();
				}
				if(grades[i].getScore() > max) {
					max = grades[i].getScore();
				}
			}
			System.out.println("Lowest Grade: " + min + ", Highest Grade: " + max + "\n");
		}
	}
}
