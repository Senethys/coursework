//2048
//This is a game contains a grid where the goal is to reach 2048 by combining tiles of the same typ.
//Authors; Eda Caner, Svitri Magnusson, Fredrik Karlsson

import java.util.Stack;     //For undo redo
import processing.sound.*;  //For sounds
import java.awt.*;          // For screen dimensions

SoundFile cheerFile;    //When win 
SoundFile gameOverFile; //sound when fail
SoundFile woodFile;     //Sound when tiles collide.
PImage en_img;          //English flag
PImage se_img;          //Swedish flag



//Get the screen size of the device. DOES NOT WORK FOR MOBILE
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
int screenSizeY = (int)screenSize.getHeight();
int screenSizeX = (int)screenSize.getWidth();
Language language = new Language();

int side = 4;      //Amount of tiles per axis
int target = 2048; //Goal
int highest = 2;   //Starting highest tile on the boards
boolean tutorial = false; //Indicates if this is a tutorial run
int tutorialStep = 1;

int [][] board = new int[side][side];     //Empty board
int [][] copyBoard = new int[side][side]; //A copy of the board so it can be modifed when iterating over it.
int [][] prev[] = new int[side][side][3];  // for each tile keeps track of the information like: prev value of the tile, what is the prev x and y values of this tile ann this info is used to implement sliding effect

int tileDistance = (screenSizeX / 30);             // tileDistance = Distance between tiles (Original value = 20)
int tileSize = (screenSizeX - screenSizeY) / 5;    // tileSize = size of the tiles (original value 100)
int score = 0; 
int animStart = 10;   //How long until animation starts 
int animLength = 10;  //How long the animation lasts

//Undo Redo Button sizes. coordinates
float undoButtonX = 70 , undoButtonY = 5, undoButtonWidth = 50, undoButtonHeight = 10;
float redoButtonX = 140 , redoButtonY = 5, redoButtonWidth = 50, redoButtonHeight = 10;


Button undoButton, redoButton, tutorialButton; //Button declarations

//Undo redo stacks.
Stack<int[][]> undoStack = new Stack(); 
Stack<int[][]> redoStack = new Stack();

//Defines colours for the tiles.
color[] colorTable = {
        color(232, 248, 245),color(232, 248, 245), color(209, 242, 235),  color(163, 228, 215),  
        color(118, 215, 196), color(72, 201, 176), color(26, 188, 156), color(23, 165, 137),
        color(20, 143, 119), color(17, 120, 100), color(14, 98, 81),
        color(17, 122, 101), color(14, 102, 85)};

color buttonColor = color(81, 46, 95);
color gridColor = color(229, 231, 233);
color emptyColor = color(232, 248, 245);

//Different states throughout the game.
enum State 
{
   start, won, running, over
}

State gameState = State.start; 

void settings() {
  if(screenSizeX < screenSizeY) {
  //Indicates that this is a phone
  screenSizeX = screenSizeX / 2;
  screenSizeY = screenSizeY / 2;
  }
  
  size(screenSizeX / 2, screenSizeY);
  undoButton = new Button(language.ui_Language(2), undoButtonX, undoButtonY, undoButtonWidth, undoButtonHeight);
  redoButton = new Button(language.ui_Language(3), redoButtonX, redoButtonY, redoButtonWidth, redoButtonHeight);
  tutorialButton = new Button(language.ui_Language(4), redoButtonX + 70, redoButtonY, redoButtonWidth, redoButtonHeight);
  
  // creating new sound files 
  cheerFile = new SoundFile(this, "Cheering.mp3");
  gameOverFile = new SoundFile(this, "GameOver.mp3");
  woodFile = new SoundFile(this, "wood.wav");
  //loading flag images for the translations
  en_img = loadImage("en.png");
  se_img = loadImage("se.png");

  //Start the game.
  restart();
  
}

//Setup is needed here so that textFont and size can be set.
void setup() {
    //This option only works with setup();
  textFont(createFont("Courier", 50));
}

//starts the game over with the initial values
void restart() {
  board = new int[4][4];
  copyBoard = new int[4][4];
  
  //Spawn 2 random tiles
  spawn();
  spawn();
  undoStack.push(board); //Push the state of the board to the undo stack.
  score = 0;
  highest = 2;
  gameState = State.running;
}

