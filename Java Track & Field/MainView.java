import javax.swing.*;
import java.awt.*;

/**
 * MainView Class
 * The view for the main Menu when the program is opened.
 * 
 * @author James Fong, Prajvin Jalan & Simon Zhang
 * @since January 15, 2014
 */
public class MainView extends JPanel
{
     private MainModel mainModel; //Main menu model connected to this main view
     
     JPanel imagePanel = new JPanel(); //JPanel used to hold image from file 
     JPanel menuPanel = new JPanel(); //JPanel used to hold the menu buttons
     
     JButton database = new JButton("Database"); //JButton used to start the database
     JButton quit = new JButton("Quit"); //JButton used to quit the database
     
     JLabel imageLabel = new JLabel(new ImageIcon("RHSS_XCountryTrackerIcon.png")); //JLabel used to display image from file 
     
     BorderLayout mainLayout = new BorderLayout(); //BorderLayout used to organize the layout of the GUI
     
     /**Constructor for the MainView class
       *@param MainModel mainModel Used to pass in the current mainModel for change 
       **/
     public MainView (MainModel mainModel)
     {
          this.mainModel = mainModel;
          this.mainModel.setGUI(this);
          this.layoutView();
          this.registeredControllers();
     }
     
     /**Draws the initial layout of the main menu GUI**/
     private void layoutView()
     {
          this.setLayout(mainLayout);
          
          imagePanel.add(imageLabel);
          menuPanel.add(database);
          menuPanel.add(quit);
          
          this.add(imagePanel, BorderLayout.NORTH);
          this.add(menuPanel, BorderLayout.SOUTH);
     }
     
     /**Assigns listeners to the required components of the MainView class**/
     private void registeredControllers()
     {
          MainButtonController databaseClicked = new MainButtonController (this.mainModel, this.database, "database");
          this.database.addActionListener (databaseClicked);
          
          MainButtonController quitClicked = new MainButtonController (this.mainModel, this.quit, "quit");
          this.quit.addActionListener (quitClicked);
     }
     
}