// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import java.rmi.RemoteException;
public abstract class AbstractFactory {
    abstract public View getView(Account account, FrontController frontcontroller) throws RemoteException;
}
