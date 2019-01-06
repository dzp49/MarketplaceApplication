// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama

public class AuthorizationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AuthorizationException(String methodName) {
		System.out.println("Invalid Authorization - Access Denined to " + methodName + "() function!");   //Exception Handling for unauthorized accesss
	}
}