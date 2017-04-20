public class CS685ProjectTesting {

    private static SecurityLayer securityObject1;
    
    /**
     * @param args the command line arguments
     */
        private static SecurityLayer securityObject1;
    
    /**
     * @param args the command line arguments
    */ 
    public static void main(String[] args) {
        // TODO code application logic here
        
        securityObject1 = new SecurityLayer();
        //securityObject1 = new SecurityLayer();
        //testSymmetric();
        //testHash();
        //testVerifyHash();
        //testInitializeKeys();
        //testSignature();
        //testVerifySign();
        //testEncodeKeyThenDecode();
        //testRSAEncryption();
        //testInitializeAES();
        //testMac();
        //testVerifyMac();
        fullPerformanceTest();
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

    
    public static void testSignature()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        System.out.println("Enter a string: ");
        try {
            s = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(CS685Project.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        byte[] sign = securityObject1.DigitalSign(s);
        try {
            System.out.println("Signature Result: "+new String(sign, "utf-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CS685Project.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
    
    
    public static void testVerifySign()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        System.out.println("Enter a string: ");
        try {
            s = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(CS685Project.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] sign = securityObject1.DigitalSign(s);
        try {
            System.out.println("sign returned: "+new String(sign, "utf-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CS685Project.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String result = securityObject1.VerifySign(sign);
        System.out.println("result: " + result);
    }
    
    public static void testEncodeKeyThenDecode()
    {
        //String keyString = securityObject1.PublicKeyToString(securityObject1.getPublicKey());
        //System.out.println("Public Key is: " + keyString);
        
        //PublicKey publicKey = securityObject1.StringToPublicKey(keyString);
        //System.out.println("Key after: " + publicKey);
    }
    
    public static void testRSAEncryption()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        System.out.println("Enter a string: ");
        try {
            s = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(CS685Project.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        //System.out.println("Input String Size is: " + s.length());
        
        byte[] encryptedString = securityObject1.EncryptRSA(s);
        //System.out.println("Encrypted string is: " + Arrays.toString(encryptedString));
        //System.out.println("Encrypted Size after return is: " + encryptedString.length);
        
        
        String decryptedBytes = securityObject1.DecryptRSA(encryptedString);
        System.out.println("Decrypted String is: " + decryptedBytes);
        //System.out.println("Decrypted String Size is: "+decryptedBytes.length());
    }
    
    
    public static void testInitializeAES()
    {
        securityObject1.initializeAESKey();
        //System.out.println();
    }
    
    
    public static void testMac()
    {
        String Message = "Hello this is james. I'm testing the MacMD5 function.";
        System.out.println(Message);
        String mac = securityObject1.MacWithMD5(Message);
        System.out.println("Mac is: "+mac);
    }
    
    
    public static void testVerifyMac()
    {
        String Message = "Hello this is james. I'm testing the MacMD5 function.";
        System.out.println(Message);
        String mac = securityObject1.MacWithMD5(Message);
        boolean verifies = securityObject1.VerifyMacMd5(Message, mac);
        System.out.println("Result of veryifyMD5: "+verifies );
        
    }
    
    
//###########################     Performance Test Block ########################################################
    
    private static void timeAESEncryption()
    {
        
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
        {
            securityObject1.EncryptWithAES_256("Hello. This is a message for the purpose of testing performance");
        }
        long time2 = System.currentTimeMillis();
        
        System.out.println("Time for AES encryption: " + (time2 - time1) + " milliseconds\n");
    }
    
    private static void timeAESDecryption()
    {
        String toTest = securityObject1.EncryptWithAES_256("Hello. This is a message for the purpose of testing performance");
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
        {
            securityObject1.DecryptWithAES_256(toTest);
        }
        long time2 = System.currentTimeMillis();
        
        System.out.println("Time for AES Decryption: " + (time2 - time1) + " milliseconds\n");
    }
    
    private static void timeHashSha1()
    {
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
        {
            securityObject1.HashWithSha1_512("Hello. This is a message for the purpose of testing performance");
        }
        long time2 = System.currentTimeMillis();
        
        System.out.println("Time for Sha1 Hash: " + (time2 - time1) + " milliseconds\n");
    }
    
    private static void timeVerifyShaHash()
    {
        
        String testhash = securityObject1.HashWithSha1_512("Hello. This is a message for the purpose of testing performance");
        
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
        {
            securityObject1.VerifyHashSha1_512("Hello. This is a message for the purpose of testing performance", testhash);
        }
        long time2 = System.currentTimeMillis();
        
        System.out.println("Time for Verify Sha1 Hash: " + (time2 - time1) + " milliseconds\n");
    }
    
    private static void timeSymmEncryption()
    {
        symm symmtest = new symm();
        symmtest.setKEY("hello");
        symmtest.setInputMessage("This is a string for the purpose of performance testing");
        
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
        {
            symmtest.Encode();
        }
        long time2 = System.currentTimeMillis();
        
        System.out.println("Time for Symmetric Encryption: " + (time2 - time1) + " milliseconds\n");
    }
    
    private static void timeSymmDecryption()
    {
        symm symmtest = new symm();
        symmtest.setKEY("hello");
        symmtest.setInputMessage("This is a string for the purpose of performance testing");
        symmtest.Encode();
        
        
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
        {
            symmtest.Decode();
        }
        long time2 = System.currentTimeMillis();
        
        System.out.println("Time for Symmetric Decryption: " + (time2 - time1) + " milliseconds\n");
    }
       
    private static void timeDigitalSig()
    {
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
        {
            byte[] thing = securityObject1.DigitalSign("Hello. This is a message for the purpose of testing performance");
        }
        long time2 = System.currentTimeMillis();
        
        System.out.println("Time for RSA signature: " + (time2 - time1) + " milliseconds\n");
    }
    
    
    private static void timeDigitalSigVerify()
    {
        
    }
    
    
    private static void timeRSAEncryption() 
    {
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
        {
            securityObject1.EncryptRSA("Hello. This is a message for the purpose of testing performance");
        }
        long time2 = System.currentTimeMillis();
        
        System.out.println("Time for RSA2048 encryption: " + (time2 - time1) + " milliseconds\n");
    }

    private static void timeRSADecryption() 
    {
        long time1 = System.currentTimeMillis();
        byte[] toDecrypt = securityObject1.EncryptRSA("Hello. This is a message for the purpose of testing performance");
        
        for(int i = 0; i < 1000; i++)
        {
            securityObject1.DecryptRSA(toDecrypt);
        }
        long time2 = System.currentTimeMillis();
        
        System.out.println("Time for RSA Decryption: " + (time2 - time1) + " milliseconds\n");
    }    
    
    private static void timeMACGeneration()
    {
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
        {
            securityObject1.MacWithMD5("Hello. This is a message for the purpose of testing performance");
        }
        long time2 = System.currentTimeMillis();
        
        System.out.println("Time for MAC generation: " + (time2 - time1) + " milliseconds\n");
    }
    
    private static void timeVerifyMacGeneration()
    {
        String Mack = securityObject1.MacWithMD5("Hello. This is a message for the purpose of testing performance");
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
        {
            securityObject1.VerifyMacMd5("Hello this hopefully works", Mack);
        }
        long time2 = System.currentTimeMillis();
        
        System.out.println("Time for MAC verification: " + (time2 - time1) + " milliseconds\n");
    }
    
    
    public static void fullPerformanceTest()
    {
        timeAESEncryption();
        timeAESDecryption();
        timeRSAEncryption();
        timeRSADecryption();
        timeHashSha1();
        timeVerifyShaHash();
        timeSymmEncryption();
        timeSymmDecryption();
        timeDigitalSig();
        timeMACGeneration();
        timeVerifyMacGeneration();
        
    }
    
    
    
}