//This determines where to spawn the random tile.
void spawn() {
  //Represents empty the coordiantes of the empty tile
  ArrayList<Integer> xs = new ArrayList<Integer>();
  ArrayList<Integer> ys = new ArrayList<Integer>();
  
  //Add increasingly bigger number for each row unless taken
  for (int j = 0 ; j < side; j++) {
    for (int i = 0 ; i < side; i++) { 
      if (board[j][i]==0) {
        xs.add(i);  //X coordiante
        ys.add(j); 
    } 
  }
}

  int rnd = (int)random(0, xs.size());
  int y = ys.get(rnd); //Y coordinate of a random tile to be generated
  int x = xs.get(rnd); //X coordinate of an random tile to be generated
  board[y][x] = random(0,1) < .5 ? 2 : 4; // puts either 2 or 4 to the new tile
  //Amount of open tiles - 1
  prev[y][x][0] = -1;
}

void draw() {
    
  background(255);
  noStroke();
  
  //Paints empty tile space 
  rectt(0, 0, width, height, 10, color(gridColor));
  for (int j = 0 ; j < side; j++) {
    for (int i = 0 ; i < side; i++) 
    {
      fill(color(emptyColor));
      rect(tileDistance+(tileDistance+tileSize)*i, tileDistance+(tileDistance+tileSize)*j, tileSize, tileSize, 5);
    }
  }
  float gscore = 0;
  
  //Offsets the numbers on the tiles to the center
  float textvoff = tileSize/3; 
  
  //For each tile check how they should be animated
  for (int j = 0 ; j < side; j++) {
    for (int i = 0 ; i < side; i++) {
      float xt = tileDistance+(tileDistance+tileSize)*i;
      float yt = tileDistance+(tileDistance+tileSize)*j;
      float x = xt;
      float y = yt;
      int val = board[j][i];
    
      //Duration since animation
      float duration = (frameCount - animStart)*1.0/animLength;
      //Checks if the animation should keep going
      
      if(tutorial) {
        switch(tutorialStep) {
          case 1: textt(language.tutorialLanguage(tutorialStep), screenSizeX/6,screenSizeY/5,400,100,color(0),20.0, CENTER);
          break;
          
          case 2: textt(language.tutorialLanguage(tutorialStep), screenSizeX/6,screenSizeY/5,400,100,color(0),20.0, CENTER);
                  textt(language.tutorial_subLanguage(tutorialStep), screenSizeX/6,screenSizeY/3,400,100,color(0),20.0, CENTER);
          break; 
          
          case 3: textt(language.tutorialLanguage(tutorialStep), screenSizeX/6,screenSizeY/5,400,100,color(0),20.0, CENTER);
                  textt(language.tutorial_subLanguage(tutorialStep), screenSizeX/6,screenSizeY/3,400,100,color(0),20.0, CENTER);
          break;
          
          case 4: textt(language.tutorialLanguage(tutorialStep), screenSizeX/6,screenSizeY/5,400,100,color(0),20.0, CENTER);
                  textt(language.tutorial_subLanguage(tutorialStep), screenSizeX/6,screenSizeY/3,400,100,color(0),20.0, CENTER);
          break;
          
          case 5: textt(language.tutorialLanguage(tutorialStep), screenSizeX/6,screenSizeY/5,400,100,color(0),20.0, CENTER);
                  textt(language.tutorial_subLanguage(tutorialStep), screenSizeX/6,screenSizeY/3,400,100,color(0),20.0, CENTER);
          break;
          
          case 6: textt(language.tutorialLanguage(tutorialStep), screenSizeX/6,screenSizeY/5,400,100,color(0),20.0, CENTER);
                  textt(language.tutorial_subLanguage(tutorialStep), screenSizeX/6,screenSizeY/3,400,100,color(0),20.0, CENTER);
          break;
          
          case 7: tutorial = false;
                  tutorialStep = 1;
          break;
        }
      }
      
      //If the framecount minus since when the animation is smaller than its animation and prev is bigger than one
      if (frameCount - animStart < animLength && prev[j][i][0]>0) 
      {
        int prevy = tileDistance+(tileDistance+tileSize)*prev[j][i][1];
        int prevx = tileDistance+(tileDistance+tileSize)*prev[j][i][2];
        x = (x - prevx)*duration + prevx;
        y = (y - prevy)*duration + prevy;
        
        if (prev[j][i][0]>1) 
        {
          val = prev[j][i][0];
          fill(colorTable[(int) (Math.log(board[j][i]) / Math.log(2)) + 1]);
          rect(xt, yt, tileSize, tileSize, 5); 
          fill(0);
          textAlign(CENTER);
          textSize(40);
          text(""+prev[j][i][0], xt, yt + textvoff, tileSize, tileSize);
          
        }
      }
      
      // If the animation minus when its supposed to start is bigger than its lengh prev is 0 or bigger
      if (frameCount - animStart > animLength || prev[j][i][0] >= 0) 
      {
        if (prev[j][i][0]>=2) {
          float grow = abs(0.5-duration)*2;
          if(frameCount - animStart > animLength*3) grow = 1;
          else gscore = grow;
          fill(0,0,255,100); // draws the blue thing around the tile after collision
          rect(x-2*grow, y-2*grow, tileSize+4*grow, tileSize+4*grow, 5);

        }

        else  if (prev[j][i][0]==1) {
          
          fill(255,100);
          rect(x-2, y-2, tileSize+4, tileSize+4, 5);
        }
        fill(200);
        if (val > 0) {
           
          fill(colorTable[(int) (Math.log(board[j][i]) / Math.log(2)) + 1]);
          rect(x, y, tileSize, tileSize, 5);
          fill(0);
          textAlign(CENTER);
          textSize(40);
          text(""+val, x, y + textvoff, tileSize, tileSize);
        }
      }
      
      if(language.currentLanguage == "English") {
         image(se_img, screenSizeX / 2.22, 5, screenSizeX/50, screenSizeX/50);
      } else {
        image(en_img, screenSizeX / 2.22, 5, screenSizeX/50, screenSizeX/50);
      }

    }
  }
  
  //Draw buttons
  undoButton.Draw();
  redoButton.Draw();
  tutorialButton.Draw();
  undoButton.label = language.ui_Language(2);
  redoButton.label = language.ui_Language(3);
  tutorialButton.label = language.ui_Language(4);
  textt(language.ui_Language(1)+ " " + score,10,5,100,50,color(0),10.0, LEFT);
  
  //Check states
  if(gameState == State.over) { 
    rectt(0,0,width,height,0,color(255,100)); 
    textt("Gameover! Click to restart", 0,height/2,width,50,color(0),30,CENTER);
 
    if(!gameOverFile.isPlaying()){
        gameOverFile.play();
    }
    
    if(mousePressed) {
      gameOverFile.stop();
      restart(); 
    }
  }
  
  if(gameState == State.won) { 
    rectt(0,0,width,height,0,color(255,100)); 
    textt("You won! Click to restart", 0,height/2,width,50,color(0),30,CENTER); 
    cheerFile.play();
    if(mousePressed)
    {
      cheerFile.stop();
      restart(); 
    }
  }
}

