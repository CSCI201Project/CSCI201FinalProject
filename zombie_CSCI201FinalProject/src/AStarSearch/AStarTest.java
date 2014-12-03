package AStarSearch;

import java.awt.image.BufferedImage;

import characters.AIChar;

import project2.GameObject;
import AStarSearch.Path.Step;

/**
 * A simple pathFinder example
 *  * 
 * @author Gornova81
 * @originalAuthor davida
 * @see http://slick.javaunlimited.net/viewtopic.php?t=841
 */
public class AStarTest implements TileBasedMap {

   public int height;
   public int width;
   private int startx;
   private int starty;
   private int endx;
   private int endy;
   protected AIChar ai = null;
   private Path path;

   private int currentStep = 0;

   /**
    * Testing map
    * 
    * # = blocking tiles
    * @ = start
    * T = target
    */

   public boolean[][] mapTiles = null;

   public AStarTest(AIChar ai) {
      width = 29;
      height = 24;
      this.ai = ai;
      mapTiles = new boolean[width][height];
      //loadMap();
   }

   /**
    * Load Map
    */
   public void loadMap(int row, int col, boolean isTree) {
            mapTiles[col][row] = false;
            if(col+1 < width && isTree){
            	mapTiles[col+1][row] = false;
            }
            if(row+1 < height && isTree){
            	mapTiles[col][row+1] = false;
            }
            if(col+1 < width && row+1 < height && isTree){
            	mapTiles[col+1][row+1] = false;
            }
 
   }


   /**
    * Calculate path from (sx,sy) to (ex,ey)
    * 
    * @param sx - start x of the path
    * @param sy - start y of the path
    * @param ex - end x of the path 
    * @param ey - end y of the path
    */
   public void updatePath(int sx, int sy, int ex, int ey) throws SlickException {
      // find any blocked paths
      AStarPathFinder pathfinder = new AStarPathFinder(this, 1000, true);
      path = pathfinder.findPath(this.ai, sx, sy, ex, ey);
      if (path == null) {
         return;//throw new SlickException("cannot find path!");
      }
      this.updateStep();
      
   }

   /**
    *Update graphic map based on current step 
    */
   public void updateLocation() {
	  if(path == null){
		  System.out.println("PATH IS NULL");
		  try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  if(path !=null){
		Step step = path.getStep();
      	this.ai.moveAI(step.getX(), step.getY());
	  }
   }

   /**
    * Print map on screen
    */
   public void printMap() {
      	for (int y = 0; y < height; y++) {
      		for (int x = 0; x < width; x++) {
        	 if(mapTiles[x][y])
        		 System.out.print(".");
        	 else
        		 System.out.print("#");
         }
         System.out.println("");
      }
   }

  /* public static void main(String args[]) {
      AStarTest ast = new AStarTest();
      // program simulation
      while (true) {
         // print map before pathfinding
         ast.printMap();
         // update
         try {
            Thread.currentThread().sleep(1000);//sleep for milliseconds
         } catch (InterruptedException ie) {
         }
         try {
            ast.updatePath(ast.startx, ast.starty,ast.endx, ast.endy);
            ast.updateStep();
            ast.updateMap();
         } catch (SlickException e) {
            e.printStackTrace();
            System.exit(-1);
         }
      }

   }*/

   /**
    * Update step if is it possibile otherwise, start again
    */
   private void updateStep() {
     // System.out.println("step: " + currentStep + " , lenghtPath: "
     //       + path.getLength());
      if (currentStep >= path.getLength() - 1) {
         // target reached
         currentStep = 0;
         //loadMap();
      } else {
         currentStep++;
      }
   }

   public int getHeightInTiles() {
      return height;
   }

   public int getWidthInTiles() {
      return width;
   }

   public void pathFinderVisited(int x, int y) {
   }

   public class MPoint {
      public int x = 0;
      public int y = 0;

      public MPoint(int px, int py) {
         x = px;
         y = py;
      }
   }

   public boolean blocked(AIChar mover, int x, int y) {
      boolean tile = mapTiles[x][y];

      return !tile;
   }

   public float getCost(AIChar mover, int sx, int sy, int tx, int ty) {
	  if(isDiagnol(sx,sy,tx,ty))
		  return 14;
	  else
		  return 10;
   }
   public boolean isDiagnol(int sx, int sy, int tx, int ty){
	   return (tx != sx) && (ty != sy);
   }
}
