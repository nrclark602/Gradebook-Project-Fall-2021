/*

* Assignment: Gradebook Project Part 2

* Name: Nicholas Clark

*/
package Project.menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import Project.exception.GradebookEmptyException;
import Project.exception.InvalidGradeException;
import Project.gradebook.AssignmentInterface;
import Project.gradebook.Discussion;
import Project.gradebook.Program;
import Project.gradebook.Quiz;
import Project.DB.*;

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
        System.out.println("9. Print to File             - Print current gradebook to file");
        System.out.println("10. Read from File           - Read all gradebook grades from file");
        System.out.println("11. To MySQL                 - Send grades in gradebook to MySQL");
        System.out.println("12. From MySQL               - Check if all grades from the database are in current gradebook");
        System.out.println("13. Search MySQL             - Search database for specific grades");
        System.out.println("14. Quit                     - Exit the program\n");
	}
	

	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * 				input - Scanner object to get input from the user	
	 * This function adds a new grade to the gradebook and asks the user for the grade's
	 * type, name, score, letter grade, and due date. Also includes type specific information
	 * depending on the inputed type (quiz, discussion, or program)
	 */
	public void addGrades(ArrayList<AssignmentInterface> grades, Scanner input) {
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
		//Attempt to get a grade's letter grade and continues until inputed number is between A-F or a-f
		while (charCheck == 0) {
			letter = input.next().charAt(0);
			if ((letter >= 'A' && letter <= 'F') || (letter >= 'a' && letter <= 'f')) {
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
			grades.add(quiz);
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
			grades.add(disc);
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
			grades.add(prog);
		}
	}
	
	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * 	 			input - Scanner object to get input from the user
	 * Return:		AssignmentInterface arraylist with removed grade
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * 				Throws a InvalidGradeException if the inputed name does not exist in the gradebook
	 * This function removes the first instance of a grade from the grade book based on an
	 * inputed name by the user
	 */
	public ArrayList<AssignmentInterface> removeGrades(ArrayList<AssignmentInterface> grades, Scanner input, String remove) throws InvalidGradeException, GradebookEmptyException {
		//Checks if the gradebook is empty
		if (grades.isEmpty()) {
			throw new GradebookEmptyException();  
		}
		Boolean removeCheck = false;
		//Iterate through gradebook 
		Iterator<AssignmentInterface> iterator = grades.iterator();
		while (iterator.hasNext()) {
		    AssignmentInterface a = iterator.next();
		    //Check first instance of deletion
			if(a.getName().equals(remove) && !removeCheck) {
	            iterator.remove();
				removeCheck = true;
			}
		}
		//Check if inputed name is not in the gradebook
		if(!removeCheck) {
			 throw new InvalidGradeException();    
		}
		else {
			System.out.println("Grade successfully removed.\n");
		}
		return grades;
	}
	
	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * 	 			input - Scanner object to get input from the user
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * This function print all the grades in the gradebook. Can sort the grades by score, letter, name, or due date
	 */
	public void printGrades(ArrayList<AssignmentInterface> grades, Scanner input) throws GradebookEmptyException {
		//Checks if the gradebook is empty
		if (grades.isEmpty()) {
			throw new GradebookEmptyException();  
		}
		System.out.println("Options for printing:\n1. Sort by Score\n2. Sort by Letter\n3. Sort by Alphabetical name\n4. Sort by Due Date");
		int optCheck = 0;
		int opt = 0;
		//Attempt to get menu option from user and throw exception if it is not an integer
		while(optCheck == 0) {
			try {
		        opt = input.nextInt();
		        optCheck = 1;
			} catch (InputMismatchException e){
				System.out.println("Invalid action selected. Please try again.\n");
				input.nextLine();
			} 
		}
		ArrayList<AssignmentInterface> temp = grades;
		switch (opt) {
        	case 1: //Sort grades by score and print
        		temp = sortScore(temp);
        		for(AssignmentInterface a : temp) {
        			System.out.println(a.toString());
        		}
        		break;
        	case 2:
        		//Sort grades by letter and print
        		temp = sortLetter(temp);
        		for(AssignmentInterface a : temp) {
        			System.out.println(a.toString());
        		}
        		break;
        	case 3: //Sort grades by name and print
        		temp = sortName(temp);
        		for(AssignmentInterface a : temp) {
        			System.out.println(a.toString());
        		}
        		break;
        	case 4: //Sort grades by date and print
        		temp = sortDate(temp);
        		for(AssignmentInterface a : temp) {
        			System.out.println(a.toString());
        		}
        		break;
            default: System.out.println("Please enter a valid number");
   		 		break;
		}
		System.out.println("\n");
	}
	
	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * This function prints the average score of all the grades
	 */
	public void printAvgScore(ArrayList<AssignmentInterface> grades) throws GradebookEmptyException {
		//Checks if the gradebook is empty
		if (grades.isEmpty()) {
			throw new GradebookEmptyException();  
		}
		double total = 0;
		//Get total combined score
		for(AssignmentInterface a : grades) {
			total = total + a.getScore();
		}
		double avg = total/((double) grades.size());
		System.out.println("Average Score: " + avg + "\n");
	}
	
	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * This function prints the lowest and highest score in the gradebook. If there is only one grade, the lowest 
	 * and highest grade are the same.
	 */
	public void printMinMax(ArrayList<AssignmentInterface> grades) throws GradebookEmptyException {
		//Checks if the gradebook is empty
		if (grades.isEmpty()) {
			throw new GradebookEmptyException();  
		}
		else {
			double min = 100, max = 0;
			//Loop to find max and min score
			for(AssignmentInterface a : grades) {
				//Lowest and highest score are the same
				if(grades.size() == 1) {
					System.out.println("Lowest Grade: " + a.getScore() + ", Highest Grade: " + a.getScore() + "\n");
					return;
				}
				if(a.getScore() < min) {
					min = a.getScore();
				}
				if(a.getScore() > max) {
					max = a.getScore();
				}
			}
			System.out.println("Lowest Grade: " + min + ", Highest Grade: " + max + "\n");
		}
	}
	
	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * This function prints a current gradebook to a new file in the GradeTextFiles folder
	 */
	public void printToFile(ArrayList<AssignmentInterface> grades) throws GradebookEmptyException {
		if (grades.isEmpty()) {
			throw new GradebookEmptyException();  
		}
	    try {
	    	//Get length of every file in folder
	    	int length = new File("src/GradesTextFiles/").list().length;
	    	//Increment new file by 1 based on length
	    	String newFileName = "GradesTextFile" + (length + 1) + ".txt";
	        File newFile = new File("src/GradesTextFiles/" + newFileName);
	        newFile.createNewFile();
	        //Write out all grades to file
	        FileWriter out = new FileWriter(newFile);
	        for(AssignmentInterface a : grades) {
	            out.write(a.getType() + " " + a.getName() + " " + (int)a.getScore() + " " + a.getLetter() + " " + a.getDueDate() + " ");
	            //Get specific object details and line.separator (equal to \n)
	            if(a.getType().equals("Quiz")) {
	            	out.write(((Quiz) a).getNumOfQues() + "" + System.getProperty("line.separator"));
	            }
	            else if(a.getType().equals("Discussion")) {
	            	out.write(((Discussion) a).getReading() + System.getProperty("line.separator"));
	            }
	            else {
	            	out.write(((Program) a).getConcept() + System.getProperty("line.separator"));
	            }
	        }
	        out.close();
	        System.out.println("File created: " + newFile.getName() + "\n");
	    } catch (IOException e) {
	        System.out.println("An error occurred. File was not created");
	    }
	}
	
	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * 	 			input - Scanner object to get input from the user
	 * Return:		ArrayList<AssignmentInterface> gradebook from file
	 * This function reads all grades from a file and puts the grades in the current gradesbook
	 */
	public ArrayList<AssignmentInterface> readFromFile(ArrayList<AssignmentInterface> grades, Scanner input) throws FileNotFoundException{
		String fileName;
		System.out.println("Enter file you want to retrieve from (Ex: example.txt)");
		fileName = input.nextLine();
		//Gets path to file and make sure it exists
		File file = new File("src/GradesTextFiles/" + fileName);
		if(file.exists()) { 
		    Scanner myReader = new Scanner(file);
		    //Loop until end of file
		    while (myReader.hasNext()) {
		    	//Get every string from file
			    String lines = myReader.nextLine();
			    //Split each string at each space
			    //Tokens 0-6 are each string in the file per grade
		    	String[] tokens = lines.split(" ");
	            if(tokens[0].equals("Quiz")) {
				    Quiz quiz = new Quiz();
				    quiz.setType(tokens[0]);
				    quiz.setName(tokens[1]);
				    quiz.setScore(Double.parseDouble(tokens[2]));
				    quiz.setLetter(tokens[3].charAt(0));
			    	String stringDate = tokens[4];
		    		LocalDate date = LocalDate.parse(stringDate);
		    		quiz.setDueDate(date);
	            	quiz.setNumOfQues(Integer.parseInt(tokens[5]));
		            grades.add(quiz);
	            }
	            else if(tokens[0].equals("Discussion")) {
				    Discussion disc = new Discussion();
				    disc.setType(tokens[0]);
				    disc.setName(tokens[1]);
				    disc.setScore(Double.parseDouble(tokens[2]));
				    disc.setLetter(tokens[3].charAt(0));
			    	String stringDate = tokens[4];
		    		LocalDate date = LocalDate.parse(stringDate);
		    		disc.setDueDate(date);
		    		disc.setReading(tokens[5]);
		            grades.add(disc);
	            }
	            else if (tokens[0].equals("Program")) {
				    Program prog = new Program();
				    prog.setType(tokens[0]);
				    prog.setName(tokens[1]);
				    prog.setScore(Double.parseDouble(tokens[2]));
				    prog.setLetter(tokens[3].charAt(0));
			    	String stringDate = tokens[4];
		    		LocalDate date = LocalDate.parse(stringDate);
		    		prog.setDueDate(date);
		    		prog.setConcept(tokens[5]);
		            grades.add(prog);
	            }
		   }
		    myReader.close();
		}
		else {
			System.out.println("The file does not exist in the folder. Please try again.\n");
		}
		return grades;
	}
	
	
	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * 	 			input - Scanner object to get input from the user
	 * Exception:	Throws a GradebookEmptyException if the gradebook is empty
	 * This function sends all grades to the Gradebook database and creates the database if it does not exist
	 */
	public void toMySQL(ArrayList<AssignmentInterface> grades, Scanner input) throws GradebookEmptyException {
		if (grades.isEmpty()) {
			throw new GradebookEmptyException();  
		}
		//Get username and password for database
		System.out.println("Enter username for gradebook database:");
		String username = input.nextLine();
		System.out.println("Enter password for gradebook database:");
		String password = input.nextLine();
        Connection connection = null;
		try {
            connection = DBUtil.getConnection(username, password);
            //Check if Gradebook database exists or not and creates one if it does not
            java.sql.DatabaseMetaData dbm = connection.getMetaData();
            ResultSet rs = dbm.getTables(null, null, "Gradebook", null);
            if (!rs.next()) {
            	String create = "CREATE TABLE Gradebook "
            			+ "("
            			+ "GradebookID INT PRIMARY KEY AUTO_INCREMENT,"
            			+ "Score DOUBLE NOT NULL,"
            			+ "Letter CHAR NOT NULL,"
            			+ "Name VARCHAR(100) NOT NULL,"
            			+ "DueDate DATE NOT NULL,"
            			+ "Type VARCHAR(100) NOT NULL,"
            			+ "Questions INT,"
            			+ "Reading VARCHAR(100),"
            			+ "Concept VARCHAR(100)"
            			+ ");";
                PreparedStatement ps = connection.prepareStatement(create);
                ps.execute();
                System.out.println("Database created.\n");
            }
            //Loop through all grades
            for (AssignmentInterface a: grades) {
            	//Insert into Gradebook database depending on if the grade is a Quiz, Discussion, or a Program
	            if(a.getType().equals("Quiz")) {
		            String sql_quiz =  "INSERT INTO Gradebook (Score, Letter, Name, DueDate, Type, Questions) VALUES (?, ?, ?, ?, ?, ?)"; 
		        	PreparedStatement ps =  connection.prepareStatement(sql_quiz); 
		       		ps.setDouble(1, a.getScore());
		       		ps.setString(2, String.valueOf(a.getLetter()));
		       		ps.setString(3, a.getName());
		       		Date date = Date.valueOf(a.getDueDate());
		       		ps.setDate(4, date);
		       		ps.setString(5, a.getType());
		       		ps.setInt(6, ((Quiz) a).getNumOfQues());
		            //Execute change
		            ps.executeUpdate(); 
	            }
	            else if(a.getType().equals("Discussion")) {
		            String sql_disc =  "INSERT INTO Gradebook (Score, Letter, Name, DueDate, Type, Reading) VALUES (?, ?, ?, ?, ?, ?)"; 
		        	PreparedStatement ps =  connection.prepareStatement(sql_disc); 
		       		ps.setDouble(1, a.getScore());
		       		ps.setString(2, String.valueOf(a.getLetter()));
		       		ps.setString(3, a.getName());
		       		Date date = Date.valueOf(a.getDueDate());
		       		ps.setDate(4, date);
		       		ps.setString(5, a.getType());
		       		ps.setString(6, ((Discussion) a).getReading());
		            //Execute change
		            ps.executeUpdate(); 
	            }
	            else if(a.getType().equals("Program")) {
		            String sql_prog =  "INSERT INTO Gradebook (Score, Letter, Name, DueDate, Type, Concept) VALUES (?, ?, ?, ?, ?, ?)"; 
		        	PreparedStatement ps =  connection.prepareStatement(sql_prog); 
		       		ps.setDouble(1, a.getScore());
		       		ps.setString(2, String.valueOf(a.getLetter()));
		       		ps.setString(3, a.getName());
		       		Date date = Date.valueOf(a.getDueDate());
		       		ps.setDate(4, date);
		       		ps.setString(5, a.getType());
		       		ps.setString(6, ((Program) a).getConcept());
		            //Execute change
		            ps.executeUpdate(); 
	            }
            }
            System.out.println("Gradebook successfully implemented into database\n");
            DBUtil.closeConnection();
            
		} catch (SQLException e) { 
	    	   System.out.println("Could not connect to database. Please try again.");
	    }
		
	}
	
	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * 	 			input - Scanner object to get input from the user
	 * Return:		ArrayList<AssignmentInterface> gradebook from file
	 * This function reads all grades from the Gradebook database and places them in the current gradebook
	 * if they are not currently in the current gradebook
	 */
	public ArrayList<AssignmentInterface> fromSQL(ArrayList<AssignmentInterface> grades, Scanner input) {
		//Get username and password for database
		System.out.println("Enter username for gradebook database:");
		String username = input.nextLine();
		System.out.println("Enter password for gradebook database:");
		String password = input.nextLine();
        Connection connection = null;
        ArrayList<AssignmentInterface> SQLGradebook = new ArrayList<AssignmentInterface>();
        try {
        	//Connect to database
            connection = DBUtil.getConnection(username, password);
        	String query = "SELECT * FROM Gradebook";
        	//Select all from database
        	PreparedStatement ps = connection.prepareStatement(query);
        	ResultSet rs = ps.executeQuery();
        	//Get all grades from database
        	while (rs.next()) { 
        		SQLGradebook = getFromSQL(SQLGradebook, rs);
        	} 
        	rs.close();
        	//Check SQL database grades and current gradebook and see which grades
        	//Current database is missing. 
        	for (AssignmentInterface a:SQLGradebook) {
        		boolean gradeCheck = false;
        		for(AssignmentInterface a2:grades) {
        			//Check if all aspects of the grade is equal for each Quiz, Discussion, and Program
        			if(a.getType().equals(a2.getType())) {
        				if(a.getType().equals("Quiz")) {
        				   if(a.getName().equals(a2.getName()) &&
        				      Double.compare(a.getScore(),a2.getScore()) == 0 &&
        				      Character.compare(a.getLetter(),a2.getLetter()) == 0 &&
        				      a.getDueDate().equals(a2.getDueDate()) &&
        					  ((Quiz) a).getNumOfQues() == ((Quiz) a2).getNumOfQues()) {
        						gradeCheck = true;
        				   }
        				}
        				else if(a.getType().equals("Discussion")) {
         				   if(a.getName().equals(a2.getName()) &&
         				      Double.compare(a.getScore(),a2.getScore()) == 0 &&
         				      Character.compare(a.getLetter(),a2.getLetter()) == 0 &&
         				      a.getDueDate().equals(a2.getDueDate()) &&
         				      ((Discussion) a).getReading().equals(((Discussion) a2).getReading())) {
         						gradeCheck = true;
         				   }
         				}
        				else if(a.getType().equals("Program")) {
         				   if(a.getName().equals(a2.getName()) &&
         				      Double.compare(a.getScore(),a2.getScore()) == 0 &&
         				      Character.compare(a.getLetter(),a2.getLetter()) == 0 &&
         				      a.getDueDate().equals(a2.getDueDate()) &&
         				      ((Program) a).getConcept().equals(((Program) a2).getConcept())) {
         						gradeCheck = true;
         				   }
         				}
        			}
        		}
        		//If grade from SQL is not in gradebook, add it too the gradebook
        		if (!gradeCheck) {
        			grades.add(a);
        		}
        	}
            System.out.println("Gradebook successfully updated based on database\n");
            DBUtil.closeConnection();
        } catch (SQLException e) { 
	    	   System.out.println("Could not connect to database. Please try again.\n");
        }
    	return grades;
	}
	
	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * 	 			input - Scanner object to get input from the user
	 * This function searches the Gradebook database for grades and prints the entrees based on user input of
	 * All quizes, discussions, programs, certain score range, certain due date range, or all even scores
	 */
	public void mySQLSearch(ArrayList<AssignmentInterface> grades, Scanner input) throws ParseException {
		//Get username and password for database
		System.out.println("Enter username for gradebook database:");
		String username = input.nextLine();
		System.out.println("Enter password for gradebook database:");
		String password = input.nextLine();
        Connection connection = null;
        ArrayList<AssignmentInterface> SQLGradebook = new ArrayList<AssignmentInterface>();
        try {
            connection = DBUtil.getConnection(username, password);
        	System.out.println("\nPrint options from database:\n1. All quizzes\n2. All discussions\n3. All programs\n"
				+ "4. All grades within a certain score range\n5. All grades within a certain due date range\n"
				+ "6. All grades with an even score");
			int opt = 0;
				try {
			        opt = input.nextInt();
				} catch (InputMismatchException e){
					System.out.println("Invalid action selected. Please try again.\n");
					input.nextLine();
				} 
			String query;
	        switch (opt) {
            	case 1: //Search for all quizes in database
            		query = "SELECT * FROM Gradebook WHERE Type = ?";
                	PreparedStatement ps_quiz = connection.prepareStatement(query);
                	ps_quiz.setString(1, "Quiz"); 
                	ResultSet rs_quiz = ps_quiz.executeQuery();
                	//Get all quiz info from the database
                	while (rs_quiz.next()) { 
                			Quiz quiz = new Quiz();
        	        		quiz.setType(rs_quiz.getString("Type"));
        	        		quiz.setLetter(rs_quiz.getString("Letter").charAt(0));
        	        		quiz.setName(rs_quiz.getString("Name"));
        	        		quiz.setScore(rs_quiz.getInt("Score"));
        	        		quiz.setDueDate(rs_quiz.getDate("DueDate").toLocalDate());
        	        		quiz.setNumOfQues(rs_quiz.getInt("Questions"));
        	        		SQLGradebook.add(quiz);
                	}
		            break;
            	case 2: //Search for all discussions in database
            	    query = "SELECT * FROM Gradebook WHERE Type = ?";
                	PreparedStatement ps_disc = connection.prepareStatement(query);
                	ps_disc.setString(1, "Discussion"); 
                	ResultSet rs_disc = ps_disc.executeQuery();
                	//Get all discussion info from the database
                	while (rs_disc.next()) { 
                			Discussion disc = new Discussion();
                			disc.setType(rs_disc.getString("Type"));
                			disc.setLetter(rs_disc.getString("Letter").charAt(0));
                			disc.setName(rs_disc.getString("Name"));
                			disc.setScore(rs_disc.getInt("Score"));
                			disc.setDueDate(rs_disc.getDate("DueDate").toLocalDate());
                			disc.setReading(rs_disc.getString("Reading"));
        	        		SQLGradebook.add(disc);
                	}
		            break;
            	case 3: //Search for all programs in database
            	    query = "SELECT * FROM Gradebook WHERE Type = ?";
                	PreparedStatement ps_prog = connection.prepareStatement(query);
                	ps_prog.setString(1, "Program"); 
                	ResultSet rs_prog = ps_prog.executeQuery();
                	//Get all programs info from the database
                	while (rs_prog.next()) { 
                			Program prog = new Program();
                			prog.setType(rs_prog.getString("Type"));
                			prog.setLetter(rs_prog.getString("Letter").charAt(0));
                			prog.setName(rs_prog.getString("Name"));
                			prog.setScore(rs_prog.getInt("Score"));
                			prog.setDueDate(rs_prog.getDate("DueDate").toLocalDate());
                			prog.setConcept(rs_prog.getString("Concept"));
        	        		SQLGradebook.add(prog);
                	}
		            break;
            	case 4: //Search for all scores in range in database
            		System.out.println("Enter lowest score range:");
            		Double lowScore = input.nextDouble();
            		System.out.println("Enter highest score range:");
            		Double highScore = input.nextDouble();
            		//Range of the scores
            	    query = "SELECT * FROM Gradebook WHERE Score BETWEEN " + lowScore + " AND " + highScore;
                	PreparedStatement ps_score = connection.prepareStatement(query);
                	ResultSet rs_score= ps_score.executeQuery();
                	//Loop through all scores and check if the grade is in the range
                	while (rs_score.next()) { 
                		SQLGradebook = getFromSQL(SQLGradebook, rs_score);
                	}
		            break; 
            	case 5: //Search for all dates in range in database
            		System.out.println("Enter least recent date range: (MM/dd/yyyy)");
            		input.nextLine();
            		String lowDateString = input.nextLine();
            		System.out.println("Enter most recent date range: (MM/dd/yyyy)");
            		String highDateString = input.nextLine();
            		//String to java.sql.Date formatted
            		SimpleDateFormat dtf = new SimpleDateFormat("MM/dd/yyyy");
            		java.util.Date low = dtf.parse(lowDateString);
            		java.util.Date high = dtf.parse(highDateString);
            		//Query between 2 dates
            	    query = "SELECT * FROM Gradebook WHERE DueDate BETWEEN ? AND ?";
                	PreparedStatement ps_date = connection.prepareStatement(query);
                	//Get Time return 0.000 for time, so it does not impact the date
                	ps_date.setDate(1, new java.sql.Date(low.getTime()));
                	ps_date.setDate(2, new java.sql.Date(high.getTime()));
                	//Loop through all grades to check for dates between
                	ResultSet rs_date = ps_date.executeQuery();
                	while (rs_date.next()) { 
                		SQLGradebook = getFromSQL(SQLGradebook, rs_date);
                	}
		            break; 
            	case 6: //Search for all even scores in database
            		//Score % 2 = even grade
            	    query = "SELECT * FROM Gradebook WHERE Score%2 = 0";
                	PreparedStatement ps_evenScore = connection.prepareStatement(query);
                	ResultSet rs_evenScore= ps_evenScore.executeQuery();
                	//Loop through all grades to check for even scores
                	while (rs_evenScore.next()) { 
                		SQLGradebook = getFromSQL(SQLGradebook, rs_evenScore);
                	}
		            break; 
            	default: System.out.println("Please enter a valid integer");
		       			break;
	        }
	        //Print all grades based on search option chosen by user
	        for (AssignmentInterface a:SQLGradebook) {
	        	System.out.println(a.toString());
	        }
	        System.out.println();
	        DBUtil.closeConnection();
        } catch (SQLException e) { 
	    	   System.out.println("Could not connect to database. Please try again.\n");
	    	   e.printStackTrace();

        }
	}
	
	/* Parameters: 	sortedScore - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * Return:		ArrayList<AssignmentInterface> gradebook sorted by score
	 * This function sorts the current gradebook by score
	 */
	public ArrayList<AssignmentInterface> sortScore(ArrayList<AssignmentInterface> sortedScore) {
	    boolean sorted = false;
	    //Keep looping until sorting is finished
	    while (!sorted) {
	    	sorted = true;
	    	for (int i = 0; i < sortedScore.size() - 1; i++) { 		      
	    		if(sortedScore.get(i).getScore() > sortedScore.get(i+1).getScore()) {
	    			//Swap adjecent scores in gradebook
	    			AssignmentInterface currGrade = sortedScore.get(i);
	    			sortedScore.set(i, sortedScore.get(i + 1));
	    			sortedScore.set(i + 1, currGrade);
	    			sorted = false;
	    		}
	    	}
	    }
	    return sortedScore;
	}
	
	/* Parameters: 	sortedLetter - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * Return:		ArrayList<AssignmentInterface> gradebook sorted by letter
	 * This function sorts the current gradebook by letter
	 */
	public ArrayList<AssignmentInterface> sortLetter(ArrayList<AssignmentInterface> sortedLetter) {
	    boolean sorted = false;
	    //Keep looping until sorting is finished
	    while (!sorted) {
	    	sorted = true;
	    	for (int i = 0; i < sortedLetter.size() - 1; i++) { 		      
	    		if(sortedLetter.get(i).getLetter() > sortedLetter.get(i+1).getLetter()) {
	    			//Swap adjecent scores in gradebook
	    			AssignmentInterface currGrade = sortedLetter.get(i);
	    			sortedLetter.set(i, sortedLetter.get(i + 1));
	    			sortedLetter.set(i + 1, currGrade);
	    			sorted = false;
	    		}
	    	}
	    }
	    return sortedLetter;
	}
	
	/* Parameters: 	sortedName - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * Return:		ArrayList<AssignmentInterface> gradebook sorted by name
	 * This function sorts the current gradebook by name
	 */
	public ArrayList<AssignmentInterface> sortName(ArrayList<AssignmentInterface> sortedName) {
	    boolean sorted = false;
	    //Keep looping until sorting is finished
	    while (!sorted) {
	    	sorted = true;
	    	for (int i = 0; i < sortedName.size() - 1; i++) { 		      
	    		if(sortedName.get(i).getName().toLowerCase().compareTo(sortedName.get(i+1).getName().toLowerCase()) > 0) {
	    			//Swap adjecent scores in gradebook
	    			AssignmentInterface currGrade = sortedName.get(i);
	    			sortedName.set(i, sortedName.get(i + 1));
	    			sortedName.set(i + 1, currGrade);
	    			sorted = false;
	    		}
	    	}
	    }
	    return sortedName;
	}
	
	/* Parameters: 	sortedDate - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * Return:		ArrayList<AssignmentInterface> gradebook sorted by date
	 * This function sorts the current gradebook by date
	 */
	public ArrayList<AssignmentInterface> sortDate(ArrayList<AssignmentInterface> sortedDate) {
	    boolean sorted = false;
	    //Keep looping until sorting is finished
	    while (!sorted) {
	    	sorted = true;
	    	for (int i = 0; i < sortedDate.size() - 1; i++) { 		      
	    		if(sortedDate.get(i).getDueDate().compareTo(sortedDate.get(i+1).getDueDate()) > 0) {
	    			//Swap adjecent scores in gradebook
	    			AssignmentInterface currGrade = sortedDate.get(i);
	    			sortedDate.set(i, sortedDate.get(i + 1));
	    			sortedDate.set(i + 1, currGrade);
	    			sorted = false;
	    		}
	    	}
	    }
	    return sortedDate;
	}

	/* Parameters: 	grades - ArrayList<AssignmentInterface> that holds all of the grades in the gradebook
	 * 				rs - ResultSet from prepared statement
	 * Return:		ArrayList<AssignmentInterface> gradebook with added Quiz, Discussion, or Program grades added
	 * This function gets all grades from SQL and stores them in a Quiz, Discussion, or Program class in an ArrayList<AssignmentInterface>
	 */
	public ArrayList<AssignmentInterface> getFromSQL(ArrayList<AssignmentInterface> grades, ResultSet rs) throws SQLException {
		//Check for type to put in each class
		String type = rs.getString("Type");
		//Stores each value for the different classes
		if(type.equals("Quiz")) {
			Quiz quiz = new Quiz();
    		quiz.setType(rs.getString("Type"));
    		quiz.setLetter(rs.getString("Letter").charAt(0));
    		quiz.setName(rs.getString("Name"));
    		quiz.setScore(rs.getInt("Score"));
    		quiz.setDueDate(rs.getDate("DueDate").toLocalDate());
    		quiz.setNumOfQues(rs.getInt("Questions"));
    		grades.add(quiz);
		}
		else if(type.equals("Discussion")) {
			Discussion disc = new Discussion();
    		disc.setType(rs.getString("Type"));
    		disc.setLetter(rs.getString("Letter").charAt(0));
    		disc.setName(rs.getString("Name"));
    		disc.setScore(rs.getInt("Score"));
    		disc.setDueDate(rs.getDate("DueDate").toLocalDate());
    		disc.setReading(rs.getString("Reading"));
    		grades.add(disc);
		}
		else if(type.equals("Program")) {
			Program prog = new Program();
    		prog.setType(rs.getString("Type"));
    		prog.setLetter(rs.getString("Letter").charAt(0));
    		prog.setName(rs.getString("Name"));
    		prog.setScore(rs.getInt("Score"));
    		prog.setDueDate(rs.getDate("DueDate").toLocalDate());
    		prog.setConcept(rs.getString("Concept"));
    		grades.add(prog);
		}
		return grades;
	}
}