//This specifies the drawing of a tile.
void rectt(float x, float y, float w, float h, float r, color c) 
{ 
  fill(c); 
  rect(x,y,w,h,r);  
}

//This specifies the drawing of the numbers in the tiles.
void textt(String t, float x, float y, float w, float h, color c, float s, int align) 
{
  fill(c); 
  textAlign(align); 
  textSize(s); 
  text(t,x,y,w,h);  
}

void mousePressed()
{
  if (undoButton.buttonPressed() && !undoStack.empty()) 
  {
    redoStack.push(board);
    board = undoStack.pop();
    draw();
  }  
  
  if (redoButton.buttonPressed() && !redoStack.empty()) 
  {
    board = redoStack.pop();
    undoStack.push(board);
    draw();
  }  
  
  if (tutorialButton.buttonPressed()) {
    playTutorial();
  }
        

   if( (mouseX > screenSizeX / 2.22 && mouseX < screenSizeX - (screenSizeX / 2.22)) && (mouseY > 5 && mouseY < screenSizeX/50)) {
     language.toggleLanguage();
  }
}

//checks if the key is pressed and if so which key is pressed and calle the necessary function
void keyPressed() {
  if(gameState == State.running) {
    if(tutorial) {
      tutorialStep++;
    }
    // at each step redo stack cleared because once you make a new move you can not redo before doing undo first
    undoStack.push(board);
    int[][] newBoard = null;
    
    if(keyCode == UP)
    {
      newBoard = moveUp();
      redoStack.clear();
    }
    else if(keyCode == DOWN)
    {
      newBoard = moveDown();
      redoStack.clear();
    }
     else if(keyCode == LEFT)
     {
      newBoard = moveLeft(); 
      redoStack.clear();
     }
    else if(keyCode == RIGHT)
    {
      newBoard = moveRight();
      redoStack.clear();
    }
    //if any tile is moved
    if (newBoard != null) {
      redoStack.clear();
      board = newBoard;
      spawn();
      for(int i = 0; i < side; i++)
      {
        for(int j = 0; j < side; j++)
        {
          if(newBoard[i][j] > highest)
            highest = newBoard[i][j]; //checks the currents highest tile in the game
        }
      }
    }
    
    if(gameover()) 
      gameState = State.over;
    if(highest == target)
      gameState = State.won;
  }
}

