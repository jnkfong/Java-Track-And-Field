import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 * DataModel Class
 * The main model that controls all the functions of the primary program (Database). The user can store/modify runner information
 * in this database by selecting the appropriate button to add a runner, edit a runner, remove a runner, or view their individual
 * statistics. The runners can be ordered by their various attributes, including their best km pace stat. This database can be
 * saved to and loaded from a file, allowing the user to work on one project continuously without the hassle of re-admitting information.
 * 
 * @author James Fong & Prajvin Jalan
 * @since January 15, 2014
 */
public class DataModel extends Object
{
     //Original list of students
     TreeMap <Integer, Runner> list = new TreeMap <Integer, Runner> (); //Treemap used to save the runners and their info 
     
     //Other collections used to order the list of students (based on if the information is int, string, byte or float)
     TreeMap <Integer, Runner> listInt = new TreeMap <Integer, Runner> (); //TreeMap used to store Runner by int values
     ArrayList <Runner> listString = new ArrayList <Runner> (); //ArrayList used to store Runner by string values
     ArrayList <Runner> listByte = new ArrayList <Runner> (); //ArrayList used to store Runner by byte values
     ArrayList <Runner> listFloat = new ArrayList <Runner> (); //ArrayList used to store Runner by float values
     
     DataView dataView; //Main database view connected to this model of the database 
     StudentView studView; //Instance of the StudentView GUI for the student section
     AddView addView; //Instance of the AddView GUI for the add section of the database 
     EditView editView; //Instance of the EditView GUI for the edit section of the database 
     RemoveView removeView; //Instance of the RemoveView GUI for the remove function
     OrderView orderView; //Instance of the orderView GUI for the order selection

     
     StatsModel statsModel; //Instance of the model class for the statistics viewer
     StatsView statsView; //Instance of the StatsView GUI for the statistics viewer 
     
     JFrame addFrame; //Frame for Adding student 
     JFrame editFrame; //Frame for Editing student
     JFrame removeFrame; //Frame for Removing student
     JFrame studFrame; //Frame for Student view
     JFrame statsFrame; //Frame for Stats view
     JFrame orderFrame; //Frame for Order view
     
     //For the current student whose information is being worked with (added/edited etc)
     int studNum; //Stores temporary student number 
     String last, first, division; //Stores temporary last name, first name and division
     byte age; //Stores temporary age 
     float raceD; //Stores temporary race distance
     int studNumEntered;//For removing or editing a student    
     
     DataPrompt prompt;//Used to check input
     
     //Connects extra classes to this model
     FileIO fileIO = new FileIO (this);
     OrderStudents orderStudents = new OrderStudents (this);
     
     /**Constructor for the DataModel class **/
     public DataModel ()
     {
          super();
          this.prompt = new DataPrompt(this);
     }
     /**Sets DataView as a part of the GUI of this class
       * @param DataView dataView Used to pass in the DataView that would be set as part of the GUI
       * **/
     public void setDataGUI (DataView dataView)
     {
          this.dataView = dataView;
     }
     /**Sets StudView as a part of the GUI of this class
       * @param StudentView studView Used to pass in the StudentView that would be set as part of the GUI
       * **/
     public void setStudGUI (StudentView studView)
     {
          this.studView = studView;
     }
     /**Sets AddView as a part of the GUI of this class
       * @param AddView addView Used to pass in the addView that would be set as part of the GUI
       * **/
     public void setAddGUI (AddView addView)
     {
          this.addView = addView;
     }
     /**Sets EditView as a part of the GUI of this class
       * @param EditView editView Used to pass in the editView that would be set as part of the GUI
       * **/
     public void setEditGUI (EditView editView)
     {
          this.editView = editView;
     }
     /**Sets RemoveView as a part of the GUI of this class
       * @param RemoveView removeView Used to pass in the removeView that would be set as part of the GUI
       * **/
     public void setRemoveGUI (RemoveView removeView)
     {
          this.removeView = removeView;
     }
     /**Sets OrderView as a part of the GUI of this class
       * @param OrderView orderView Used to pass in the orderView that would be set as part of the GUI
       * **/
     public void setOrderGUI (OrderView orderView)
     {
          this.orderView = orderView;
     }
     
     /**sets current student's student number
       *@param int studNum Used to pass in the current student number
       *@param String viewType Used to pass in the view to be changed
       **/
     public void setCurrentStudNum (int studNum, String viewType)
     {
          this.studNum = studNum;
          if (viewType.equals("addView")) {
               this.addView.updateAdd(true, this.addView.studNum, this.addView.last, "");
          }
          else {
               this.editView.updateEdit(true, this.editView.studNum, this.editView.last, "");
          }
     }
     
     /**sets current student's last name
       * @param String last Used to pass in the current last name 
       * @param String viewType Used to pass in the view to be changed
       * **/
     public void setCurrentLast (String last, String viewType)
     {
          this.last = last;
          if (viewType.equals("addView")) {
               this.addView.updateAdd(true, this.addView.last, this.addView.first, "");
          }
          else {
               this.editView.updateEdit(true, this.editView.last, this.editView.first, "");
          }
     }
     
