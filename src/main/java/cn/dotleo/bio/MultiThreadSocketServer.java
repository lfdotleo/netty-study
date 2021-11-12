package cn.dotleo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadSocketServer {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(234);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection from " + socket);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
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
                        } finally {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String processRequest(String request) {
        return "ack: " + request;
    }
}
