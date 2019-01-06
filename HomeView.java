// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import java.rmi.RemoteException;
//Homeview is a factory
public class HomeView extends AbstractFactory {

    Account account;
    FrontController frontcontroller;
    public HomeView(Account account, FrontController frontcontroller) {
        this.account = account;
        this.frontcontroller = frontcontroller;
    }

    @Override
    public View getView(Account account, FrontController frontcontroller) throws RemoteException {

        if(account.userType.equals("customer")){
            View homeView = (View) new CustomerHome(account, frontcontroller);
            return homeView;//returns customer view
        }else if(account.userType.equals("admin")){
            View adminView = (View) new AdminHome(account, frontcontroller);
            return adminView;//returns admin view
        }else{
            return null;
        }
    }
}
