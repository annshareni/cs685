package cs685;

import java.io.*;
import java.net.*;

import javax.swing.JTextField;
public class Provider{
    ServerSocket providerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    JTextField txtNew;
    
    //Socket clientSocket;
	symm s;
    
    
    Provider(symm sObj, JTextField txtNew){
    	this.s = sObj;
    	this.txtNew = txtNew;
    }
    void run()
    {
        try{
            //1. creating a server socket
            providerSocket = new ServerSocket(2004, 10);
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
                    message = (String)in.readObject();
                    System.out.println("client>" + message);
                    
                    if(message.charAt(0)=='0') { // symmetric algorithm
            			//s = new symm();
            			int endIndex = message.length();
            			String in_m = message.substring(1, endIndex);
            			System.out.println(in_m);
            			System.out.println(in_m.length());
            			s.setInputMessage(in_m);
            			//s.Decode();
            			txtNew.setText("new message! ");
            			
            			
            		}else if (message.charAt(0)=='1') { // Asymmetric algorithm
            			
            		}
                    
                    
                    
                    if (message.equals("bye"))
                        sendMessage("bye");
                }
                catch(ClassNotFoundException classnot){
                    System.err.println("Data received in unknown format");
                }
            }while(!message.equals("bye"));
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
        Provider server = new Provider();
        while(true){
            server.run();
        }
    }
    */
}