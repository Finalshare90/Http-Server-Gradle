package http.server.gradle;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.google.common.io.Files;
import com.kogaplanet.lunarlatteMarkupLanguage.api.TagHandler;

public class Connection implements Runnable{

	ServerSocket serverSocket;
	InputStream socketIn;
	OutputStream socketOut;
	Socket socket;
	
	TagHandler tagHandler;
	
	@Override
	public void run() {
	
		try {
		
			socketIn = socket.getInputStream();
			socketOut = socket.getOutputStream();
		
			char[]requestData = new char[3000];


			InputStreamReader socketDataIn = new InputStreamReader(socketIn);

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
        		
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public Connection(Socket socket, ServerSocket serverSocket, TagHandler tagHandler) {
			this.serverSocket = serverSocket;
			this.tagHandler = tagHandler;
			this.socket = socket;
		}
	}

