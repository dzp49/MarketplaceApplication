// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

   //product list
    private List<Product> productList = new ArrayList<>();
    private Connection connection;

    ProductService() {
        this.connection = DatabaseConnection.getDatabaseConnection().getConnection();
    }

    public List<Product> getProductList() throws SQLException {
        String query = " select * from spinnama_db.product";
        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()) {
            Product product = new Product();
            product.setProdcutId(resultSet.getInt("product_id"));
            product.setName(resultSet.getString("product_name"));
            product.setDescription(resultSet.getString("description"));
            product.setPrice(resultSet.getInt("price"));
            product.setQuantity(resultSet.getInt("quantity"));
            productList.add(product);
        }
        return productList;
    }


    public boolean addProduct(Product p) throws SQLException {
            //insert command into table.
        String insertQuery = "insert into spinnama_db.product (product_name,description,price,quantity) values ('" +
                p.getName() + "','" + p.getDescription() + "'," + p.getPrice() + "," +p.getQuantity() +")";
        
        System.out.println(insertQuery);

        Statement statement = (Statement) connection.createStatement();
        int status = statement.executeUpdate(insertQuery);
        return status == 1;
    }

    public String buy(Account session, List<Item> itemList) throws SQLException {
        Statement statement;
        boolean flag = true;
        String buyStauts = "";
        for(int i = 0; i < itemList.size(); i++){
            String lastestProductQuantity = "select quantity from spinnama_db.product where product_id = "
                    + itemList.get(i).getItemId() ;
            statement = (Statement) connection.createStatement();
            ResultSet resultSet = statement.executeQuery(lastestProductQuantity);
            int lastestQuantity =0;
            while(resultSet.next()) {
                lastestQuantity = resultSet.getInt(1);
            }

            if(itemList.get(i).getQuantity() <= lastestQuantity){
                int updatedQuantity = lastestQuantity - itemList.get(i).getQuantity();
                String updateQuantity = "update spinnama_db.product set quantity = " + updatedQuantity;
                statement = (Statement) connection.createStatement();
                statement.executeUpdate(updateQuantity);

                String removeItemsFromCartQuery =
                        "delete from spinnama_db.cart_items where item_id IN (SELECT item_id from item where product_id = " +
                itemList.get(i).getItemId() + ")";
                statement = (Statement) connection.createStatement();
                statement.executeUpdate(removeItemsFromCartQuery);
                String success = "Item bout : " + itemList.get(i).getItemId() + " : " + itemList.get(i).getItemName();
                buyStauts += "\n" + i + ": " + success;
            }else{
                flag = false;
                String error = "Not in Stock + " + itemList.get(i).getItemId() + " : " + itemList.get(i).getItemName();
                buyStauts += "\n" + i +": " +  error;
            }
        }

        return buyStauts;
    }

    public boolean addProductToCart(Account account, int productId, int quantity) throws SQLException {

        Statement statement;
        Product p = getProductDetails(productId);
        int price = (p.getPrice() * quantity);
        int cartId = 0;

        String checkIfUserHasCart = "select cart_id from spinnama_db.cart where username = '" + account.getUsername() +"'";
        statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(checkIfUserHasCart);
        if (!resultSet.next()) {
            String createCartQuery = "insert into spinnama_db.cart (username) values('" + account.getUsername() +"')";
            statement = (Statement) connection.createStatement();
            cartId = statement.executeUpdate(createCartQuery, Statement.RETURN_GENERATED_KEYS);
            System.out.println(cartId);
        }else{
            cartId = resultSet.getInt(1);
            System.out.println(cartId);
        }

        String insertItemQuery = "insert into spinnama_db.item (product_id, item_quantity, final_price) values (" + productId
                +"," + quantity + "," + price +")";
        statement = (Statement) connection.createStatement();
        statement.executeUpdate(insertItemQuery, Statement.RETURN_GENERATED_KEYS);

        String maxId = "select max(item_id) from spinnama_db.item";
        int itemId = 0;
        statement = (Statement) connection.createStatement();
        ResultSet res = statement.executeQuery(maxId);
        while (res.next()){
            itemId = res.getInt(1);
        }


        System.out.println(itemId);
        System.out.println(cartId);
        String insertCartQuery = "insert into spinnama_db.cart_items (cart_id, item_id) values (" + cartId
                    +"," + itemId +")";
        statement = (Statement) connection.createStatement();
        int status = statement.executeUpdate(insertCartQuery);

        return status == 1;
    }

    private Product getProductDetails(int productId) throws SQLException {
        String query = " select * from spinnama_db.product where product_id =" + productId;
        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Product product = new Product();
        while(resultSet.next()) {
            product.setProdcutId(resultSet.getInt("product_id"));
            product.setName(resultSet.getString("product_name"));
            product.setDescription(resultSet.getString("description"));
            product.setPrice(resultSet.getInt("price"));
            product.setQuantity(resultSet.getInt("quantity"));
        }
        return product;
    }

    public List<Item> viewCart(Account account) throws SQLException {
        List<Item> itemList = new ArrayList<>();
        List<Integer> items = new ArrayList<>();
        Statement statement;
       //selecting items from cart
        String getAllCartItems = "Select i.item_id from spinnama_db.item i, spinnama_db.cart c, spinnama_db.cart_items ci" +
                " where ci.item_id = i.item_id and c.cart_id = ci.cart_id and c.username = '" + account.getUsername() + "'";


        statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getAllCartItems);
        int i=0;
        while(resultSet.next()) {
            items.add(resultSet.getInt(1));
            i++;
        }

        for(int j=0; j < items.size(); j++) {
            //selecting item details from cart
            String cartItems = "SELECT p.product_id, p.product_name, p.description, i.item_quantity, i.final_price from spinnama_db.item i, " +
                    "spinnama_db.product p " +
                    "where i.item_id = " + items.get(j) +" and i.product_id = p.product_id";
            statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(cartItems);
            Item item = new Item();
            while(rs.next()) {
              item.setItemId(rs.getInt(1));
              item.setItemName(rs.getString(2));
              item.setDescription(rs.getString(3));
              item.setQuantity(rs.getInt(4));
              item.setFinalPrice(rs.getInt(5));
              itemList.add(item);
            }
        }

        return itemList;
    }
}
