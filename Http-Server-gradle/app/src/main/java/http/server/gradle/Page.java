package http.server.gradle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Page {
	
	HeaderType status;
	public ArrayList<String> content = new ArrayList<>();
	public File file;
	private ArrayList<String> processedData;
	
	
	public Page(String fileName, HeaderType status, ArrayList<String> processedData){
		file = new File(fileName);
		this.status = status;
		this.processedData = processedData;
	}
	
	public List<String> getContent() {
		
		if(content.size() <= 0) {
			createContent(content);
		}
		
		return content;
		
	}
	
	
	private void createContent(List<String> content) {
		try {
		System.out.println(file.getAbsolutePath());
		Scanner in = new Scanner(file);
		content.add(RequestHandler.HEADERS.get(status));
		if(!processedData.get(11).contains("text/html")) {
		
			content.add("Content-Type: */*,image/avif,image/webp,image/png \n");
			content.add("\n");
			
		}else {
			content.add("Content-Type: text/html \n");
			content.add("\n");
			
			while(in.hasNext()){
				content.add(in.nextLine());
			}
		}
		
		in.close();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setHeader(HeaderType header) {
		status = header;
	}
}
