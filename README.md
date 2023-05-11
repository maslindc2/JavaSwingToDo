# Team 4 Java Swing ToDo
## Original Source Code
[TODO-JavaSwing by RaymondSWE](https://github.com/RaymondSWE/TODO-JavaSwing)


## Running and Building the project
### Background Info
The projects file structure may look different than the source code.  The original developers used eclipse to create the project. The original file structure prevented Maven from reading the Java classes that were imported as well as had trouble locating the main class.  To solve this we have restructured it so anyone can simply open the project up in Intellij and they should be set. Our restructing has had no impact on program's functionality.

### Dependencies
- JDK 20 we recommend using Amazon Corretto 20, other JDKs may or may not work.  Amazon Corretto 20 is the only JDK we've tested.
- Intellij Community or Ultimate. While other IDE's may work you might have problems with the folder structure. 
- Testing requires Maven, JUnit, and Mockito. Intellij should fetch these packages automatically using the pom.xml file.

### Opening/Running the Project
1. Open the project folder with Intellij.
2. Mark the sources and test root folders in the Intellij project folder explorer.
	1. To mark the sources root directory right click on the folder labeled "src", hover over the "Mark Directory As" option and click on "Sources Root"
	2. To mark the test directory root, right click on the folder labeled "test", hover over the "Mark Directory As" option and click on "Test Sources Root"
3. If you would like to run the original project first, open the file ToDo under the "src" folder.  This file contains the main method for the project. Simply run this file by selecting the Run option at the top of Intellij and click on Run "ToDo.java"
4. If you would to run the tests open the file ToDoTest.java located under the "test" folder. Simply run the test by selecting the Run option at the top of Intellij and click on Run "ToDoTest.java"

## What's left to test? 
In this section we have a checklist with all of the features we are planning on testing for internal unit tests and UI testing.

### How do we write automated UI tests?
To write tests for specific UI elements or buttons that you are not able to use a getter on, you have to use [assertJ](https://joel-costigliola.github.io/assertj/assertj-swing-getting-started.html). Automated UI tests are located in the file **ToDoGUITest.java** under the test folder. I have a comment in there with all of the names that assertJ uses to find specific elements. *Note this file will be under the UI-Tests branch until grading for TP2 is completed.*

### Tests to Implement
- [ ] Test task naming, can we change the name of a task to say something else other than New Study/Home/Work Task
- [ ] Testing sorting methods
	- [x] Test the sort by completed button sorts completed tasks on the GUI. Should show a completed task moving to the top of the UI and the uncompleted tasks should be below the completed one. 
	- [ ] Test the sort by alphabetically button sorts the tasks on the GUI in alphabetical order.
	- [ ] Test the sort by type button sorts the tasks on the GUI by task type. 
	- [ ] Test if the text "Total task completed: 0/0" does not go negative when you create a task, remove it, and then call one of the sort methods. Hint this will cause the string to say "Total task completed: 0/-1" this test should fail.
- [x] Test Completed checkbox on tasks
	- [x] Test if the checkbox marks the work task as completed should update the string on the bottom of the GUI to reflect the number of tasks completed.
	- [x] Test if the checkbox marks the home task as completed should update the string on the bottom of the GUI to reflect the number of tasks completed.
	- [x] Test if the checkbox marks the study task as completed should update the string on the bottom of the GUI to reflect the number of tasks completed.
- [ ] Test if the checkbox Important on the Work Task sets the background color of the task to RED.
