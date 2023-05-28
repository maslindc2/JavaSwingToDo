package com.team4;
import java.util.Comparator;

//this class is for sorting tasks in alphabetical order.
public class TaskTextComparator implements Comparator {
	// This function compares the text of 2 task objects. It returns an int and
	// takes 2 inputs of type Object.
	public int compare(Object o1, Object o2) {
		Task task1 = (Task) o1;
		Task task2 = (Task) o2;
		return (task1.getText()).compareTo(task2.getText());
	}
}
