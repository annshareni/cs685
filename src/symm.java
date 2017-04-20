/*
 * author Rui & James
 * security layer
 * */



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



public class symm {
	
	public final String base_array = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public final String base_1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String KEY= "VIGENERE";
	private String cypherText;
	private String plainText ;
	private String hash;
	private String HashToCompare;
	private String MessageToCheck;
	private String Algorithm;
	private boolean integrity;
	
	
   private static String symmetricKey = "cs685AESKeyUsage";
  
   private static KeyPair keyPair = null;
   private static Signature shawithrsa = null;
   private static PublicKey OtherPersonsPublicKey;
   private static BigInteger RSAModulus;
   private static Cipher cipher = null;
   private static SecretKey sessionKey = null;
   private static Key MacKey;	    
	
	String inputMessage;
	char[][] matrix;
	
	
	public symm() {
		matrix = new char[26][26];

		for (int i =0 ; i< 26 ; i++) {
			for (int j =0 ; j< 26 ; j++) {
				matrix[i][j]= base_array.charAt(i+j);
			} 
			
		} // end for
		
		 initialtizeRSAKeys();
		
	}// end constructor
	
	public String getInputMessage() {
		return inputMessage;
	}

	public void setInputMessage(String inputMessage) {
		this.inputMessage = inputMessage.toUpperCase();
		
	}
	
	public String getKEY() {
		return KEY;
	}

	public void setKEY(String kEY) {
		KEY = kEY.toUpperCase();
	}

	public void Encode(){
		
		String CypherText = ""; 
		int j = 0; // code index used in encoding
		
		
		for (int i=0; i< inputMessage.length();i++) {
			char ch = inputMessage.charAt(i);
		    
		    if ( (ch >= 'A') && (ch <= 'Z')) {
		    	int col = base_1.indexOf(ch);
		    	
		    	if ( j > (KEY.length()-1) ) // when the code runs out length, roll over
					j = 0;
		    	
		    	int row = base_1.indexOf(KEY.charAt(j));	// get the row number
				j = j+1;
				
				char cypherChar = matrix[row][col]; // get the encrypted letter in map
				
				CypherText = CypherText+cypherChar;
		    	
		    	
			}else {
				CypherText = CypherText+ch;
			}
			
		    setCypherText(CypherText);
		   
			
		}//end for 
				
		
	}  // end encode 
	
	
	

	public void  Decode() {
		//inputMessage=inputMessage.toUpperCase();
		String PlainText= "";
		int jj=0 ;
		
		for (int i=0; i< inputMessage.length();i++) {
			char ch = inputMessage.charAt(i);
			
			if ( (ch >= 'A') && (ch <= 'Z')) {
				if (jj > (KEY.length()-1 ))
					jj=0;
				
				char[] row = new char[26]; 
				
				
				row = matrix[base_1.indexOf( KEY.charAt(jj) )] ;//row is the line of the matrix
				
				jj = jj+1; 
				int col = 0; 
				for(int ii=0; ii< 26; ii++) {
					if(row[ii] == ch)
						col = ii;
					
				}
				
				

				char temp = base_1.charAt(col) ;// get the decrypted letter in map
				
				String plainChar = String.valueOf(temp);
				PlainText= PlainText+ plainChar;

				
			} else {
				PlainText= PlainText+ ch;
			}
			
		}
		
		setPlainText(PlainText);
		
	
	}// end decode
	
	
	public String getCypherText() {
		return cypherText;
	}

	public void setCypherText(String cypherText) {
		this.cypherText = cypherText;
	}