int[][] moveUp() {
  return go(-1, 0);
}
 
int[][] moveDown() {
   return go(1, 0);
}
 
int[][] moveLeft() {
   return go(0, -1);
}
 
int[][] moveRight() {
   return go(0, 1);
}
//checks if there is any available moves left in the game
boolean gameover() 
{
  int[] dx = {  1, -1, 0, 0  } ;
  int[] dy = {  0, 0, 1, -1  };
  int[][][] prevbak = prev; //copy of the prev to work on it just like we do for the board
  boolean out = true;
  int prevscore = score;
  for (int i = 0 ; i < 4; i++) 
  {
    if (go(dy[i], dx[i]) != null) 
      out = false;
  }
 
  prev = prevbak;
  score = prevscore;
  return out;
}
// moves all the tiles that can move in the direction of the key pressed
int[][] go(int dy, int dx) 
{
  int[][] copyBoard = new int[4][4];
  for (int j = 0 ;j < 4; j++) 
  {
    for (int i = 0 ; i < 4; i++) 
    {
      copyBoard[j][i] = board[j][i];
    }
  }
  
  prev = new int[4][4][3];
  boolean moved = false; 
  if (dx != 0 || dy != 0) //checks if the tile actually needs to be moved in any direction but 0
  {
    int d =  dx != 0 ? dx : dy; // direction will be either horizontal or vertical
    for (int perp = 0; perp < side; perp++) //iterate as many times as rows/columns 
    {
      for (int along = (d > 0 ? side - 2 : 1); along != (d > 0 ? -1 : side); along-=d) 
      {
        int y = dx != 0 ? perp : along, x = dx != 0 ? along : perp, ty = y, tx = x;
        if (copyBoard[y][x]==0) continue; // continue if it does not hit any other tile
        for (int i=(dx != 0 ? x : y)+d; i!= (d > 0 ? side : -1); i+=d) 
        {
          int r = dx != 0 ? y : i, c = dx != 0 ? i : x;
          if (copyBoard[r][c] != 0 && copyBoard[r][c] != copyBoard[y][x]) // if it hits a tile that it can not merge
            break;
          if (dx != 0) //iterates over either the row or the column based on the direction
            tx = i; 
          else 
            ty = i;
        }
        if ( (dx != 0 && tx == x) || (dy != 0 && ty == y))  // it continues to check the next spot on the board
          continue;
        else if (copyBoard[ty][tx]==copyBoard[y][x]) //if it detects two tiles that can merge hitting
        {
          prev[ty][tx][0] = copyBoard[ty][tx];          
          copyBoard[ty][tx] *= 2;
          score += copyBoard[ty][tx];
          moved = true;
        }
        else if ( (dx != 0 && tx != x) || (dy != 0 && ty != y)) 
        {
          prev[ty][tx][0] = 1;
          copyBoard[ty][tx] = copyBoard[y][x];
          moved = true;
        }
      if (moved) 
      {
        prev[ty][tx][1] = y;
        prev[ty][tx][2] = x;
        copyBoard[y][x] = 0; //the initial value of the tile moved is set to 0 since it moved
      }
    }
   }
  }
  if (!moved) return null;
  animStart = frameCount;
  return copyBoard;
}

//Tutorial restarts the game with extra graphics.
void playTutorial() {
  restart();
  tutorial = true;
  draw();
}
