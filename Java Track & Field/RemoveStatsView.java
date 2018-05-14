import javax.swing.*;
import java.awt.*;

/**
 * RemoveStatsView Class
 * The view that allows the user to remove statistics information for the runner through their practice time and race time logs.
 * 
 * @author James Fong & Prajvin Jalan
 * @since January 15, 2014
 */
public class RemoveStatsView extends JPanel
{
     StatsModel model; //Main Stats model connected to this view of the student Stats
     JButton remove = new JButton("Remove"); //JButton that removes the stats from the runner's stats
     
     JLabel askPracticeTime = new JLabel("Log of Practice Time: ");  //JLabel describing practice time log text field
     JLabel askRaceTime = new JLabel("Log of Race Time: "); //JLabel describing race time log text field
     
     JTextField practiceTime = new JTextField(10); //JTextField for user to input practice time log
     JTextField raceTime = new JTextField(10); //JTextField for user to input race time log
     
     JLabel systemMessage = new JLabel(); //JLabel that displays system message
     
     JPanel north = new JPanel(); //JPanel for most components (inputs, button)
     JPanel south = new JPanel(); //JPanel for system message label
     
     BorderLayout mainLayout = new BorderLayout(); //Layout for this Remove Stats View
     
     /** Constructor for the RemoveStatsView class
       * @param StatsModel model     Links the main Stats model to this view
       */
     public RemoveStatsView (StatsModel model)
     {
          this.model = model; //sets this view's model
          this.model.setRemoveStatsGUI(this); //connects this view to the stats model
          this.layoutView(); //sets this view's looks
          this.disableComponents(); //disables all components
          this.practiceTime.setEnabled(true); //enables first component
          this.registeredControllers(); //creates all component controllers
     }
     
     /**Sets up this RemoveStatsView's layout (looks) */
     public void layoutView()
     {
          //sets this view's size and layout
          this.setPreferredSize(new Dimension(800,120));
          this.setLayout(mainLayout);
          
          north.setBorder(BorderFactory.createTitledBorder("Input"));
          south.setBorder(BorderFactory.createTitledBorder("System Message"));
          
          //initial message for removing a practice time
          systemMessage.setText("Please enter the log of the practice time you would like to remove. Leave empty if you would not like to remove a practice time.");
          
          //adds components to upper panel
          north.add(askPracticeTime);
          north.add(practiceTime);
          
          north.add(askRaceTime);
          north.add(raceTime);
                              
          north.add(remove);
          
          //adds system message label to lower panel
          south.add(systemMessage);
          
          //adds all panels to this main view 
          this.add(north, BorderLayout.NORTH);
          this.add(south, BorderLayout.CENTER);
     }
     
     /**Creates controllers for the required components in the Remove Stats View class*/
     public void registeredControllers()
     {
          //remove stat button controller
          StatsButtonController removeStatClicked = new StatsButtonController (this.model, this.remove, "removeStat");
          this.remove.addActionListener(removeStatClicked);
          
          //log practice time text field controller
          StatsTextFieldController practiceTimeChanged = new StatsTextFieldController (this.model, this.practiceTime, "indexPrac");
          this.practiceTime.addActionListener(practiceTimeChanged);
          
          //log race time text field controller
          StatsTextFieldController raceTimeChanged = new StatsTextFieldController (this.model, this.raceTime, "indexRace");
          this.raceTime.addActionListener(raceTimeChanged);
     }
     
     /** Updates this Remove Stats View based on values passed in: which components to enable/disable (based on user input), and whether to add a message
       * @param boolean correctInput     whether or not the user entered the correct input
       * @param JTextField currentTextField     the current text field being worked with
       * @param JComponent nextComponent     the next component to enable (either a text field or a button)
       * @param String message     displays a message if the user input was incorrect, or none if it was correct
       */
     public void update (boolean correctInput, JTextField currentTextField, JComponent nextComponent, String message)
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
     
     /** Disables all this view's components*/
     private void disableComponents ()
     {
          this.practiceTime.setEnabled(false);
          this.raceTime.setEnabled(false);
          this.remove.setEnabled(false);
     }
}