     /**sets current student's first name
       * @param String first Used to pass in the current first name 
       * @param String viewType Used to pass in the view to be changed
       * **/
     public void setCurrentFirst (String first, String viewType)
     {
          this.first = first;
          if (viewType.equals("addView")) {
               this.addView.updateAdd(true, this.addView.first, this.addView.age, "");
          }
          else {
               this.editView.updateEdit(true, this.editView.first, this.editView.age, "");
          }
     }
     
     /**sets current student's age
       * @param byte age Used to pass in the current age
       * @param String viewType Used to pass in the view to be changed
       * **/
     public void setCurrentAge (byte age, String viewType)
     {
          this.age = age;
          if (viewType.equals("addView")) {
               this.addView.updateAdd(true, this.addView.age, this.addView.division, "");
          }
          else {
               this.editView.updateEdit(true, this.editView.age, this.editView.division, "");
          }
     }
     
     /**sets current student's division
       * @param String division Used to pass in the current division
       * @param String viewType USed to pass in the view to be changed
       * **/
     public void setCurrentDivision (String division, String viewType)
     {
          this.division = division;
          if (viewType.equals("addView")) {
               this.addView.updateAdd(true, this.addView.division, this.addView.raceD, "");
          }
          else {
               this.editView.updateEdit(true, this.editView.division, this.editView.raceD, "");
          }
     }
     
    /**sets current student's race distance
       * @param float raceD Used to pass in the current race distance
       * @param String viewType Used to pass in the view to be changed
       * **/
     public void setCurrentRaceD (float raceD, String viewType)
     {
          this.raceD = raceD;
          if (viewType.equals("addView")) {
               this.addView.updateAdd(true, this.addView.raceD, this.addView.enter, "Please click 'Enter' to add the student.");
          }
          else {
               this.editView.updateEdit(true, this.editView.raceD, this.editView.edit, "Please click 'Enter' to edit the student.");
          }
     }
     
     /**sets student number entered (used for editing/removing/opening student statistics)
       * @param int studNumEntered Used to pass in the student number entered by the user
       * @param String viewType Used to pass in the view to be changed
       * **/
     public void setCurrentStudNumEntered (int studNumEntered, String viewType)
     {
          this.studNumEntered = studNumEntered;
          if (viewType.equals("studentView")) {
               this.studView.updateStudent(true, this.studView.inputStud, this.studView.studEntered, "Please click 'Enter' to continue.");
          }
          else {
               this.removeView.updateRemove(true, this.removeView.studentNumber, this.removeView.remove, "Please click 'Remove' to remove the student.");
          }
     }
     
     /**adds student using information set for current student**/
     public void addStudent()
     {
          this.list.put(this.studNum, new Runner (this.studNum, this.last, this.first, this.age, this.division, this.raceD));
          
          this.addFrame.setVisible(false); //hides add frame after student is added
          this.dataView.updateData(); //updates main database view
     }
     
     /**removes student using the specific student number entered**/
     public void removeStudent ()
     {
          this.list.remove(this.studNumEntered);
          
          this.removeFrame.setVisible(false); //hides remove frame after student is removed
          this.dataView.updateData(); //updates main database view
     }
     
     /**edits student with student number (studNumEntered) with new values**/
     public void editStudent()
     {
          Runner temp = new Runner (this.studNum, this.last, this.first, this.age, this.division, this.raceD);
          
          //Add the current runner stats into a temporary runner stats
          temp.runnerStats.addOldPracticeTimes(this.list.get(studNumEntered).runnerStats.practiceTimes);
          temp.runnerStats.addOldRaceTimes(this.list.get(studNumEntered).runnerStats.raceTimes);
          temp.runnerStats.addOldKmPaces(this.list.get(studNumEntered).runnerStats.kmPaces);
          temp.runnerStats.addOldRaceDistances(this.list.get(studNumEntered).runnerStats.raceDistances);
          
          //replaces student with student number (studNumEntered) with set values
          this.list.remove(this.studNumEntered);
          this.list.put(this.studNum, temp);
          
          this.editFrame.setVisible(false); //hides edit frame after student is edited
          this.dataView.updateData(); //updates main database view
     }
     
     /**orders students by student number (same as original default order)**/
     public void orderStudNum()
     {
          for (int currentStudent : this.list.keySet()) {
               this.listInt.put(currentStudent, this.list.get(currentStudent));
          }
          this.list.clear();
          this.list.putAll(this.listInt);
          
          this.dataView.updateData();
     }
     
     /**orders students by last name**/
     public void orderLast()
     {
          this.listString.clear(); //clears list before re-making
          
          for (int currentStudent : this.list.keySet()) {
               this.listString.add(this.list.get(currentStudent));
          }
          
          this.orderStudents.sortString("last");
          this.dataView.updateData("string");
     }
     
