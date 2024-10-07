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
		
		int portNumber = Integer.parseInt(args[0]);
		
		try (ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket1 = serverSocket.accept();
				PrintWriter responseWriter1 = new PrintWriter(clientSocket1.getOutputStream(), true);
				BufferedReader requestReader1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
				) {
			String usersRequest = requestReader1.readLine();
			ArrayList<String> packets = new ArrayList<String>();
			ArrayList<String> copyPackets = new ArrayList<>();
			
			do {
				System.out.println("\"" + usersRequest + "\" received");
				
//				boolean isFinished = false;
//				while(isFinished) {
				//response = make array and loop through indexes
				
				
				
		        String message = "We love Touro!";

		        //loop that fills up arraylist
		        for (int i = 0; i < message.length(); i++) {
		            // Get the character at the current index and convert it to a String
		            String character = String.valueOf(message.charAt(i));
		            packets.add(character); // Add the character to the ArrayList
		            copyPackets.add(character); //adds to arraylist that will be shuffled
		        }

		        responseWriter1.println("Packets to be recieved: " + packets.size());
		        Collections.shuffle(copyPackets);
		        int index = 0;
		        for(int i = 0; i < copyPackets.size(); i++) {
		        	if(rand.nextInt(100) > 20) {
		        		for(int j = 0; j < packets.size(); j++) {
		        			if(copyPackets.get(i).equalsIgnoreCase(packets.get(j))) {
		        				index = j;
		        			}
		        		}
//					String response = String.valueOf(index + " " + packets.get(i));
//					System.out.println("Responding: \"" + response + "\""); //+ index
					responseWriter1.println("Responding: " + index + " " + packets.get(i));
		        		
		        	}
				}
		        
		        responseWriter1.println("Finished sending packets.");
		        
		        for(int i = 0; i < packets.size(); i++) {
		        	if(Integer.parseInt(usersRequest) == i) {
		        		if(rand.nextInt(100) > 20) {
		        			responseWriter1.println("Responding: " + index + " " + packets.get(i));
		        		}
		        	}
		        }
		        
		        //break;
		        
			} while(!usersRequest.equalsIgnoreCase("All packets recieved."));
		        
		} 
		
		catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	

}
}
