// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import java.rmi.RemoteException;
//Dispatcher will dispatch the respective views
public class Dispatcher {

    Account account;
    public void dispatch(Account account, FrontController frontcontroller) throws RemoteException {
        this.account = account;
        try{
        if(account != null){
            AbstractFactory abstractFactory = new HomeView(account, frontcontroller);
            abstractFactory.getView(account, frontcontroller).view();
        }else{
            System.out.println("Invalid Credentials");
        }}catch (NullPointerException e){
            e.printStackTrace();
        }

    }
}
