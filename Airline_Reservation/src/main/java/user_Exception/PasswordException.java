package user_Exception;

import java.util.regex.*;

//Custom exception for invalid passwords
public class PasswordException extends Exception {
	public PasswordException(String message) {
		super(message);
	}
}

