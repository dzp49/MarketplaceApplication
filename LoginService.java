// Honor Pledge://
// I pledge that I have neither given nor //
// received any help on this assignment.//
// spinnama
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;

class LoginService {

    private Connection connection;

    LoginService() {
        this.connection = DatabaseConnection.getDatabaseConnection().getConnection();
    }

    public Account validateCredentials(String uname, String pass, String type) throws SQLException {
        String loginQuery = "SELECT * from spinnama_db.users where username='"+uname+"' and password='" + pass+ "'";
        Statement statement = (Statement) connection.createStatement();
        ResultSet resultSet = statement.executeQuery(loginQuery);
        if (resultSet.next()) {
            Account account = new Account();
            account.setUsername(uname);
            account.setPassword(pass);
            account.setUserType(type);
            return account;
        }
        return null;
    }
}
