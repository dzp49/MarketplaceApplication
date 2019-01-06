// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

// RMI interface for server
public interface Controller extends java.rmi.Remote{
	// abstract classes
	public int connect() throws RemoteException;

	public Account login(String uname, String pass, String type) throws RemoteException, SQLException;
    
	@RequiresRole("customer")
	public String buy(Account session, List<Item> itemList) throws RemoteException, SQLException;

	@RequiresRole("admin")
	public boolean addProduct(Account session, Product p) throws RemoteException, SQLException;

	public List<Product> browse(Account session) throws RemoteException, SQLException;

	@RequiresRole("customer")
	public boolean addProductToCart(Account account, int productId, int quantity) throws RemoteException, SQLException;

	@RequiresRole("customer")
	List<Item> viewCart(Account account) throws RemoteException, SQLException;
}