	public String getPlainText() {
		return plainText;
	}

	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}
	
	public String getHash() {
		return hash;
	}
	
	public String getHashToCompare() {
		return HashToCompare;
	}

	
	public String getAlgorithm() {
		return Algorithm;
	}
	
	public void setAlgorithm(String a) {
		this.Algorithm = a;
	}
	
	public boolean isIntegrity() {
		return integrity;
	}

	public void setIntegrity() {
		this.integrity = VerifyHashSha1_512(MessageToCheck, HashToCompare);
	 
	}
	
	public String getMessageToCheck() {
		return MessageToCheck;
	}
	
	 public void EncryptWithAES_256(){
	        
	        System.out.println((symmetricKey).getBytes().length);
	        System.out.println("input is "+inputMessage);
	        try
	        {
	            Cipher cipher = Cipher.getInstance("AES");
	            byte[] key = symmetricKey.getBytes("UTF-8");
	            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
	            
	            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
	            
	            byte[] inputBytes = inputMessage.getBytes();     
	            byte[] outputBytes = cipher.doFinal(inputBytes);
	            
	            String line = java.util.Base64.getEncoder().encodeToString(outputBytes);
	            
	            cypherText = line;
	        }
	        catch (Exception e)
	        {
	            System.err.println("Error in EncryptWithAES!\n " + e.getMessage() + "\n");
	            cypherText = null;
	        }
	    }

	    
	    
	    public void DecryptWithAES_256() {
	        try{
	            Cipher decryptObject = Cipher.getInstance("AES");
	            byte[] key = symmetricKey.getBytes("UTF-8");
	            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
	            
	            decryptObject.init(Cipher.DECRYPT_MODE, secretKeySpec);
	            System.out.println(inputMessage);
	            System.out.println(cypherText);
	            byte[] bytesToDecrypt = java.util.Base64.getDecoder().decode(inputMessage.getBytes());
	            byte[] DecryptedBytes = decryptObject.doFinal(bytesToDecrypt);
	            String line =  new String(DecryptedBytes, "UTF8");
	            plainText = line;
	        }
	        catch (Exception e){
	            System.err.println("Error in DecryptWithAES!\n " + e.getMessage() + "\n");
	            plainText = null;
	        }
	    }
	
	
	 public String HashWithSha1_512(String Message) {
	        
	        //try block written by Daniel Davis
	        try
	        {
	            MessageDigest sha = MessageDigest.getInstance("SHA-512");
	            sha.update(Message.getBytes());
	            byte[] hashed = sha.digest();
	            hash  = java.util.Base64.getEncoder().encodeToString(hashed);
	            return hash;
	        } 
	        catch (Exception e) 
	        {
	            System.err.println("Error in hashing! " + e.getMessage() + "!");
	        }
	        hash = null;
	        return hash;
	    }

	   
	    public boolean VerifyHashSha1_512(String MessageToCheck, String HashToCompare) {
	        
	        String hashForCompare = HashWithSha1_512(MessageToCheck);
	        return hashForCompare.equals(HashToCompare);
	    }
	    
	    public void setToCompare(String line) {
	    	System.out.println(line.length());
	    	Algorithm  = line.substring(0,1);
	    	HashToCompare = line.substring(1,89);
	    	MessageToCheck = line.substring(89, line.length());
	    }
	    
	    ///////////////RSA 
	    
	    /**
	     *@param newKey public key for this client
	     */    
	    public void setSymmetricKey(String newKey)
	    {
	        symm.symmetricKey = newKey;
	    }
	    

	    /**
	     *@return public key for this client
	     */
	    public PublicKey getPublicKey()
	    {
	        return symm.keyPair.getPublic();
	    }    
	    
	    
	    private void initialtizeRSAKeys()
	    {
	        //Snippet mostly provided by Daniel Davis
	        try 
	        {   
	            BigInteger rsaPrime;
	            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
	            
	            RSAKeyGenParameterSpec kpgSpec = new RSAKeyGenParameterSpec(2048, BigInteger.valueOf(3));
	            keyPairGenerator.initialize(kpgSpec);
	            KeyPair kp = keyPairGenerator.genKeyPair();
	            

	            keyPair = keyPairGenerator.generateKeyPair();
	            
	            
	            
	            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	            RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
	            RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);
	           
	            
	            
	            
	            
	            
	            
	            System.out.println("e = Public Key Exponent : " + rsaPubKeySpec.getPublicExponent() + "\n");
	            //System.out.println("d = Private Key Exponent : " + rsaPrivKeySpec.getPrivateExponent() + "\n");
	            //System.out.println("n = Modulus : " + rsaPubKeySpec.getModulus() + "\n");
	            
	            cipher = Cipher.getInstance("RSA");
	        } 
	        catch (InvalidKeySpecException | NoSuchPaddingException | NoSuchAlgorithmException ex)
	        {
	            System.err.println("Error in initializeRSA! " + ex.getMessage() + "!");        
	        } catch (InvalidAlgorithmParameterException ex) {
	            Logger.getLogger(symm.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    
	    
	    public void setTargetsPublicKey(String TargetsPublicKey) 
	    {
	        symm.OtherPersonsPublicKey = this.StringToPublicKey(TargetsPublicKey);
	    }    
	    
	    
	    public String getOtherPersonsPublicKey() 
	    {
	        return this.PublicKeyToString(OtherPersonsPublicKey);
	    }
	    
	    
	    protected PublicKey StringToPublicKey(String key)
	    {
	        try {
	            byte[] keyBytes = java.util.Base64.getDecoder().decode(key.getBytes("utf-8"));
	            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
	            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	            PublicKey keyPublic = keyFactory.generatePublic(spec);
	            
	            return keyPublic;
	        } 
	        catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException ex) 
	        {
	            System.err.println("Error in SecurityLayer.StringToPublicKey! " + ex.getMessage() + "!"); 
	        }
	        
	        return null;
	    }
	    
	      
	    protected String PublicKeyToString(Key key)
	    {
	        String publicKey = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
	        //String publicK = java.util.Base64.encodeToString(key.getEncoded(), java.util.Base64.DEFAULT);       
	        return publicKey;
	    }  

	    
	    /**
	     *  Generates a new sessionKey to be used for RSA encryption.
	     */
	    protected void initializeAESKey()
	    {
	        try 
	        {
	            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
	            sessionKey = keyGen.generateKey();
	        } 
	        catch (NoSuchAlgorithmException ex) 
	        {
	            System.err.println("Exception in initializeAESKey: " + ex);        
	        }
	    }
	    
	    
	   // public SecurityLayer() {
	       
	   // }

	    
	    public byte[] EncryptRSA(String PlainText)
	    {
	        byte[] encryptedBytes;
	        
	        try 
	        {
	            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());//SecurityLayer.OtherPersonsPublicKey);
	            encryptedBytes = cipher.doFinal(PlainText.getBytes("UTF-8"));
	            
	            //System.out.println("Encrypted Size: " + encryptedBytes.length);
	            
	            return encryptedBytes;
	            //return java.util.Base64.getEncoder().encodeToString(encryptedBytes);
	        } 
	        catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) 
	        {
	            System.err.println("Exception in EncryptRSA: " + ex);
	        } catch (UnsupportedEncodingException ex) {
	            Logger.getLogger(symm.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
	        return null;
	    }

	    
	    public String DecryptRSA(byte[] cipherText)
	    {
	        byte[] decryptedBytes;
	        
	        try {
	            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
	            
	            //System.out.println("Length: " + cipherText.length);
	            
	            decryptedBytes = cipher.doFinal(cipherText);

	            return new String(decryptedBytes, "utf-8");//java.util.Base64.getEncoder().encodeToString(decryptedBytes);
	        } 
	        catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) 
	        {
	            System.err.println("Exception in DecryptRSA: " + ex);
	        } catch (UnsupportedEncodingException ex) {
	            Logger.getLogger(symm.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        
	        return null;
	    }
	    
	    
	    public static void setRSAModulus(BigInteger RSAModulus) 
	    {
	        symm.RSAModulus = RSAModulus;
	    }
	    
    
	    /*public static void main(String args[]) throws IOException
	    {
	       symm s = new symm();
	       byte[] p = s.EncryptRSA("hello");
	       System.out.println(p);
	     // String line = s.DecryptRSA(p);
	     // System.out.println(line);
	      String a = p.toString();
	      System.out.println(a);
	      String n = new String(p, "utf-8");
	    System.out.println(n); 
	     // byte[] bb = a.getBytes(Charset.forName("UTF-16"));
    	 // String hh = "hello";
    	//  byte[] aa = hh.getBytes(Charset.forName("UTF-8"));
    	 // bb[i] = Byte.valueOf(yy);
	     for (int i =0; i< a.length() ; i++) {
	    	  String yy =a.substring(i, i+1);
	    	  System.out.println(yy);
	    	 // bb[i] = Byte.valueOf(yy);
	      }
	    // System.out.println(aa);
	      //byte[] value = a.getBytes();
	      //System.out.println(value);
	     
	      //s.DecryptRSA(aa); 
	      
	      System.out.println(s.DecryptRSA(p));
	      
	      
	      // s.setKEY("uah");
	      // s.setInputMessage("hello");
	      // s.EncryptWithAES_256();
	      // s.HashWithSha1_512(s.getCypherText());
	      // String line= "1"+s.getHash()+s.getCypherText();
	       //s.setToCompare(line);
	       //String line1 = s.getCypherText();
	       //String line2 =line.substring(1, 89);
	       //String line3 =line.substring(89, line.length());
	      // String line2 =line.substring(0,1);
	      // String line = s.getCypherText();
	      // System.out.println(line);
	      // s.setInputMessage(line1);
	       //s.DecryptWithAES_256();
	       //System.out.println(s.getPlainText());
			 
	    } */
	    
	

}// end class
