/*

* Assignment: Gradebook Project Part 2

* Name: Nicholas Clark

*/
package Project.gradebook;

import java.time.LocalDate;

public interface AssignmentInterface {
	 public double getScore();
	 public char getLetter();
	 public String getName();
	 public LocalDate getDueDate();
	 public String getType();
	 public void setScore(double score);
	 public void setLetter(char letter);
	 public void setName(String name);
	 public void setDueDate(LocalDate dueDate);
	 public void setType(String type);
	 public String toString();
}
