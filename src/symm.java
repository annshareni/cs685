package cs685;



public class symm {
	
	public final String base_array = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public final String base_1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String KEY ;//= "VIGENERECIPHER";
	private String cypherText;
	private String plainText ;

	

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
	
	 /*public static void main(String args[])
    {
       symm s = new symm();
       s.setInputMessage("jj");
       s.Encode();
       System.out.println(s.getCypherText());
       s.setInputMessage(" ER");
       s.Decode();
       System.out.println(s.getPlainText());
       
		 
    }
    */
    
	

}// end class
