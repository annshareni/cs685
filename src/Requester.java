package cs685;


import java.io.*;
import java.net.*;
public class Requester{
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    symm s;
    
    Requester(symm sObj){
    	this.s = sObj;
    }
    void run()
    {
        try{
            //1. creating a socket to connect to the server
            requestSocket = new Socket("192.168.0.104", 2004);
            System.out.println("I am client Connected to localhost in port 2004, I am initiate message");
            //2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            //3: Communicating with the server
            do{
                try{
                    message = (String)in.readObject();
                    System.out.println("server>" + message);
                    String outline ="0 "+s.getCypherText();
                    sendMessage(outline);
                    message = "bye";
                    sendMessage(message);
                }
                catch(ClassNotFoundException classNot){
                    System.err.println("data received in unknown format");
                }
            }while(!message.equals("bye"));
        }
        catch(UnknownHostException unknownHost){
            System.err.println("You are trying to connect to an unknown host!");
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
        finally{
            //4: Closing connection
            try{
                in.close();
                out.close();
                requestSocket.close();
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
            System.out.println("client>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    
    /*
    public static void main(String args[])
    {
        Requester client = new Requester();
        client.run();
    }
    */
}
