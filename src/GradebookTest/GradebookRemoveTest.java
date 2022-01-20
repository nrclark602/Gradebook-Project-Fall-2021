/*

* Assignment: Gradebook Project Part 2

* Name: Nicholas Clark

*/
package GradebookTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import Project.exception.GradebookEmptyException;
import Project.exception.InvalidGradeException;
import Project.gradebook.AssignmentInterface;
import Project.gradebook.Discussion;
import Project.gradebook.Program;
import Project.gradebook.Quiz;
import Project.menu.MenuOptions;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class GradebookRemoveTest {
	private ArrayList<AssignmentInterface> gradebook;
	private Scanner input;
	private MenuOptions menuOptions;
	
	@BeforeEach
	public void setUp() {
		gradebook = new ArrayList<AssignmentInterface>();
		input = new Scanner(System.in);
		menuOptions = new MenuOptions();
	}
	
	@Test
	public void testGradebookRemoveQuiz() throws InvalidGradeException, GradebookEmptyException {
		Quiz quiz = new Quiz();
		quiz.setName("Quiz");
		gradebook.add(quiz);
		gradebook = menuOptions.removeGrades(gradebook, input, "Quiz");
		Assertions.assertEquals(0, gradebook.size());
	}
	
	@Test
	public void testGradebookRemoveDiscussion() throws InvalidGradeException, GradebookEmptyException {
		Discussion disc = new Discussion();
		disc.setName("Discussion");
		gradebook.add(disc);
		gradebook = menuOptions.removeGrades(gradebook, input, "Discussion");
		Assertions.assertEquals(0, gradebook.size());
	}
	
	@Test
	public void testGradebookRemoveProgram() throws InvalidGradeException, GradebookEmptyException {
		Program prog = new Program();
		prog.setName("Program");
		gradebook.add(prog);
		gradebook = menuOptions.removeGrades(gradebook, input, "Program");
		Assertions.assertEquals(0, gradebook.size());
	}
	
	@Test
	public void testGradebookRemoveEntreeNotInGradebook() throws InvalidGradeException, GradebookEmptyException {
		Program prog = new Program();
		prog.setName("Program");
		gradebook.add(prog);
		InvalidGradeException e = null;
		try {
			gradebook = menuOptions.removeGrades(gradebook, input, "Discussion");
		} catch (InvalidGradeException ex) {
			e = ex;	  
		}
		Assertions.assertTrue(e instanceof InvalidGradeException);
	}
	
	@Test
	public void testGradebookRemoveEmptyGradebook() throws InvalidGradeException, GradebookEmptyException {
		GradebookEmptyException e = null;
		try {
			gradebook = menuOptions.removeGrades(gradebook, input, "Discussion");
		} catch (GradebookEmptyException ex) {
			e = ex;	  
		}
		Assertions.assertTrue(e instanceof GradebookEmptyException);
	}
	
	@Test
	public void testGradebookRemoveWith2Entrees() throws InvalidGradeException, GradebookEmptyException {
		Program prog = new Program();
		prog.setName("Program");
		gradebook.add(prog);
		Program prog2 = new Program();
		prog2.setName("Program2");
		gradebook.add(prog2);
		gradebook = menuOptions.removeGrades(gradebook, input, "Program");
		Assertions.assertEquals(1, gradebook.size());
	}
	
	@Test
	public void testGradebookRemoveFirstEntree() throws InvalidGradeException, GradebookEmptyException {
		Program prog = new Program();
		prog.setName("Program");
		gradebook.add(prog);
		Program prog2 = new Program();
		prog2.setName("Program2");
		gradebook.add(prog2);
		Program prog3 = new Program();
		prog3.setName("Program3");
		gradebook.add(prog3);
		gradebook = menuOptions.removeGrades(gradebook, input, "Program");
		Assertions.assertEquals(2, gradebook.size());
	}
	
	@Test
	public void testGradebookRemoveMiddleEntree() throws InvalidGradeException, GradebookEmptyException {
		Program prog = new Program();
		prog.setName("Program");
		gradebook.add(prog);
		Program prog2 = new Program();
		prog2.setName("Program2");
		gradebook.add(prog2);
		Program prog3 = new Program();
		prog3.setName("Program3");
		gradebook.add(prog3);
		gradebook = menuOptions.removeGrades(gradebook, input, "Program2");
		Assertions.assertEquals(2, gradebook.size());
	}
	
	@Test
	public void testGradebookRemoveLastEntree() throws InvalidGradeException, GradebookEmptyException {
		Program prog = new Program();
		prog.setName("Program");
		gradebook.add(prog);
		Program prog2 = new Program();
		prog2.setName("Program2");
		gradebook.add(prog2);
		Program prog3 = new Program();
		prog3.setName("Program3");
		gradebook.add(prog3);
		gradebook = menuOptions.removeGrades(gradebook, input, "Program3");
		Assertions.assertEquals(2, gradebook.size());
	}
	
	@Test
	public void testGradebookRemoveEntreesWithTheSameName() throws InvalidGradeException, GradebookEmptyException {
		Program prog = new Program();
		prog.setName("Program");
		gradebook.add(prog);
		Program prog2 = new Program();
		prog2.setName("Program");
		gradebook.add(prog2);
		gradebook = menuOptions.removeGrades(gradebook, input, "Program");
		Assertions.assertEquals(1, gradebook.size());
	}
	
	@AfterEach
	public void tearDown() {
		input.close();
		gradebook = null;
	}

	
}
