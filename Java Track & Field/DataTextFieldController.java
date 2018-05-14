import javax.swing.*;
import java.awt.event.*;

/**
 * DataTextFieldController Class
 * This class is used as a controller for the text fields within the main Database.
 * 
 * @author Prajvin Jalan
 * @since January 15, 2014
 */
public class DataTextFieldController extends Object implements ActionListener
{
     DataModel model; //model linked to the component
     JTextField textField; //current text field linked to the controller
     String type; //the specific type of text field being changed
     String viewType; //the view whose text field is being changed
     
     /**
     * Main constructor for the controller - links the component to the model.
     * @param DataModel model      the model for the Database
     * @param JTextField textField      the text field being linked to the controller
     * @param String type      the type of text field being changed
     * @param String viewType      the type of view whose text field is being changed
     */
     public DataTextFieldController (DataModel model, JTextField textField, String type, String viewType)
     {
          super();
          this.model = model;
          this.textField = textField;
          this.type = type;
          this.viewType = viewType;
     }
     
     /**
     * Updates the database accordingly when a text field is changed (based on the type of text field being changed)
     * @param ActionEvent e      the change event of the text field (an action is performed)
     */
     public void actionPerformed(ActionEvent e)
     {
          if (this.type.equals("studNum")) { //validates the input and, if valid, saves the student number for future use
               this.model.prompt.getInt(this.textField.getText(), this.textField, this.viewType);
          }
          else if (this.type.equals("last")) { //validates the input and, if valid, saves the last name for future use
               this.model.prompt.getString(this.textField.getText(), this.textField, this.type, this.viewType);
          }
          else if (this.type.equals("first")) { //validates the input and, if valid, saves the first name for future use
               this.model.prompt.getString(this.textField.getText(), this.textField, this.type, this.viewType);
          }
          else if (this.type.equals("age")) { //validates the input and, if valid, saves the age for future use
               this.model.prompt.getByte(this.textField.getText(), this.textField, this.viewType);
          }
          else if (this.type.equals("division")) { //validates the input and, if valid, saves the division for future use
               this.model.prompt.getString(this.textField.getText(), this.textField, this.type, this.viewType);
          }
          else if (this.type.equals("raceD")) { //validates the input and, if valid, saves the race distance for future use
               this.model.prompt.getFloat((this.textField.getText()), this.textField, this.viewType);
          }
          else { //validates the input and, if valid, saves the student number for future use (this type of student number is used to open the edit or stats frame)
               this.model.prompt.getInt(this.textField.getText(), this.textField, this.viewType);
          }
     }
}