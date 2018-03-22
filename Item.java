package br.com.globo.test;
import java.awt.List;
import java.util.ArrayList;

public class Item {
	private String title;
	private String link;
	private ArrayList description;
	
	public Item(String title, String link, ArrayList description) {
		super();
		this.title = title;
		this.link = link;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public ArrayList getDescription() {
		return description;
	}

	public void setDescription(ArrayList description) {
		this.description = description;
	}	
	
	
	
	
	
	
}
