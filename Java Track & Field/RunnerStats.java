import java.util.*;

/**
 * RunnerStats Class
 * The class that extends the Object class to create a Runner Stats Object with various statistical information; including 
 * a list of practice times, a list of race times, a list of km paces, and a list of race distances (race distances and km
 * paces are connected only to practice times). This object is an attribute of the runner object, and is created when a runner 
 * is added to the main Database. This object also has various methods that calculate the best, worst, average, and average 
 * of recent three of the runner's stats (practice times, race times, and km paces). These are used by the user to assess 
 * the runner's performance, and they can comment about this performance for future reference.
 * 
 * @author James Fong, Prajvin Jalan & Simon Zhang
 * @since January 15, 2014
 */
public class RunnerStats extends Object
{
     ArrayList <Float> practiceTimes = new ArrayList <Float> (); //ArrayList of float for a runner�s log of practice times
     ArrayList <Float> raceTimes = new ArrayList <Float> ();  //ArrayList of float for a runner�s log of race times
     ArrayList <Float> kmPaces = new ArrayList <Float> (); //ArrayList of float for a runner�s log of kmpace times
     ArrayList <Float> raceDistances = new ArrayList <Float> (); //ArrayList of float for a runner�s log of race distance times
     
     float raceD; //Temporary stores the race distance of the user
     String comment = ""; //Temporary stores the comment of the user
     
     /**Constructor of the RunnerStats class
       * @param float raceD Used to pass in the race distance the runner is associated with
       * **/
     public RunnerStats(float raceD)
     {
          super();
          this.raceD = raceD;
     }
     /**Adds all the old practice times into the temporary arraylist of practices times
       * @param ArrayList <Float> oldValues Passes in the old practice times of the runner
       * **/
     public void addOldPracticeTimes (ArrayList <Float> oldValues)
     {
          for (int x = 0; x < oldValues.size(); x++) {
               this.practiceTimes.add(oldValues.get(x));
          }
     }
     
     /**Adds all the old race times into the temporary arraylist of race times
       * @param ArrayList <Float> oldValues Passes in the old race times of the runner
       * **/
     public void addOldRaceTimes (ArrayList <Float> oldValues)
     {
          for (int x = 0; x < oldValues.size(); x++) {
               this.raceTimes.add(oldValues.get(x));
          }
     }
     
     /**Adds all the old KmPaces times into the temporary arraylist of KmPaces times
       * @param ArrayList <Float> oldValues Passes in the old KmPaces times of the runner
       * **/
     public void addOldKmPaces (ArrayList <Float> oldValues)
     {
          for (int x = 0; x < oldValues.size(); x++) {
               this.kmPaces.add(oldValues.get(x));
          }
     }
     
       /**Adds all the old race distance into the temporary arraylist of race distance
       * @param ArrayList <Float> oldValues Passes in the old race distance of the runner
       * **/
     public void addOldRaceDistances (ArrayList <Float> oldValues)
     {
          for (int x = 0; x < oldValues.size(); x++) {
               this.raceDistances.add(oldValues.get(x));
          }
     }
     /**Determiens the kmPace
       * @param float practiceTimes Passes in practice time to determine kmPace
       * @returns float returns the calculated kmPace
       * **/
     public float calcKmPace (float practiceTime)
     {
          return practiceTime/this.raceD;
     }
     
     /**Determines the best practice or race time
       * @param String type Passes in string to determine which best value to return
       * @returns float returns the best practice or race times
       * **/
     public float getBest (String type) 
     {
          //get best for practice time
          if (type.equals("practice")) {
               
               float best = practiceTimes.get(0);
               
               //loop that gets the best practice time from the list
               for (int i = 1; i < practiceTimes.size(); i++) {
                    if (practiceTimes.get(i) < best && practiceTimes.get(i) != 0) {
                         best = practiceTimes.get(i);
                    }
               }
               return best; //returns the best practice time
          }
          
          //get best for race time
          else if (type.equals ("race")) {
               
               float best = raceTimes.get(0);
               
               //loop that gets the best race time from the list
               for (int i = 1; i < raceTimes.size(); i++) {
                    if (raceTimes.get(i) < best && raceTimes.get(i) != 0) {
                         best = raceTimes.get(i);
                    }
               }
               return best; //returns the best race time
          }
          
          //get best for km pace
          else {
               
               float best = Float.POSITIVE_INFINITY; //sets the km pace to a maximum value
               
               //loop that gets the best km pace
               for (int i = 0; i < kmPaces.size(); i++) {
                    if (kmPaces.get(i) < best && kmPaces.get(i) != 0) {
                         best = kmPaces.get(i);
                    }
               }
               return best; //returns the best km pace
          }
     }
     
