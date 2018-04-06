package br.com.globo.test;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Crawler {
	
	public static void main (String[] args) throws IOException{
		
		String pag = "https://revistaautoesporte.globo.com/rss/ultimas/feed.xml";
		ArrayList infos = new ArrayList();
		infos = takeInfos(pag);
		JsonObject json = addArrayJson(infos);
		printJson(json);
	
				
	}
	
	//Function que pega as informações da página web
	public static ArrayList takeInfos(String pag) throws IOException{
		Document doc = Jsoup.connect(pag).get();
		Document docDesc = null;
		
		Elements tagItem = doc.select("item");
		Elements tagP    = null;
		Elements tagImg  = null;
		Elements tagLi   = null;
		
		String tagDesc = "";
		String title   = "";
		String link    = "";
		String type    = "";
		
		Description desc   = null;
		ArrayList listDesc = new ArrayList();
		ArrayList itemAll  = new ArrayList();
		
		String[] contentLinks = {};
		
		int nCnt  = 0;
		int nCnt2 = 0;	
		int nCnt3 = 0;
		
		for (Element element : tagItem) {
			
			title = element.select("title").text().trim() ;
			link  = element.select("link").text().trim();
			
			tagDesc = element.select("description").text().trim();
			docDesc = Jsoup.parse(tagDesc);
			tagP    = docDesc.select("p");
			tagImg  = docDesc.select("img");
			tagLi   = docDesc.select("li");
			
			listDesc = new ArrayList();
			
			//pega os <p>
			for (Element elementP : tagP) {

				String a = elementP.select("p").text().trim();
				if(!a.equals(" ")){
					type = "text";
					desc = new Description(type, elementP.select("p").text().trim(), new String[] {});	
					listDesc.add(desc);
				}	
			}
			
			//pega os <img>
			for (Element elementImg : tagImg) {
				type = "img";
				desc = new Description(type, elementImg.select("img").attr("src").trim(), new String[] {});	
				listDesc.add(desc);	
			}
			
			//pega os <links>
			contentLinks = new String[tagLi.size()];
			for (Element elementLi : tagLi) {
				type = "links";
				contentLinks[nCnt3] = elementLi.select("li").select("a").attr("href").trim();
				
				desc = new Description(type, "", contentLinks);	
				listDesc.add(desc);
				
				nCnt3++;
			}
			
			
			//Define um bloco Item e adiciona em um arrayList
			Item i = new Item(title, link, listDesc);
			itemAll.add(i);
			
			//Limpa variaveis que precisam ser limpas
			nCnt3 = 0;
			
		}
		
		return itemAll;
		
	}
	
	//Function que constroi o json conforme arraypassado 
	public static JsonObject addArrayJson(ArrayList itemAll){
		
		JsonObject jsonFeed        = new JsonObject();
		JsonObject jsonItem 	   = new JsonObject();
		JsonObject jsonDescription = null;
		JsonArray itemDescrip 	   = null;
		ArrayList descript         = new ArrayList();
		JsonObject d1 		       = null;
		int nCnt  = 0;
		int nCnt2 = 0;
		String tagType = "";
		
		while(nCnt < itemAll.size()){
			
						
			jsonDescription = new JsonObject();
			
	        jsonDescription.addProperty("title", ((Item) itemAll.get(nCnt)).getTitle());
	        jsonDescription.addProperty("link", ((Item) itemAll.get(nCnt)).getLink());
	         
	        itemDescrip = new JsonArray();
	        
	        descript = ((Item) itemAll.get(nCnt)).getDescription();
	        
	        
	        while(nCnt2 < descript.size()){
	        	d1 = new JsonObject();
	        	d1.addProperty("type", ((Description) descript.get(nCnt2)).getType());	   
		        tagType = ((Description) descript.get(nCnt2)).getType();
		        
		        if (tagType.equals("links"))      	
		        	addArrayJson(d1, "content", ((Description) descript.get(nCnt2)).getContentLinks());
		        
		        else
		        	d1.addProperty("content", ((Description) descript.get(nCnt2)).getContent());		   
		      		        
		             
		        itemDescrip.add(d1);
		        nCnt2++;
	        }		        
	       
	        jsonDescription.add("description", itemDescrip);  
	        jsonItem.add("item"+nCnt, jsonDescription);  
	         
	        //Limpa variáveis
	        descript.clear();
	        nCnt2 = 0;
	      
			nCnt++;
		}
		jsonFeed.add("feed", jsonItem);
		
		return jsonFeed;
		
	}
	
	//Exibe json na tela
	public static void printJson(JsonObject json){
		int nCnt = 0;
		String stringJson = json.toString();
			
		while(stringJson.indexOf("item"+nCnt) != -1){
			stringJson = stringJson.replace("item"+nCnt, "item");
			nCnt++;
		}
		
        System.out.println(stringJson);
	}
	
	
	//Function que monta e retorna o array de links
	public static void addArrayJson(JsonObject jo, String property, String[] values) {
	    JsonArray array = new JsonArray();
	    for (String value : values) {
	        array.add(new JsonPrimitive(value));
	    }
	    jo.add(property, array);
	}
	
}