     /**orders students by first name**/
     public void orderFirst()
     {
          this.listString.clear(); //clears list before re-making
          
          for (int currentStudent : this.list.keySet()) {
               this.listString.add(this.list.get(currentStudent));
          }
          
          this.orderStudents.sortString("first");
          this.dataView.updateData("string");
     }
     
     /**orders students by age**/
     public void orderAge()
     {
          this.listByte.clear(); //clears list before re-making
          
          for (int currentStudent : this.list.keySet()) {
               this.listByte.add(this.list.get(currentStudent));
          }
          
          this.orderStudents.sortByte();
          this.dataView.updateData("byte");
     }
     
     /**orders students by division**/
     public void orderDivision()
     {
          this.listString.clear(); //clears list before re-making
          
          for (int currentStudent : this.list.keySet()) {
               this.listString.add(this.list.get(currentStudent));
          }
          
          this.orderStudents.sortString("division");
          this.dataView.updateData("string");
     }
     
     /**orders students by race distance**/
     public void orderRaceD()
     {
          this.listFloat.clear(); //clears list before re-making
          
          for (int currentStudent : this.list.keySet()) {
               this.listFloat.add(this.list.get(currentStudent));
          }
          
          this.orderStudents.sortFloat(this.listFloat);
          this.dataView.updateData("float");
     }
     
     /**orders by best km pace within a divison**/
     public void orderBestKmPace()
     {
          
     }
     
     /**Creates add view in frame**/
     public void openAdd()
     {
          this.addFrame = new JFrame("Add Student");
          this.addView = new AddView(this); //creates view and connects to this model
          
          this.addFrame.setContentPane(this.addView);
          this.addFrame.pack();
          this.addFrame.setVisible(true);
          this.addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
     
     /**Creates edit view in frame**/
     public void openEdit()
     {
          this.studFrame.setVisible(false); //hides student number frame after student number is entered
          
          this.editFrame = new JFrame("Edit Student");
          this.editView = new EditView(this, this.studNumEntered); //creates view and connects to this model (and to a certain runner)
          
          this.editFrame.setContentPane(this.editView);
          this.editFrame.pack();
          this.editFrame.setVisible(true);
          this.editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
     
     /**Creates add remove in frame**/
     public void openRemove()
     {
          this.removeFrame = new JFrame("Remove Student");
          this.removeView = new RemoveView(this); //creates view and connects to this model
          
          this.removeFrame.setContentPane(this.removeView);
          this.removeFrame.pack();
          this.removeFrame.setVisible(true);
          this.removeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
     
     /**Creates student view in frame**/
     public void openStudent(String previousButton)
     {
          this.studFrame = new JFrame("Student Number");
          this.studView = new StudentView(this, previousButton); //creates view and connects to this model (and to the previous button clicked)
          
          this.studFrame.setContentPane(this.studView);
          this.studFrame.pack();
          this.studFrame.setVisible(true);
          this.studFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
     
     /**Creates stats view in frame**/
     public void openStats()
     {
          this.studFrame.setVisible(false); //hides student number frame after student number is entered
          
          this.statsFrame = new JFrame(this.list.get(this.studNumEntered).first + " " + this.list.get(this.studNumEntered).last + "'s Runner Statistics");
          
          //creates Stats View and Model and connects to a certain runner from this model
          this.statsModel = new StatsModel(this.list.get(this.studNumEntered), this);
          this.statsView = new StatsView(this.statsModel);
          
          this.statsFrame.setContentPane(this.statsView);
          this.statsFrame.setSize(new Dimension(1000,800));
          this.statsFrame.setVisible(true);
          this.statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
     
     /**Creates order view in frame**/
     public void openOrder()
     {
          this.orderFrame = new JFrame("Order Students");
          
          //creates view and connects to this model
          this.orderView = new OrderView(this);
          
          this.orderFrame.setContentPane(this.orderView);
          this.orderFrame.pack();
          this.orderFrame.setVisible(true);
          this.orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     }
     
     /**Converts a float value to a string value (in the form mm:ss)
       * @param float value     float value that is to be converted
       * @returns     the string value converted from the float value
       */
     public String convertToString (float value)
     {
          //returns "N/A" if there is no value yet (no stats entered for this student)
          if (value == 0.0 || value == Float.POSITIVE_INFINITY) {
               return "N/A";
          }
          //if there is a value, will convert it and return the converted value in the form (mm:ss)
          else {
               String sDigit = Integer.toString((int)(value));
               String sDecimal = Integer.toString((int)((value % 1)*60));
               
               //adds zeros to the beginning of the value if only one digit long
               if (sDigit.length() == 1) {
                    sDigit = "0".concat(sDigit);
               }
               if (sDecimal.length() == 1) {
                    sDecimal = "0".concat(sDecimal);
               }
               
               return (sDigit + ":" + sDecimal);
          }
     }
}