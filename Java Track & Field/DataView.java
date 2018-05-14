import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;

/**
 * DataView Class
 * The view for the main Database program. Includes the buttons for all the program functions (add student, remove student,
 * edit student, student stats, order students, save and load) and a table to display the various runner information.
 * 
 * @author James Fong, Prajvin Jalan & Simon Zhang
 * @since January 15, 2014
 */
public class DataView extends JPanel
{
     DataModel model; //Main database model connected to this view of the database
     
     JButton order = new JButton ("Order Options");//JButton that opens another JFrame to order runners
     JButton save = new JButton("Save"); //JButton used to save data from the program
     JButton load = new JButton("Load"); //JButton used to save data from the program
     JButton add = new JButton("Add Student"); //JButton that opens another JFrame to add a runner
     JButton remove = new JButton("Remove Student");//JButton that opens another JFrame to remove a runner
     JButton edit = new JButton("Edit Student");//JButton that opens another JFrame to edit a runner
     JButton stats = new JButton("Student Stats");//JButton that opens another JFrame to view the stats of a runner
     
     Object [][] rowData = null; //Initial row stats for all tables
     Object [] columnNames = {"Student Number", "Last Name", "First Name", "Age", "Division", "Race Distance (km)", "Best Km Pace (min/km)"}; //Used to display the headers on the JTable   
     DefaultTableModel dtm = new DefaultTableModel(rowData, columnNames); //Stores the statistics in view of a table format with a header
     JTable studentInformation = new JTable(dtm); //Displays the information found in the DefaultTableModel in a table format
     
     BorderLayout dataLayout = new BorderLayout(); //BorderLayout used to format the GUI to be presentable
     BorderLayout southLayout = new BorderLayout(); //BorderLayout used to format the GUI to be presentable
     
     JPanel mainButtons = new JPanel(); //JPanel used to hold all the main buttons
     JPanel table = new JPanel(); //JPanel used to hold the JTable     
     JPanel orderStudents = new JPanel(); //JPanel used to hold JButton for the selection of order of runners
     JPanel fileOptions = new JPanel(); //JPanel used to hold the save and load option
     JPanel otherButtons = new JPanel(); //JPanel used to hold all the bottom panels
     
     /**Constructor for the DataView class
       *@param DataModel model Used to pass in the current dataModel that is being modified 
       **/
     public DataView (DataModel model)
     {
          this.setPreferredSize (new Dimension (1000,650)); //sets this panels size
          this.model = model; //sets model
          this.model.setDataGUI(this); //connects this view to the model
          this.layoutView(); //sets up view of this panel
          this.registeredControllers(); //registers all component controllers
          this.updateData(); //updates this main view
     }
     
     /**Draws the initial layout of DataView GUI**/
     public void layoutView()
     {
          this.setLayout(dataLayout);
          
          //Adds the main buttons onto the mainButton panel
          mainButtons.setBorder(BorderFactory.createTitledBorder("Options"));
          mainButtons.add(add);
          mainButtons.add(remove);
          mainButtons.add(edit);
          mainButtons.add(stats);
          
          //Sets the length of each of the columns on the JTable
          studentInformation.getColumnModel().getColumn(0).setPreferredWidth(120);
          studentInformation.getColumnModel().getColumn(1).setPreferredWidth(120);
          studentInformation.getColumnModel().getColumn(2).setPreferredWidth(120);
          studentInformation.getColumnModel().getColumn(3).setPreferredWidth(45);
          studentInformation.getColumnModel().getColumn(4).setPreferredWidth(90);
          studentInformation.getColumnModel().getColumn(5).setPreferredWidth(120);
          studentInformation.getColumnModel().getColumn(6).setPreferredWidth(150);
          
          //creates the table with the scroll pane and header colour
          studentInformation.setEnabled(false);
          table.setBorder(BorderFactory.createTitledBorder("Student Information"));
          JTableHeader header = studentInformation.getTableHeader();
          header.setBackground(Color.LIGHT_GRAY);
          JScrollPane scrollPane = new JScrollPane(studentInformation);
          scrollPane.setPreferredSize(new Dimension(790,400));
          studentInformation.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
          table.add(scrollPane);
          
          //Adds the order button into the order panel
          orderStudents.setBorder(BorderFactory.createTitledBorder("Order Students"));
          orderStudents.add(order);
          
          //Adds the save and loat button into the fileOption panel
          fileOptions.setBorder(BorderFactory.createTitledBorder("File Options"));
          fileOptions.add(save);
          fileOptions.add(load);
          
          //Associate certain panels into the otherButtons category panel
          otherButtons.setLayout(southLayout);
          otherButtons.add(orderStudents, BorderLayout.NORTH);
          otherButtons.add(fileOptions, BorderLayout.SOUTH);
          
          this.add(mainButtons, BorderLayout.NORTH);
          this.add(table, BorderLayout.CENTER);
          this.add(otherButtons, BorderLayout.SOUTH);
     }
     
