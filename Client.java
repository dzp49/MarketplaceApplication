// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import java.rmi.*;
//import java.rmi.server.*;
import java.util.*;
//Client class takes in the user credentials.
public class Client{
	public static void main(String args[]){
		try {
		Controller controller= (Controller) Naming.lookup("//10.234.136.55:2999/Server");
		int isConnected = controller.connect();
        if(isConnected == 1){
			
          	System.out.println("Connected to server");

          	System.out.print("Username: ");
			Scanner in = new Scanner(System.in);
			String uname = in.next();
			System.out.print("password: ");
			String password = in.next();
			System.out.print("usertype: ");
			String userType = in.next();

			FrontController frontController = new FrontController(controller);
			frontController.validateLogin(uname,password,userType);
        }
		} catch(Exception e){
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		
}
}