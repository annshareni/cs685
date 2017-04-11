public class CS685ProjectTesting {

    private static SecurityLayer securityObject1;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        securityObject1 = new SecurityLayer();
        //testSymmetric();
        //testHash();
        //testVerifyHash();
        //testInitializeKeys();
    }
    
    public static void testSymmetric(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        System.out.println("Enter a string: ");
        try {
            s = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(CS685Project.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String helloString = securityObject1.EncryptWithAES_256(s);
        System.out.println("Output of EncryptWithAES_256 : " + helloString + "\n");
        
        String decryptedString = securityObject1.DecryptWithAES_256(helloString);
        System.out.println("Output of DecryptWithAES_256 : " + decryptedString + "\n");
    }
    
    
    public static void testHash(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        System.out.println("Enter a string: ");
        try {
            s = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(CS685Project.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String hashString = securityObject1.HashWithSha1_512(s);
        System.out.println("Output of HashWithSha1 : " + hashString + "\n");
        System.out.println("Size of hash: "+ hashString.length());
    }
    
    
    public static void testVerifyHash()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        System.out.println("Enter a string: ");
        try {
            s = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(CS685Project.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String hashString = securityObject1.HashWithSha1_512(s);
        
        boolean hashbool = securityObject1.VerifyHashSha1_512(s, hashString);
        
        System.out.println("Output of VerifyHashWithSha1 : " + hashbool + "\n");

    }

    public static void testInitializeKeys()
    {
        securityObject1.initialtizeRSAKeys();
    }    
}
