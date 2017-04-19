package cs685project;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.spec.*;
import javax.crypto.BadPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author James
 */
public class SecurityLayer implements ISecurityLayer{

    private static String symmetricKey = "cs685AESKeyUsage";
    private static KeyPair keyPair = null;
    private static Signature shawithrsa = null;
    private static PublicKey OtherPersonsPublicKey;
    private static BigInteger RSAModulus;
    private static Cipher cipher = null;
    private static SecretKey sessionKey = null;
    private static Key MacKey;
    
//###############    VERIFIED BLOCK      #########################################################
    
    @Override
    public String EncryptWithAES_256(String plainText){
        
        //System.out.println((symmetricKey).getBytes().length);
        //System.out.println("input is "+plainText);
        try
        {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] key = symmetricKey.getBytes("UTF-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            
            byte[] inputBytes = plainText.getBytes("UTF-8");     
            byte[] outputBytes = cipher.doFinal(inputBytes);
            
            return java.util.Base64.getEncoder().encodeToString(outputBytes);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
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
            
            byte[] bytesToDecrypt = java.util.Base64.getDecoder().decode(cypherText.getBytes("UTF-8"));
            byte[] DecryptedBytes = decryptObject.doFinal(bytesToDecrypt);

            return new String(DecryptedBytes, "UTF-8");
        }
        catch (Exception e){
            System.err.println("Error in DecryptWithAES!\n " + e.getMessage() + "\n");
            return null;
        }
    }

    
    @Override
    public String HashWithSha1_512(String Message) {
        
        //try block written by Daniel Davis
        try
        {
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            sha.update(Message.getBytes("UTF-8"));
            byte[] hashed = sha.digest();

            return java.util.Base64.getEncoder().encodeToString(hashed);
        } 
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) 
        {
            System.err.println("Error in hashing! " + e.getMessage() + "!");
        }
        
        return null;
    }

    
    @Override
    public boolean VerifyHashSha1_512(String MessageToCheck, String HashToCompare) {
        
        String hashForCompare = HashWithSha1_512(MessageToCheck);
        return hashForCompare.equals(HashToCompare);
    }

    
    /**
     *@param newKey public key for this client
     */    
    public void setSymmetricKey(String newKey)
    {
        SecurityLayer.symmetricKey = newKey;
    }
    

    /**
     *@return public key for this client
     */
    public PublicKey getPublicKey()
    {
        return SecurityLayer.keyPair.getPublic();
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
            Logger.getLogger(SecurityLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void setTargetsPublicKey(String TargetsPublicKey) 
    {
        SecurityLayer.OtherPersonsPublicKey = this.StringToPublicKey(TargetsPublicKey);
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
    
    
    public SecurityLayer() {
        initialtizeRSAKeys();
    }

    
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
            Logger.getLogger(SecurityLayer.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SecurityLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public static void setRSAModulus(BigInteger RSAModulus) 
    {
        SecurityLayer.RSAModulus = RSAModulus;
    }
    
    
//##################  Unverified Block ###############################################################      
    @Override
    public byte[] DigitalSign(String Message) 
    {
        //function heavily inspired by examples provided by Daniel Davis        
        
        
        byte[] sign;
        
        try 
        {
            shawithrsa = Signature.getInstance("SHA512withRSA");
            
            shawithrsa.initSign(keyPair.getPrivate());
            shawithrsa.update(Message.getBytes("UTF-8"));
            sign = shawithrsa.sign();
            
            System.out.println("sign inside digitalSig: "+new String(sign, "utf-8"));
            

            
            
            
            return sign;//java.util.Base64.getEncoder().encodeToString(sign);
        } 
        catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException | SignatureException ex) 
        {
            System.err.println("Error in DigitalSign before initSign! " + ex.getMessage() + "!");
        }

        return null;
    }

    
    @Override
    public String VerifySign(byte[] Message) 
    {
        try {
            System.out.println("inside VerifySign, before verify, Message is: "+new String(Message, "utf-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SecurityLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try 
        {
            shawithrsa = Signature.getInstance("SHA512withRSA");
            
            shawithrsa.initVerify(keyPair.getPublic()); //should be theirPublicKey
            shawithrsa.update(Message);//.getBytes("utf-8"));
            boolean verifies = shawithrsa.verify(Message);//Message);
            System.out.println("Verify: "+verifies);
            System.out.println("After verification, message is: "+new String(Message, "utf-8"));
            if (verifies)
            {
                return new String(Message, "utf-8");
            }
            
            //System.out.println("\nVerify -- " + verifies + " --");
        } 
        catch (InvalidKeyException | SignatureException | NoSuchAlgorithmException ex) 
        {
            System.err.println("Error in VerifySign: "+ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SecurityLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }  
    
    
    public String MacWithMD5(String Message)
    {
        try
        {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
            MacKey = keyGenerator.generateKey();
            
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(MacKey);
            byte[] result = mac.doFinal(Message.getBytes("UTF-8"));
            
            //System.out.println("Message Authentication Code : " + java.util.Base64.getEncoder().encodeToString(result));
            
            return java.util.Base64.getEncoder().encodeToString(result);
        
        } 
        catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException e) 
        {
            System.err.println("Error in Message Authentication Codes! " + e.getMessage() + "!");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SecurityLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public boolean VerifyMacMd5(String Message, String Mac)
    {
        return false;
    }
    
}

