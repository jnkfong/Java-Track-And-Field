import javax.swing.*;
import java.awt.event.*;

/**
 * StatsButtonController Class
 * This class is used as a controller for the buttons within the Runner Statistics program.
 * 
 * @author Prajvin Jalan
 * @since January 15, 2014
 */
public class StatsButtonController extends Object implements ActionListener
{
     StatsModel model; //model linked to the component
     JButton button; //current button linked to the controller
     String type; //the specific type of button being clicked
     
     /**
     * Main constructor for the controller - links the component to the model.
     * @param StatsModel model      the model for the Stats View
     * @param JButton button      the button being linked to the controller
     * @param String type      the type of button being clicked
     */
     public StatsButtonController (StatsModel model, JButton button, String type)
     {
          super();
          this.model = model;
          this.button = button;
          this.type = type;
     }
     
     /**
     * Updates the stats view accordingly when a button is clicked (based on the type of button being clicked)
     * @param ActionEvent e      the change event of a button (an action is performed)
     */
     public void actionPerformed(ActionEvent e)
     {
          if (this.type.equals("add")) { //opens frame to add stats
               this.model.openAddStats();
          }
          else if (this.type.equals("remove")) { //opens frame to remove stats
               this.model.openRemoveStats();
          }
          else if (this.type.equals("addStat")) { //adds the stats
               this.model.addStats();
          }
          else if (this.type.equals("removeStat")) { //removes the stats
               this.model.removeStats();
          }
     }
}