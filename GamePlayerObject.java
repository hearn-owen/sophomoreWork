//Owen Hearn


import java.util.*;
import java.lang.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;   
   
   
public class GamePlayerObject extends GameObject
{

   public GamePlayerObject(Color blockColor, int xPos, int yPos)//parameters
   {
      super(blockColor, xPos, yPos);
   }
   
   public void playerXLoc(int change)
   {
      xPos+=change;//xPos yPos is protected var from class above
   }
   public void playerYLoc(int change)
   {
      yPos+=change;
   }
}