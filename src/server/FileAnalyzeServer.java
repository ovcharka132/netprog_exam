package server;

import java.io.*;
import java.net.*;
import java.util.Date;

public class FileAnalyzeServer {
    public static void main(String[] args) {
        if (args.length < 1) return;

        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("New client connected");

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String recv = reader.readLine();
                System.out.printf("Received String:\n\t%s\n", recv);
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                final int wordsCount =getWordsCount(recv);
                writer.println(wordsCount);
                System.out.printf("Words count:\n\t%d\n", wordsCount);
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static int getWordsCount(String text) {
        try {
            return text.split(" ").length;
        } catch (Exception e) {
            return -1;
        }
    }
}