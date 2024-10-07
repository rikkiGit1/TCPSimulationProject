package code;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	
	public static void main(String[] args) throws IOException {

		 Random rand = new Random();
		// Hard code in port number if necessary:
		args = new String[] { "30121" };
		
		if (args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		
		int portNumber = Integer.parseInt(args[0]); //holds the value of 30121
		
		try (ServerSocket serverSocket = new ServerSocket(portNumber); //sets up server with specific port number passed in
			 Socket clientSocket1 = serverSocket.accept(); //sets up connection between server and client
			 PrintWriter responseWriter1 = new PrintWriter(clientSocket1.getOutputStream(), true); //variable writes to the client socket
			 BufferedReader requestReader1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream())); //receives from the client
			) {
		
			String usersRequest = requestReader1.readLine(); //holds value of what client sends back - "Hello Server" - connects to lines 33-34
			System.out.println("\"" + usersRequest + "\" received"); //prints out "Hello Server" received
			
			ArrayList<Packet> packets = new ArrayList<>(); //ordered arraylist
			ArrayList<Packet> copyPackets = new ArrayList<>(); //shuffled arraylist
			
			String message = "We love Touro!";
			responseWriter1.println(message.length()); //sends client the length of the message - connects to line 36
			
			for (int i = 0; i < message.length(); i++) {
	            // Get the character at the current index and convert it to a String
	            String character = String.valueOf(message.charAt(i)); //gets each character of the string 
	            packets.add(new Packet(i, character)); // Add the packet value and packet number to the ArrayList
	            copyPackets.add(new Packet(i, character)); //adds to arraylist that will be shuffled
	        }
			
	        responseWriter1.println("Packets to be recieved: " + packets.size()); //writes to client packets to be received plus size connects to line 37 
			
			
		        Collections.shuffle(copyPackets); //shuffles the copyPackets arraylist
		        //int index = 0;
		        for(int i = 0; i < copyPackets.size(); i++) {
		        	if(rand.nextInt() > 20) { //if it passes through
		        		responseWriter1.println(copyPackets.get(i).getPacketNumber() + " " + copyPackets.get(i).getPacketValue()); //connects to client line 43
		        		System.out.println("Responding: " + copyPackets.get(i).getPacketNumber() + " " + copyPackets.get(i).getPacketValue());
		
		        	}
		        }
		        responseWriter1.println("Finished sending packets.");
		        
		        do {
		        System.out.println(requestReader1.readLine()); //prints out "Missing packets" - connects to line 51
		        
		        String clientResponse;
		        while((clientResponse = requestReader1.readLine()) != null) { //keeps reading from line 59
		        	
		        	for(int i = 0; i < copyPackets.size(); i++) {
		        		if(clientResponse.equals(copyPackets.get(i).getPacketNumber())) {
		        			if(rand.nextInt(100) > 20) {
		        			System.out.println("Resending " + copyPackets.get(i).getPacketNumber() + "" + copyPackets.get(i).getPacketValue());
		        			responseWriter1.println("Resending " + copyPackets.get(i).getPacketNumber() + "" + copyPackets.get(i).getPacketValue());
		        		}
		        		}	
		        	}
		        }
		        responseWriter1.println("Finished sending packets.");
		        
			} while(!usersRequest.equalsIgnoreCase("All packets recieved."));
		        
		} 
		
		catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	

}
}
