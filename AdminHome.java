// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import java.rmi.RemoteException;
import java.util.*;
//This class presents the admin view
public class AdminHome implements View {
    Items item=new Items();
	Account account;
    FrontController frontcontroller;
    public AdminHome(Account account, FrontController frontcontroller) {
        this.frontcontroller = frontcontroller;
        this.account = account;
    }

    @Override
    public void view() throws RemoteException {
        System.out.println("Welcome :" + account.username + " ,you are logged in as :" +
         account.getUserType());
        
        do{
        int choice; 
        System.out.println("1. Browse : ");
        System.out.println("2. Add Product : ");

        Scanner in = new Scanner(System.in);
        System.out.println("Enter choice : ");
        choice = in.nextInt();
            switch(choice){

                case 1:
                    browse();
                    break;
                case 2:
                    addProduct();
                    break;
                default :
                    break;
            }
        }while(true);
    }
    private void browse() throws RemoteException {
        List<Product> productList = frontcontroller.browse(account);
        for(Product p : productList){
            System.out.println(p.toString());
        }
    }

    private void addProduct() throws RemoteException {
        //setters for values
        Product p = new Product();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter Name:");
        String name = in.next();
        p.setName(name);
        System.out.println("Enter Description:");
        String description = in.next();
        p.setDescription(description);
        System.out.println("Enter quantity:");
        int quantity = in.nextInt();
        p.setQuantity(quantity);
        System.out.println("Enter price:");
        int price = in.nextInt();
        p.setPrice(price);
        boolean flag = frontcontroller.addProduct(account, p);
        if(flag){
            System.out.println("item added");
            browse();
        }else {
            System.out.println("item not added");
        }
    }

}
