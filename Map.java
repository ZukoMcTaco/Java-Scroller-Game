import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

/**
 * Reads and contains in memory the map of the game.
 *
 */
public class Map {

	/* Representation of the map */
	private char[][] map;
	
	/* Map name */
	private String mapName;
	
	/* Gold required for the human player to win */
	private int goldRequired;
	public Map() {
		this.goldRequired = 0;

	}

	/**
	 * Function that reads a text file to get the map to play
	 * @param fileName Name of the file to be read
	 * @return converted_map A 2D char array that contains the map to be played with
	 */

    public char [][] readMap(String fileName) {

		ArrayList<String> current_map = new ArrayList<>();
		char [][]failed_map= new char[1][1];
		try {
			File map_file = new File(fileName);
			Scanner contents = new Scanner(map_file);
			String name = contents.nextLine();
			String goldRequired = contents.nextLine();
			setGold(goldRequired);
			while (contents.hasNextLine()) {
				String data = contents.nextLine();
				current_map.add(data);
			}
			contents.close();
			char[][] converted_map = stringToCharArr(current_map);
			return converted_map;
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist.");
		}
		return failed_map;
	}

	/**
	 * Procedure that sets the attribute map
	 * @param map contains the 2D char array map to be played
	 */
	public void setMap(char[][] map){

		this.map=map;
	}

	/**
	 * Function that converts a String ArrayList to a 2D char array
	 * @param unconverted An ArrayList that contains the map to be played
	 * @return converted A 2D char array that contains the map to be played
	 */
	public char [][] stringToCharArr( ArrayList<String> unconverted){

		int width = unconverted.size();
		int length= unconverted.get(0).length();
		char [][] converted = new char[width][length];
		for (int i = 0;i < width;i++){
			String currentRow=unconverted.get(i);
			for (int j = 0; j < length; j++){
				converted[i][j]=currentRow.charAt(j);
			}
		}
		return converted;

					}

	/**Procedure which sets the gold required for a map
	 * @param goldRead String containing the amount of gold needed to win
	 */
	public void setGold(String goldRead){

		int gold = Character.getNumericValue(goldRead.charAt(4));
		this.goldRequired=gold;
	}
	public char[][] get_current_map(){
		//Function that returns the map
		return this.map;
	}

	/**Function that returns the gold to win
	 * @return gold A string containing the gold required
	 */
	public String getGold(){

		String gold = String.valueOf(this.goldRequired);
		return "Gold to win: " + gold;
	}

	/**
	 * Function that returns the goldRequired
	 * @return goldRequired attribuet which has the gold required to win.
	 */
	public int getGoldRequired(){

		return this.goldRequired;
	}
}
