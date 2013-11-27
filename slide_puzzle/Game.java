/**
  * Don Mills CI
  * ICS4U H. Strelkovska
  * String Game
  * Scrambled Word Slide Puzzle
  *
  * Hussain Jasim #310372974
  *
  * This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License.
  * To view a copy of this license, visit
  * http://creativecommons.org/licenses/by-nc-sa/3.0/
  * or send a letter to
  * Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
**/

package slide_puzzle;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

// This class contains the game loop, main method,
// and auxiliary functionality
public class Game implements Runnable {
	private ArrayList<PuzzleWord> puzzleWords;
	private PuzzleWord currentPuzzleWord;
	private Puzzle currentPuzzle;
	private Scanner cl;
	private BufferedReader puzzleWordsReader, highScoresReader;
	private BufferedWriter highScoresWriter;
	private Path puzzleWordsPath, highScoresPath;

public void displayHelp() {
			System.out.println("Slide Puzzle Word Game");
		System.out.println("Rules:");
		System.out.println("The game will display a rectangular board with letter tiles, with one of the tiles being empty.");
		System.out.println("You must 'slide'' adjacent tiles into the empty space until the puzzle is completed.");
		System.out.println("The puzzle is completed when the letter tiles are in order from left to right and top to bottom, forming the target word, and the empty tile is at the bottom right.");
		System.out.println("You will be given a hint to help you guess the target word.");
		System.out.println();
		System.out.println("You slide tiles by indicating which direction you want to slide a tile in.");
		System.out.println("For example, if you want to slide the adjacent tile to the left of the empty space right, you must indicate right.");
		System.out.println();
		System.out.println("Input Options:");
		System.out.println("R : right");
		System.out.println("L : left");
		System.out.println("U : up");
		System.out.println("D : down");
		System.out.println("h : show hint");
		System.out.println("? : show this help message");
		System.out.println("Q : quit game");
		System.out.println();
	}
	public void displayMenuOptions() {
		System.out.println("Menu Options");
		System.out.println("? : game help");
		System.out.println("s : high scores");
		System.out.println("p : play a game");
		System.out.println("q : quit");
		System.out.println();
	}

