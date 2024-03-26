/**
 * Runs the game with a human player and contains code needed to read inputs.
 *
 */

public class HumanPlayer {
    private int playerPosVert;
    private int playerPosHori;
    private int goldCollected;


    public HumanPlayer(int posVert,int posHoriz){
        this.playerPosVert=posVert;
        this.playerPosHori=posHoriz;
        this.goldCollected=0;
    }
    /**
     * Function that makes the player unit go North, and checks if it is a valid move
     * @param map 2D char array map that contains the map being played
     * @return String either "Success" or "Fail" which depends on if the move is valid
     */

    public String playerGoNorth(char[][] map){
        if (map[this.get_vertical_pos()-1][this.get_horiz_pos()] != '#') {
            this.playerPosVert -= 1;
            return "Success";
        }
        return "Fail";
    }

    /**
     * Function that makes the player unit go West, and checks if it is a valid move
     * @param map 2D char array map that contains the map being played
     * @return String either "Success" or "Fail" which depends on if the move is valid
     */
    public String playerGoWest(char[][] map){
        if (map[this.get_vertical_pos()][this.get_horiz_pos()-1] != '#') {
            this.playerPosHori -= 1;
            return "Success";
        }
        return "Fail";
    }

    /**
     * Function that makes the player unit go South, and checks if it is a valid move
     * @param map 2D char array map that contains the map being played
     * @return String either "Success" or "Fail" which depends on if the move is valid
     */
    public String playerGoSouth(char [][] map){
        if (map[this.get_vertical_pos()+1][this.get_horiz_pos()] !='#') {
            this.playerPosVert += 1;
            return "Success";
        }
        return "Fail";
    }

    /**
     * Function that makes the player unit go East, and checks if it is a valid move
     * @param map 2D char array map that contains the map being played
     * @return String either "Success" or "Fail" which depends if the move is valid
     */
    public String playerGoEast(char [][] map){
        if(map[this.get_vertical_pos()][this.get_horiz_pos()+1] !='#') {
            this.playerPosHori += 1;
            return "Success";
        }
        return "Fail";
    }

    /**
     * Function that returns the vertical position of a player
     * @return playerPosVert the vertical position of a player
     */
    public int get_vertical_pos(){

        return this.playerPosVert;
    }
    
    public int get_horiz_pos(){
        //function that returns the x co-ordinate of the player
        return this.playerPosHori;
    }
    public String gold(){
        //Function that returns the gold acquired
        String gold = String.valueOf(this.goldCollected);
        return "Gold owned: " + gold;
    }

    public String pickup(char [][] map){
        //function that is used to pickup gold.
        if (map[this.get_vertical_pos()][this.get_horiz_pos()]=='G'){
            map[this.get_vertical_pos()][this.get_horiz_pos()]='.';
            this.goldCollected+=1;
            return "Success";
        }
        return "Fail";
    }

    /**
     * Function that exits the player if they are standing on an exit tile
     * and if they have enough gold to leave
     * @param map contains a 2D char array of the map being played
     * @param currentMap an instance of the class Map
     * @return "WIN" or "LOSE" depending on if
     */
    public String quit(char[][] map, Map currentMap){

        if (this.goldCollected == currentMap.getGoldRequired() &&
                map[this.get_vertical_pos()][this.get_horiz_pos()]=='E' ){
            return "WIN";
        }
        return "LOSE";
    }

    /**
     * Function that returns the view of the bot
     * @param posX x co-ordinate of player
     * @param posY y co-ordinate of player
     * @param map  2D char array containg the map that is being played
     * @param posBotX x co-ordinate of bot
     * @param posBotY y co-ordinate of bot
     * @return view a 2D 5x5 char array containing the view of the player
     */
    public char[][] look(int posX, int posY, char[][] map, int posBotX, int posBotY){

        boolean colSpecialCase=false;
        int botVectorX;
        int botVectorY;
        int rowView = 0;
        int colView = 0;
        int colViewMax=4;
        int rowMap = posY-2;
        int colMap = posX-2;
        int rowMax = posY+2;
        int colMax = posX+2;
        if (rowMap<0){
            rowMap+=1;
            rowView=1;

        }
        if (colMap<0){
            colMap+=1;
            colView=1;
            colSpecialCase=true;
        }
        if (rowMax>map.length-1){
            rowMax-=1;
        }
        if (colMax>map[0].length-1){
            colMax-=1;
            colViewMax=3;
        }
        char [][] view ={
                {'#','#','#','#','#'},
                {'#','#','#','#','#'},
                {'#','#','#','#','#'},
                {'#','#','#','#','#'},
                {'#','#','#','#','#'}
        };
        for (int row = rowMap ; row <= rowMax ; row++){
            for ( int col = colMap ; col <= colMax ; col++){
                view[rowView][colView] = map[row][col];
                colView+=1;
                if (colView >colViewMax ){
                    colView=0;
                    if (colSpecialCase){
                        colView=1;
                    }
                }
            }
            rowView+=1;
        }
        view[2][2]='P';
        if ((rowMap <= posBotY && posBotY <= rowMax)
                && (colMap <= posBotX && posBotX<=colMax)){
        //checks if the Bot is in the field of view of the player
            botVectorX = 2 + posBotX - posX;
            botVectorY = 2 + posBotY - posY;
            view[botVectorY][botVectorX]='B';
        }
        return view;
    }

}