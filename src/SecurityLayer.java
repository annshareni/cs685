package cs685;

import java.security.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author James
 */
public class SecurityLayer implements ISecurityLayer{

    private final String symmetricKey = "cs685AESKeyUsage";
    
    @Override
    public String EncryptWithAES_256(String plainText){
        
        System.out.println((symmetricKey).getBytes().length);
        System.out.println("input is "+plainText);
        try
        {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] key = symmetricKey.getBytes("UTF-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            
            byte[] inputBytes = plainText.getBytes();     
            byte[] outputBytes = cipher.doFinal(inputBytes);
            
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
            Cipher decryptObject = Cipher.getInstance("AES");
            byte[] key = symmetricKey.getBytes("UTF-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            
            decryptObject.init(Cipher.DECRYPT_MODE, secretKeySpec);
            
            byte[] bytesToDecrypt = java.util.Base64.getDecoder().decode(cypherText.getBytes());
            byte[] DecryptedBytes = decryptObject.doFinal(bytesToDecrypt);

            return new String(DecryptedBytes, "UTF8");
        }
        catch (Exception e){
            System.err.println("Error in DecryptWithAES!\n " + e.getMessage() + "\n");
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
    public String HashWithSha1_256(String Message) {
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
