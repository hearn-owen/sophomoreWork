//Owen hearn, Dr. Mood, video game project


import java.util.Scanner;
import java.util.ArrayList;
import java.lang.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel
{

   
   //background vars
   public int playerX, playerY, rows, columns, mapSpacerX, mapSpacerY, colorInt;
   Scanner file;
   
   //make  block objects
   ArrayList<ArrayList<GameObject>> mapBlocks = new ArrayList<ArrayList<GameObject>>(); //2d arraylist of blocks
   GamePlayerObject player;
   
   //timer and movement
   boolean up;//for keyevent and timer
   boolean left;
   boolean right;
   
   //GameCollides vars and object
   GameCollides collideObject;
   
   public static boolean touchGround, touchCeiling;
   int gravity, jump;
   int timerCount;
   
   public GamePanel()
   {
      super();
      setPreferredSize(new Dimension(800,600));//make panel
      
      try
      {
         file = new Scanner(new File("gameBackground.txt"));//create scanner
      }
      catch(FileNotFoundException fnfe)
      {
         System.out.println("File does not exist");
      }

      scanMap();//scan the map in to arraylist as objects
      player = new GamePlayerObject(Color.BLUE, playerX, playerY);//put player on board
   
      collideObject = new GameCollides(mapBlocks);//object for collide methods
      
      addKeyListener(new KeyEventDemo());//create key board reader
      setFocusable(true);
      
      
      
      Timer t = new Timer(10, new TimeListener());//create timer
      t.start();
   }
   
   
   //map scanning method
   public void scanMap()
   {
     
      playerX = 25*file.nextInt()-1;//get data   //minus one so that it gets suck in border until moved.
      playerY = 25*file.nextInt();
      rows = file.nextInt();
      columns = file.nextInt();
      

      
      for(int i = 0; i < rows; i++)
      {
         ArrayList<GameObject> inner = new ArrayList<GameObject>();//make inner arraylist every time a place in the outer arraylist is made
         mapBlocks.add(inner);//connect the two arraylists
         
         for(int j = 0; j < columns; j++)
         {
            colorInt = file.nextInt();
            
            if(colorInt == 0)
            {
               mapBlocks.get(i).add(null);//dont add anything for gray, use background
            }
            else if(colorInt == 1)
            {
               mapBlocks.get(i).add(new GameWallObject(Color.RED, mapSpacerX, mapSpacerY));//fill out the arraylist with two different types of objects based upon color
            }
            else if(colorInt == 2)
            {
               mapBlocks.get(i).add(new GameVictoryObject(Color.GREEN, mapSpacerX, mapSpacerY));
            }
         
            mapSpacerX += 25;//Xposition counter
            
         }
         mapSpacerY += 25;//y position counter
         mapSpacerX = 0;
      }
   }
   
   public void paintComponent(Graphics g) //painting in panel  is done here
   {
      g.setColor(Color.GRAY);//draw background
      g.fillRect(0,0,800,600);
      
      for(int i = 0; i < rows; i++)// draw background 
      {
         for(int j = 0; j < columns; j++)
         {
            if(mapBlocks.get(i).get(j)!=null)
            {
               mapBlocks.get(i).get(j).draw(g);
            }
         }
      }

      player.draw(g); //draw player, the only thing moving
      
   }
   
   public class KeyEventDemo implements KeyListener //keylistener
   {
      public void keyTyped(KeyEvent e) {}
      public void keyReleased(KeyEvent e) 
      {
         if(e.getKeyCode() == KeyEvent.VK_W)
         {
            up = false;//use booleans to make code run fast through timer
         }
         if(e.getKeyCode() == KeyEvent.VK_A)
         {
            left = false;
         }
         if(e.getKeyCode() == KeyEvent.VK_D)
         {
            right = false;
         }
       }
       public void keyPressed(KeyEvent e) 
       {
         if(e.getKeyCode() == KeyEvent.VK_W)
         {
            up = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_A)
         {
            left = true;
         }
         if(e.getKeyCode() == KeyEvent.VK_D)
         {
            right = true;
         }

         repaint(); //calls the paint component
       }
   }
   
   public class TimeListener implements ActionListener //timer
   {

      public void actionPerformed(ActionEvent e) // movement done in here
      {
      if(timerCount%4==0)//this makes the rate of upward acceleration greater than the rate of downward acceleration
         if(gravity<=0) //becasue in the prompt n counts up by .1 or 1 dependingly
            gravity++;
      
      if(timerCount%2==0) // this is true every 20ms.
      {
         if(up && !collideObject.hitsDown(player.getX(), player.getY())) //rather than use jump and gravity I use gravity from -7 to 7;
            gravity = -7;//negative seven, meaning it has just started jumping
         else if(gravity < 7 && gravity > 0 && collideObject.hitsDown(player.getX(), player.getY()))//8 so that 7 is included
            gravity++;//gravity is counting up, accelerating downward even if gravity is negative.

            
//VICTORY IS FIRING EARLY  , it is called within the if statements collides check, have played with it a lot and concluded it is close enough per time put in
         for(int i = 0; i<2*Math.abs(gravity); i++)//times 2 because n changes per 20 ms but the redraw is every 10ms
         {    
         
         repaint();
                                              //for statement lets me move by 1 pixel at a time rather than gravity (n) pixels at a time
            if(gravity<0) //if moving up
               if(collideObject.hitsUp(player.getX(), player.getY())) // if player doesnt hit something on top side
               {
      //collideObject.victory();
                  player.playerYLoc(-1);//move up one pixel, remember, its repeated |gravity| times
                  
      //repaint();
      //collideObject.hitsUp(player.getX(), player.getY());
               }
               else
                  gravity = 0;//gravtiy is 0 if it hits something
               
            if(gravity>0) // if moving down
               if(collideObject.hitsDown(player.getX(), player.getY()))//if it doesnt hit a block
               {
      //collideObject.victory();
                  player.playerYLoc(1);//move one pixel down 
                  
      //repaint();
      //collideObject.hitsDown(player.getX(), player.getY());
               }
               else
                  gravity = 0; //gravtiy is 0 if it is sitting on soemthing
            }
      }

         if(left)//while moving left...
         {
            if(collideObject.hitsLeft(player.getX(), player.getY()))//if it does not hit a block
            {
               player.playerXLoc(-1);//this method moves the player 1 picel left
            }
         }
         
         if(right)//while moving right...
         {
            if(collideObject.hitsRight(player.getX(), player.getY()))//if it does not hit a block..
            {
               player.playerXLoc(1);// this method moves the player 1 pixel right
            }
         }
         
         repaint(); //calls the paint component
   
         timerCount++; // for gravity based actions

      }
   }   

   
   
   
   
   public static void main(String[] args)
   {
      JFrame frame = new JFrame("game");//make and name frame
      frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));//to put panel in center
      frame.getContentPane().setBackground(Color.BLACK);//make background black
      frame.setSize(835,655);//funky sizes so that whole panel could be seen
      GamePanel panel= new GamePanel();//make panel
      frame.add(panel);//put panel on frame
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
}