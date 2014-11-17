package module;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {
	private File directive;
	private ChessBoard board;
	
	public Program(String pathName) {
		this.directive = new File(pathName);
	}
	
	public void Run() {
		Scanner scan = null;
		board = new ChessBoard();
		board.init();
		
		try {
			scan = new Scanner(directive);
		} catch(FileNotFoundException e) {
			System.out.println("File not Found");
		}
		
		String command;
		Pattern movePattern = Pattern.compile("(([A-Ha-h][1-8])\\s([A-Ha-h][1-8]))");
		Pattern moveCapturePattern = Pattern.compile("(([A-Ha-h][1-8])\\s([A-Ha-h][1-8])\\*)");
		Pattern moveTwoPattern = Pattern.compile("((([A-Ha-h][1-8])\\s([A-Ha-h][1-8]))\\s(([A-Ha-h][1-8])\\s([A-Ha-h][1-8])))");
		Pattern placePattern = Pattern.compile("(([pPrRnNbBqQkK])([lLdD])([A-Ha-h][1-8]))");
		
		while(scan.hasNextLine()) {
			command = scan.nextLine();
			Matcher moveOneMatcher = movePattern.matcher(command);
			Matcher moveCaptureMatcher = moveCapturePattern.matcher(command);
			Matcher moveTwoMatcher = moveTwoPattern.matcher(command);
			Matcher placeMatcher = placePattern.matcher(command);
			Piece piece;
		
			if(placeMatcher.find()) {
				piece = CreatePiece(placeMatcher.group(2), placeMatcher.group(3));
				int[] coordinates = getLocation(placeMatcher.group(4));
				piece.Set(coordinates[0], coordinates[1]);
				board.setPiece(piece, (7 - piece.getX()), piece.getY());
				
				System.out.println(piece.getColor() + " " + piece.getName() + " placed at " + placeMatcher.group(4) + ".");
				board.printBoard();
				System.out.println();
			}
			else if(moveTwoMatcher.find()) {	
				if(PartialMoveTwo(moveTwoMatcher.group(3), moveTwoMatcher.group(4))) {
					if(PartialMoveTwo(moveTwoMatcher.group(6), moveTwoMatcher.group(7))) {
						System.out.println(moveTwoMatcher.group(3) + " has been moved to " + moveTwoMatcher.group(4) + ", and " + moveTwoMatcher.group(6) + " has been moved to " + moveTwoMatcher.group(7) + ".");
						board.printBoard();
						System.out.println();	
					}
					else {
						System.out.println("-----INVALID PIECE SELECTION-----");
						System.out.println();
					}
					
				}
				else {
					System.out.println("-----INVALID PIECE SELECTION-----");
					System.out.println();
				}
			}
			else if(moveCaptureMatcher.find()) {
				int[] location = getLocation(moveCaptureMatcher.group(2));
				int[] destination = getLocation(moveCaptureMatcher.group(3));
				
				if(board.checkBoard(location)) {
					Piece target = board.getPiece(location[0], location[1]);
					
					if(board.checkBoard(destination)) {
						board.setPiece(null, destination[0], destination[1]);
						target.Set(destination[0], destination[1]);
						board.setPiece(target, target.getX(), target.getY());
						board.setPiece(null, location[0], location[1]);
					}
					
					System.out.println(moveCaptureMatcher.group(2) + " has been moved to " + moveCaptureMatcher.group(3) + ", and has captured the piece that was there.");
					board.printBoard();
					System.out.println();
				}
				else {
					System.out.println("-----INVALID PIECE SELECTION-----");
					System.out.println();
				}
			}
			else if(moveOneMatcher.find()) {
				int[] location = getLocation(moveOneMatcher.group(2));
				int[] destination = getLocation(moveOneMatcher.group(3));
				
				if(board.checkBoard(location)) {
					Piece target = board.getPiece(location[0], location[1]);
					
					if(!board.checkBoard(destination)) {
						target.Set(destination[0], destination[1]);
						board.setPiece(target, target.getX(), target.getY());
						board.setPiece(null, location[0], location[1]);
					}
					
					System.out.println(moveOneMatcher.group(2) + " has been moved to " + moveOneMatcher.group(3) + ".");
					board.printBoard();
					System.out.println();
				}
				else {
					System.out.println("-----INVALID PIECE SELECTION-----");
					System.out.println();
				}
			}
			else {
				System.out.println("---------INVALID COMMAND---------");
				System.out.println();
			}
		}
		scan.close();
	}
		
	private Piece CreatePiece(String piece, String color) {
		if(color.equalsIgnoreCase("L")) {
			if(piece.equalsIgnoreCase("P")) {
				return new Piece('P', "Pawn", "White");
			}
			else if(piece.equalsIgnoreCase("R")) {
				return new Piece ('R', "Rook", "White");
			}
			else if(piece.equalsIgnoreCase("N")) {
				return new Piece ('N', "Knight", "White");
			}
			else if(piece.equalsIgnoreCase("B")) {
				return new Piece('B', "Bishop", "White");
			}
			else if(piece.equalsIgnoreCase("Q")) {
				return new Piece('Q', "Queen", "White");
			}
			else if(piece.equalsIgnoreCase("K")) {
				return new Piece('K', "King", "White");
			}
			else {
				throw new RuntimeException("NetherCodeTouched");
			}
		}
		else if(color.equalsIgnoreCase("D")) {
			if(piece.equalsIgnoreCase("P")) {
				return new Piece('p', "Pawn", "Black");
			}
			else if(piece.equalsIgnoreCase("R")) {
				return new Piece('r', "Rook", "Black");
			}
			else if(piece.equalsIgnoreCase("N")) {
				return new Piece('n', "Knight", "Black");
			}
			else if(piece.equalsIgnoreCase("B")) {
				return new Piece('b', "Bishop", "Black");
			}
			else if(piece.equalsIgnoreCase("Q")) {
				return new Piece('q', "Queen", "Black");
			}
			else if(piece.equalsIgnoreCase("K")) {
				return new Piece('k', "King", "Black");
			}
			else {
				throw new RuntimeException("NetherCodeTouched");
			}
		}
		else {
			throw new RuntimeException("NetherCodeTouched");
		}
	}
	private int[] getLocation(String location) {
		int[] arraySpots = new int[2];
		String x = location.substring(0,  1);
		Integer y = Integer.parseInt(location.substring(1, 2));
		
		switch(x) {
		case "A":
			arraySpots[0] = 0;
			break;
		case "B":
			arraySpots[0] = 1;
			break;
		case "C":
			arraySpots[0] = 2;
			break;
		case "D":
			arraySpots[0] = 3;
			break;
		case "E":
			arraySpots[0] = 4;
			break;
		case "F":
			arraySpots[0] = 5;
			break;
		case "G":
			arraySpots[0] = 6;
			break;
		case "H":
			arraySpots[0] = 7;
			break;
		}
		arraySpots[1] = 7 - (y - 1);
		
		return arraySpots;
		
	}
	private boolean PartialMoveTwo(String here, String there) {
		int[] location = getLocation(here);
		int[] destination = getLocation(there);
		
		if(board.checkBoard(location)) {
			Piece target = board.getPiece(location[0], location[1]);
						
			if(!board.checkBoard(destination)) {
				target.Set(destination[0], destination[1]);
				board.setPiece(target, target.getX(), target.getY());
				board.setPiece(null, location[0], location[1]);
				return true;
			}
			return false;
		}
		else {
			return false;
		}
	}
}
