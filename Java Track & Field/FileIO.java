import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.*;

/**
 * FileIO Class
 * The class that handles all the saving and loading of the database. If the user wishes to save to a file, a dialog
 * box prompts them to choose the file name and where they want to save it to (this program saves all objects within 
 * the database program and creates a ".sav" file that contains the information). If the user wishes to load to a file,
 * a dialog box prompts them to choose the file where the information they want to load is contained (all objects in
 * that ".sav" file are transferred to the database program).
 * 
 * @author James Fong & Simon Zhang
 * @since Jan 15, 2014
 */
public class FileIO
{
     JPanel fileContents = new JPanel(); //Used to load file chooser dialog box
     JFileChooser fileChooser = new JFileChooser(); //Used to allow user to load/save a certain file
     File myFile;  //File to be saved in/loaded from        
     FileNameExtensionFilter filter = new FileNameExtensionFilter("SAV files","sav");
     
     int studNum; //Temporary integer to hold student number
     String last, first, division; //Temporary string to hold Student's last name, first name and division
     byte age; //Temporary byte to hold student's age number
     float raceD; //Temporary float to hold student's race distance
     
     DataModel model; //Load the DataModel to gather or input the DataModel's information 
     
     /**Constructor of the FileIO class 
       * @param DataModel model Used to pass in the DataModel to gather or input the DataModel's information 
       **/
     public FileIO (DataModel dataModel)
     {
          super();
          this.model = dataModel;
     }
          