	// Reads in the different puzzle words and configurations from a text file
	// with entries separated by commas in this order:
	// word, hint, width, height
	// and separate puzzle configurations separated by newlines.
	private void readPuzzleWords() {
		String line = null;
		String[] puzzleWord;
		int lineCounter = 0;
		if(!Files.exists(puzzleWordsPath))
			throw new IllegalArgumentException(
				String.format("Unable to access puzzle words file %s%n", puzzleWordsPath)
			);
		if(!Files.isReadable(puzzleWordsPath))
			throw new IllegalArgumentException(
				String.format("Unable to read puzzle words file %s%n", puzzleWordsPath)
			);
		try {
			puzzleWordsReader = Files.newBufferedReader(puzzleWordsPath, java.nio.charset.Charset.defaultCharset());
			while((line = puzzleWordsReader.readLine()) != null) {
			lineCounter ++;
				puzzleWord = line.split(", ");
				if(puzzleWord.length != 4)
					throw new IllegalArgumentException(
						String.format("Illegal number of entries at line %d in %s%n", lineCounter, puzzleWordsPath)
					);
				if(puzzleWord[0].length()%2 == 0)
					throw new IllegalArgumentException(
						String.format("Length of word entry must be odd at line %d in %s%n", lineCounter, puzzleWordsPath)
				);
				try {
					puzzleWords.add(
						new PuzzleWord(puzzleWord[0], puzzleWord[1], Integer.parseInt(puzzleWord[2]), Integer.parseInt(puzzleWord[3]))
					);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
						String.format("Width and height entries must be base-ten integers at line %d in %s", lineCounter, puzzleWordsPath)
					);
				}
			}
		} catch (IOException e) {
			System.err.format("Caught IOException while reading puzzle words file %s: %n%s%n", puzzleWordsPath, e);
			return;
		} finally {
		try {
				puzzleWordsReader.close();
			} catch (IOException e) {
				System.err.format("%s%n", e);
			}
		}
	}

	//reads in the high scores from a text file
	// set up with a name and a score on each line, separated by a comma and whitespace.
	// Score is recorded as the rounds required to solve a puzzle,
	// So the lower the score the better.
	public LinkedHashMap<String, Integer> readHighScores() {
	LinkedHashMap<String, Integer> highScores = new LinkedHashMap<String, Integer>();
	String line;
	String[] entry;
		if(!Files.exists(highScoresPath))
			throw new IllegalArgumentException(
				String.format("Unable to access the high scores file at %s%n", highScoresPath)
			);
		if(!Files.isReadable(highScoresPath))
			throw new IllegalArgumentException(
				String.format("Unable to read the high scores file at %s%n", highScoresPath)
			);
		try {
			highScoresReader = Files.newBufferedReader(highScoresPath, java.nio.charset.Charset.defaultCharset());
			while((line = highScoresReader.readLine()) != null) {
				entry = line.split(", ");
				try {
				highScores.put(entry[0], Integer.parseInt(entry[1]));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
						String.format("High score must be a base-ten integer for %s  in %s", entry[0], highScoresPath)
					);
				}
			}
		} catch (IOException e) {
			System.err.format("Caught IOException while reading high scores file %s:%n%s%n", highScoresPath, e);
		} finally {
			try {
				highScoresReader.close();
			} catch (IOException e) {
			System.err.format("%s%n", e);
		}
		}
		return highScores;
	}

	// Prints out the high scores in a human-readable format.
	public void displayHighScores() {
		LinkedHashMap<String, Integer> highScores;
		int nameColumnWidth = 0;
		try {
			highScores = readHighScores();
		System.out.println("High scores:");
			for(String name : highScores.keySet())
				if(name.length() > nameColumnWidth)
					nameColumnWidth = name.length();
			for(Map.Entry<String, Integer> entry : highScores.entrySet())
				System.out.format("%-" + (nameColumnWidth+2) + "s%d%n", entry.getKey(), entry.getValue());
			System.out.println();
		} catch(IllegalArgumentException e) {
			System.err.format("%s%n", e);
		}
	}

	// Checks the high scores and updates a player's score if the given score is lower,
	// or creates a new entry for them if they don't have an existing one.
	public void updateHighScores(String name, int score) {
		LinkedHashMap<String, Integer> highScores;
		highScores = readHighScores();
		if(!Files.isWritable(highScoresPath))
			throw new IllegalArgumentException(
				String.format("Unable to write to high scores file %s%n", highScoresPath)
			);
		if(!highScores.get(name).equals(true) || highScores.get(name) > score) {
			highScores.put(name, score);
			try {
				this.highScoresWriter = Files.newBufferedWriter(highScoresPath, java.nio.charset.Charset.defaultCharset());
				for(Map.Entry<String, Integer> entry : highScores.entrySet())
					highScoresWriter.write(
						String.format("%s, %s%n", entry.getKey(), entry.getValue())
					);
			} catch(IOException e) {
				System.err.format("Caught IOException while updating high scores file %s: %n%s%n", highScoresPath, e);
			} finally {
				try {
					highScoresWriter.close();
			} catch(IOException e) {
					System.err.format("%s%n", e);
				}
			}
		}
	}

	// The loop where actual gameplay takes place.
	public void play() {
		String playerInput, name = null;
		Puzzle.Direction dir;
		int roundCounter = 0;
		Boolean trackScore = false;
		currentPuzzleWord = puzzleWords.get((int)(Math.random()*puzzleWords.size()));
		currentPuzzle = new Puzzle(currentPuzzleWord);
		System.out.print("Do you want to keep track of your score? (y/n): ");
		if(cl.nextLine().trim().toLowerCase().equals("y")) {
			trackScore = true;
			System.out.print("Please enter your name for high score tracking: ");
			name = cl.nextLine().trim().toLowerCase();
		}
		while(true) {
			currentPuzzle.displayPuzzle();
			System.out.println("? for help; h for a hint");
			playerInput = cl.nextLine().trim().toLowerCase();
			if(playerInput.equals("u"))
				dir = Puzzle.Direction.UP;
			else if(playerInput.equals("d"))
				dir = Puzzle.Direction.DOWN;
			else if(playerInput.equals("l"))
				dir = Puzzle.Direction.LEFT;
			else if(playerInput.equals("r"))
				dir = Puzzle.Direction.RIGHT;
			else if(playerInput.equals("h")) {
				System.out.println(currentPuzzleWord.getHint());
				continue;
			} else if(playerInput.equals("?")) {
				displayHelp();
				continue;
			} else if(playerInput.equals("q")) {
				break;
			} else {
				System.out.println("That is not a valid input option.");
				continue;
			}
			try {
				currentPuzzle.slideTile(dir);
				roundCounter++;
			} catch (IllegalArgumentException e) {
				System.out.println("The puzzle cannot slide in that direction.");
				continue;
			}
			if(currentPuzzle.isComplete()) {
				System.out.println("Success! You completed the puzzle!");
			if(trackScore)
				updateHighScores(name, roundCounter);
				break;
			}
		}
		System.out.println("You played for " + roundCounter + " rounds.");
	}

	// Main loop where the program runs
	public void run() {
		String selection;
		while(true) {
			displayMenuOptions();
			selection = cl.nextLine().trim().toLowerCase();
			if(selection.equals("?"))
				displayHelp();
			else if(selection.equals("s"))
				displayHighScores();
			else if(selection.equals("p"))
				play();
			else if(selection.equals("q"))
				break;
		}
	}

	public Game(Path puzzleWordsPath, Path highScoresPath) throws IOException {
		puzzleWords = new ArrayList<PuzzleWord>();
		this.puzzleWordsPath = puzzleWordsPath;
		this.highScoresPath = highScoresPath;
		cl = new Scanner(System.in);
		readPuzzleWords();
	}

	public static void main(String[] args) {
		Game game;
		Thread th;
		String wordfile = "", scorefile = "";
		// Get alternate files for words and scores from command line options if given
		for(int i = 0; i < args.length; i++) {
			String arg = args[i].toLowerCase();
			if(arg.equals("-w") || arg.equals("--wordfile"))
				wordfile = args[++i];
			else if(arg.equals("-s") || arg.equals("--scorefile"))
				scorefile = args[++i];
			else {
				System.out.println("Command-line options:");
				System.out.println();
				System.out.println("-w, --wordfile  <path> Path to the file where the puzzle word data is stored (default slidePuzzleWords.txt)");
				System.out.println("-s, --scorefile <path> Path to the file where the high scores data is stored (default slidePuzzleScores.txt)");
				return;
			}
		}
		 if(wordfile.equals(""))
			wordfile = "slidePuzzleWords.txt";
		if(scorefile.equals(""))
			scorefile = "slidePuzzleScores.txt";
			try {
		game = new Game(Paths.get(wordfile), Paths.get(scorefile));
		th = new Thread(game);
		th.start();
		} catch(IOException e) {
			System.err.format("%s%n", e);
		}
	}
}
