import javax.swing.*;
import java.awt.*;

/**
 * RemoveView Class
 * The view that allows the user to remove a runner from the database; the user inputs their student number and the
 * runner is removed from the program (if the student number exists).
 * 
 * @author James Fong & Prajvin Jalan
 * @since January 15, 2014
 */
public class RemoveView extends JPanel
{
     DataModel model; //Main database model connected to this view of the database
     JButton remove = new JButton("Remove"); //JButton used to remove the student with the associated student number 
     
     JPanel north = new JPanel(); //JPanel that holds most components
     JPanel south = new JPanel(); //JPanel that holds system message
     
     BorderLayout mainLayout = new BorderLayout(); //Layout of this Remove View
     
     JLabel askRemove = new JLabel ("Enter the Student's Number to be removed:"); //JLabel describing the student number text field
     JTextField studentNumber = new JTextField (5); //JTextField for user to input a student number
     
     JLabel systemMessage = new JLabel(); //JLabel that displays a system message
     
     /** Constructor for the RemoveView class
       * @param DataModel model     Links the main Database model to this view
       */
     public RemoveView (DataModel model)
     {
          this.model = model; //sets model
          this.model.setRemoveGUI(this); //connects this view to the main database model
          this.layoutView(); //sets up this panels view
          this.disableComponents(); //disables all this panel's components
          this.studentNumber.setEnabled(true); //enables the first component in this view
          this.registeredControllers(); //creates this panel's controllers
     }
     
     /**Sets up this Remove View's layout (looks) */
     public void layoutView()
     {
          //sets this view's size and layout
          this.setPreferredSize(new Dimension(700,120));
          this.setLayout(mainLayout);
          
          north.setBorder(BorderFactory.createTitledBorder("Input"));
          south.setBorder(BorderFactory.createTitledBorder("System Message"));
          
          //Action components in upper part of panel
          north.add(askRemove);
          north.add(studentNumber);
          north.add(remove);
          
          //System message in lower part of panel
          south.add(systemMessage);
          
          //adds all panels to the main Remove View panel
          this.add(north, BorderLayout.NORTH);
          this.add(south, BorderLayout.CENTER);
     }
     
     /**Creates controllers for the required components in the Remove View class*/
     public void registeredControllers()
     {
          //remove button controller
          DataButtonController removeStudentClicked = new DataButtonController (this.model, this.remove, "removeStudent");
          this.remove.addActionListener(removeStudentClicked);
          
          //student number text field controller
          DataTextFieldController studNumChanged = new DataTextFieldController (this.model, this.studentNumber, "studNumEntered", "removeView");
          this.studentNumber.addActionListener(studNumChanged);
     }
     
     /** Updates this Remove View based on values passed in: which components to enable/disable (based on user input), and whether to add a message
       * @param boolean correctInput     whether or not the user entered the correct input
       * @param JTextField currentTextField     the current text field being worked with
       * @param JComponent nextComponent     the next component to enable (either a text field or a button)
       * @param String message     displays a message if the user input was incorrect, or none if it was correct
       */
     public void updateRemove (boolean correctInput, JTextField currentTextField, JComponent nextComponent, String message)
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
     
     /**Disables all this Remove View's components*/
     private void disableComponents()
     {
          this.remove.setEnabled(false);
          this.studentNumber.setEnabled(false);
     }
}