
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;


public class ToDoGUITest extends AssertJSwingJUnitTestCase {

    /**
     * Here is where UI tests that involve automatically clicking on a button, text field, or check box occurs
     * Use the assertJ getting started guide to understand how this works.
     *
     * Button Names that assertJ uses to find specific buttons
     * New HomeTask button is named "HomeTaskbutton"
     * New StudyTask button is named "StudyTaskbutton"
     * New CustomTask button is named "CustomTaskbutton"
     * Sort by Alphabetical Button is named "sortByAlfButton"
     * Sort by Completion Button is named "sortByCompButton"
     * Sort by Type button is named "sortByTypeButton"
     *
     * Remove Buttons names that assertJ uses to find specific remove buttons
     * HomeTask remove button is named "removeHomeTask"
     * StudyTask remove button is named "removeStudyTask"
     * WorkTask remove button is named "removeWorkTask"
     *
     * Input fields that assertJ uses to find specific input fields
     * StudyTask input field is named "studyTaskInputField"
     * WorkTask input field is named "workTaskInputField"
     * HomeTask input field is named "homeTaskInputField"
     *
     * Label Names that assertJ uses to find specific labels
     * StudyTask label is named "studyTaskTextLabel"
     * HomeTask label is named "homeTaskTextLabel"
     * Work or CustomTask labels is named "workTaskTextLabel"
     * Total Tasks Completed label is named "totalTasksLabel"
     *
     * Checkbox names that assertJ uses to find specific checkboxes
     * StudyTask completed checkbox is named "studyTaskIsCompleted"
     * HomeTask completed checkbox is named "homeTaskIsCompleted"
     * Work or CustomTask completed check box is named "workTaskIsCompleted"
     * Work or CustomTask is Important check box is named "workTaskIsImportant"
     */
    private FrameFixture window;
    @Override
    protected void onSetUp(){
        ToDo todo = GuiActionRunner.execute(() -> new ToDo());
        window = new FrameFixture(robot(), todo.getFrame());
        window.show();
    }

    @Test
    public void sortByCompletion(){
        window.button("HomeTaskbutton").click();
        window.button("StudyTaskbutton").click();

    }

}
