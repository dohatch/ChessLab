package module;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Program {
	private File directive;
	
	public Program(String pathName) {
		this.directive = new File(pathName);
	}
	
	public void Run() {
		Scanner scan = null;
		try {
			scan = new Scanner(directive);
		} catch(FileNotFoundException e) {
			System.out.println("File not Found");
		}
		
		String command;
		//String movePattern = "(([A-Z][0-9])\\s([A-Z][0-9]))";
		Pattern movePattern = Pattern.compile("(([A-Ha-h][1-8])\\s([A-Ha-h][1-8]))"); // (group 1 (group 2) (group 3))
		//String moveCapturePattern = "(([A-Z][0-9])\\s([A-Z][0-9]\\*))";
		Pattern moveCapturePattern = Pattern.compile("(([A-Ha-h][1-8])\\s([A-Ha-h][1-8])\\*)");
		//String moveTwoPattern = "((([A-Z][0-9])\\s([A-Z][0-9]))\\s(([A-Z][0-9])\\s([A-Z][0-9])))";
		Pattern moveTwoPattern = Pattern.compile("((([A-Ha-h][1-8])\\s([A-Ha-h][1-8]))\\s(([A-Ha-h][1-8])\\s([A-Ha-h][1-8])))");
		//String placePattern = "(([A-Z]{2})([A-Z][0-9]))";
		Pattern placePattern = Pattern.compile("(([pPrRnNbBqQkK])([lLdD])([A-Ha-h][1-8]))");
		
		
		
		while(scan.hasNextLine()) {
			command = scan.nextLine();
			Matcher moveOneMatcher = movePattern.matcher(command);
			Matcher moveCaptureMatcher = moveCapturePattern.matcher(command);
			Matcher moveTwoMatcher = moveTwoPattern.matcher(command);
			Matcher placeMatcher = placePattern.matcher(command);
		
			if(placeMatcher.find()) {
				String piece = IdentifyPiece(placeMatcher.group(2), placeMatcher.group(3));
				System.out.println(piece + " has been placed at " + placeMatcher.group(4) + ".");
			}
			else if(moveTwoMatcher.find()) {
				System.out.println(moveTwoMatcher.group(3) + " has been moved to " + moveTwoMatcher.group(4) + ", and " + moveTwoMatcher.group(6) + " has been moved to " + moveTwoMatcher.group(7) + ".");
			}
			else if(moveCaptureMatcher.find()) {
				System.out.println(moveCaptureMatcher.group(2) + " has been moved to " + moveCaptureMatcher.group(3) + " and has captured the piece that was there.");
			}
			else if(moveOneMatcher.find()) {
				System.out.println(moveOneMatcher.group(2) + " has been moved to " + moveOneMatcher.group(3) + ".");
			}
			else {
				System.out.println("---------INVALID COMMAND---------");
			}
		}
		scan.close();
	}
		
	private String IdentifyPiece(String piece, String color) {
		
		
		if(color.equalsIgnoreCase("L")) {
			if(piece.equalsIgnoreCase("P")) {
				return "White Pawn";
			}
			else if(piece.equalsIgnoreCase("R")) {
				return "White Rook";
			}
			else if(piece.equalsIgnoreCase("N")) {
				return "White Knight";
			}
			else if(piece.equalsIgnoreCase("B")) {
				return "White Bishop";
			}
			else if(piece.equalsIgnoreCase("Q")) {
				return "White Queen";
			}
			else if(piece.equalsIgnoreCase("K")) {
				return "White King";
			}
			else {
				throw new RuntimeException("NetherCodeTouched");
			}
		}
		else if(color.equalsIgnoreCase("D")) {
			if(piece.equalsIgnoreCase("P")) {
				return "Black Pawn";
			}
			else if(piece.equalsIgnoreCase("R")) {
				return "Black Rook";
			}
			else if(piece.equalsIgnoreCase("N")) {
				return "Black Knight";
			}
			else if(piece.equalsIgnoreCase("B")) {
				return "Black Bishop";
			}
			else if(piece.equalsIgnoreCase("Q")) {
				return "Black Queen";
			}
			else if(piece.equalsIgnoreCase("K")) {
				return "Black King";
			}
			else {
				throw new RuntimeException("NetherCodeTouched");
			}
		}
		else {
			throw new RuntimeException("NetherCodeTouched");
		}
	}
}
