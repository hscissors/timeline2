package com.soundsofpolaris.timeline.timeline;

public class Timeline {
	private int id;
	private String name;
	private int color;
    private String imageFile;

	private boolean selected = false;

	public Timeline(int id, String name, int color, String imageFile){
		this.id = id;
		this.name = name;
		this.color = color;
        this.imageFile = imageFile;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getColor(){
		return this.color;
	}

    public String getImageFile(){ return this.imageFile; }
	
	public boolean getSelected(){
		return this.selected;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
}
