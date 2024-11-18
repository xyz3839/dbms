import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCExample {
    public static void main(String[] args) {
        // MySQL database URL
        String jdbcURL = "jdbc:mysql://localhost:3306/manali2005";
        String username = "root";
        String password = "root";

        try {
            // Step 1: Load the MySQL JDBC driver (Optional in modern JDBC)
            // Class.forName("com.mysql.cj.jdbc.Driver"); 

            // Step 2: Establish a connection
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            // Insert data into the users table
            insertUser(connection, "John Doe", "john@example.com", "password123");
            insertUser(connection, "Jane Smith", "jane@example.com", "securePassword");

            // Step 3: Create a Statement object
            Statement statement = connection.createStatement();

            // Step 4: Execute a SQL query to retrieve data
            String sql = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(sql);

            // Step 5: Process the result set
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                System.out.println(id + ", " + name + ", " + email);
            }

            // Step 6: Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to insert a user into the users table
    private static void insertUser(Connection connection, String name, String email, String password) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password); // Consider hashing the password before inserting
            preparedStatement.executeUpdate();
            System.out.println("User added: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
