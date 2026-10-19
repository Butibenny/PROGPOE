package assignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class loginTest {
    
    // Instance of the class being tested
    private login userLogin;
    
    public loginTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        // Initialize a fresh instance before each test
        userLogin = new login();
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCheckUserName() {
        // Valid username (contains '_' and <= 5 characters)
        assertTrue(userLogin.checkUserName("ab_c"));
        assertTrue(userLogin.checkUserName("a_1"));
        
        // Invalid: missing underscore
        assertFalse(userLogin.checkUserName("abcde"));
        
        // Invalid: longer than 5 characters
        assertFalse(userLogin.checkUserName("abc_de"));
        
        // Invalid: null
        assertFalse(userLogin.checkUserName(null));
    }

    @Test
    public void testCheckPasswordComplexity() {
        // Valid password (>=8 chars, upper, digit, special)
        assertTrue(userLogin.checkPasswordComplexity("Valid123!"));
        
        // Invalid: too short
        assertFalse(userLogin.checkPasswordComplexity("Val1!"));
        
        // Invalid: no uppercase
        assertFalse(userLogin.checkPasswordComplexity("invalid123!"));
        
        // Invalid: no digit
        assertFalse(userLogin.checkPasswordComplexity("InvalidPass!"));
        
        // Invalid: no special character
        assertFalse(userLogin.checkPasswordComplexity("Invalid1234"));
    }

    @Test
    public void testCheckCellPhoneNumber() {
        // Valid South African number
        assertTrue(userLogin.checkCellPhoneNumber("+27821234567"));
        
        // Invalid: missing +27
        assertFalse(userLogin.checkCellPhoneNumber("0821234567"));
        
        // Invalid: incorrect length (too short)
        assertFalse(userLogin.checkCellPhoneNumber("+2782123456"));
        
        // Invalid: incorrect length (too long)
        assertFalse(userLogin.checkCellPhoneNumber("+278212345678"));
        
        // Invalid: contains letters
        assertFalse(userLogin.checkCellPhoneNumber("+2782123456A"));
    }

    @Test
    public void testRegisterUser() {
        // Test successful registration
        String successMessage = "The two above conditions have been met, and the user has been registered successfully.";
        assertEquals(successMessage, userLogin.registerUser("ab_c", "Valid123!", "+27821234567"));
        
        // Test invalid username format
        String badUserMsg = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        assertEquals(badUserMsg, userLogin.registerUser("toolong_user", "Valid123!", "+27821234567"));
        
        // Test invalid password format
        String badPassMsg = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        assertEquals(badPassMsg, userLogin.registerUser("ab_c", "weak", "+27821234567"));
        
        // Test invalid cell number format
        String badCellMsg = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        assertEquals(badCellMsg, userLogin.registerUser("ab_c", "Valid123!", "0821234567"));
    }

    @Test
    public void testLoginUser() {
        // First register a valid user
        userLogin.registerUser("ab_c", "Valid123!", "+27821234567");
        
        // Successful login
        assertTrue(userLogin.loginUser("ab_c", "Valid123!"));
        
        // Failed login: incorrect username
        assertFalse(userLogin.loginUser("wrong", "Valid123!"));
        
        // Failed login: incorrect password
        assertFalse(userLogin.loginUser("ab_c", "WrongPass1!"));
    }

    @Test
    public void testReturnLoginStatus() {
        // Setup name for status message
        userLogin.setFirstName("John");
        userLogin.setLastName("Doe");
        
        // Test successful login status
        String expectedSuccess = "Welcome John, Doe it is great to see you.";
        assertEquals(expectedSuccess, userLogin.returnLoginStatus(true));
        
        // Test failed login status
        String expectedFailure = "Username or password incorrect, please try again.";
        assertEquals(expectedFailure, userLogin.returnLoginStatus(false));
    }

    @Test
    public void testSetAndGetFirstName() {
        userLogin.setFirstName("Alice");
        assertEquals("Alice", userLogin.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        userLogin.setLastName("Smith");
        assertEquals("Smith", userLogin.getLastName());
    }
}