package org.owasp.esapi.crypto;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncryptionException;

public class CipherTextSerializerTest {
    private Cipher encryptor = null;
    private IvParameterSpec ivSpec = null;  // Note: FindBugs reports false positive
                                            // about this being unread field.

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        encryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] ivBytes = null;
        ivBytes = ESAPI.randomizer().getRandomBytes(encryptor.getBlockSize());
        ivSpec = new IvParameterSpec(ivBytes);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testAsSerializedByteArray() {
        CipherSpec cipherSpec = new CipherSpec(encryptor, 128);
        cipherSpec.setIV(ivSpec.getIV());
        SecretKey key;
        try {
            key = CryptoHelper.generateSecretKey(cipherSpec.getCipherAlgorithm(), 128);
            encryptor.init(Cipher.ENCRYPT_MODE, key, ivSpec);

            byte[] raw;
            raw = encryptor.doFinal("Hello".getBytes("UTF8"));
            CipherText ct = ESAPI.encryptor().encrypt(key, new PlainText("Hello") );
            assertTrue( ct != null );   // Here to eliminate false positive from FindBugs.
            CipherTextSerializer cts = new CipherTextSerializer( ct );
            byte[] serializedBytes = cts.asSerializedByteArray();
            CipherText result = CipherText.fromPortableSerializedBytes(serializedBytes);
            PlainText pt = ESAPI.encryptor().decrypt(key, result);
            assertTrue( "Hello".equals( pt.toString() ) );
        } catch (Exception e) {
            fail("Test failed: Caught exception: " + e.getClass().getName() + "; msg was: " + e);
            e.printStackTrace(System.err);
        }
    }

    @Test
    public final void testAsCipherText() {
        try {
            CipherText ct = ESAPI.encryptor().encrypt( new PlainText("Hello") );
            CipherTextSerializer cts = new CipherTextSerializer( ct );
            CipherText result = cts.asCipherText();
            assertTrue( ct.equals(result) );
            PlainText pt = ESAPI.encryptor().decrypt(result);
            assertTrue( "Hello".equals( pt.toString() ) );
        } catch (EncryptionException e) {
            fail("Caught EncryptionException; exception msg: " + e);
        }
    }

}
