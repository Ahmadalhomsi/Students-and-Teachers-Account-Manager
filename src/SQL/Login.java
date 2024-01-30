/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

import static java.lang.String.valueOf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xandros
 */
public class Login {

    String url = "jdbc:postgresql://localhost:5432/chat_app";
    String user = "postgres";
    String dpassword = "ahmad";

    public boolean adminLogin(int username, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, this.dpassword);

            String query = "SELECT * FROM admin WHERE admin_id=? AND admin_pas=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            boolean isValidLogin = resultSet.next();

            connection.close();

            return isValidLogin;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean StudentLogin(int username, String password) { // username or OgrNo

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM student WHERE student_no=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            boolean isValidLogin = resultSet.next();

            connection.close();

            return isValidLogin;
        } catch (SQLException e) {
            //e.printStackTrace();
            return false;
        }
    }

    public Student studentLoginInfo(int username, String password) {
        Student student = null;

        try {
            Connection connection = DriverManager.getConnection(url, user, this.dpassword);

            String query = "SELECT * FROM student WHERE student_no=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                student = new Student(
                        resultSet.getString("student_name"),
                        resultSet.getInt("student_no"),
                        resultSet.getString("password"),
                        resultSet.getString("ilgi_alanlar"),
                        resultSet.getDouble("academic_average"),
                        resultSet.getInt("requests_count")
                );
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    public Teacher teacherLoginInfo(int username, String password) {
        Teacher teacher = null;

        try {
            Connection connection = DriverManager.getConnection(url, user, this.dpassword);

            String query = "SELECT * FROM teacher WHERE teacher_no=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            //boolean nullCheck = false; // not null
            if (resultSet.next()) {
                teacher = new Teacher(
                        resultSet.getString("teacher_name"),
                        resultSet.getInt("teacher_no"),
                        resultSet.getString("password"),
                        resultSet.getString("ilgi_alanlar"));

            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacher;
    }

    public boolean teacherLogin(int username, String password) { // username or teacherNo

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM teacher WHERE teacher_no=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            boolean isValidLogin = resultSet.next();

            connection.close();

            return isValidLogin;
        } catch (SQLException e) {
            //e.printStackTrace();
            return false;
        }
    }

    //// READING
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();

        try {

            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM student";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Retrieve student information
                // (assuming column names match your Student class fields)
                Student student;
                String studentName = resultSet.getString("student_name");
                int studentNo = resultSet.getInt("student_no");
                String password = resultSet.getString("password");
                String ilgi_alanlar = resultSet.getString("ilgi_alanlar");
                double academicAverage = resultSet.getDouble("academic_average");
                int requestsCount = resultSet.getInt("requests_count");

                student = new Student(studentName, studentNo, password, ilgi_alanlar, academicAverage, requestsCount);

                studentList.add(student);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teacherList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM teacher";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Retrieve teacher information
                // (assuming column names match your Teacher class fields)
                String teacherName = resultSet.getString("teacher_name");
                int teacherNo = resultSet.getInt("teacher_no");
                String password = resultSet.getString("password");
                String ilgi_alanlar = resultSet.getString("ilgi_alanlar");

                Teacher teacher;
                teacher = new Teacher(teacherName, teacherNo, password, ilgi_alanlar, null, null, null, null);

                teacherList.add(teacher);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacherList;
    }

    private int getRowCount(ResultSet resultSet) throws SQLException {
        resultSet.last();
        int rowCount = resultSet.getRow();
        resultSet.beforeFirst();
        return rowCount;
    }

    public void printData(String[][] data) {
        // Define the column headers
        System.out.printf("%-20s %-30s %-20s %-20s\n", "student_name", "student_no", "Department", "Academic Average");

        // Print each row of data
        for (String[] row : data) {
            System.out.printf("%-20s %-30s %-20s %-20s\n", row[0], row[1], row[2], row[3]);
        }
    }

}
