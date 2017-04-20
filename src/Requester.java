/*
 * @author Rui Wang
 * client side of soket
 * */


import java.io.*;
import java.net.*;
public class Requester{
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
   // ByteArrayOutputStream bObj ;//= new ByteArrayOutputStream();
    String message;
    static symm s;
    
    Requester(symm sObj){
    	this.s = sObj;
    }
    void run()
    {
        try{
            //1. creating a socket to connect to the server
            requestSocket = new Socket("192.168.0.102", 2004);
            System.out.println("I am client Connected to localhost in port 2004, I am initiate message");
            //2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
           // bObj = new ByteArrayOutputStream();
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            //3: Communicating with the server
            do{
                try{
                    message = (String)in.readObject();
                    System.out.println("server>" + message);
                    String outline =s.getAlgorithm()+s.getHash()+s.getCypherText();
                    
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
        	 byte[] p = s.EncryptRSA("hello");
             
   	        //bObj.reset();
   	        //bObj.write(p);
            //out.writeObject(bObj);
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    
    
  /* public static void main(String args[])
    {
        
	  // symm s = new symm() ;
	   Requester client = new Requester(s);
        client.run();
    }*/
    
    
}

