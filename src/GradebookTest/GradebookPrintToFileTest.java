/*

* Assignment: Gradebook Project Part 2

* Name: Nicholas Clark

*/
package GradebookTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Project.exception.GradebookEmptyException;
import Project.gradebook.AssignmentInterface;
import Project.gradebook.Discussion;
import Project.gradebook.Program;
import Project.gradebook.Quiz;
import Project.menu.MenuOptions;

public class GradebookPrintToFileTest {
	private ArrayList<AssignmentInterface> gradebook;
	private MenuOptions menuOptions;
	
	@BeforeEach
	public void setUp() {
		gradebook = new ArrayList<AssignmentInterface>();
		menuOptions = new MenuOptions();
	}
	
	@Test
	public void testFileInsertIntoFolder() throws GradebookEmptyException {
    	int length = new File("src/GradesTextFiles/").list().length;
		Quiz quiz = new Quiz();
		quiz.setName("Quiz");
		quiz.setType("Quiz");
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    LocalDate date = LocalDate.parse("02/02/2020", dtf);
		quiz.setDueDate(date);
		quiz.setLetter('A');
		quiz.setScore(100);
		quiz.setNumOfQues(10);
		gradebook.add(quiz);
    	menuOptions.printToFile(gradebook);
    	int length2 = new File("src/GradesTextFiles/").list().length;
		Assertions.assertEquals(length+1, length2);
	    File del = new File("src/GradesTextFiles/GradesTextFile" + length2 + ".txt"); 
	    del.deleteOnExit();
	}
	
	@Test
	public void testPrinttoFileEmptyGradebook() {
		GradebookEmptyException e = null;
		try {
	    	menuOptions.printToFile(gradebook);
		} catch (GradebookEmptyException ex) {
			e = ex;	  
		}
		Assertions.assertTrue(e instanceof GradebookEmptyException);
	}
	
	@Test
	public void testPrintQuiztoFile() throws GradebookEmptyException, FileNotFoundException {
		Quiz quiz = new Quiz();
		quiz.setName("Quiz");
		quiz.setType("Quiz");
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    LocalDate date = LocalDate.parse("02/02/2020", dtf);
		quiz.setDueDate(date);
		quiz.setLetter('A');
		quiz.setScore(100);
		quiz.setNumOfQues(10);
		gradebook.add(quiz);
    	menuOptions.printToFile(gradebook);
    	int length = new File("src/GradesTextFiles/").list().length;
		File file = new File("src/GradesTextFiles/GradesTextFile" + length + ".txt");
	    Scanner myReader = new Scanner(file);
	    String[] tokens = null;
	    while (myReader.hasNext()) {
		    String lines = myReader.nextLine();
	    	tokens = lines.split(" ");
	    }
	    Assertions.assertEquals(10, Integer.parseInt(tokens[5]));
	    file.deleteOnExit();
	    myReader.close();
	}
	
	@Test
	public void testPrintDisctoFile() throws GradebookEmptyException, FileNotFoundException {
		Discussion disc = new Discussion();
		disc.setName("Discussion");
		disc.setType("Discussion");
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    LocalDate date = LocalDate.parse("02/02/2020", dtf);
	    disc.setDueDate(date);
	    disc.setLetter('A');
	    disc.setScore(100);
	    disc.setReading("reading1");
		gradebook.add(disc);
    	menuOptions.printToFile(gradebook);
    	int length = new File("src/GradesTextFiles/").list().length;
		File file = new File("src/GradesTextFiles/GradesTextFile" + length + ".txt");
	    Scanner myReader = new Scanner(file);
	    String[] tokens = null;
	    while (myReader.hasNext()) {
		    String lines = myReader.nextLine();
	    	tokens = lines.split(" ");
	    }
	    Assertions.assertEquals("reading1", tokens[5]);
	    file.deleteOnExit();
	    myReader.close();
	}
	
	@Test
	public void testPrintProgramtoFile() throws GradebookEmptyException, FileNotFoundException {
		Program prog = new Program();
		prog.setName("Program");
		prog.setType("Program");
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    LocalDate date = LocalDate.parse("02/02/2020", dtf);
	    prog.setDueDate(date);
	    prog.setLetter('A');
	    prog.setScore(100);
	    prog.setConcept("concept1");
		gradebook.add(prog);
    	menuOptions.printToFile(gradebook);
    	int length = new File("src/GradesTextFiles/").list().length;
		File file = new File("src/GradesTextFiles/GradesTextFile" + length + ".txt");
	    Scanner myReader = new Scanner(file);
	    String[] tokens = null;
	    while (myReader.hasNext()) {
		    String lines = myReader.nextLine();
	    	tokens = lines.split(" ");
	    }
	    Assertions.assertEquals("concept1", tokens[5]);
	    file.deleteOnExit();
	    myReader.close();
	}
	
	@Test
	public void testPrinttoFileWithTwoEntrees() throws GradebookEmptyException, FileNotFoundException {
		Quiz quiz = new Quiz();
		quiz.setName("Quiz");
		quiz.setType("Quiz");
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    LocalDate date = LocalDate.parse("02/02/2020", dtf);
		quiz.setDueDate(date);
		quiz.setLetter('A');
		quiz.setScore(100);
		quiz.setNumOfQues(10);
		gradebook.add(quiz);
		Program prog = new Program();
		prog.setName("Program");
		prog.setType("Program");
	    LocalDate date2 = LocalDate.parse("02/02/2020", dtf);
	    prog.setDueDate(date2);
	    prog.setLetter('A');
	    prog.setScore(100);
	    prog.setConcept("concept1");
		gradebook.add(prog);
    	menuOptions.printToFile(gradebook);
    	int length = new File("src/GradesTextFiles/").list().length;
		File file = new File("src/GradesTextFiles/GradesTextFile" + length + ".txt");
	    Scanner myReader = new Scanner(file);
	    String[] tokens = null;
	    while (myReader.hasNext()) {
		    String lines = myReader.nextLine();
	    	tokens = lines.split(" ");
	    	if(tokens[0].equals("Quiz")) {
	    	    Assertions.assertEquals(10, Integer.parseInt(tokens[5]));
	    	}
	    	else if (tokens[0].equals("Concept")) {
	    	    Assertions.assertEquals("concept1", tokens[5]);
	    	}
	    }
	    file.deleteOnExit();
	    myReader.close();
	}
}
