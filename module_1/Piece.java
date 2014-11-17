package module;

public class Piece {
	private char symbol;
	private String name;
	private String color;
	private int x;
	private int y;
	
	public Piece(Character symbol, String name, String color) {
		this.symbol = symbol;
		this.name = name;
		this.color = color;
	}
	
	public Piece(Character symbol, String name, String color, Integer x, Integer y) {
		this.symbol = symbol;
		this.name = name;
		this.color = color;
		this.x = x;
		this.y = y;
	}
	
	public void Set(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	public char getSymbol(){
		return this.symbol;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public String getName() {
		return this.name;
	}
	public String getColor() {
		return this.color;
	}
	
}
