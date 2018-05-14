import javax.swing.*;

/**
 * StatsPrompt Class
 * This class is used to validate information input by the user in the Runner Statistics program. A system message prompts
 * them to re-input their information if it is incorrect (the view is updated accordingly).
 * 
 * @author James Fong, Prajvin Jalan & Simon Zhang
 * @since January 15, 2014
 */
public class StatsPrompt extends Object
{
     StatsModel model; //connects this class to the main Stats model
     
     /** Constructor for the StatsPrompt class
       * @param StatsModel model     links this class to the model
       */ 
     public StatsPrompt (StatsModel model)
     {
          super();
          this.model = model;
     }
     
     /** Check validity for a string value (for practice and race times, in the form mm:ss)
       * @param prompt     The user's input that is passed in
       * @param currentTextField     The current text field being worked with
       * @param type     The specific text field being worked with (because there are two Float text fields)
       */
     public void getString (String prompt, JTextField currentTextField, String type)
     {
          String message = null; //system message in stats view
          
          //if there is nothing in the textfield, will add a 0 (placeholder in table for future values)
          if (prompt.length() == 0) {
               if (type.equals("practiceTime")) {
                    this.model.setCurrentPractice("00:00");
               }
               else { //raceTime
                    this.model.setCurrentRace("00:00");
               }
          }
          //if there is something in the text field
          else {
               //only if the input is validated correctly, will the value be set (mm:ss)
               if (prompt.length() == 5 && (prompt.substring(2,3).equals(":")) && this.tryParseInt(prompt.substring(0,2)) > 0
                        && this.tryParseInt(prompt.substring(3)) >= 0 && this.tryParseInt(prompt.substring(3)) <= 59) {
                    if (type.equals("practiceTime")) {
                         this.model.setCurrentPractice(prompt);
                    }
                    else { //raceTime
                         this.model.setCurrentRace(prompt);
                    }
               }
               //if the input is incorrect, will prompt user to retry
               else {
                    message = "Your input is invalid. Please enter a valid time in the form (mm:ss).";
                    this.updateView(currentTextField, message, "addStatsView");
               }
          }
     }
     
     /** Check validity for an int value (for logs of km pace, practice time, and race time)
       * @param prompt     The user's input that is passed in
       * @param currentTextField     The current text field being worked with
       * @param type     The specific text field being worked with (because there are three Int text fields)
       */
     public void getInt (String prompt, JTextField currentTextField, String type)
     {
          String message = null; //system message in stats view
          int data = this.tryParseInt(prompt); //parses typed data as integer
          
          //if there is nothing in the textfield, will add a 0 (no log to be removed)
          if (prompt.length() == 0) {
               if (type.equals("indexPrac")) {
                    this.model.setCurrentLogPractice(0);
               }
               else { //indexRace
                    this.model.setCurrentLogRace(0);
               }
          }
          else {
               //not an integer
               if (data <= 0) {
                    message = "Your input is invalid. Please enter a valid log number (above 0).";
                    this.updateView(currentTextField, message, "removeStatsView");
               }
               //is an integer
               else {
                    //sets practice time log
                    if (type.equals("indexPrac")) {
                         if (data <= this.model.runner.runnerStats.practiceTimes.size()) {
                              this.model.setCurrentLogPractice(data);
                         }
                         //cannot find practice time log
                         else {
                              message = "The runner does not have a Practice Time for that log. Please try again.";
                              this.updateView(currentTextField, message, "removeStatsView");
                         }
                    }
                    else { //indexRace
                         //sets race time log
                         if (data <= this.model.runner.runnerStats.raceTimes.size()) {
                              this.model.setCurrentLogRace(data);
                         }
                         //cannot find race time log
                         else {
                              message = "The runner does not have a Race Time for that log. Please try again.";
                              this.updateView(currentTextField, message, "removeStatsView");
                         }
                    }
               }
          }
     }
     
     /**Returns the integer value parsed from the text (0 if not an integer value)
       * @param String text     the text passed in to be parsed into an integer
       */ 
     public Integer tryParseInt (String text) {
          try {
               return new Integer(text);
          } catch (NumberFormatException e) {
               return -1;
          }
     }
     
     /** Updates view accordingly if input is invalid/incorrect
       * @param JTextField currentTextField     the current text field being worked with
       * @param String message     the message to be displayed to the user
       * @param String viewType     the type of view to be updated (add stats or remove stats view)
       */
     public void updateView (JTextField currentTextField, String message, String viewType)
     {
          //add stats view
          if (viewType.equals("addStatsView")){
               this.model.addStatsView.update(false, currentTextField, currentTextField, message);
          }
          //remove stats view
          else {
               this.model.removeStatsView.update(false, currentTextField, currentTextField, message);
          }
     }
}