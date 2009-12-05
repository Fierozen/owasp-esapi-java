/**
 * OWASP Enterprise Security API (ESAPI)
 *
 * This file is part of the Open Web Application Security Project (OWASP)
 * Enterprise Security API (ESAPI) project. For details, please see
 * <a href="http://www.owasp.org/index.php/ESAPI">http://www.owasp.org/index.php/ESAPI</a>.
 *
 * Copyright (c) 2007 - The OWASP Foundation
 *
 * The ESAPI is published by OWASP under the BSD license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 *
 * @author Jeff Williams <a href="http://www.aspectsecurity.com">Aspect Security</a>
 * @created 2007
 */
package org.owasp.esapi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.owasp.esapi.filters.ClickjackFilterTest;
import org.owasp.esapi.filters.SafeRequestTest;
import org.owasp.esapi.util.CipherSpecTest;
import org.owasp.esapi.util.CryptoHelperTest;
import org.owasp.esapi.util.ObjFactoryTest;
import org.owasp.esapi.StringUtilitiesTest;
import org.owasp.esapi.waf.WAFFilterTest;

/**
 * The Class AllTests. Execute all the JUnit 3 tests.
 *
 * @author Jeff Williams (jeff.williams@aspectsecurity.com)
 */
public class AllTests extends TestCase {

    /**
	 * Instantiates a new all tests.
	 *
	 * @param testName
	 *            the test name
	 */
    public AllTests(String testName) {
        super(testName);
    }

    /**
     * {@inheritDoc}
     * @throws Exception
     */
    protected void setUp() throws Exception {
    	// none
    }

    /**
     * {@inheritDoc}
     * @throws Exception
     */
    protected void tearDown() throws Exception {
    	// none
    }

    /**
	 * suite method automatically generated by JUnit module. Note: to have this
	 * work with JUnit 4 test cases, the JUnit 4 test case must define the static
	 * {@code suite()} method as:
	 * <pre>
	 * 		public static junit.framework.Test suite() {
	 *			return new JUnit4TestAdapter(XYZTest.class);
	 * 		}
	 * </pre>
	 * The key to supporting JUnit 4 test cases using the JUnit 3 test runner
	 * is to use the {@code JUnit4TestAdapter}. See {@code org.owasp.esapi.util.CryptoHelperTest}
	 * for an example.
	 * 
	 * @return the test
	 */
    public static Test suite() {
        System.out.println( "INITIALIZING ALL JUNIT 3 and 4 TESTS" );

        // WARNING
        // Be sure to read the DefaultSecurityConfiguration documentation
        // for information on how to set up your resources directories.

        // clear the User file to prep for tests
        PrintWriter writer = null;
        try {
        	File file = ESAPI.securityConfiguration().getResourceFile( "users.txt" );
            if ( file == null ) throw new IOException( "Can't find user file");
            file.createNewFile();
            writer = new PrintWriter(new FileWriter(file));
            writer.println("# This is the user file associated with the ESAPI library from http://www.owasp.org");
            writer.println("# accountName | hashedPassword | roles | locked | enabled | rememberToken | csrfToken | oldPasswordHashes | lastPasswordChangeTime | lastLoginTime | lastFailedLoginTime | expirationTime | failedLoginCount");
            writer.println();
            writer.flush();
        } catch (IOException e) {
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        TestSuite suite = new TestSuite("AllTests");
       
        suite.addTest(org.owasp.esapi.reference.JavaLoggerTest.suite());
        suite.addTest(org.owasp.esapi.reference.Log4JLoggerTest.suite());
        suite.addTest(org.owasp.esapi.reference.SafeFileTest.suite());
        suite.addTest(org.owasp.esapi.reference.UserTest.suite());
        suite.addTest(org.owasp.esapi.ESAPITest.suite());
        suite.addTest(org.owasp.esapi.reference.RandomizerTest.suite());
        suite.addTest(org.owasp.esapi.reference.AccessControllerTest.suite());
        suite.addTest(org.owasp.esapi.reference.HTTPUtilitiesTest.suite());
        suite.addTest(org.owasp.esapi.reference.ValidatorTest.suite());
        suite.addTest(org.owasp.esapi.reference.EncryptorTest.suite());
        suite.addTest(org.owasp.esapi.reference.IntrusionDetectorTest.suite());
        suite.addTest(org.owasp.esapi.reference.AccessReferenceMapTest.suite());
        suite.addTest(org.owasp.esapi.reference.IntegerAccessReferenceMapTest.suite());
        suite.addTest(org.owasp.esapi.reference.ExecutorTest.suite());
        suite.addTest(org.owasp.esapi.reference.EncoderTest.suite());
        suite.addTest(org.owasp.esapi.reference.EncryptedPropertiesTest.suite());
        suite.addTest(org.owasp.esapi.reference.AuthenticatorTest.suite());
        suite.addTest(org.owasp.esapi.reference.DefaultCipherTextTest.suite());  // A JUnit 4 test
        suite.addTest(org.owasp.esapi.reference.LegacyEncryptorTest.suite());	 // A JUnit 4 test

        // main
        suite.addTest(org.owasp.esapi.ValidationErrorListTest.suite() );
        suite.addTest( org.owasp.esapi.UserTest.suite() );
        suite.addTest( org.owasp.esapi.PlainTextTest.suite() );					 // A JUnit 4 test
        
        // codecs
        suite.addTest(org.owasp.esapi.codecs.CodecTest.suite() );

        // exceptions
        suite.addTest(org.owasp.esapi.errors.EnterpriseSecurityExceptionTest.suite());

        // filters
        suite.addTest(WAFFilterTest.suite());
        suite.addTest(ClickjackFilterTest.suite());
        suite.addTest(SafeRequestTest.suite());
        
        // util
        suite.addTest(CipherSpecTest.suite());		// A JUnit 4 test
        suite.addTest(CryptoHelperTest.suite());	// A JUnit 4 test
        suite.addTest(ObjFactoryTest.suite());
        suite.addTest(StringUtilitiesTest.suite());
        
        return suite;
    }
}
