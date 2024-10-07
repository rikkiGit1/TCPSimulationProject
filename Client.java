package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;

public class Client {
    
    public static void main(String[] args) {
    	
    	//final int TOTAL_PACKETS = 14;
        args = new String[] {"127.0.0.1", "30121"};
        
        if (args.length != 2) {
            System.err.println("Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }
        
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        System.out.println("Connecting to server...");
        try (
                Socket clientSocket = new Socket(hostName, portNumber); //client socket
                PrintWriter requestWriter = new PrintWriter(clientSocket.getOutputStream(), true); // stream to write text requests to server
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // stream to read text response from server
        	) {
                // Send an initial message to the server
                String initialMessage = "Hello, Server!"; //first message to server - connects to usersRequest variable on line 28
                requestWriter.println(initialMessage); // send initial message to server - line 28
                
                int totalPackets = Integer.parseInt(responseReader.readLine()); //recieves message length from server line 34
                System.out.println(responseReader.readLine()); //prints out packets to be recieved plus length - connects to line 45
                
                // Now read server responses in a loop
                ArrayList<Packet> tracker = new ArrayList<>(totalPackets);
                
                while (tracker.size() < totalPackets) {
                	
                	
                	String serverResponse; //reads the server message from line 51
                	while((serverResponse = responseReader.readLine()) != null) {
                    int packetNumber = Integer.parseInt(serverResponse.substring(0, 1)); //stores the packet number 
                    String packetValue = serverResponse.substring(1); //stores the packet value
                    tracker.add(new Packet(packetNumber, packetValue)); //adds packet number and packet value to new Packet, adds to tracker arraylist
                    
                    boolean hasPacket = false;
                    if(responseReader.readLine().equalsIgnoreCase("Finished sending packets.")) {
                    	System.out.println("Missing packets: ");
                    	requestWriter.println("Missing packets: "); //sends message to client - connects to line 57
                    	for(int i = 0; i < tracker.size(); i++) {
                    		for(int j = 0; j < tracker.size(); j++) {
                    			if(i == Integer.parseInt(tracker.get(j).getPacketNumber())) {
                    				hasPacket = true;
                    			}
                    		}
                    		if(!hasPacket) {
                    			requestWriter.println(tracker.get(i).getPacketNumber()); //connects to line 58
                    		}
                    	}
                    }
                    requestWriter.println("All packets received.");
                    
                    Collections.sort(tracker);
                  for(int i = 0; i < tracker.size(); i++) {
                	  System.out.print(tracker.get(i).getPacketValue());
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

