// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

// Interface Implementation

public class ControllerImpl extends UnicastRemoteObject implements Controller {

	ControllerImpl()throws RemoteException{
            DatabaseConnection.getDatabaseConnection();
	}
	
	@Override
	//Used to check if Server is Connected
	public int connect() throws RemoteException {
		return 1;
	}

	@Override
	//Login Credentials
	public Account login(String uname, String pass, String type) throws RemoteException, SQLException {
		Account account = new Account();
        LoginService ls = new LoginService();
        account = ls.validateCredentials(uname, pass, type);
		return account;
	}

	public String buy(Account session, List<Item> itemList) throws RemoteException, SQLException {
        ProductService ps = new ProductService();
		return ps.buy(session, itemList);
	}

	public boolean addProduct(Account session, Product p) throws RemoteException, SQLException {
        ProductService ps = new ProductService();
        return ps.addProduct(p);
	}

	public List<Product> browse(Account session) throws RemoteException, SQLException {
        ProductService ps = new ProductService();
		return ps.getProductList();
	}

	@Override
	public boolean addProductToCart(Account account, int productId, int quantity) throws RemoteException, SQLException {
		ProductService ps = new ProductService();
		 return ps.addProductToCart(account,productId,quantity);
	}

	@Override
	public List<Item> viewCart(Account account) throws RemoteException,SQLException {
		ProductService ps = new ProductService();
		return ps.viewCart(account);
	}


}