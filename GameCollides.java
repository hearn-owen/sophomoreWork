
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameCollides
{

   ArrayList<ArrayList<GameObject>> mapBlocks = new ArrayList<ArrayList<GameObject>>();//create arraylist
   
   private Color hitsColor;
   private int hitsX, hitsY;
   
   public GameCollides(ArrayList<ArrayList<GameObject>> mapBlocks)
   {
      this.mapBlocks = mapBlocks;//the arraylist here is identical to the arraylist in the GamePanel, could have used a static arraylist instead...
   }

   public boolean hitsUp (int x, int y) //does it hit on the top side?
   {
      if(mapBlocks.get((y-1)/25).get((x)/25) != null)//top left corner
      {
         hitsColor = mapBlocks.get((y-1)/25).get((x)/25).getColor();//save color for victory method
         victory();//check for victory
         return false;
      }
      else if(mapBlocks.get((y-1)/25).get((x+24)/25) != null)//top right corner
      {
         hitsColor = mapBlocks.get((y-1)/25).get((x+24)/25).getColor(); // -1, +24,+25 or no addition to pinpoint exact points of corners
         victory();                                                     //-1 and +25 are 1 pixel off of square
         return false;                                                  //0 and + 24 are corners of square
      }
      return true;
   }
   
   public boolean hitsDown (int x, int y)//does it hit on bottom side
   {
      if(mapBlocks.get((y+25)/25).get((x)/25) != null)
      {
         hitsColor = mapBlocks.get((y+25)/25).get((x)/25).getColor();
         victory();
         return false;
      }
      else if(mapBlocks.get((y+25)/25).get((x+24)/25) != null)
      {
         hitsColor = mapBlocks.get((y+25)/25).get((x+24)/25).getColor();
         victory();
         return false;
      }
      return true;
   }
   
   public boolean hitsLeft (int x, int y) // does it hit on the left side
   {
      if(mapBlocks.get((y)/25).get((x-1)/25) != null)
      {
         hitsColor = mapBlocks.get((y)/25).get((x-1)/25).getColor();
         victory();
         return false;
      }
      else if(mapBlocks.get((y+24)/25).get((x-1)/25) != null)
      {
         hitsColor = mapBlocks.get((y+24)/25).get((x-1)/25).getColor();
         victory();
         return false;
      }
      return true;
   }
   
   public boolean hitsRight (int x, int y) // does it hit on the right side?
   {
      if(mapBlocks.get((y)/25).get((x+25)/25) != null)
      {
         hitsColor = mapBlocks.get((y)/25).get((x+25)/25).getColor();
         victory();
         return false;
      }
      else if(mapBlocks.get((y+24)/25).get((x+25)/25) != null)
      {
         hitsColor = mapBlocks.get((y+24)/25).get((x+25)/25).getColor();
         victory();
         return false;
      }
      return true;
   }
   
   public void victory()//checks for victory
   {
      if(hitsColor == Color.GREEN)//only runs if block is green
      {
         JOptionPane.showMessageDialog(null, "You win!!!");//pop up and close program on button
         System.exit(1);
      }
   }

}