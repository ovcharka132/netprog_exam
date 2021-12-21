package client;

import java.net.*;
import java.io.*;

public class FileAnalyzeClient {

    public static void main(String[] args) {
        if (args.length < 3) return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        String filename = args[2];

        FileAnalyzeClient fac = new FileAnalyzeClient();

        try (Socket socket = new Socket(hostname, port)) {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            final String fileContent =fac.loadFileContent(filename);
            System.out.printf("Sending String \"%s\"...\n", fileContent);
            writer.println(fileContent);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String recv = reader.readLine();

            System.out.printf("Received words count from server: %s\n",recv);


        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }

    }

    private String loadFileContent(String quoteFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(quoteFile));
        String readBuffer;

        StringBuilder bldr = new StringBuilder("");
        System.out.println("Building string...");
        while ((readBuffer = reader.readLine()) != null) {
            if(!bldr.isEmpty()) bldr.append(" ");
            bldr.append(readBuffer);
        }
        reader.close();
        return bldr.toString();
    }
}
