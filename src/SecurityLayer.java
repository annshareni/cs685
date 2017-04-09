package cs685;

import java.security.*;
import javax.crypto.*;
import java.lang.String;


/**
 *
 * @author James
 */
public class SecurityLayer implements ISecurityLayer{

    private final String encryptionKey = "cs685AESKeyUsage";
    
    @Override
    public String EncryptWithAES_256(String plainText){
        
        System.out.println((encryptionKey).getBytes().length);
        System.out.println("input is "+plainText);
        try
        {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] key = encryptionKey.getBytes("UTF-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            
            byte[] inputBytes = plainText.getBytes();     
            byte[] outputBytes = cipher.doFinal(inputBytes);
            
            String outPut = outputBytes.toString();
            System.out.println("outPut is: "+outPut);
            
            return java.util.Base64.getEncoder().encodeToString(outputBytes);
            //Base64Utils.encodeToString(outputBytes);
        }
        catch (Exception e)
        {
            System.err.println("Error in EncryptWithAES!\n " + e.getMessage() + "\n");
            return null;
        }
    }

    @Override
    public String DecryptWithAES_256(String cypherText) {
        try{
            Cipher dcipher = Cipher.getInstance("AES");
            dcipher = Cipher.getInstance("AES");
            byte[] key = encryptionKey.getBytes("UTF-8");
            
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            
            dcipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            
            byte[] dec =java.util.Base64.getDecoder().decode(cypherText.getBytes());
            byte[] utf8 = dcipher.doFinal(dec);

            return new String(utf8, "UTF8");
        }
        catch (Exception e){
            System.err.println("Error in EncryptWithAES!\n " + e.getMessage() + "\n");
            return null;
        }
    }


    @Override
    public String DigitalSign(String Message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String VerifySign(String Message, String theirPublicKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String HashWithSha1_256(String Message, String HashKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean VerifyHashSha1_256(String MessageToCheck, String HashToCompare) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public SecurityLayer() {
        try{
            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(2048);
            PublicSchemeKeyPair = keyGenerator.generateKeyPair();
        }
        catch (Exception e) {
            System.err.println("Error in Key generation " + e.getMessage() + "!");
        }
    }

    private static KeyPair PublicSchemeKeyPair = null;
    private static String SymmetricKey = "CS685SymmetricKey";
    
    



}
