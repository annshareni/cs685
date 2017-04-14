

import java.security.MessageDigest;

import javax.crypto.Cipher;
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
	
	
   private final String symmetricKey = "cs685AESKeyUsage";
	    
	
	String inputMessage;
	char[][] matrix;
	
	
	public symm() {
		matrix = new char[26][26];

		for (int i =0 ; i< 26 ; i++) {
			for (int j =0 ; j< 26 ; j++) {
				matrix[i][j]= base_array.charAt(i+j);
			} 
			
		} // end for
		
		
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
    
	    /*public static void main(String args[])
	    {
	       symm s = new symm();
	       s.setKEY("uah");
	       s.setInputMessage("hello");
	       s.EncryptWithAES_256();
	       s.HashWithSha1_512(s.getCypherText());
	       String line= "1"+s.getHash()+s.getCypherText();
	       s.setToCompare(line);
	       String line1 = s.getCypherText();
	       //String line2 =line.substring(1, 89);
	       //String line3 =line.substring(89, line.length());
	      // String line2 =line.substring(0,1);
	      // String line = s.getCypherText();
	      // System.out.println(line);
	       s.setInputMessage(line1);
	       s.DecryptWithAES_256();
	       System.out.println(s.getPlainText());
			 
	    }*/
	    
	

}// end class
