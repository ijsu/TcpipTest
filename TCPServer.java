import java.net.*;
import java.io.*;
import java.util.*;

class TCPServer {

    public static void main (String[] args) throws IOException {
		ServerSocket server = null;
		Socket client = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		boolean running = true;

		try {
            server = new ServerSocket(5566);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5566 ");
            System.err.println(e);
            System.exit(1);
        }
		
		System.out.println("Waiting for client to connect ....");
		try {
			client = server.accept();
			br = new BufferedReader(new InputStreamReader(System.in));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.err.println(e);
			System.exit(1);
		}

		System.out.println("Client connected. please input data --->");
		while (running) {
			try{ 
				String str = br.readLine();
				pw.println(str);
				System.out.println("Server --> Client: " + str);
				pw.flush();
				if (str.indexOf("/quit") >= 0) {
						System.out.println("Server Quit Input!");
						running = false;
				}
			} catch (Exception e) {
				System.out.println("Server Exception!");
				running = false;
				}
		}
		System.out.println("Server Close Connection!");;
		client.close();
	}
	
}
