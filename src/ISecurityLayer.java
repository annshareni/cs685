package cs685;

import java.security.*;
import javax.crypto.*;
import java.lang.String;

/**
 *  
 * @author James
 */
public interface ISecurityLayer {
    
    /**
    * @return ciphertext resulting from the encryption. Will be Null if encryption failed. 
    * @author James
    */
    public String EncryptWithAES_256(String plainText);
    
    /**
    * @return plaintext resulting from the decryption. Will be Null if encryption failed. 
    * @author James
    */    
    public String DecryptWithAES_256(String cypherText);

    /**
    * @return ciphertext resulting from the encryption. Will be Null if encryption failed. 
    * @author James
    */    
    public String DigitalSign(String Message);  
    
    /**
    * @return plaintext resulting from the decryption. Will be Null if encryption failed. 
    * @author James
    */
    public String VerifySign(String Message, String theirPublicKey);

    /**
    * @return hash resulting from the decryption. Will be Null if hash failed. 
    * @author James
    */
    public String HashWithSha1_256(String Message);
    
    /**
    * @return true if hash of first parameter is equal to second parameter
    * @param MessageToCheck The message to be verified.
    * @param HashToCompare The hash sent with the message.
    * @author James
    */
    public boolean VerifyHashSha1_256(String MessageToCheck, String HashToCompare);
    
}
