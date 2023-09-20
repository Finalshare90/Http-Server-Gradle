package http.server.gradle;


import java.net.ServerSocket;
import com.kogaplanet.lunarlatteMarkupLanguage.Parser;
import com.kogaplanet.lunarlatteMarkupLanguage.api.TagHandler;

public class Main {
	
    public static void main( String[] args ){
    	
    	TagHandler tagHandler = new TagHandler();
    	tagHandler.parserInit(new Parser("config.3ml"));   
    	
    	try {
    		ServerSocket server = new ServerSocket(Integer.parseInt(tagHandler.call("port").data.get(0)));
    		
    		while(true) {
    		
    			System.out.println("Waiting for connection..."); 
    			Thread serverThread = new Thread(new Connection(server.accept(),server, tagHandler));
    			serverThread.start();
    			
    		
    		}
    		
    		} catch (Exception e) {
    			e.printStackTrace();
    			System.err.println("Não foi possivel abrir uma conexão entre o servidor"
    								+ "e o cliente.");
    		}
    	}
    }

