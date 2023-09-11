package http.server.gradle;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.google.common.io.Files;
import com.kogaplanet.lunarlatteMarkupLanguage.Parser;
import com.kogaplanet.lunarlatteMarkupLanguage.api.TagHandler;

public class Main {
	
	
	
	
	
    public static void main( String[] args ){
    	
    	TagHandler tagHandler = new TagHandler();
    	
    	try {
    		
    		while(true) {
    		tagHandler.parserInit(new Parser("config.3ml"));   
    		ServerSocket server = new ServerSocket(Integer.parseInt(tagHandler.call("port").data.get(0)));
    		Socket socket =	server.accept();			
    		char[]requestData = new char[3000];
    		
    		
    		System.out.println("Connected: " + socket.getInetAddress());
    		InputStreamReader socketDataIn = new InputStreamReader(socket.getInputStream());
    		
    		socketDataIn.read(requestData);
    		
    		
    		RequestHandler handler = new RequestHandler(requestData, tagHandler);
    		
    		PrintWriter socketDataOut = new PrintWriter(socket.getOutputStream());
    		
    		List<String> data = handler.parseRequest();
    		
    		try {
    		if(handler.page.getContent().get(1).contains("text/html")) {
    			handler.writeData(data, socketDataOut);
    			
    		}else {	
    			handler.writeData(data, socketDataOut);
    			Files.copy(handler.page.file, socket.getOutputStream());
    			}
    		}catch (Exception e) {
    			data = handler.filesTable.get("/404").getContent();
    			handler.writeData(data, socketDataOut);
			}
    		
    		socketDataOut.flush();
    		
			socket.close();
			server.close();
    		}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Não foi possivel abrir uma conexão entre o servidor"
							 + "e o cliente.");
			}
    	}
}
