//Owen Hearn


import java.util.*;
import java.lang.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;   
   
   
   public abstract class GameObject
   {
      //GameObject vars
      protected int xPos, yPos;
      private Color blockColor;
      
      
      public GameObject(Color blockColor, int xPos, int yPos)//parameters
      {
         this.blockColor=blockColor;//transfer vars
         this.xPos=xPos;
         this.yPos=yPos;
      }
      
      public void draw(Graphics g) // draw block in GamePanel
      {
         g.setColor(blockColor);
         g.fillRect(xPos,yPos,25,25);
      }
      
      public Color getColor()//accessors to get data from objects in arraylist
      {
         return blockColor;
      }
      public int getX()
      {
         return xPos;
      }
      public int getY()
      {
         return yPos;
      }
      
      public abstract void playerXLoc(int change); // abstract methods, really only applicable to GamePlayerObject
      public abstract void playerYLoc(int change);
   }
