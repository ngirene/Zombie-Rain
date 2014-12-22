package com.zombier;

import java.util.ArrayList;

public class Player {
	
	private int hearts;
	private int score;
	private ArrayList<Item> items;
	
	public Player() {
		// TODO Auto-generated constructor stub
		this.hearts = 3;
		this.score = 0;
		ArrayList<Item> items = new ArrayList<Item>();
	}
	public int getHearts() {
		return hearts;
	}

	public void setHearts(int hearts) {
		this.hearts = hearts;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public void addItems(Item item){
		this.items.add(item);
	}
	
	public void deleteItems(Item item){
		this.items.remove(item);
	}

}
