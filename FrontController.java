// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Front controller parses the request and sends it to ControllerImpl
class FrontController {
	
	Controller controller;
	Dispatcher dispatcher;
	public FrontController(Controller controller){
		this.controller = controller;
		dispatcher = new Dispatcher();
	}
	
	public void validateLogin(String usernmae, String password, String userType) throws RemoteException {
		Account account = null;
		try {
			account = controller.login(usernmae, password, userType);
			if(account != null){
				dispatcher.dispatch(account, this);
			}else{
				dispatcher.dispatch(null, this);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Product> browse (Account account)throws RemoteException{
		try {
			return controller.browse(account);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String buy (Account account, List<Item> itemList) throws RemoteException	{
		String result = null;
		try {
			result = controller.buy(account, itemList);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean addProduct (Account account, Product p) throws RemoteException{
		try {
			return controller.addProduct(account, p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addToCart(Account account, int productId , int quantity) {
		try {
			return  controller.addProductToCart(account, productId ,quantity);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Item> viewCart(Account account) {
		List<Item> itemList = new ArrayList<>();
		try {
			itemList =  controller.viewCart(account);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return itemList;
	}
}