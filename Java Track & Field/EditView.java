import javax.swing.*;
import java.awt.*;

/**
 * EditView Class
 * The view that allows the user to edit the general information for the runner; including their student number, last name, 
 * first name, age, division, and race distance. This will replace the old runner with the new information (but with their previous
 * statistics and performance information, if any).
 * 
 * @author James Fong & Prajvin Jalan
 * @since January 15, 2014
 */
public class EditView extends JPanel
{
     DataModel model; //Main database model connected to this view of the database
     
     JButton edit = new JButton("Edit"); //JButton used to edit the student's information
     
     int studNumEntered; //used to input student's values into appropriate componenents
     
     JLabel askStud = new JLabel("Student #: "); //JLabel describing student number text field
     JLabel askLast = new JLabel("Last Name: "); //JLabel describing student last name text field
     JLabel askFirst = new JLabel("First Name: "); //JLabel describing student first name text field
     JLabel askAge = new JLabel("Age: "); //JLabel describing student age text field
     JLabel askDivision = new JLabel("Division: "); //JLabel describing student division text field
     JLabel askRaceD = new JLabel("Race Distance: "); //JLabel describing student race distance text field
     
     JLabel systemMessage = new JLabel(); //JLabel that displays the system message
     
     JTextField studNum = new JTextField(6); //JTextField for user to input student number
     JTextField last = new JTextField(10); //JTextField for user to input student last name
     JTextField first = new JTextField(10); //JTextField for user to input student first name
     JTextField age = new JTextField(2); //JTextField for user to input student age
     JTextField division = new JTextField(10); //JTextField for user to input student division
     JTextField raceD = new JTextField(4); //JTextField for user to input student race distance
     
     JPanel north = new JPanel(); //JPanel that holds most components (labels, textfields, button)
     JPanel south = new JPanel(); //JPanel that holds the system message label
     
     BorderLayout mainLayout = new BorderLayout(); //Layout for this Edit View
     
     /** Constructor for the EditView class
       * @param DataModel model     Links the main Database model to this view
       * @param int studNumEntered     Links the runner's student number whose information is to be displayed in text fields
       */
     public EditView (DataModel model, int studNumEntered)
     {
          this.model = model; //sets this views model
          this.studNumEntered = studNumEntered; //sets this view's runner's student number
          this.model.setEditGUI(this); //connects this view to the main database model
          this.layoutView(); //sets up this panel's view
          this.disableComponents(); //disables all components
          this.studNum.setEnabled(true); //enables the first component
          this.registeredControllers(); //creates controllers for appropriate components
     }
     
     /**Sets up this Edit View's layout (looks) */
     public void layoutView()
     {
          //sets this view's size and layout
          this.setPreferredSize(new Dimension(1100,120));
          this.setLayout(mainLayout);
          
          north.setBorder(BorderFactory.createTitledBorder("Input"));
          south.setBorder(BorderFactory.createTitledBorder("System Message"));
          
          //Inputs and action components put to the upper part of the panel
          north.add(askStud);
          north.add(studNum);
          
          north.add(askLast);
          north.add(last);
          
          north.add(askFirst);
          north.add(first);
          
          north.add(askAge);
          north.add(age);
          
          north.add(askDivision);
          north.add(division);
          
          north.add(askRaceD);
          north.add(raceD);
          
          north.add(edit);
          
          //System message put to the lower part of the panel
          south.add(systemMessage);
          
          //adds all panels to the main Edit View panel
          this.add(north, BorderLayout.NORTH);
          this.add(south, BorderLayout.CENTER);
          
          //Pre-sets the text in the text fields to the runner's information (corresponding to the student number passed in when this view is created)
          this.studNum.setText(Integer.toString(this.model.list.get(this.studNumEntered).studNum));
          this.last.setText(this.model.list.get(this.studNumEntered).last);
          this.first.setText(this.model.list.get(this.studNumEntered).first);
          this.age.setText(Byte.toString(this.model.list.get(this.studNumEntered).age));
          this.division.setText(this.model.list.get(this.studNumEntered).division);
          this.raceD.setText(Float.toString(this.model.list.get(this.studNumEntered).raceD));
     }
     
     /**Creates controllers for the required components in the Edit View class*/
     public void registeredControllers()
     {
          //edit button controller
          DataButtonController editStudentClicked = new DataButtonController (this.model, this.edit, "editStudent");
          this.edit.addActionListener(editStudentClicked);
          
          //student number text field controller
          DataTextFieldController studNumChanged = new DataTextFieldController (this.model, this.studNum, "studNum", "editView");
          this.studNum.addActionListener(studNumChanged);
          
          //student last name text field controller
          DataTextFieldController lastChanged = new DataTextFieldController (this.model, this.last, "last", "editView");
          this.last.addActionListener(lastChanged);
          
          //student first name text field controller
          DataTextFieldController firstChanged = new DataTextFieldController (this.model, this.first, "first", "editView");
          this.first.addActionListener(firstChanged);
          
          //student age text field controller
          DataTextFieldController ageChanged = new DataTextFieldController (this.model, this.age, "age", "editView");
          this.age.addActionListener(ageChanged);
          
          //student division text field controller
          DataTextFieldController divisionChanged = new DataTextFieldController (this.model, this.division, "division", "editView");
          this.division.addActionListener(divisionChanged);
          
          //student race distance text field controller
          DataTextFieldController raceDChanged = new DataTextFieldController (this.model, this.raceD, "raceD", "editView");
          this.raceD.addActionListener(raceDChanged);
     }
     
     /** Updates this Edit View based on values passed in: which components to enable/disable (based on user input), and whether to add a message
       * @param boolean correctInput     whether or not the user entered the correct input
       * @param JTextField currentTextField     the current text field being worked with
       * @param JComponent nextComponent     the next component to enable (either a text field or a button)
       * @param String message     displays a message if the user input was incorrect, or none if it was correct
       */
     public void updateEdit (boolean correctInput, JTextField currentTextField, JComponent nextComponent, String message)
     {
          //if user input was not correct, will highlight current text field (for user to re-enter information)
          if (correctInput == false) {
               currentTextField.selectAll();
               systemMessage.setText(message);
          }
          //if user input was correct, will disable current component, enable next component, and display a message
          else {
               currentTextField.setEnabled(false);
               nextComponent.setEnabled(true);
               nextComponent.requestFocusInWindow();
               systemMessage.setText(message);
          }
     }
     
     /**Disables all components in this Edit View**/
     private void disableComponents()
     {
          this.studNum.setEnabled(false);
          this.last.setEnabled(false);
          this.first.setEnabled(false);
          this.age.setEnabled(false);
          this.division.setEnabled(false);
          this.raceD.setEnabled(false);
          this.edit.setEnabled(false);
     }
}