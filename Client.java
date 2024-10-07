package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
    
    public static void main(String[] args) {
    	
    	final int TOTAL_PACKETS = 14;
        args = new String[] {"127.0.0.1", "30121"};
        
        if (args.length != 2) {
            System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }
        
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        System.out.println("Connecting to server...");
        try (
                Socket clientSocket = new Socket(hostName, portNumber);
                PrintWriter requestWriter = new PrintWriter(clientSocket.getOutputStream(), true); // stream to write text requests to server
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // stream to read text response from server
        	) {
                // Send an initial message to the server
                String initialMessage = "Hello, Server!";
                requestWriter.println(initialMessage); // send initial message to server
                
                // Now read server responses in a loop
                ArrayList<String> tracker = new ArrayList<>(TOTAL_PACKETS);
                String serverResponse;
                responseReader.readLine();
                while ((serverResponse = responseReader.readLine()) != null) {
                    System.out.println("SERVER RESPONDS: \"" + serverResponse + "\"");
                    tracker.add(Integer.parseInt(String.valueOf(serverResponse.charAt(13))),(String.valueOf(serverResponse.charAt(15))));
                    
                    boolean isNull = false;
                    if(serverResponse.equalsIgnoreCase("Finished sending packets.")) {
                    	//keeps track of missing packets
                    	for(int i = 0; i < tracker.size(); i++) {
                    		if(tracker.get(i) == null) {
                    		requestWriter.println("Missing packet " + i);
                    		isNull = true;
                    		}
                    		if(!isNull) {
                    			requestWriter.println("All packets recieved.");
                    		}
                    	}
                    }
                   
                }
                
                
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " + hostName);
                System.exit(1);
            }
    }
}

