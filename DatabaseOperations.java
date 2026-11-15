import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;

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
                String headerFormat = "%-6s %-14s %-13s %-30s %-1s%n";
                String tableFormat = "%-6d %-14s %-13s %-30s %-1tF%n";
                System.out.printf(headerFormat, "ID", "First Name", "Last Name", "Email", "Enrollment Date");
                while (rs.next()) {
                    int id = rs.getInt("student_id");
                    String f_name = rs.getString("first_name");
                    String l_name = rs.getString("last_name");
                    String email = rs.getString("email");
                    Date enrollment = rs.getDate("enrollment_date");
                    System.out.printf(tableFormat, id, f_name, l_name, email, enrollment);
                }
                System.out.println();

                // Close resources
                rs.close();
                stmt.close();

            } else {
                System.out.print("Failed to establish connection.\n");
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
                System.out.println("New student added successfully.\n");

                pstmt.close(); // Close prepared statement

            } else {
                System.out.print("Failed to establish connection.\n");
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
                System.out.println("Student email updated successfully.\n");

                pstmt.close(); // Close prepared statement

            } else {
                System.out.print("Failed to establish connection.\n");
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
                System.out.println("Student deleted successfully.\n");

                pstmt.close(); // Close prepared statement

            } else {
                System.out.print("Failed to establish connection.\n");
            }
            conn.close(); // Close connection
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DatabaseOperations operations = new DatabaseOperations();
        Scanner sc = new Scanner(System.in);
        boolean flag = true;

        while (flag){
            System.out.println("1. Get all students\n" +
                                "2. Add a student\n" +
                                "3. Update a student's email\n" +
                                "4. Delete a student\n" +
                                "5. Exit");
            System.out.println("Enter a number:");

            int input = sc.nextInt();
            switch (input){
                case 1:
                    System.out.println("Printing all students...");
                    operations.getAllStudents();
                    break;

                case 2:
                    System.out.println("Adding a new student...");
                    System.out.println("Enter first name:");
                    sc.nextLine();
                    String firstName = sc.nextLine();
                    System.out.println("Enter last name:");
                    String lastName = sc.nextLine();
                    System.out.println("Enter email:");
                    String email = sc.nextLine();
                    System.out.println("Enter enrollment date (YYYY-MM-DD):");
                    String date = sc.nextLine();

                    operations.addStudent(firstName,lastName, email, Date.valueOf(date));
                    break;

                case 3:
                    System.out.println("Updating a student's email...");
                    System.out.println("Enter the student's ID:");
                    int id = sc.nextInt();
                    System.out.println("Enter the new email:");
                    sc.nextLine();
                    String new_email = sc.nextLine();

                    operations.updateStudentEmail(id, new_email);
                    break;

                case 4:
                    System.out.println("Deleting a student...");
                    System.out.println("Enter ID of student to remove:");
                    int id1 = sc.nextInt();

                    operations.deleteStudent(id1);
                    break;

                case 5:
                    sc.close();
                    System.out.println("Exiting...");
                    flag = false;
                    break;
            }
        }
    }
}