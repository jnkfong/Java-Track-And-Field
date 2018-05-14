import javax.swing.*;
import java.awt.event.*;

/**
 * DataButtonController Class
 * This class is used as a controller for the buttons within the main Database.
 * 
 * @author Prajvin Jalan
 * @since January 15, 2014
 */
public class DataButtonController extends Object implements ActionListener
{
     DataModel model; //model linked to the component
     JButton button; //current button linked to the controller
     String type; //the specific type of button being clicked
     String previousButton; //the previous button clicked (if any) that affects this buttons action
     
     /**
     * Main constructor for the controller - links the component to the model.
     * @param DataModel model      the model for the Database
     * @param JButton button      the button being linked to the controller
     * @param String type      the type of button being clicked
     */
     public DataButtonController (DataModel model, JButton button, String type)
     {
          super();
          this.model = model;
          this.button = button;
          this.type = type;
     }
     
     /**
     * Secondary constructor for the controller - links the component to the model; used if a previous button affects the action of the current button
     * @param DataModel model      the model for the Database
     * @param JButton button      the button being linked to the controller
     * @param String type      the type of button being clicked
     * @param String previousButton      the previous button clicked that affects the action of the current button
     */
     public DataButtonController (DataModel model, JButton button, String type, String previousButton)
     {
          super();
          this.model = model;
          this.button = button;
          this.type = type;
          this.previousButton = previousButton;
     }
     
     /**
     * Updates the database accordingly when a button is clicked (based on the type of button being clicked)
     * @param ActionEvent e      the change event of a button (an action is performed)
     */
     public void actionPerformed(ActionEvent e)
     {
          if (this.type.equals("add")) { //opens frame to add a student
               this.model.openAdd();
          }
          else if (this.type.equals("remove")) { //opens frame to remove a student
               this.model.openRemove();
          }
          else if (this.type.equals("edit")) { //opens student frame (to input student number); stores that edit was clicked
               this.model.openStudent("edit");
          }
          else if (this.type.equals("stats")) { //opens student frame (to input student number); stores that stats was clicked
               this.model.openStudent("stats");
          }
          else if (this.type.equals("addStudent")) { //adds the student
               this.model.addStudent();
          }
          else if (this.type.equals("editStudent")) { //edits the student
               this.model.editStudent();
          }
          else if (this.type.equals("removeStudent")) { //removes the student
               this.model.removeStudent();
          }
          else if (this.type.equals("enterStudent")) { //opens edit frame or stats frame based on previous button clicked
               if (this.previousButton.equals("edit")) {
                    this.model.openEdit();
               }
               if (this.previousButton.equals("stats")){
                    this.model.openStats();
               }
          }
          else if (this.type.equals("order")) { //opens order students frame
               this.model.openOrder();
          }
          else if (this.type.equals("orderStudNum")) { //orders students by student number
               this.model.orderStudNum();
          }
          else if (this.type.equals("orderLast")) { //orders students by their last name
               this.model.orderLast();
          }
          else if (this.type.equals("orderFirst")) { //orders students by their first name
               this.model.orderFirst();
          }
          else if (this.type.equals("orderAge")) { //orders students by their age
               this.model.orderAge();
          }
          else if (this.type.equals("orderDivision")) { //orders students by their division
               this.model.orderDivision();
          }
          else if (this.type.equals("orderRaceD")) { //orders students by their race distance
               this.model.orderRaceD();
          }
          else if (this.type.equals("orderBestKmPace")) { //orders students by their best km pace
               this.model.orderBestKmPace();
          }
          else if (this.type.equals("save")) { //saves the file
               this.model.fileIO.save();
          }
          else { //loads the file
               this.model.fileIO.load(); 
          }
          
     }
}