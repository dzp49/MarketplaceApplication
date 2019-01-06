// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import java.rmi.RemoteException;
import java.util.*;
//This is Customer view
public class CustomerHome implements View{

    private Scanner in = new Scanner(System.in);
    Items item=new Items();
    Account account;
    private FrontController frontcontroller;
    CustomerHome(Account account, FrontController frontcontroller) {
        this.account = account;
        this.frontcontroller = frontcontroller;
    }

    @Override
    public void view() throws RemoteException {
        System.out.println("Welcome :" + account.username + " ,you are logged in as :" + 
        account.getUserType());
        
        do{
        int choice; 
        System.out.println("1. Browse : ");
        System.out.println("2. View Cart : ");
        System.out.println("3. Buy : ");


        System.out.println("Enter choice : ");
        choice = in.nextInt();
            switch(choice){

                case 1:
                    browse();
                    break;
                case 2:
                    viewCart();
                    break;
                default :
                    break;
            }
        }while(true);
    }
    private void browse() throws RemoteException {
        //product list
        List<Product> productList = frontcontroller.browse(account);
        for(Product p : productList){
            System.out.println(p.toString());
        }
        System.out.println("Enter product id to add to cart : ");
        int productId = in.nextInt();
        System.out.println("Enter quantity to add to cart : ");
        int quantity = in.nextInt();
        boolean  isAdded = frontcontroller.addToCart(account, productId ,quantity);
        if(isAdded){
            System.out.println("Item as been added to cart");
            view();
        }
    }

    private void viewCart() throws RemoteException {
        System.out.println("***********************CART ITEMS*******************************");
        List<Item> itemList = frontcontroller.viewCart(account);
        for (Item anItemList : itemList) {
            System.out.println("Product ID : " + anItemList.getItemId());
            System.out.println("Product Name : " + anItemList.getItemName());
            System.out.println("Product Description : " + anItemList.getDescription());
            System.out.println("Product Quantity : " + anItemList.getQuantity());
            System.out.println("Product Price : " + anItemList.getFinalPrice());
            System.out.println("*************************************************************");
        }

        System.out.println("\n");
        System.out.println("1. Browse");
        System.out.println("2. Buy current cart items");

        System.out.println("Enter your choice");

        int choice = in.nextInt();
        if(choice == 1){
            browse();
        }else{
            buy(itemList);
        }
    }

    private void buy(List<Item> itemList) throws RemoteException {
        String flag = frontcontroller.buy(account, itemList);
        System.out.println("********************************************");
        System.out.println(flag);
        System.out.println("********************************************");
        browse();
    }
}
