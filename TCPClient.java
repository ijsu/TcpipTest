import java.io.*;
import java.util.*;
import java.net.*;

class TCPClient {
	public static void main(String argv[]) throws Exception {
		Socket socket = new Socket("192.168.1.139", 5566);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		boolean running = true;
		while(running) {
			try {
				String str = br.readLine();
				if (str.indexOf("/quit") >= 0) {
						System.out.println("Server Quit Connection!");
						running = false;
				} else 
					System.out.println("Received from server: " + str);
			} catch (Exception e) {
				System.out.println("Connection Exception!");
				running = false;
				}
		}
		System.out.println("Connection Close!");
		socket.close();
	}
}