     /**Used to open dialog and returns a certain file chosen by the user
       *@parm String type Used to determine the type if user chose to save or load a file
       *@return File myFile Returns file chosen by the user
       **/
     public File getFile(String type)
     {                  
          fileChooser.setFileFilter(filter);
          
          int fileSelected;
          
          if (type.equals("save")) { //saving a file opens corresponding dialog box
               fileSelected = fileChooser.showSaveDialog(fileContents);
          }
          else { //loading a file opens corresponding dialog box
               fileSelected = fileChooser.showOpenDialog(fileContents);
          }
          
          if(fileSelected == JFileChooser.APPROVE_OPTION)
          {           
               myFile = (fileChooser.getSelectedFile());
          }
          
          return myFile; 
     }
     /**Exports the data from the program into the .sav file**/
     public void save()
     {
          try{  // Catch errors in I/O 
               
               FileOutputStream saveFile = new FileOutputStream(this.getFile("save")+".sav");
               
               // Create an ObjectOutputStream to put objects into save file.
               ObjectOutputStream save = new ObjectOutputStream(saveFile);
               
               //Loop program to save all information in a certain flow format
               if (this.model.list != null)
               {
                    int counter = 1;
                    //save the runner's general information
                    for (int currentStudent : this.model.list.keySet()) {
                         save.writeObject(this.model.list.get(currentStudent).studNum);
                         save.writeObject(this.model.list.get(currentStudent).last);
                         save.writeObject(this.model.list.get(currentStudent).first);
                         save.writeObject(this.model.list.get(currentStudent).age);
                         save.writeObject(this.model.list.get(currentStudent).division);
                         save.writeObject(this.model.list.get(currentStudent).raceD);
                         
                         //Check the number of practice times to loop the amount of practice times to save
                         save.writeObject(this.model.list.get(currentStudent).runnerStats.practiceTimes.size());
                         
                         if (this.model.list.get(currentStudent).runnerStats.practiceTimes != null)
                         {   
                              for(int x = 0; x < this.model.list.get(currentStudent).runnerStats.practiceTimes.size(); x++)
                              {
                                   save.writeObject(this.model.list.get(currentStudent).runnerStats.practiceTimes.get(x));
                              }
                         }
                         
                         //Check the number of race times to loop the amount of race times to save
                         save.writeObject(this.model.list.get(currentStudent).runnerStats.raceTimes.size());
                         
                         if (this.model.list.get(currentStudent).runnerStats.raceTimes != null) 
                         {
                              for(int x = 0; x < this.model.list.get(currentStudent).runnerStats.raceTimes.size(); x++)
                              {
                                   save.writeObject(this.model.list.get(currentStudent).runnerStats.raceTimes.get(x));
                              }
                         }
                         
                         //Check the number of kmpace times to loop the amount of kmpace times to save
                         save.writeObject(this.model.list.get(currentStudent).runnerStats.kmPaces.size());
                         if (this.model.list.get(currentStudent).runnerStats.kmPaces != null) 
                         {
                              for(int x = 0; x < this.model.list.get(currentStudent).runnerStats.kmPaces.size(); x++)
                              {
                                   save.writeObject(this.model.list.get(currentStudent).runnerStats.kmPaces.get(x));
                              }
                         }
                         //Check the number of race distance times to loop the amount of race distances to save
                         save.writeObject(this.model.list.get(currentStudent).runnerStats.raceDistances.size());
                         
                         if (this.model.list.get(currentStudent).runnerStats.raceDistances != null) 
                         {
                              for(int x = 0; x < this.model.list.get(currentStudent).runnerStats.raceDistances.size(); x++)
                              {
                                   save.writeObject(this.model.list.get(currentStudent).runnerStats.raceDistances.get(x));
                              }
                         }
                         //Saves any available comments 
                         save.writeObject(this.model.list.get(currentStudent).runnerStats.comment);
                         
                         //Loop if there are more runners in the list
                         if(counter < this.model.list.size())
                         {
                              save.writeObject("NewRunner");
                              counter++;
                         }
                    }
                    
               }
               save.writeObject("End");
               save.close(); //Close the save file
          }
          catch(Exception exc){
               //**FOR AUTHORIZED DEVELOPER USE ONLY**
               //exc.printStackTrace(); // If there was an error, print the info.
          }
     }
     /**Imports data from the .txt file to display in the program**/
     public void load()
     {
          try{
               this.model.list.clear();
               //Load file into an input stream
               FileInputStream saveFile = new FileInputStream(this.getFile("load"));
               
               // Create an ObjectInputStream to get objects from save file.
               ObjectInputStream save = new ObjectInputStream(saveFile);
               
               //Unwind loop in a format to be read and re add information properly 
               String info = ("start");
               while(info != "End")
               {
                 //Retrieve general information of the runner 
                    studNum = (Integer) save.readObject();
                    last = (String) save.readObject();
                    first = (String) save.readObject();
                    age = (Byte) save.readObject();
                    division = (String) save.readObject();
                    raceD = (Float) save.readObject();
                    Runner addRunner = new Runner (studNum, last, first, age, division, raceD);
                    
                    //Retreive the number of loops to add practice times
                    int size = (Integer) save.readObject();
                    for (int x=0; x<size; x++)
                    {
                         addRunner.runnerStats.practiceTimes.add((Float) save.readObject());
                    }
                    
                    //Retreive the number of loops to add race times
                    size = (Integer) save.readObject();
                    for (int x=0; x<size; x++)
                    {
                         addRunner.runnerStats.raceTimes.add((Float) save.readObject());
                    }
                    
                    //Retreive the number of loops to add knPace times
                    size = (Integer) save.readObject();
                    for (int x=0; x<size; x++)
                    {
                         addRunner.runnerStats.kmPaces.add((Float) save.readObject());
                    }
                    
                    //Retreive the number of loops to add race distances 
                    size = (Integer) save.readObject();
                    for (int x=0; x<size; x++)
                    {
                         addRunner.runnerStats.raceDistances.add((Float) save.readObject());
                    }
                    //Retreive any potential comments of the runner 
                    addRunner.runnerStats.setComment((String)save.readObject());
                    //add runner into the list
                    this.model.list.put(addRunner.studNum, addRunner);
                    
                    //check if there are further runners
                    info = (String) save.readObject();
               }
               save.close(); // This also closes saveFile.
          }
          catch(Exception exc){
               //**FOR AUTHORIZED DEVELOPER USE ONLY**
               //exc.printStackTrace(); // If there was an error, print the info.
               
          }
          this.model.dataView.updateData(); //Update dataView for new information added
     }
}