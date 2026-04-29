import java.sql.Connection;
import com.securenotes.database.DatabaseConnection;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();

            if (conn != null && !conn.isClosed()) {
                System.out.println("The connection was successful!");
            }

        } catch (Exception e) {
            System.out.println("Connection Failed!:");
            e.printStackTrace();
        }
    }
}