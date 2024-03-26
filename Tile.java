import java.util.Random;

/**
 * Class which handles validity of player Tiles
 */
public class Tile {

    /**Function that returns a boolean expression to see if a tile is valid
     * @param posX the x co-ordinate of player
     * @param posY the y co-ordinate of player
     * @param map holds 2D char array of whole map
     * @return returns a boolean expression to see if it is a valid tile.
     */
    public boolean isNotValidTile(int posX, int posY,char[][] map){

        return !(map[posY][posX]=='#');


    }

    /**Function that loops until a set of valid tiles is found
     * @param length the length of the map
     * @param width the width of the map
     * @param map a 2D char array holding the whole map
     */
    public int[] ValidTileLoop(int length, int width, char [][] map){

        Random rand = new Random();
        int horiPos = 0;
        int vertiPos = 0;
        int [] vectorPos = new int[2];
        boolean validTile = false;
        while (!validTile) {
            horiPos = 1 + rand.nextInt(length - 2);
            vertiPos = 1 + rand.nextInt(width - 2);
            validTile = isNotValidTile(horiPos,vertiPos,map);
        }
        vectorPos[0]=horiPos;
        vectorPos[1]=vertiPos;
        return vectorPos;
    }
}
