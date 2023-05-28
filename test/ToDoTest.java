import org.junit.jupiter.api.Test;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    //Fetching the Study Task object to check the type is Study
    HomeTask homeTask = (HomeTask) todo.getTaskList().get(0);

    assertAll("Counters and the size of task arraylist should equal 1 for StudyTask",
            // Check the total count because it is a separate var that gets incremented when a task is created
            () -> assertEquals(1, todo.getTotalCount(), "createNewHomeTask_TotalCount=1"),
            // The arraylist size should be of size 1 since we have only created 1 task
            () -> assertEquals(1, todo.getTaskList().size(), "createNewHomeTask_TaskListSize=1"),
            // The task type of HomeTask should be Home
            () -> assertEquals("Home", homeTask.getTaskType()),
            // As we have just created a HomeTask, it should not be complete
            () -> assertFalse(homeTask.isComplete()),
            // Text at the bottom displaying how many completed tasks should indicate 0/1 tasks completed
            () -> assertEquals("Total task completed: 0/1", todo.getTotalTasksBottomText(), "createNewHomeTask_CompletedTasks_0/1"));
  }

  @Test
  public void createNewStudyTask_1StudyTaskCreated()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getStudyTaskButton());

    todo.actionPerformed(actionEventMock);

    StudyTask studyTask = (StudyTask) todo.getTaskList().get(0);
    assertAll("Counters and the size of task arraylist should equal 1 for StudyTask",
            () -> assertEquals(1, todo.getTotalCount(), "createNewStudyTask_TotalCount=1"),
            () -> assertEquals(1, todo.getTaskList().size(), "createNewStudyTask_TaskListSize=1"),
            () -> assertEquals("Study", studyTask.getTaskType()),
            () -> assertFalse(studyTask.isComplete()),
            () -> assertEquals("Total task completed: 0/1", todo.getTotalTasksBottomText(), "createNewStudyTask_CompletedTasks_0/1"));
  }

  @Test
  public void createNewStudyTask_getTaskText()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getStudyTaskButton());

    todo.actionPerformed(actionEventMock);

    assertAll("Initialize task with name new study task",
            () -> assertEquals("New study task", ((StudyTask) todo.getTaskList().get(0)).getText()));
  }
  @Test
  public void createNewHomeTask_getTaskText()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getHomeTaskButton());

    todo.actionPerformed(actionEventMock);

    assertAll("Initialize task with name new home task",
            () -> assertEquals("New home task", ((HomeTask) todo.getTaskList().get(0)).getText()));
  }

  @Test
  public void createNewCustomTask_getTaskText()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getCustomTaskButton());

    todo.actionPerformed(actionEventMock);

    assertAll("Initialize task with name new custom task",
            () -> assertEquals("New custom task", ((CustomTask) todo.getTaskList().get(0)).getText()));
  }

  @Test
  public void create2Tasks_taskTextComparatorSuccessTest()
  {
    ToDo todo = new ToDo();

    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getStudyTaskButton());
    todo.actionPerformed(actionEventMock);

    when(actionEventMock.getSource()).thenReturn(todo.getStudyTaskButton());
    todo.actionPerformed(actionEventMock);

    TaskTextComparator taskTextComparator = new TaskTextComparator();
    int result = taskTextComparator.compare(todo.getTaskList().get(0),todo.getTaskList().get(1));

    assertAll("Initialize task with name new study task",
            () -> assertEquals(0, result));
  }

  @Test
  public void create2Tasks_taskTextComparatorFailureTest()
  {
    ToDo todo = new ToDo();

    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getStudyTaskButton());
    todo.actionPerformed(actionEventMock);

    when(actionEventMock.getSource()).thenReturn(todo.getCustomTaskButton());
    todo.actionPerformed(actionEventMock);

    TaskTextComparator taskTextComparator = new TaskTextComparator();
    int result = taskTextComparator.compare(todo.getTaskList().get(0),todo.getTaskList().get(1));

    assertAll("Compare two tasks",
            () -> assertNotEquals(0, result));
  }

  @Test
  public void createNewCustomTask_1CustomTaskCreated()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getCustomTaskButton());
    todo.actionPerformed(actionEventMock);
    CustomTask customTask = (CustomTask) todo.getTaskList().get(0);
    assertAll("Counters and the size of task arraylist should equal 1 for CustomTask",
            // Check that the total count variable = 1 since we created only one task
            () -> assertEquals(1, todo.getTotalCount(), "createNewCustomTask_TotalCount=1"),
            // Check that the task arraylist size = 1 since we created only one task
            () -> assertEquals(1, todo.getTaskList().size(), "createNewCustomTask_TaskListSize=1"),
            () -> assertEquals("Work", customTask.getTaskType()),
            () -> assertFalse(customTask.isComplete()),
            () -> assertFalse(customTask.isImportant()),
            // Check that the text telling the user how many tasks exist shows only 1 task has been created
            () -> assertEquals("Total task completed: 0/1", todo.getTotalTasksBottomText(), "createNewCustomTask_CompletedTasks_0/1"));
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

    assertAll("After creating and removing a task the counters and task list size should = 0",
            //Check that the task was removed by checking the count
            () -> assertEquals(0, todo.getTotalCount()),
            //Check that the Gui updated showing 0/0 tasks
            () -> assertEquals("Total task completed: 0/0", todo.getTotalTasksBottomText(), "createNewHomeTaskAndRemoveIt_CompletedTasks_0/0"),
            // Check that the task has been removed from the task list
            () -> assertEquals(0, todo.getTaskList().size()));
  }

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

    assertAll("After creating and removing a task the counters and task list size should = 0",
            //Check that the task was removed by checking the count
            () -> assertEquals(0, todo.getTotalCount()),
            //Check that the Gui updated showing 0/0 tasks
            () -> assertEquals("Total task completed: 0/0", todo.getTotalTasksBottomText(), "createNewStudyTaskAndRemoveIt_CompletedTasks_0/0"),
            // Check that the task has been removed from the task list
            () -> assertEquals(0, todo.getTaskList().size()));
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

    assertAll("After creating and removing a task the counters and task list size should = 0",
            //Check that the task was removed by checking the count
            () -> assertEquals(0, todo.getTotalCount()),
            //Check that the Gui updated showing 0/0 tasks
            () -> assertEquals("Total task completed: 0/0", todo.getTotalTasksBottomText(), "createNewCustomTaskAndRemoveIt_CompletedTasks_0/0"),
            // Check that the task has been removed from the task list
            () -> assertEquals(0, todo.getTaskList().size()));
  }


  @Test
  public void itemStateChangedIfStateChangeIs0TaskStaysUncompleted_StudyTaskIsUncompleted(){
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getStudyTaskButton());
    todo.actionPerformed(actionEventMock);
    StudyTask task = (StudyTask) todo.getTaskList().get(0);

    TaskListener mockedTaskListener = spy(task.getTaskListener());
    task.setTaskListener(mockedTaskListener);

    TaskInputListener taskInputListener = new TaskInputListener(task, task.getTextField(), task.getTextLabel());


    ItemEvent mockedItemEvent = mock(ItemEvent.class);
    when(mockedItemEvent.getStateChange()).thenReturn(0);
    taskInputListener.itemStateChanged(mockedItemEvent);

    verify(mockedTaskListener, atLeastOnce()).taskUncompleted(task);

  }

  @Test
  public void itemStateChangedIfStateChangeIs1TaskUpdatesToCompleted_StudyTaskIsCompleted(){
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getStudyTaskButton());
    todo.actionPerformed(actionEventMock);
    StudyTask task = (StudyTask) todo.getTaskList().get(0);

    TaskListener mockedTaskListener = spy(task.getTaskListener());
    task.setTaskListener(mockedTaskListener);

    TaskInputListener taskInputListener = new TaskInputListener(task, task.getTextField(), task.getTextLabel());


    ItemEvent mockedItemEvent = mock(ItemEvent.class);
    when(mockedItemEvent.getStateChange()).thenReturn(1);
    taskInputListener.itemStateChanged(mockedItemEvent);

    verify(mockedTaskListener, atLeastOnce()).taskCompleted(task);
  }

  @Test
  public void itemStateChangedIfStateChangeIs1ButListenerIsNullTaskStaysUncompleted_StudyTaskIsUncompleted(){
    StudyTask task = new StudyTask();
    TaskInputListener taskInputListener = new TaskInputListener(task, task.getTextField(), task.getTextLabel());
    ItemEvent mockedItemEvent = mock(ItemEvent.class);
    when(mockedItemEvent.getStateChange()).thenReturn(1);
    taskInputListener.itemStateChanged(mockedItemEvent);
    assertFalse(task.isComplete());
  }
  @Test
  public void createNewStudyTaskThenRemoveIt_StudyTaskRemoved()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getStudyTaskButton());

    todo.actionPerformed(actionEventMock);

    //Get the task that was added from the TaskList
    Task out = (Task) todo.getTaskList().get(0);
    //Call taskRemoved to removing a task from the Gui
    todo.taskRemoved(out);

    assertAll("After creating and removing a task the counters and task list size should = 0",
            //Check that the task was removed by checking the count
            () -> assertEquals(0, todo.getTotalCount()),
            //Check that the Gui updated showing 0/0 tasks
            () -> assertEquals("Total task completed: 0/0", todo.getTotalTasksBottomText(), "createNewStudyTaskAndRemoveIt_CompletedTasks_0/0"));
  }

  @Test
  public void createNewStudyTaskMarkCompleteThenRemoveIt_StudyTaskRemoved()
  {
    ToDo todo = new ToDo();
    ActionEvent actionEventMock = mock(ActionEvent.class);
    when(actionEventMock.getSource()).thenReturn(todo.getStudyTaskButton());

    todo.actionPerformed(actionEventMock);
    StudyTask studyTask = (StudyTask) todo.getTaskList().get(0);
    todo.taskCompleted(studyTask);
    assertEquals("Total task completed: 1/1", todo.getTotalTasksBottomText(), "createNewStudyTaskMarkCompleteThenRemoveIt_CompletedTasks_1/1");
    todo.taskRemoved(studyTask);
    assertEquals(0, todo.getTotalCount(), "createNewStudyTaskMarkCompleteThenRemoveIt_TotalCount=0");
    assertEquals("Total task completed: 0/0", todo.getTotalTasksBottomText(), "createNewStudyTaskMarkCompleteThenRemoveIt_CompletedTasks_0/0");
  }

  // Calling Main for extra class coverage note this doesn't test anything
  @Test
  public void callingMain(){
    ToDo.main(null);
  }
  //Note this does empty method this purely for increasing method coverage
  // this method is from the TaskListener interface and is required in the ToDo class, since that class implements TaskListener
  @Test
  public void callingTaskChanged(){
    StudyTask task = new StudyTask();
    ToDo todo = new ToDo();
    todo.taskChanged(task);
  }
}
