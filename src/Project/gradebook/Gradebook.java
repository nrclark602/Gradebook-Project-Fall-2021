/*

* Assignment: Gradebook Project Part 1

* Name: Nicholas Clark

*/
package Project.gradebook;

import java.util.InputMismatchException;
import java.util.Scanner;

import Project.exception.GradebookEmptyException;
import Project.exception.GradebookFullException;
import Project.exception.InvalidGradeException;
import Project.menu.MenuOptions;

public class Gradebook {
	public static void main(String[] args) {
		System.out.println("Please enter the maximum amount of grades in the gradebook (From 1-20)");
        Scanner input = new Scanner(System.in);
		int gradeCheck = 0,  maxGrade = 0;
		//Attempt to get user input for max num of grades
		//Throw exception if input is not an integer and loop until the integer is between 1-20
		while (gradeCheck == 0) {
			try {
				maxGrade = input.nextInt();
		    } catch (InputMismatchException e){
		    	input.nextLine();
		    }
	        if (maxGrade > 20 || maxGrade < 1) {
	        	System.out.println("Invalid number of grades selected. Please try again.");
	        	System.out.println("Please enter the maximum amount of grades in the gradebook (From 1-20)");
	        }
	        else {
	        	gradeCheck = 1;
	        }
		}
		System.out.println("\n");
		//Create new gradebook with the user input for max num of grades
        AssignmentInterface[] gradebook = new AssignmentInterface[maxGrade];
		int gradebookSize = 0, opt = 0;
		MenuOptions menu = new MenuOptions();
		//Loop until user wants to quit the program
		while (opt != 9) {
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
	            case 1: //Attempt to add a grade to gradebook and throw a GradebookFullException if the gradebook is full
	            		try {
	            			menu.addGrades(gradebook, gradebookSize, input);
	            			++gradebookSize;
	            		} catch(GradebookFullException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
	            			System.out.println("Gradebook is full. Please remove a grade or exit the application.\n");
	            		}
			            break;
	            case 2: //Attempt to remove the first instance of a grade from the gradebook and throw an InvalidGradeException if 
	            		//the inputed grade does not exist and a GradebookEmptyException if the gradebook is empty
	            		try {
		            		gradebook = menu.removeGrades(gradebook, gradebookSize, maxGrade, input);
		            		--gradebookSize;
	            		} catch (InvalidGradeException e) {
	            			System.out.println("The program ran into an error: " + e.toString());  
	            			System.out.println("The name of the grade that was entered is not in the gradebook. Please try again.\n");
	            		} catch (GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
	            			System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
	            		}
                		break;
	            case 3: //Attempt to print all grades in gradebook and throws a GradebookEmptyException if the gradebook is empty
	            		try {
	            			menu.printGrades(gradebook, gradebookSize);
	            		} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
	            			System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
	            		}
	            		break;
	            case 4: //Attempt to print average score and throws a GradebookEmptyException if the gradebook is empty
	            		try {
	            			menu.printAvgScore(gradebook, gradebookSize);
        				} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
        					System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
        				}
	            		break;
	            case 5: //Attempt to print the lowest and highest score and throws a GradebookEmptyException if the gradebook is empty
	            		try {
	            			menu.printMinMax(gradebook, gradebookSize);
						} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
        					System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
						}
	            		break;
	            case 6: //Attempt to print quiz question average and throws a GradebookEmptyException if the gradebook is empty
	            		try {
            				Quiz quiz = new Quiz();
            				quiz.printQQuesAvg(gradebook, gradebookSize);
						} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
							System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
						}
	            		break;
	            case 7: //Attempt to print all discussion readings and throws a GradebookEmptyException if the gradebook is empty
	            		try {
            				Discussion disc = new Discussion();
            				disc.printDReadings(gradebook, gradebookSize);
						} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
							System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
						}
	            		break;
	            case 8:	//Atempt to print all programming concepts and throws a GradebookEmptyException if the gradebook is empty
	            		try {
            				Program prog = new Program();
            				prog.printPConcepts(gradebook, gradebookSize);
						} catch(GradebookEmptyException e) {
	            			System.out.println("The program ran into an error: " + e.toString()); 
							System.out.println("The gradebook is empty. Please enter a grade and try again.\n");
						}
        				break;
	            case 9: //Exits the program
	            		System.out.println("Exiting the program...\n");
	            		break;
	            default: System.out.println("Please enter a valid number");
	            		 break;
	            }
		}
		input.close();
	}
}
