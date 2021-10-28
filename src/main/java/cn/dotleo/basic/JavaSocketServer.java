package cn.dotleo.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaSocketServer {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(234);
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String request, response;
            while ((request = in.readLine()) != null) {
                if ("exit".equals(request)) {
                    break;
                }
                response = processRequest(request);
                out.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processRequest(String request) {
        return "ack: " + request;
    }
}
