package com.avishek.model;

public class Size {

	private String name;
	private int quantiy;
	public Size() {
		super();
	}
	public Size(String name, int quantiy) {
		super();
		this.name = name;
		this.quantiy = quantiy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantiy() {
		return quantiy;
	}
	public void setQuantiy(int quantiy) {
		this.quantiy = quantiy;
	}
	
}
