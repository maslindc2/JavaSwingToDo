package com.team4.demo;

import org.assertj.core.api.SoftAssertions;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

import java.awt.*;

import static org.assertj.swing.assertions.Assertions.assertThat;


public class ToDoGUITest extends AssertJSwingJUnitTestCase {

    /**
     * Here is where UI tests that involve automatically clicking on a button, text field, or check box occur
     * Use the assertJ getting started guide to understand how this works.
     * <p>
     * The name for the task panel where all the tasks are stored is "taskPanel"
     * <p>
     * Button names that assertJ uses to find specific buttons
     * New HomeTask button is named "HomeTaskbutton"
     * New StudyTask button is named "StudyTaskbutton"
     * New CustomTask button is named "WorkTaskbutton"
     * Sort by Alphabetical Button is named "sortByAlfButton"
     * Sort by Completion Button is named "sortByCompButton"
     * Sort by Type button is named "sortByTypeButton"
     * <p>
     * Remove Button names that assertJ uses to find specific remove buttons
     * HomeTask remove button is named "removeHomeTask"
     * StudyTask remove button is named "removeStudyTask"
     * WorkTask remove button is named "removeWorkTask"
     * <p>
     * Input fields that assertJ uses to find specific input fields
     * StudyTask input field is named "studyTaskInputField"
     * WorkTask input field is named "workTaskInputField"
     * HomeTask input field is named "homeTaskInputField"
     * <p>
     * Label names that assertJ uses to find specific labels
     * StudyTask label is named "studyTaskTextLabel"
     * HomeTask label is named "homeTaskTextLabel"
     * Work or CustomTask labels is named "workTaskTextLabel"
     * Total Tasks Completed label is named "totalTasksLabel"
     * <p>
     * Checkbox names that assertJ uses to find specific checkboxes
     * StudyTask completed checkbox is named "studyTaskIsCompleted"
     * HomeTask completed checkbox is named "homeTaskIsCompleted"
     * Work or CustomTask completed check box is named "workTaskIsCompleted"
     * Work or CustomTask is Important check box is named "workTaskIsImportant"
     */
    private FrameFixture window;
    public ToDo todo;

    @Override
    protected void onSetUp() {
        this.todo = GuiActionRunner.execute(() -> new ToDo());
        window = new FrameFixture(robot(), this.todo.getFrame());
        window.show();
    }

    @Test
    public void sortByTaskCompletion_createTwoTasksSetOneAsComplete_CompletedStudyTaskIsNowOnTop() {

        // Click on "New WorkTask" button
        window.button("WorkTaskbutton").click();

        // Click on "New StudyTask" button
        window.button("StudyTaskbutton").click();

        // Click on the checkbox in Study task to mark it complete
        window.checkBox("studyTaskIsCompleted").click();
        // Click on the "Sorted Completed" button
        window.button("sortByCompButton").click();

        /**
         * Use the assertJ assertion to check that the study task is now placed on top of the uncompleted Work task
         * window.panel("taskPanel") finds the taskPanel where the created tasks are stored, we then attach .target() which
         * gets the JPanel object, we then call getComponents() which returns an array of swing components that make up the taskPanel
         * we then get the first index which is supposed to be the Study Task or whatever task is currently at the top of the task panel,
         * we then use isInstanceOf to make sure it's a StudyTask.
         */
        assertThat(window.panel("taskPanel").target().getComponents()[0]).isInstanceOf(StudyTask.class);
    }

    @Test
    public void sortByTaskCompletion_createTwoTasksSetOneAsCompleteThenReverseTheCompletionSorting_UncompletedWorkTaskIsNowOnTop() {

        // Click on "New WorkTask" button
        window.button("WorkTaskbutton").click();

        // Click on "New StudyTask" button
        window.button("StudyTaskbutton").click();

        // Click on the checkbox in Study task to mark it complete
        window.checkBox("studyTaskIsCompleted").click();
        // Click on the "Sorted Completed" button
        window.button("sortByCompButton").click();

        /**
         * Use the assertJ assertion to check that the study task is now placed on top of the uncompleted Work task
         * window.panel("taskPanel") finds the taskPanel where the created tasks are stored, we then attach .target() which
         * gets the JPanel object, we then call getComponents() which returns an array of swing components that make up the taskPanel
         * we then get the first index which is supposed to be the Study Task or whatever task is currently at the top of the task panel,
         * we then use isInstanceOf to make sure it's a StudyTask.
         */
        assertThat(window.panel("taskPanel").target().getComponents()[0]).isInstanceOf(StudyTask.class);
        window.button("sortByCompButton").click();
        assertThat(window.panel("taskPanel").target().getComponents()[0]).isInstanceOf(CustomTask.class);
    }

