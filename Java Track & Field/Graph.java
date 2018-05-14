import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Graph Class
 * The Graph class is an extension of the JPanel that allows an input of an arraylist of points to be drawn and
 * connected. The graph determines key points in the arraylist, and draws itself within the scale while including 
 * all points in the array.
 * 
 * @author James Fong
 * @since Jan 15, 2014
 */
public class Graph extends JPanel {
     
     private DataModel model; //The Main Database model that this graph is connected to (to use its methods)
     private ArrayList <Float> points = new ArrayList <Float>(); //Used to provide all the points that are going to be graphed
     private ArrayList <Float> storage = new ArrayList <Float>(); //Used to store converted plots and exclude 0
     private Color lineColor = new Color(44, 102, 230, 180); //Used to determine the color of the lines connecting each of the points
     private Color pointColor = new Color(100, 100, 100, 180); //Used to determine the color of the points on the graph
     private static final int MAX_HEIGHT = 400; //Used to determine the maximum height of the graph
     private static final int MAX_WIDTH = 400; //Used to determine the maxiumum width of the graph
     private static final int INCREMENT = 10; //Used to determine the number of increments on the x and y axis
     private int SPACE = MAX_HEIGHT/INCREMENT; //Used to determine the amount of space between each row and column of the graph
     private static final int POINT_WIDTH = 5; //Used to determine the radius of each point
     private static final int SIDE_BORDER = 64; //Used to determine the amount of space to input scale
     /** Constructor of the graph class
       * @param ArrayList <Float> points Used to provide all the points that are going to be graphed
       * **/
     public Graph (ArrayList <Float> points, DataModel model) 
     {
          this.model = model; //connects graph to database model (to use its methods)
          
          try{
               for (int i = 0; i< points.size();i++)    //loads all the arraylist points into an arraylist for future reference
               {
                    //adds points to graph only if not zero
                    if(points.get(i)>0)
                    {
                         this.points.add(points.get(i));
                         this.storage.add(points.get(i));
                    }
               }
          }
          catch(NullPointerException x)
          {
          }
     }
     /**Determines the largest point in the array 
       *@returns float maxPoint Returns the largest number in the arraylist 
       **/
     public float getMaxPoint()
     {
          float maxPoint = this.points.get(0);        
          for (int countMax = 0; countMax<this.points.size(); countMax++)
          {
               if (this.points.get(countMax)>=maxPoint)
               {
                    maxPoint = this.points.get(countMax);                        
               }
          }
          return maxPoint;
     }
     /** Converts all the point's y coordinates to fit the graph **/
     public void convertYpoints()
     {
          //Change all the points to fit the graph of 400 by 400
          float convertedSpacing = MAX_HEIGHT/this.getMaxPoint();
          for (int countMax = 0; countMax<this.storage.size(); countMax++)
          {
               float addNewPoint = this.storage.get(countMax)*convertedSpacing;
               this.storage.set(countMax, addNewPoint);
               
          }
          
     }
     /** Converts all the point's x coordinates to fit the graph
       * @returns widthSpace    the width of the graph
       */
     public double convertXpoints()
     {
          double widthSpace = MAX_WIDTH /this.storage.size();  
          return widthSpace;
     }
     /**overrides paint component**/
     protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          Graphics2D g2 = (Graphics2D)g;
          //Try-Catch used in case there are no numbers within the arraylist
          try{
               //Determine the scale numbers ratio of the graph
               double scale = (double)Math.round((this.getMaxPoint()/INCREMENT)*100)/100;             
               double numberScale = this.getMaxPoint();
               int counter = 0;  
               //Prints the scale numbers on the y -axis
               g2.drawString((this.getMaxPoint()+""),SIDE_BORDER/2,0);
               while(counter<MAX_HEIGHT)
               {            
                    
                    g2.drawString((this.model.convertToString((float)(numberScale))+"-"),SIDE_BORDER/2,counter);
                    numberScale = numberScale-scale;
                    counter = counter+(MAX_HEIGHT/INCREMENT);
               }
               g2.drawString(("00:00"),SIDE_BORDER/2,MAX_HEIGHT);
               //converts all the point's y coordinates everytime to fit the graph
               this.convertYpoints(); 
               
          }
          catch(IndexOutOfBoundsException e)
          {
               
          }
          //Draw background of the graph
          g2.setColor(Color.WHITE);
          g2.fillRect(SIDE_BORDER,0,MAX_HEIGHT,MAX_WIDTH );
          
          
          //Draw horizontal lines for x axis
          g2.setColor(Color.BLACK);
          int x = 0;
          while (x < MAX_HEIGHT)
          {
               g2.drawLine(SIDE_BORDER, x+SPACE, MAX_WIDTH+SIDE_BORDER , x+SPACE);
               x = x+SPACE;
               
          }
          
          //Draw vertical lines for y axis 
          x = 0;
          while (x < MAX_WIDTH )
          {
               g2.drawLine(x+SPACE+SIDE_BORDER,0, x+SPACE+SIDE_BORDER,MAX_HEIGHT);
               x = x+SPACE;
          }
          
          //Try-Catch used in case there are no numbers within the arraylist
          try{
               //Draw points on the graph
               g2.setColor(pointColor);
               for (int i = 0; i < this.storage.size(); i++) {
                    int x1 = (int) (i*this.convertXpoints()+SIDE_BORDER);
                    int y1 = (int) (MAX_HEIGHT - (this.storage.get(i))); 
                    g2.fillOval(x1,y1,POINT_WIDTH,POINT_WIDTH);
               }
               //Draw the lines that connects the points on the graph
               if(this.storage.size() > 1)
               {
                    g2.setColor(lineColor);
                    for (int i = 1; i < this.storage.size(); i++) {
                         int x1 = (int)((i-1)*this.convertXpoints()+SIDE_BORDER);
                         int y1 = (int)(MAX_HEIGHT - (this.storage.get(i-1)));
                         int x2 = (int) (i*this.convertXpoints()+SIDE_BORDER);
                         int y2 = (int) (MAX_HEIGHT - (this.storage.get(i))); 
                         g2.drawLine(x1,y1,x2,y2);
                    }
               }
          }
          catch(IndexOutOfBoundsException ex)
          {
               
          }
     }
}