import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.event.ActionEvent;


import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoTest
{
  @Test
  public void createNewHomeTask_1HomeTaskCreated()
  {
    // This is the main class where events are created and removed
    ToDo todo = new ToDo();

    //Creating a mocked version of ActionEvent
    ActionEvent actionEventMock = mock(ActionEvent.class);

    //When getSource is called return the actual HomeTaskButton object
    when(actionEventMock.getSource()).thenReturn(todo.getHomeTaskButton());

    // Call actionPerformed this is where tasks get created when the HomeTask, StudyTask, or CustomTask buttons are pressed
    todo.actionPerformed(actionEventMock);

    // Checking total count because it is a separate var that gets incremented when a task is created
    assertEquals(1, todo.getTotalCount(), "createNewHomeTask_TotalCount=1");

    // The arraylist size should be of size 1 since we only created 1 task
    assertEquals(1, todo.getTaskList().size(), "createNewHomeTask_TaskListSize=1");

    // Text at the bottom displaying how many completed tasks should indicate 0/1 tasks completed
    assertEquals("Total task completed: 0/1", todo.getTotalTasksBottomText(), "createNewHomeTask_CompletedTasks_0/1");
  }
  @Test
  public void createNewStudyTask_1StudyTaskCreated()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getStudyTaskButton());

    todo.actionPerformed(actionEventMock);

    assertEquals(1, todo.getTotalCount(), "createNewStudyTask_TotalCount=1");
    assertEquals(1, todo.getTaskList().size(), "createNewStudyTask_TaskListSize=1");
    assertEquals("Total task completed: 0/1", todo.getTotalTasksBottomText(), "createNewStudyTask_CompletedTasks_0/1");
  }
  @Test
  public void createNewCustomTask_1CustomTaskCreated()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getCustomTaskButton());

    todo.actionPerformed(actionEventMock);

    assertEquals(1, todo.getTotalCount(), "createNewCustomTask_TotalCount=1");
    assertEquals(1, todo.getTaskList().size(), "createNewCustomTask_TaskListSize=1");
    assertEquals("Total task completed: 0/1", todo.getTotalTasksBottomText(), "createNewCustomTask_CompletedTasks_0/1");
  }
}
