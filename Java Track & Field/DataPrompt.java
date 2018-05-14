import javax.swing.*;

/**
 * DataPrompt Class
 * This class is used to validate information input by the user in the main Database program. A system message prompts
 * them to re-input their information if it is incorrect (the view is updated accordingly).
 * 
 * @author Prajvin Jalan
 * @since January 15, 2014
 */
public class DataPrompt extends Object
{
     DataModel model; //connects this class to the main Database model
     
     /** Constructor for the DataPrompt class
       * @param StatsModel model     links this class to the model
       */
     public DataPrompt (DataModel model)
     {
          super();
          this.model = model;
     }
     
     /** Check validity for an int value (for student number)
       * @param prompt     The user's input that is passed in
       * @param currentTextField     The current text field being worked with
       * @param viewType     The current view that is being worked with (add, edit, student, remove)
       */
     public void getInt (String prompt, JTextField currentTextField, String viewType)
     {
          String message = null; //system message to be displayed
          int data = this.tryParseInt(prompt); //parses typed data as integer
          
          //not an integer
          if (data == 0) {
               message = "Your input is invalid. Please enter a valid number.";
               this.updateView(currentTextField, message, viewType);
          }
          //not a student number
          else if (data < 100000 || data > 999999) {
               message = "The student number you have entered is invalid. Please enter a 6-digit student number.";
               this.updateView(currentTextField, message, viewType);
          }
          //is a student number
          else {
               //UPDATES DATABASE
               //add student view
               if (viewType.equals("addView")) {
                    if (this.model.list.size() == 0) {
                         this.model.setCurrentStudNum(data, viewType);
                    }
                    //if at least one student is in the list
                    else {
                         for (int currentStudent : this.model.list.keySet()) {
                              if (this.model.list.get(currentStudent).studNum != data) {
                                   this.model.setCurrentStudNum(data, viewType);
                              }
                              //to make sure student numbers are unique (otherwise list will replace old student, which is not supposed to happen)
                              else {
                                   message = "There is already a student in this database with that student number. Please enter a new student number.";
                                   this.updateView(currentTextField, message, viewType);
                              }
                              break;
                         }
                    }
               }
               //edit student view
               else if (viewType.equals("editView")) {
                    this.model.setCurrentStudNum(data, viewType);
               }
               //student view and remove view
               else {
                    boolean notFound = true;
                    for (int currentStudent : this.model.list.keySet()) {
                         //if student found; stops looking through list
                         if (this.model.list.get(currentStudent).studNum == data) {
                              notFound = false;
                              this.model.setCurrentStudNumEntered (data, viewType);
                              break;
                         }
                         //student not yet found
                         else {
                              notFound = true;
                         }
                    }
                    //if (throughout list) student was not found, user must input a proper student number
                    if (notFound == true) {
                         message = "There is no student in this database that matches that student number. Please try again.";
                         this.updateView(currentTextField, message, viewType);
                    }
               }
          }
     }
     
     /**Returns the integer value parsed from the text (0 if not an integer value)
       * @param String text     the text passed in to be parsed into an integer
       * @returns     the integer value parsed from the text (a number, or 0)
       */ 
     public Integer tryParseInt (String text) {
          try {
               return new Integer(text);
          } catch (NumberFormatException e) {
               return 0;
          }
     }
     
     /** Checks validity for a string value (for last name, first name, rank)
       * @param prompt     The user's input that is passed in
       * @param currentTextField     The current text field being worked with
       * @param type     The specific text field being worked with (because there are three String text fields)
       * @param viewType     The current view that is being worked with (add, edit, student, remove)
       */
     public void getString (String prompt, JTextField currentTextField, String type, String viewType)
     {
          String message = null;
          
          //no input
          if (prompt.length() == 0) {
               message = "You have not entered anything. Please try again.";
               this.updateView(currentTextField, message, viewType);
          }
          //not a word
          else if (this.tryParseInt(prompt) != 0) {
               message = "Your input is not a word. Please enter a valid word.";
               this.updateView(currentTextField, message, viewType);
          }
          //is a word (last name, first name, or rank)
          else {
               if (type.equals("last")) {
                    this.model.setCurrentLast(prompt, viewType);
               }
               else if (type.equals("first")) {
                    this.model.setCurrentFirst(prompt, viewType);
               }
               else {
                    this.model.setCurrentDivision(prompt, viewType);
               }
          }
     }
     
     /** Check validity for a byte value (for age)
       * @param prompt     The user's input that is passed in
       * @param currentTextField     The current text field being worked with
       * @param viewType     The current view that is being worked with (add, edit, student, remove)
       */
     public void getByte (String prompt, JTextField currentTextField, String viewType)
     {
          String message = null;
          byte data = this.tryParseByte(prompt);
          
          //not a byte value
          if (data == 0) {
               message = "Your input is invalid. Please enter a valid number.";
               this.updateView(currentTextField, message, viewType);
          }
          //not an age
          else if (data < 13 || data > 19) {
               message = "The age you have entered is invalid. Please enter a valid age (between 13 and 19 years old).";
               this.updateView(currentTextField, message, viewType);
          }
          //is an age
          else {
               this.model.setCurrentAge(data, viewType);
          }
     }
     
     /**Returns the byte value parsed from the text (0 if not a byte value)
       * @param String text     the text passed in to be parsed into an integer
       * @returns     the byte value parsed from the text (or 0)
       */ 
     public Byte tryParseByte (String text) {
          try {
               return new Byte(text);
          } catch (NumberFormatException e) {
               return 0;
          }
     }          
     
     /** Check validity for a float value (for race distance)
       * @param prompt     The user's input that is passed in
       * @param currentTextField     The current text field being worked with
       * @param viewType     The current view that is being worked with (add, edit, student, remove)
       */
     public void getFloat (String prompt, JTextField currentTextField, String viewType)
     {
          String message = null;
          float data = this.tryParseFloat(prompt);
          
          //not a valid race distance
          if (data <= 0.0 || data > 10) {
               message = "The race distance you have entered is invalid. Please enter a valid race distance.";
               this.updateView(currentTextField, message, viewType);
          }
          //is a race distance
          else {
               this.model.setCurrentRaceD(data, viewType);
          }
     }
     
     /**Returns the float value parsed from the text (0.0 if not a float value)
       * @param String text     the text passed in to be parsed into an integer
       * @returns     the float value parsed from the text (or 0.0)
       */ 
     public Float tryParseFloat (String text) {
          try {
               return new Float(text);
          } catch (NumberFormatException e) {
               return -1f;
          }
     }
     
     /** Updates view accordingly if input is invalid/incorrect
       * @param JTextField currentTextField     the current text field being worked with
       * @param String message     the message to be displayed to the user
       * @param String viewType     the type of view to be updated (add stats or remove stats view)
       */
     public void updateView (JTextField currentTextField, String message, String viewType)
     {
          //add student view
          if (viewType.equals("addView")) {
               this.model.addView.updateAdd(false, currentTextField, currentTextField, message);
          }
          //edit student view
          else if (viewType.equals("editView")) {
               this.model.editView.updateEdit(false, currentTextField, currentTextField, message);
          }
          //student view
          else if (viewType.equals("studentView")){
               this.model.studView.updateStudent(false, currentTextField, currentTextField, message);
          }
          //remove student view
          else {
               this.model.removeView.updateRemove(false, currentTextField, currentTextField, message);
          }
     }
}