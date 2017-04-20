/*
 * @author Rui Wang
 * server side of socket
 * */

import java.io.*;
import java.net.*;

import javax.swing.JTextField;
public class Provider{
    ServerSocket providerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    String handshake;
    static JTextField txtNew;
    
    //Socket clientSocket;
	static symm s;
    
    
    Provider(symm sObj, JTextField txtNew){
    	this.s = sObj;
    	this.txtNew = txtNew;
    }
    void run()
    {
        try{
            //1. creating a server socket
            providerSocket = new ServerSocket(2005, 10);
            //2. Wait for connection
            System.out.println("I am server Waiting for connection(listen on port 2004)");
            connection = providerSocket.accept();
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            //3. get Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            sendMessage("Connection successful");
            //4. The two parts communicate via the input and output streams
            do{
                try{
                	handshake = (String)in.readObject();
                	if (handshake.equals("bye")) {
                        sendMessage("bye");}
                	else {
                    message = handshake;
                    System.out.println("client>" + message);
                    s.setToCompare(message);
                    s.setIntegrity();
                    System.out.println(s.isIntegrity());
                    txtNew.setText("new message! ");
                    if (s.isIntegrity()) {
                    	System.out.println(s.getMessageToCheck());
                    	s.inputMessage = s.getMessageToCheck();
             	
                    }
                    	
                    }
                    
                    
                    
                    
                    
                }
                catch(ClassNotFoundException classnot){
                    System.err.println("Data received in unknown format");
                }
            }while(!handshake.equals("bye"));
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            //4: Closing connection
            try{
                in.close();
                out.close();
                providerSocket.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }
    void sendMessage(String msg)
    {
        try{
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    
    /*public static void main(String args[])
    {
        Provider server = new Provider(s, txtNew);
        while(true){
            server.run();
        }
    }*/
    
}