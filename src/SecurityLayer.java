package cs685;

import java.security.*;
import javax.crypto.*;
import java.lang.String;


/**
 *
 * @author James
 */
public class SecurityLayer implements ISecurityLayer{

    @Override
    public String EncryptWithAES_256(String plainText) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String DecryptWithAES_256(String cypherText) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
