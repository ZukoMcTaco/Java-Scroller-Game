
import java.util.Scanner;
/**
 * Contains the main logic part of the game, as it processes.
 *
 */
public class GameLogic {

	/**
	 * Function that returns a boolean expression if the game is or isn't complete
	 * @param player an instance of the class HumanPlayer.
	 * @param bot an instance of the class Bot
	 * @return gameComplete A boolean variable which holds the state of the game.
	 */
	public boolean gameOver(HumanPlayer player, Bot bot){
		boolean gameComplete=false;
		if ((player.get_vertical_pos()== bot.get_vertical_pos())
				&& (player.get_horiz_pos()==bot.get_horiz_pos())){
			gameComplete = true;
			System.out.println("LOSE");
		}
		return gameComplete;
	}

	/**
	 * Function that handles the bot's decisions to chase the player
	 * @param view Holds a 2D char array containing the bot's view
	 * @param map Holds a 2D char array containing the whole map
	 * @param player an instance of the class HumanPlayer.
	 * @param bot an instance of the class Bot
	 * @param currMap an instance of the class Map
	 */
	public void botDecision(char[][] view,
							char[][] map,
							HumanPlayer player,
							Bot bot,
							Map currMap) {

		int dispX = bot.get_horiz_pos()- player.get_horiz_pos();
		int dispY = bot.get_vertical_pos() - player.get_vertical_pos();
		if (bot.isPlayerInView(view)) {
			if (dispY<dispX){
				//prioritises vertical movement if displacement vertically is less
				if (dispY<0){
					bot.playerGoSouth(map);
				}
				else if (dispY>0) {
					bot.playerGoNorth(map);
				}
				else{
					//if the bot is in the same Y co-ordinate
					if (dispX<0){
						bot.playerGoEast(map);
					}
					else if (dispX>0) {
						bot.playerGoWest(map);
					}
				}
			}
			else if (dispX<dispY) {
				//prioritises movement horizontally if displacement in x is smaller
				if (dispX<0){
					bot.playerGoEast(map);
				}
				else if (dispX>0) {
					bot.playerGoWest(map);
				}
				else{
					//if the bot is in the same x co-ordinate
					if (dispY>0){
						bot.playerGoNorth(map);
					}
					else if (dispY<0) {
						bot.playerGoSouth(map);
					}
				}
			}
			else{
				//if in a diagonal orientation, choose random move
				bot.randDecision(player, map, currMap,bot);
			}
		}
		else{
			//if not in scope, make random decision
			bot.randDecision(player, map, currMap,bot);
		}
	}


	/**
	 *Procedure that manages the Inputs of the user
	 * @param playerInput The string that the user enters
	 * @param player Instance of player that is passed through
	 * @param map A 2D char array that holds the map that is being played
	 * @param currMap An instance of Map
	 * @param bot An instance of bot
	 * @param isBot a boolean expression to check if the object accessing method is a bot or not
	 * @return result returns the result of a decision made by player
	 */
	public String userInputManager(String playerInput,
								 HumanPlayer player,
								 char[][] map,
								 Map currMap,
								 Bot bot,
								 boolean isBot) {

		String result="Fail";
		switch (playerInput) {
			case "MOVE W":
				if (!isBot) {
					result = player.playerGoWest(map);
				}
				else{
					result = bot.playerGoWest(map);
				}
				break;
			case "MOVE E":
				if (!isBot) {
					result = player.playerGoEast(map);
				}
				else{
					result = bot.playerGoEast(map);
				}
				break;
			case "MOVE N":
				if (!isBot) {
					result = player.playerGoNorth(map);
				}
				else{
					result = bot.playerGoNorth(map);
				}
				break;
			case "MOVE S":
				if (!isBot) {
					result = player.playerGoSouth(map);
				}
				else{
					result = bot.playerGoWest(map);
				}
				break;
			case "PICKUP":
				result= player.pickup(map);
				break;
			case "GOLD":
				result = player.gold();
				break;
			case "QUIT":
				result= player.quit(map, currMap);
				break;
			case "HELLO":
				result = currMap.getGold();
				break;
			case "LOOK":
				char[][] view = player.look(player.get_horiz_pos(),
						player.get_vertical_pos(),
						map,
						bot.get_horiz_pos(),
						bot.get_vertical_pos());
				for (int a = 0; a < view.length; a++) {
					System.out.println(view[a]);
				}
				result="";
				break;
		}
		return result;
	}


	//main function to run the program
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		String mapName;//string that is read by scanner
		boolean mapValid = false;
		GameLogic logic = new GameLogic();
		Map currentMap = new Map();

		while (!mapValid) {
			//Will not progress unless a valid map is set
			System.out.println("Enter map you want: ");
			mapName = scanner.nextLine();
			if (mapName.equals("quit")){
				System.exit(0);
			}
			char[][] mapRead = currentMap.readMap(mapName);
			currentMap.setMap(mapRead);
			if (mapRead.length > 1) {
				mapValid = true;
			}
		}
		char[][]map= currentMap.get_current_map();


		int width = map.length;
		int length = map[0].length;

		Tile tile = new Tile();

		//contains the vector position of player
		int [] vectorPos = tile.ValidTileLoop(length, width, map);
		int horiPos = vectorPos[0];
		int vertiPos = vectorPos[1];

		HumanPlayer player = new HumanPlayer(vertiPos,horiPos);

		//the bot's vector position initialised
		int [] vectorPosBot = tile.ValidTileLoop(length, width, map);
		while (vectorPosBot[0] != horiPos && vectorPosBot[1] != vertiPos){
			vectorPosBot = tile.ValidTileLoop(length, width, map);
		}
		int horiBot = vectorPosBot[0];
		int vertiBot = vectorPosBot[1];
		Bot bot = new Bot(vertiBot,horiBot);

		boolean gameDone=false;
		boolean isBot;

		while(!gameDone) {
			//Main loop for the game
			if (logic.gameOver(player,bot)){
				gameDone=true;
			}
			isBot=false;//sets the player's turn
			String playerInput = scanner.nextLine();
			String result = logic.userInputManager(playerInput, player, map,currentMap,bot,isBot);
			System.out.println(result);

			if (logic.gameOver(player,bot)
					|| result.equals("WIN")
					|| result.equals("LOSE")){
				gameDone=true;
			}
			char[][] botView= bot.look(player.get_horiz_pos(),
					player.get_vertical_pos(),
					map,
					bot.get_horiz_pos(),
					bot.get_vertical_pos());
			logic.botDecision(botView, map, player,bot,currentMap);
			if (logic.gameOver(player,bot)){
				gameDone=true;
			}
		}
		System.out.println("FINISHED");
		System.exit(0);
    }
}