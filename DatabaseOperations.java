import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class DatabaseOperations {
    String url = "jdbc:postgresql://localhost:5432/Student";
    String user = "postgres";
    String password = "postgres18";

    // Return all students
    public void getAllStudents() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String SQL = "SELECT * FROM students";
                ResultSet rs = stmt.executeQuery(SQL);

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
            } else {
                System.out.print("Failed to establish connection.");
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudent(String f_name, String l_name, String email, Date enrollment) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(insertSQL);

                pstmt.setString(1, f_name);
                pstmt.setString(2, l_name);
                pstmt.setString(3, email);
                pstmt.setDate(4, enrollment);
                pstmt.executeUpdate();
                System.out.println("New student added successfully.");

            } else {
                System.out.print("Failed to establish connection.");
            }
            conn.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        DatabaseOperations operations = new DatabaseOperations();

        operations.getAllStudents();
        operations.addStudent("Samantha", "Nora", "smth@gmail.com", Date.valueOf("2024-01-08"));
        operations.getAllStudents();
    }
}