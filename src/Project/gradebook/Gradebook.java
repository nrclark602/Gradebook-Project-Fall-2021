/*

* Assignment: Gradebook Project Part 2

* Name: Nicholas Clark

*/
package Project.gradebook;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Project.exception.GradebookEmptyException;
import Project.exception.InvalidGradeException;
import Project.menu.MenuOptions;

public class Gradebook {
	public static void main(String[] args) throws FileNotFoundException, ParseException {
        Scanner input = new Scanner(System.in);
		//Create new gradebook
        ArrayList<AssignmentInterface> gradebook = new ArrayList<AssignmentInterface>();
		int opt = 0;
		MenuOptions menu = new MenuOptions();
		//Loop until user wants to quit the program
		while (opt != 14) {
			menu.displayMenu();
			int optCheck = 0;
			//Attempt to get menu option from user and throw exception if it is not an integer
			while(optCheck == 0) {
				try {
			        opt = input.nextInt();
			        optCheck = 1;
				} catch (InputMismatchException e){
					System.out.println("Invalid action selected. Please try again.\n");
					menu.displayMenu();
					input.nextLine();
				} 
			}
			input.nextLine();
	        switch (opt) {
	            case 1: //Add a grade to gradebook
	            		menu.addGrades(gradebook, input);
			            break;
	            case 2: //Attempt to remove the first instance of a grade from the gradebook and throw an InvalidGradeException if 
	            		//the inputed grade does not exist and a GradebookEmptyException if the gradebook is empty
	            		try {
	            			System.out.println("Please type the name of the assignment you want to remove");
	            			//Get name of grade to be removed
	            			String remove = input.nextLine();
		            		gradebook = menu.removeGrades(gradebook, input, remove);
	            		} catch (InvalidGradeException e) {
	            			System.out.println("The program ran into an error: " + e.toString());  
	            			System.out.println("The name of the grade that was entered is not in the gradebook. Please try again.\n");
	            		} catch (GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
	            			System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
	            		}
                		break;
	            case 3: //Attempt to print all grades in gradebook and throws a GradebookEmptyException if the gradebook is empty
	            		//Sort by score, letter, name, or duedate
	            		try {
	            			menu.printGrades(gradebook, input);
	            		} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
	            			System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
	            		}
	            		break;
	            case 4: //Attempt to print average score and throws a GradebookEmptyException if the gradebook is empty
	            		try {
	            			menu.printAvgScore(gradebook);
        				} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
        					System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
        				}
	            		break;
	            case 5: //Attempt to print the lowest and highest score and throws a GradebookEmptyException if the gradebook is empty
	            		try {
	            			menu.printMinMax(gradebook);
						} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
        					System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
						}
	            		break;
	            case 6: //Attempt to print quiz question average and throws a GradebookEmptyException if the gradebook is empty
	            		try {
            				Quiz quiz = new Quiz();
            				quiz.printQQuesAvg(gradebook);
						} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
							System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
						}
	            		break;
	            case 7: //Attempt to print all discussion readings and throws a GradebookEmptyException if the gradebook is empty
	            		try {
            				Discussion disc = new Discussion();
            				disc.printDReadings(gradebook);
						} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
							System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
						}
	            		break;
	            case 8:	//Attempt to print all programming concepts and throws a GradebookEmptyException if the gradebook is empty
	            		try {
            				Program prog = new Program();
            				prog.printPConcepts(gradebook);
						} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
							System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
						}
        				break;
	            case 9:	//Attempt to print current gradebook to new file and throws a GradebookEmptyException if the gradebook is empty
	            		try {
	            			menu.printToFile(gradebook);
	            		} catch (GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
	            			System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
	            		}
	            		break;
	            case 10://Read grades from file
            			gradebook = menu.readFromFile(gradebook, input);
            			break;
	            case 11://Attempt to send gradebook to mySQL and throws a GradebookEmptyException if the gradebook is empty
	            		try {
	            			menu.toMySQL(gradebook, input);
	            		} catch (GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
	            			System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
	            		}
	            		break;
	            case 12://Read grades from mySQL
	            		gradebook = menu.fromSQL(gradebook, input);
	            		break;
	            case 13://Search database for certain grades
						menu.mySQLSearch(gradebook, input);
	            		break;
	            case 14: //Exits the program
	            		System.out.println("Exiting the program...\n");
	            		break;
	            default: System.out.println("Please enter a valid number");
	            		 break;
	            }
		}
		input.close();
	}
}