     /**Assigns listeners to the required components in the Data View class**/
     public void registeredControllers()
     {
          //add button controller
          DataButtonController addClicked = new DataButtonController (this.model, this.add, "add");
          this.add.addActionListener(addClicked);
          
          //remove button controller
          DataButtonController removeClicked = new DataButtonController (this.model, this.remove, "remove");
          this.remove.addActionListener(removeClicked);
          
          //edit button controller
          DataButtonController editClicked = new DataButtonController (this.model, this.edit, "edit");
          this.edit.addActionListener(editClicked);
          
          //stats button controller
          DataButtonController statsClicked = new DataButtonController (this.model, this.stats, "stats");
          this.stats.addActionListener(statsClicked);
          
          //order button controller
          DataButtonController orderClicked = new DataButtonController (this.model, this.order, "order");
          this.order.addActionListener(orderClicked);
          
          //save button controller
          DataButtonController saveClicked = new DataButtonController (this.model, this.save, "save");
          this.save.addActionListener(saveClicked);
          
          //load button controller
          DataButtonController loadClicked = new DataButtonController (this.model, this.load, "load");
          this.load.addActionListener(loadClicked);
     }
     
     /**Redraws DataView **/
     public void updateData()
     {
          //deletes original table
          if (dtm.getRowCount() > 0) {
               for (int i = dtm.getRowCount(); i > 0; i--) {
                    dtm.removeRow(i - 1);
               }
          }
          
          //recreates table with all runner information
          for (int studNum : this.model.list.keySet()) {
               dtm.addRow (new Object [] {this.model.list.get(studNum).studNum, this.model.list.get(studNum).last,
                    this.model.list.get(studNum).first, this.model.list.get(studNum).age, 
                    this.model.list.get(studNum).division, this.model.list.get(studNum).raceD, 
                    this.model.convertToString(this.model.list.get(studNum).runnerStats.getBest("kmPace"))});
          }
          
          this.updateButtons();
     }
     
     /**Updates Data View using ordered list
       * @param String type     the type of list to use to display the table
       */ 
     public void updateData(String type)
     {
          //deletes original table
          if (dtm.getRowCount() > 0) {
               for (int i = dtm.getRowCount(); i > 0; i--) {
                    dtm.removeRow(i - 1);
               }
          }
          
          //Recreates table with all runner information (order to output is determined by type)
          
          //if ordered based on a string value
          if (type.equals("string")) {
               for (int x = 0; x < this.model.listString.size(); x++) {
                    dtm.addRow (new Object[] {this.model.listString.get(x).studNum, 
                         this.model.listString.get(x).last, this.model.listString.get(x).first, 
                         this.model.listString.get(x).age, this.model.listString.get(x).division, 
                         this.model.listString.get(x).raceD, 
                         this.model.convertToString(this.model.listString.get(x).runnerStats.getBest("kmPace"))});
               }
          }
          //if ordered based on byte value
          else if (type.equals("byte")) {
               for (int x = 0; x < this.model.listByte.size(); x++) {
                    dtm.addRow (new Object[] {this.model.listByte.get(x).studNum, 
                         this.model.listByte.get(x).last, this.model.listByte.get(x).first, 
                         this.model.listByte.get(x).age, this.model.listByte.get(x).division, 
                         this.model.listByte.get(x).raceD, 
                         this.model.convertToString(this.model.listByte.get(x).runnerStats.getBest("kmPace"))});
               }
          }
          //if ordered based on float value
          else {
               for (int x = 0; x < this.model.listFloat.size(); x++) {
                    dtm.addRow (new Object[] {this.model.listFloat.get(x).studNum, 
                         this.model.listFloat.get(x).last, this.model.listFloat.get(x).first, 
                         this.model.listFloat.get(x).age, this.model.listFloat.get(x).division, 
                         this.model.listFloat.get(x).raceD,
                         this.model.convertToString(this.model.listFloat.get(x).runnerStats.getBest("kmPace"))});
               }
          }
          
          this.updateButtons();
     }
     
     /**updates data with specific buttons enabled**/
     private void updateButtons()
     {
          //disables remove, edit, stats, and order buttons if there are no students in the list
          if (this.model.list.size() == 0) {
               this.remove.setEnabled(false);
               this.edit.setEnabled(false);
               this.stats.setEnabled(false);
               this.order.setEnabled(false);
          }
          //enables remove, edit, and stats buttons if there is a student in the list, but disables order button since
          //list is only one student
          else if (this.model.list.size() == 1) {
               this.remove.setEnabled(true);
               this.edit.setEnabled(true);
               this.stats.setEnabled(true);
               this.order.setEnabled(false);
          }
          //enables remove, edit, stats, and order buttons if there are students in the list
          else {
               this.remove.setEnabled(true);
               this.edit.setEnabled(true);
               this.stats.setEnabled(true);
               this.order.setEnabled(true);
          }
     }
}