     /**Determines the worst practice or race time
       * @param String type Passes in string to determine which worst value to return
       * @returns float returns the worst practice or race times
       * **/
     public float getWorst(String type) 
     {
          //get worst for practice time
          if (type.equals("practice")) {
               
               float worst = practiceTimes.get(0);
               
               //loop that gets the worst practice time
               for (int i = 1; i < practiceTimes.size(); i++) {
                    if (practiceTimes.get(i) > worst) {
                         worst = practiceTimes.get(i);
                    }
               }
               return worst; //returns worst race time
          }
          
          //get worst for race time
          else { 
               
               float worst = raceTimes.get(0);
               
               //loop that gets the worst race time
               for (int i = 1; i < raceTimes.size(); i++) {
                    if (raceTimes.get(i) > worst) {
                         worst = raceTimes.get(i);
                    }
               }
               return worst; //returns worst race time
          }
     }
     
     /**Determines the average practice or race time
       * @param String type Passes in string to determine which average value to return
       * @returns float returns the average practice or race times
       * **/
     public float getAverage(String type) 
     {
          float total = 0; //total values to get average of
          float average = 0; //average of values
          int size = 0; //number of values
          
          //get average of practice times
          if (type.equals("practice")) {
               //finds the average of the practice times (adds times that are not 0)
               for (int i = 0; i < practiceTimes.size(); i++) {
                    if (practiceTimes.get(i) != 0) {
                         total = total + practiceTimes.get(i);
                         size++;
                    }
               }
               //calculates and returns average of practice times
               average = (float)(total/size);
               return average;
          }
          
          //get average of race times
          else { 
               //finds the average of the race times (adds times that are not 0)
               for (int i = 0; i < raceTimes.size(); i++) {
                    if (raceTimes.get(i) != 0) {
                         total = total + raceTimes.get(i);
                         size++;
                    }
               }
               //calculates and returns average of race times
               average = (float)(total/size);
               return average;
          }
     }
     
     /**Determines the last 3 practice or race time's aveage
       * @param String type Passes in string to determine last 3 practice or race time's aveage to return
       * @returns float returns the  last 3 practice or race time's aveage
       * **/
     public float getAverageThree(String type) 
     {
          float total = 0; //total values to get average three of
          float averageThree = 0; //average of three values
          int count = 0; //counts the values that have been added (for the average three)
          
          //average three for practice times
          if (type.equals("practice")) {
               int i = practiceTimes.size();
               
               //as long as there are more than three values
               if(practiceTimes.size()>=3){
                    do
                    {
                         //gets the values as long as they aren't 0, adds up and increments counters
                         if(practiceTimes.get(i-1)!=0)
                         {
                              total = total + practiceTimes.get(i-1);
                              i--;
                              count++;
                         }
                         else
                         {
                              i--;
                         }
                    }while(count<3 && i > 0);
                    
                    //returns average three
                    if (count == 3) {
                         averageThree = (float)(total/3);
                         return averageThree;
                    }
                    else {
                         return 0;
                    }
               }
                else
               {
                    return 0f;
               }
          }
          
          //average three for race times
          else {
               int i = raceTimes.size();
               
               //as long as there are more than three values
               if(raceTimes.size()>=3){
                    do
                    {
                         //gets the values as long as they aren't 0, adds up and increments counters
                         if(raceTimes.get(i-1)!=0)
                         {
                              total = total + raceTimes.get(i-1);
                              i--;
                              count++;
                         }
                         else
                         {
                              i--;
                         }
                    }while(count<3 && i > 0);
                    
                    //returns average three
                    if (count == 3) {
                         averageThree = (float)(total/3);
                         return averageThree;
                    }
                    else {
                         return 0;
                    }
               }
                else
               {
                    return 0f;
               }
          }
     }
     /**Sets the comment to be associated with the runner
       * @param String comment Retrieves user's comments about the runner
       * **/
     public void setComment(String comment)
     {
          this.comment = comment;
     }
}