    @Test
    public void sortByTaskType_createThreeTasksSortByType_NoChangeInTaskOrder() {

        window.button("HomeTaskbutton").click();
        // Click on "New WorkTask" button
        window.button("WorkTaskbutton").click();
        // Click on "New StudyTask" button
        window.button("StudyTaskbutton").click();


        // Click on the "Sorted Completed" button
        window.button("sortByTypeButton").click();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(window.panel("taskPanel").target().getComponents()[0]).isInstanceOf(HomeTask.class);
            softly.assertThat(window.panel("taskPanel").target().getComponents()[1]).isInstanceOf(CustomTask.class);
            softly.assertThat(window.panel("taskPanel").target().getComponents()[2]).isInstanceOf(StudyTask.class);
        });
    }

    @Test
    public void sortByTaskType_createTwoStudyTasksOneWorkTask_WorkTaskOnTop() {

        window.button("StudyTaskbutton").click();
        // Click on "New WorkTask" button
        window.button("StudyTaskbutton").click();
        // Click on "New StudyTask" button
        window.button("WorkTaskbutton").click();

        // Click on the "Sorted Completed" button
        window.button("sortByTypeButton").click();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(window.panel("taskPanel").target().getComponents()[0]).isInstanceOf(CustomTask.class);
            softly.assertThat(window.panel("taskPanel").target().getComponents()[1]).isInstanceOf(StudyTask.class);
            softly.assertThat(window.panel("taskPanel").target().getComponents()[2]).isInstanceOf(StudyTask.class);
        });
    }


    @Test
    public void createAndSortTasksAlphabetically_WorkTaskOnTopStudySecondHomeThird() {
        window.button("HomeTaskbutton").click();
        window.button("StudyTaskbutton").click();
        window.button("WorkTaskbutton").click();

        window.textBox("homeTaskInputField").setText("C");
        window.textBox("studyTaskInputField").setText("B");
        window.textBox("workTaskInputField").setText("A");

        window.button("sortByAlfButton").click();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(window.panel("taskPanel").target().getComponents()[0]).isInstanceOf(CustomTask.class);
        softly.assertThat(window.panel("taskPanel").target().getComponents()[1]).isInstanceOf(StudyTask.class);
        softly.assertThat(window.panel("taskPanel").target().getComponents()[2]).isInstanceOf(HomeTask.class);
    }

    @Test
    public void createThreeTasksAndNameUsingALetterSortByAlphabetical_TasksAreSortedAlphabetically() {
        window.button("HomeTaskbutton").click();
        window.button("StudyTaskbutton").click();
        window.button("WorkTaskbutton").click();

        window.textBox("homeTaskInputField").setText("C");
        window.textBox("studyTaskInputField").setText("B");
        window.textBox("workTaskInputField").setText("A");

        window.button("sortByAlfButton").click();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(window.panel("taskPanel").target().getComponents()[0]).isInstanceOf(CustomTask.class);
        softly.assertThat(window.panel("taskPanel").target().getComponents()[1]).isInstanceOf(StudyTask.class);
        softly.assertThat(window.panel("taskPanel").target().getComponents()[2]).isInstanceOf(HomeTask.class);
    }

    @Test
    public void markWorkTaskComplete_TaskCompletedUpdatesToShow1of1TaskComplete() {
        window.button("WorkTaskbutton").click();
        window.checkBox("workTaskIsCompleted").click();
        window.label("totalTasksLabel").requireText("Total task completed: 1/1");
    }

    @Test
    public void markHomeTaskComplete_TaskCompletedUpdatesToShow1of1TaskComplete() {
        window.button("HomeTaskbutton").click();
        window.checkBox("homeTaskIsCompleted").click();
        window.label("totalTasksLabel").requireText("Total task completed: 1/1");
    }

    @Test
    public void markStudyTaskComplete_TaskCompletedUpdatesToShow1of1TaskComplete() {
        window.button("StudyTaskbutton").click();
        window.checkBox("studyTaskIsCompleted").click();
        window.label("totalTasksLabel").requireText("Total task completed: 1/1");
    }

    @Test
    public void importantCheckBoxOnWorkTaskChecked_SetsBackgroundColorOfTaskToRed_BackgroundIsRed() {
        window.button("WorkTaskbutton").click();
        window.checkBox("workTaskIsImportant").click();
        assertThat(window.panel("taskPanel").target().getComponents()[0].getBackground()).isEqualTo(Color.red);
    }

    @Test
    public void importantCheckBoxOnWorkTaskChecked_isImportantReturnsTrue() {
        window.button("WorkTaskbutton").click();
        window.checkBox("workTaskIsImportant").click();
        CustomTask task = (CustomTask) todo.getTaskList().get(0);
        assertThat(task.isImportant()).isTrue();
    }

    @Test
    public void importantCheckBoxOnWorkTaskCheckedThenUnchecked_isImportantReturnsFalse() {
        window.button("WorkTaskbutton").click();
        window.checkBox("workTaskIsImportant").click();
        window.checkBox("workTaskIsImportant").click();
        CustomTask task = (CustomTask) todo.getTaskList().get(0);
        assertThat(task.isImportant()).isFalse();
    }

    @Test
    public void markWorkTaskImportant_WorkTaskBackgroundRed_UnMarkImportant_BackgroundReturnsToNormal() {
        window.button("WorkTaskbutton").click();
        window.checkBox("workTaskIsImportant").click();
        assertThat(window.panel("taskPanel").target().getComponents()[0].getBackground()).isEqualTo(Color.red);
        window.checkBox("workTaskIsImportant").click();
        assertThat(window.panel("taskPanel").target().getComponents()[0].getBackground().getRGB()).isEqualTo(window.background().target().getRGB());
    }
}
