import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;
import org.mockito.Mockito;

public class ToDoTest
{

  @Test
  public void createNewHomeTask_1HomeTaskCreated()
  {
    // This is the main class where events are created and removed
    ToDo todo = new ToDo();

    //Creating a mocked version of ActionEvent
    ActionEvent actionEventMock = mock(ActionEvent.class);

    //When getSource is called, return the actual HomeTaskButton object
    when(actionEventMock.getSource()).thenReturn(todo.getHomeTaskButton());

    // Call actionPerformed function
    // this is where tasks get created when the HomeTask, StudyTask, or CustomTask buttons are pressed
    todo.actionPerformed(actionEventMock);

    // Check the total count because it is a separate var that gets incremented when a task is created
    assertEquals(1, todo.getTotalCount(), "createNewHomeTask_TotalCount=1");

    // The arraylist size should be of size 1 since we have only created 1 task
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


  /**
   * Test creating a new task and remove it
   * NOTE: Removing from the arraylists was never setup by the original devs so any created task stays in the taskList
   * I have added this feature to make it pass, we can remove it later if we are not supposed to fix functionality
   * This is located in the method called taskRemoved on line 224
   */
  @Test
  public void createNewStudyTaskAndRemoveIt_TaskListSize0()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getStudyTaskButton());

    todo.actionPerformed(actionEventMock);

    //Get the task that was added from the TaskList
    Task out = (Task) todo.getTaskList().get(0);
    //Call taskRemoved to removing a task from the Gui
    todo.taskRemoved(out);

    //Check that the task was removed by checking the count
    assertEquals(0, todo.getTotalCount());
    //Check that the Gui updated showing 0/0 tasks
    assertEquals("Total task completed: 0/0", todo.getTotalTasksBottomText(), "createNewStudyTaskAndRemoveIt_CompletedTasks_0/0");

    assertEquals(0, todo.getTaskList().size());
  }

  @Test
  public void createNewHomeTaskAndRemoveIt_TaskListSize0()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getHomeTaskButton());

    todo.actionPerformed(actionEventMock);

    //Get the task that was added from the TaskList
    Task out = (Task) todo.getTaskList().get(0);
    //Call taskRemoved to removing a task from the Gui
    todo.taskRemoved(out);

    //Check that the task was removed by checking the count
    assertEquals(0, todo.getTotalCount());
    //Check that the Gui updated showing 0/0 tasks
    assertEquals("Total task completed: 0/0", todo.getTotalTasksBottomText(), "createNewHomeTaskAndRemoveIt_CompletedTasks_0/0");

    assertEquals(0, todo.getTaskList().size(), "createNewHomeTaskAndRemoveIt_TaskListSize=0");
  }


  @Test
  public void createNewCustomTaskAndRemoveIt_TaskListSize0()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getCustomTaskButton());

    todo.actionPerformed(actionEventMock);

    //Get the task that was added from the TaskList
    Task out = (Task) todo.getTaskList().get(0);
    //Call taskRemoved to removing a task from the Gui
    todo.taskRemoved(out);

    //Check that the task was removed by checking the count
    assertEquals(0, todo.getTotalCount());
    //Check that the Gui updated showing 0/0 tasks
    assertEquals("Total task completed: 0/0", todo.getTotalTasksBottomText(), "createNewCustomTaskAndRemoveIt_CompletedTasks_0/0");

    assertEquals(0, todo.getTaskList().size(), "createNewHomeTaskAndRemoveIt_TaskListSize=0");
  }

}
