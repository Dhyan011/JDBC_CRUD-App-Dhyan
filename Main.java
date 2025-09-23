import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple POJO (Plain Old Java Object) to represent a User.
 */
class User {
    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}

/**
 * Main class to demonstrate JDBC CRUD (Create, Read, Update, Delete) operations.
 */
public class Main {

    // Database connection details.
    private static final String DB_URL = "jdbc:mysql://localhost:3306/companydb";
    private static final String USER = "root";
    private static final String PASS = "password"; // <-- IMPORTANT: Change this to your password

    public static void main(String[] args) {
        try {
            System.out.println("--- Starting JDBC CRUD Demo ---");

            // CREATE
            System.out.println("\n1. CREATING new users...");
            int generatedId1 = createUser(new User(0, "Ada Lovelace", "ada@example.com"));
            int generatedId2 = createUser(new User(0, "Grace Hopper", "grace@example.com"));
            System.out.println("Users created with IDs: " + generatedId1 + " and " + generatedId2);

            // READ
            System.out.println("\n2. READING all users...");
            List<User> users = getAllUsers();
            users.forEach(System.out::println);

            // UPDATE
            System.out.println("\n3. UPDATING user with ID " + generatedId1 + "...");
            updateUser(generatedId1, "Ada Lovelace King", "ada.king@example.com");
            System.out.println("User updated. Reading all users again:");
            getAllUsers().forEach(System.out::println);

            // DELETE
            System.out.println("\n4. DELETING user with ID " + generatedId1 + "...");
            deleteUser(generatedId1);
            System.out.println("User deleted. Final list of users:");
            getAllUsers().forEach(System.out::println);

            System.out.println("\n--- Demo Finished ---");

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates a new user in the database.
     * @return The auto-generated ID of the new user.
     */
    public static int createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        int generatedId = -1;

        // try-with-resources ensures the connection and statement are closed automatically.
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());

            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        }
        return generatedId;
    }

    /**
     * Retrieves all users from the database.
     * @return A List of User objects.
     */
    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
            }
        }
        return users;
    }

    /**
     * Updates an existing user's information by their ID.
     */
    public static void updateUser(int id, String newName, String newEmail) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newName);
            pstmt.setString(2, newEmail);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        }
    }

    /**
     * Deletes a user from the database by their ID.
     */
    public static void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}

