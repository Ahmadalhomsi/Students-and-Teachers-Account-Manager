/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author xandros
 */
public class TablesOperation {
    
    String url = "jdbc:postgresql://localhost:5432/chat_app";
    String user = "postgres";
    String dpassword = "ahmad";
    
    public Student getStudentByStudentNo(int studentNo) {
    Student student = null;

    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        String query = "SELECT * FROM student WHERE student_no=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, studentNo);

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

    
    public Teacher getTeacherByTeacherNo(int teacherNo) {
    Teacher teacher = null;

    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        String query = "SELECT * FROM teacher WHERE teacher_no=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, teacherNo);

        ResultSet resultSet = statement.executeQuery();

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
    
    public Lesson getLessonFromNewLessons(String lessonCode, int teacherNo) {
    Lesson lesson = null;

    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        String query = "SELECT * FROM new_lessons WHERE lesson_code = ? AND teacher_no = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, lessonCode);
        statement.setInt(2, teacherNo);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String lessonName = resultSet.getString("lesson_name");
            String ilgiAlanlar = resultSet.getString("ilgi_alanlar");
            int totalKont = resultSet.getInt("total_kont");
            int remKont = resultSet.getInt("rem_kont");

            lesson = new Lesson(lessonName, lessonCode, ilgiAlanlar, totalKont, remKont, teacherNo);
        }

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lesson;
}


    
    public void updateNewLesson(String lessonName, String lessonCode, String ilgi_alanlar, int totalKont, int remKont, int teacherNo) {
    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        // Update the row in the new_lessons table
        String queryUpdateLesson = "UPDATE new_lessons SET lesson_name = ?, lesson_code = ?, ilgi_alanlar = ?, total_kont = ?, rem_kont = ? WHERE lesson_code = ? AND teacher_no = ?";
        PreparedStatement updateLessonStatement = connection.prepareStatement(queryUpdateLesson);
        updateLessonStatement.setString(1, lessonName);
        updateLessonStatement.setString(2, lessonCode);
        updateLessonStatement.setString(3, ilgi_alanlar);
        updateLessonStatement.setInt(4, totalKont);
        updateLessonStatement.setInt(5, remKont);
        updateLessonStatement.setString(6, lessonCode);
        updateLessonStatement.setInt(7, teacherNo);
        updateLessonStatement.executeUpdate();

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
}
    
    public void deleteNewLesson(String lessonName, String lessonCode, String ilgiAlanlar, int teacherNo) {
    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        // Delete the row from the new_lessons table
        String queryDeleteFromNewLessons = "DELETE FROM new_lessons WHERE lesson_name = ? AND lesson_code = ? AND ilgi_alanlar = ? AND teacher_no = ?";
        PreparedStatement deleteFromNewLessonsStatement = connection.prepareStatement(queryDeleteFromNewLessons);
        deleteFromNewLessonsStatement.setString(1, lessonName);
        deleteFromNewLessonsStatement.setString(2, lessonCode);
        deleteFromNewLessonsStatement.setString(3, ilgiAlanlar);
        deleteFromNewLessonsStatement.setInt(4, teacherNo);
        deleteFromNewLessonsStatement.executeUpdate();

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    
    public void updateStudentWithPassword(int studentNo, String studentName, String password, String ilgi_alanlar, double academicAverage) {
    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        // Update student information
        String queryUpdateStudent = 
            "UPDATE student " +
            "SET student_name = ?, password = ?, ilgi_alanlar = ?, academic_average = ? " +
            "WHERE student_no = ?";
        PreparedStatement updateStudentStatement = connection.prepareStatement(queryUpdateStudent);
        updateStudentStatement.setString(1, studentName);
        updateStudentStatement.setString(2, password);
        updateStudentStatement.setString(3, ilgi_alanlar);
        updateStudentStatement.setDouble(4, academicAverage);
        updateStudentStatement.setInt(5, studentNo);
        updateStudentStatement.executeUpdate();

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public void updateStudentWithOutPassword(int studentNo, String studentName, String ilgi_alanlar, double academicAverage) {
    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        // Update student information
        String queryUpdateStudent = 
            "UPDATE student " +
            "SET student_name = ?, ilgi_alanlar = ?, academic_average = ? " +
            "WHERE student_no = ?";
        PreparedStatement updateStudentStatement = connection.prepareStatement(queryUpdateStudent);
        updateStudentStatement.setString(1, studentName);
        updateStudentStatement.setString(2, ilgi_alanlar);
        updateStudentStatement.setDouble(3, academicAverage);
        updateStudentStatement.setInt(4, studentNo);
        updateStudentStatement.executeUpdate();

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    
    public void updateTeacherWithPassword(int teacherNo, String teacherName, String password, String ilgi_alanlar) {
    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        // Update teacher information
        String queryUpdateTeacher = 
            "UPDATE teacher " +
            "SET teacher_name = ?, password = ?, ilgi_alanlar = ? " +
            "WHERE teacher_no = ?";
        PreparedStatement updateTeacherStatement = connection.prepareStatement(queryUpdateTeacher);
        updateTeacherStatement.setString(1, teacherName);
        updateTeacherStatement.setString(2, password);
        updateTeacherStatement.setString(3, ilgi_alanlar);
        updateTeacherStatement.setInt(4, teacherNo);
        updateTeacherStatement.executeUpdate();

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public void updateTeacherWithOutPassword(int teacherNo, String teacherName, String ilgi_alanlar) {
    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        // Update teacher information
        String queryUpdateTeacher = 
            "UPDATE teacher " +
            "SET teacher_name = ?, ilgi_alanlar = ? " +
            "WHERE teacher_no = ?";
        PreparedStatement updateTeacherStatement = connection.prepareStatement(queryUpdateTeacher);
        updateTeacherStatement.setString(1, teacherName);
        updateTeacherStatement.setString(2, ilgi_alanlar);
        updateTeacherStatement.setInt(3, teacherNo);
        updateTeacherStatement.executeUpdate();

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public void createStudent(String studentName, int studentNo, String password, String ilgi_alanlar) {
    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        String query = "INSERT INTO student (student_name, student_no, password, ilgi_alanlar) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, studentName);
        statement.setInt(2, studentNo);
        statement.setString(3, password);
        statement.setString(4, ilgi_alanlar);

        statement.executeUpdate();

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public void createTeacher(String teacherName, int teacherNo, String password, String ilgi_alanlar) {
    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        String query = "INSERT INTO teacher (teacher_name, teacher_no, password, ilgi_alanlar) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, teacherName);
        statement.setInt(2, teacherNo);
        statement.setString(3, password);
        statement.setString(4, ilgi_alanlar);

        statement.executeUpdate();

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public void addTeachers(ArrayList<Teacher> teachers) {
    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        for (Teacher teacher : teachers) {
            String query = "INSERT INTO teacher (teacher_no, teacher_name, password, ilgi_alanlar) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, teacher.getTeacherNo());
            statement.setString(2, teacher.getTeacherName());
            statement.setString(3, teacher.getPassword());
            statement.setString(4, teacher.getIlgiAlanlar());

            statement.executeUpdate();
        }

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public void addStudents(ArrayList<Student> students) {
    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        for (Student student : students) {
            String query = "INSERT INTO student (student_no, student_name, password, ilgi_alanlar, academic_average, requests_count) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, student.getStudentNo());
            statement.setString(2, student.getStudentName());
            statement.setString(3, student.getPassword());
            statement.setString(4, student.getIlgiAlanlar());
            statement.setDouble(5, student.getAcademicAverage());
            statement.setDouble(6, student.getRequests_count());

            statement.executeUpdate();
        }

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }




}



    
}
