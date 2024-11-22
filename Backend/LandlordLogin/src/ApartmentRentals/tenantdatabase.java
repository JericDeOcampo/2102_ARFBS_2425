
package ApartmentRentals;
import DatabaseConnection.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class tenantdatabase {
    
    private static final String url = "jdbc:mysql://localhost:3306/arfbs";
    private static final String user = "root";
    private static final String password = "";
    private Connection con;
    
      public Connection getConnection() {
        return con;
    }

    public String saveOrderToDatabase(String FullName, String ContactNumber, String Username, String Password) {
        String Id = null;
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO tenantsignup (FullName, ContactNumber, Username, Password) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, FullName);
            pstmt.setString(2, ContactNumber);
            pstmt.setString(3,Username);
            pstmt.setString(4,Password);
            
          

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Id = generatedKeys.getString(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Id;
    }
    
     public boolean validateTenantLogin(String Username, String Password) {
        String sql = "SELECT * FROM tenantsignup WHERE Username = ? AND Password = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, Username); // Bind username
            pstmt.setString(2, Password); // Bind password

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true; // Login successful
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Login failed
    }
}

