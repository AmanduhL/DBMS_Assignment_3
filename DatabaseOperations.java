import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class DatabaseOperations {
    // Database credentials
    String url = "jdbc:postgresql://localhost:5432/Student";
    String user = "postgres";
    String password = "postgres18";

    // Return all students from the database
    public void getAllStudents() {
        try {
            // Connect to database
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                // Create statement and execute query
                Statement stmt = conn.createStatement();
                String SQL = "SELECT * FROM students";
                ResultSet rs = stmt.executeQuery(SQL);

                // For each student in the database, print out their info
                while (rs.next()) {
                    int id = rs.getInt("student_id");
                    String f_name = rs.getString("first_name");
                    String l_name = rs.getString("last_name");
                    String email = rs.getString("email");
                    Date enrollment = rs.getDate("enrollment_date");
                    System.out.println("ID: " + id +
                            ", First Name: " + f_name +
                            ", Last Name: " + l_name +
                            ", Email: " + email +
                            ", Enrollment Date: " + enrollment);
                }

                // Close resources
                rs.close();
                stmt.close();

            } else {
                System.out.print("Failed to establish connection.");
            }
            conn.close(); // Close connection
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a student to the record
    public void addStudent(String f_name, String l_name, String email, Date enrollment) {
        try {
            // Connect to database
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                // Create prepared statement with SQL query
                String SQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(SQL);

                // Fill statement with values from function parameter and execute the completed query
                pstmt.setString(1, f_name);
                pstmt.setString(2, l_name);
                pstmt.setString(3, email);
                pstmt.setDate(4, enrollment);
                pstmt.executeUpdate();
                System.out.println("New student added successfully.");

                pstmt.close(); // Close prepared statement

            } else {
                System.out.print("Failed to establish connection.");
            }
            conn.close(); // Close connection
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Update a student's email given their ID
    public void updateStudentEmail(int student_id, String new_email) {
        try {
            // Connect to database
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                // Create prepared statement with SQL query
                String SQL = "UPDATE students SET email = ? WHERE student_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(SQL);

                // Add missing values into query and execute it
                pstmt.setString(1, new_email);
                pstmt.setInt(2, student_id);
                pstmt.executeUpdate();
                System.out.println("Student email updated successfully.");

                pstmt.close(); // Close prepared statement

            } else {
                System.out.print("Failed to establish connection.");
            }
            conn.close(); // Close connection
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a student from record
    public void deleteStudent(int student_id) {
        try {
            // Connect to database
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                // Create a prepared statement with the deletion query
                String SQL = "DELETE FROM students WHERE student_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(SQL);

                // Set missing value to given ID and execute query
                pstmt.setInt(1, student_id);
                pstmt.executeUpdate();
                System.out.println("Student email updated successfully.");

                pstmt.close(); // Close prepared statement

            } else {
                System.out.print("Failed to establish connection.");
            }
            conn.close(); // Close connection
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DatabaseOperations operations = new DatabaseOperations();

        operations.getAllStudents();
        //operations.addStudent("Samantha", "Nora", "smth@gmail.com", Date.valueOf("2024-01-08"));
        operations.deleteStudent(4);
        operations.getAllStudents();
        //operations.updateStudentEmail(4, "updated@gmail.com");
    }
}