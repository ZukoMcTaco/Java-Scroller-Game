import java.util.Random;

/**
 * Class which is responsible for the Bot's decisions
 *
 */
public class Bot extends HumanPlayer{
    public Bot(int posVert, int posHoriz) {
        //Default constructor for the bot
        super(posVert, posHoriz);
    }


    /**
     * Function that checks if the Player can be seen by the bot
     * @param view a 5x5 2D char array that contains the view of the bot
     * @return playerInView returns True if Player is in the bot's view otherwise it returns false
     */
    public boolean isPlayerInView(char[][] view){
        int length= view[0].length;
        for (int y = 0; y < view.length; y++){
            for(int x=0; x < length; x++){
                if (view[y][x]=='P'){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Procedure which manages the random decision made by bot
     * it generates a valid move
     * @param player instance of the HumanPlayer class.
     * @param map 2D char array that contains the whole map.
     * @param currMap Instance of the class Map.
     * @param bot Instance of the class Bot.
     */
    public void randDecision(HumanPlayer player, char[][] map, Map currMap, Bot bot){

        Random rand = new Random();
        boolean validPlay = false;
        GameLogic logic = new GameLogic();
        int decisionIndex;
        boolean isBot=true;
        String[] decisions ={"MOVE N",
                "MOVE E",
                "MOVE S",
                "MOVE W"};//contains the commands to use by bot
        while (!validPlay){
            decisionIndex = rand.nextInt(decisions.length-1);
            String result = logic.userInputManager(decisions[decisionIndex],player,map,currMap,bot,isBot);
            if (result.equals("Success")){
                validPlay=true;
            }
        }

    }

    /**
     * Function that returns the view of the bot
     * @param posX x co-ordinate of player
     * @param posY y co-ordinate of player
     * @param map  2D char array containg the map that is being played
     * @param posBotX x co-ordinate of bot
     * @param posBotY y co-ordinate of bot
     * @return view A 5x5 2D Char array of the bot's view.
     */
    public char[][] look(int posX, int posY, char[][] map, int posBotX, int posBotY){

        //function that returns the view of the Bot
        boolean colSpecialCase = false;// checks if
        int playerVectorX;
        int playerVectorY;
        int rowView = 0;
        int colView = 0;
        int colViewMax=4;
        int rowMap = posBotY - 2;
        int colMap = posBotX - 2;
        int rowMax = posBotY + 2;
        int colMax = posBotX + 2;
        if (rowMap<0){
            //if the y co-ordinate is less than 0
            rowMap+=1;
            rowView=1;

        }
        if (colMap<0){
            //if the x co-ordinate becomes less than 0
            colMap+=1;
            colView=1;
            colSpecialCase=true;
        }
        if (rowMax>map.length-1){
            //if the max y co-ordinate is exceeded
            rowMax-=1;
        }
        if (colMax>map[0].length-1){
            //if the max x co-ordinate is exceeded
            colMax-=1;
            colViewMax=3;
        }
        char [][] view ={
                {'#','#','#','#','#'},
                {'#','#','#','#','#'},
                {'#','#','#','#','#'},
                {'#','#','#','#','#'},
                {'#','#','#','#','#'}
        };//char array that contains the iew
        for (int row = rowMap ; row <= rowMax ; row++){
            for ( int col = colMap ; col <= colMax ; col++){
                view[rowView][colView] = map[row][col];
                colView+=1;
                if (colView >colViewMax ){
                    colView = 0;
                    if (colSpecialCase){
                        colView = 1;
                    }
                }
            }
            rowView+=1;
        }
        view[2][2]='B';
        if ((rowMap <= posY && posY <= rowMax)
                && (colMap <= posX && posX<=colMax)){
            //checks if the Bot is in the field of view of the player
            playerVectorX = 2 + posX - posBotX;
            playerVectorY = 2 + posY - posBotY;
            view[playerVectorY][playerVectorX] = 'P';
            //sets the player icon on the view.
        }
        return view;
    }

}
