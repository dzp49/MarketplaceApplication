// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import java.lang.reflect.Proxy;
import java.rmi.*;
//import java.rmi.server.*;

public class Server{
	private static final long serialVersionUID = 1L;

	public static void main(String args[]){
		try{
		System.out.println("Creating Marketplace Server!!");
        Controller control = new ControllerImpl();
       /* //This is the machine the server runs on.
		Naming.rebind("//10.234.136.55:2999/Server", control);
		System.out.println("Server Ready!");*/

		Controller controller = (Controller)      
		Proxy.newProxyInstance(Controller.class.getClassLoader(),                
		new Class<?>[] {Controller.class},               
		new AuthorizationInvocationHandler(new ControllerImpl()));


		Naming.rebind("//10.234.136.55:2999/Server", controller);

		}catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}