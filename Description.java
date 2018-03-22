package br.com.globo.test;

public class Description {
	private String type;
	private String content;
	private String[] contentLinks;
	
	public Description(String type, String content, String[] contentLinks) {
	
		this.type = type;
		this.content = content;
		this.contentLinks = contentLinks;
	}
	
	public Description(String type, String[] contentLinks) {
	
		this.type = type;
		this.contentLinks = contentLinks;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getContentLinks() {
		return contentLinks;
	}

	public void setContentLinks(String[] contentLinks) {
		this.contentLinks = contentLinks;
	}
	
	
	
	
	
	
	

}
