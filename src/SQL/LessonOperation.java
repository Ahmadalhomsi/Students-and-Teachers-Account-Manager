/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

import Base.NotificationsSend;
import Base.RandomLessonsLists;
import Base.TeacherPriority;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author xandros
 */
public class LessonOperation {

    String url = "jdbc:postgresql://localhost:5432/chat_app";
    String user = "postgres";
    String dpassword = "ahmad";

    public void addApprovedLessonsForAllStudents() { // updateAllStudentsLessonArrays
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            // Get all student numbers
            String queryGetStudentNumbers = "SELECT student_no FROM student";
            PreparedStatement getStudentNumbersStatement = connection.prepareStatement(queryGetStudentNumbers);
            ResultSet studentNumbersResultSet = getStudentNumbersStatement.executeQuery();

            while (studentNumbersResultSet.next()) {
                int studentNo = studentNumbersResultSet.getInt("student_no");

                // Get approved lessons for the student
                String queryGetApprovedLessons
                        = "SELECT l.lesson_name, l.lesson_code, l.status "
                        + "FROM lessons l "
                        + "WHERE l.student_no = ? AND l.status = 'Onayli'";

                PreparedStatement getLessonsStatement = connection.prepareStatement(queryGetApprovedLessons);
                getLessonsStatement.setInt(1, studentNo);
                ResultSet lessonsResultSet = getLessonsStatement.executeQuery();

                ArrayList<String> lessonsName = new ArrayList<>();
                ArrayList<String> lessonsCode = new ArrayList<>();
                ArrayList<String> statuses = new ArrayList<>();

                while (lessonsResultSet.next()) {
                    String lessonName = lessonsResultSet.getString("lesson_name");
                    String lessonNo = lessonsResultSet.getString("lesson_code");
                    String status = lessonsResultSet.getString("status");

                    lessonsName.add(lessonName);
                    lessonsCode.add(lessonNo);
                    statuses.add(status);
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLessonForStudent(int studentNo, Lesson lesson) {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            // Extract lesson details
            String lessonName = lesson.getLessonName();

            // Update the student's lessons_name column
            String queryUpdateLessons
                    = "UPDATE student "
                    + "SET lessons_name = (SELECT ARRAY(SELECT DISTINCT unnest(lessons_name || ?))) "
                    + "WHERE student_no = ?";
            PreparedStatement updateLessonsStatement = connection.prepareStatement(queryUpdateLessons);
            updateLessonsStatement.setString(1, lessonName);
            updateLessonsStatement.setInt(2, studentNo);
            updateLessonsStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Lesson> getStudentsRequests() {
        List<Lesson> availableLessons = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM lessons";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Lesson lesson = new Lesson(
                        resultSet.getString("lesson_name"),
                        resultSet.getString("lesson_code"),
                        resultSet.getString("ilgi_alanlar"),
                        resultSet.getInt("student_no"),
                        resultSet.getInt("teacher_no"),
                        resultSet.getString("status")
                );
                availableLessons.add(lesson);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableLessons;
    }

    public List<Lesson> getStudentRequestsByStudentNo(int studentNo) {
        List<Lesson> studentRequests = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM lessons WHERE student_no=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentNo);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Lesson lesson = new Lesson(
                        resultSet.getString("lesson_name"),
                        resultSet.getString("lesson_code"),
                        resultSet.getString("ilgi_alanlar"),
                        resultSet.getInt("student_no"),
                        resultSet.getInt("teacher_no"),
                        resultSet.getString("status")
                );
                studentRequests.add(lesson);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentRequests;
    }

    public void requestToAddLesson(int studentNo, String lesson_code) {
    int teacherNo = 0; // Extra for Sending message
    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        // Check if a request already exists for the given lesson and student
        String queryCheckExistingRequest = "SELECT * FROM lessons WHERE lesson_code = ? AND student_no = ?";
        PreparedStatement checkExistingRequestStatement = connection.prepareStatement(queryCheckExistingRequest);
        checkExistingRequestStatement.setString(1, lesson_code);
        checkExistingRequestStatement.setInt(2, studentNo);
        ResultSet existingRequestResultSet = checkExistingRequestStatement.executeQuery();

        if (!existingRequestResultSet.next()) {
            // Check if the student has already made a request for the same lesson with a different teacher
            String queryCheckExistingRequestWithDifferentTeacher
                    = "SELECT * FROM lessons WHERE lesson_code = ? AND student_no = ? AND teacher_no <> (SELECT teacher_no FROM lessons WHERE lesson_code = ? AND student_no = ?)";
            PreparedStatement checkExistingRequestWithDifferentTeacherStatement = connection.prepareStatement(queryCheckExistingRequestWithDifferentTeacher);
            checkExistingRequestWithDifferentTeacherStatement.setString(1, lesson_code);
            checkExistingRequestWithDifferentTeacherStatement.setInt(2, studentNo);
            checkExistingRequestWithDifferentTeacherStatement.setString(3, lesson_code);
            checkExistingRequestWithDifferentTeacherStatement.setInt(4, studentNo);
            ResultSet existingRequestWithDifferentTeacherResultSet = checkExistingRequestWithDifferentTeacherStatement.executeQuery();

            if (!existingRequestWithDifferentTeacherResultSet.next()) {
                // Get lesson details
                String queryGetLesson = "SELECT * FROM new_lessons WHERE lesson_code = ?";
                PreparedStatement getLessonStatement = connection.prepareStatement(queryGetLesson);
                getLessonStatement.setString(1, lesson_code);
                ResultSet lessonResultSet = getLessonStatement.executeQuery();

                if (lessonResultSet.next()) {
                    int originalRemKont = lessonResultSet.getInt("rem_kont");
                    if (originalRemKont > 0) {

                        // Increment requests_count for the student
                        String queryUpdateRequestsCount = "UPDATE student SET requests_count = requests_count + 1 WHERE student_no = ?";
                        PreparedStatement updateRequestsCountStatement = connection.prepareStatement(queryUpdateRequestsCount);
                        updateRequestsCountStatement.setInt(1, studentNo);
                        updateRequestsCountStatement.executeUpdate();

                        // Decrement rem_kont in original row
                        String queryUpdateRemKont = "UPDATE new_lessons SET rem_kont = ? WHERE lesson_code = ?";
                        PreparedStatement updateRemKontStatement = connection.prepareStatement(queryUpdateRemKont);
                        updateRemKontStatement.setInt(1, originalRemKont - 1);
                        updateRemKontStatement.setString(2, lesson_code);
                        updateRemKontStatement.executeUpdate();

                        // Insert new row with student details
                        String queryInsert = "INSERT INTO lessons (lesson_name, lesson_code, ilgi_alanlar, student_no, teacher_no, status) "
                                + "VALUES (?, ?, ?, ?, ?, 'pending')";
                        PreparedStatement insertStatement = connection.prepareStatement(queryInsert);
                        insertStatement.setString(1, lessonResultSet.getString("lesson_name"));
                        insertStatement.setString(2, lesson_code);
                        insertStatement.setString(3, lessonResultSet.getString("ilgi_alanlar"));
                        insertStatement.setInt(4, studentNo);
                        insertStatement.setInt(5, lessonResultSet.getInt("teacher_no"));

                        teacherNo = lessonResultSet.getInt("teacher_no"); // Extra for Sending message

                        insertStatement.executeUpdate();
                    }
                }
            } else {
                System.out.println("You have already made a request for this lesson with a different teacher.");
                // Add code here to inform the student about the existing request.
            }
        } else {
            System.out.println("A request for this lesson already exists for the student.");
            // You can add code here to handle the case where a request already exists.
        }

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Sendin Notification To the Teacher
    NotificationsSend notificationSend = new NotificationsSend();
    notificationSend.sendMessage(studentNo, teacherNo, " " + lesson_code + " Dersi ekleme talepi gonderdi");
}


    public void updateTotalKontForAllLessons(int newTotalKont) {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String queryUpdateTotalKont = "UPDATE new_lessons SET total_kont = ?";
            PreparedStatement updateTotalKontStatement = connection.prepareStatement(queryUpdateTotalKont);
            updateTotalKontStatement.setInt(1, newTotalKont);
            updateTotalKontStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelLessonRequest(int studentNo, String lessonNo) {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            // Check if there is a pending request for the specified student and lesson
            String queryCheckRequest = "SELECT * FROM lessons WHERE student_no = ? AND lesson_code = ? AND status = 'pending'";
            PreparedStatement checkRequestStatement = connection.prepareStatement(queryCheckRequest);
            checkRequestStatement.setInt(1, studentNo);
            checkRequestStatement.setString(2, lessonNo);
            ResultSet requestResultSet = checkRequestStatement.executeQuery();

            if (requestResultSet.next()) {
                // There is a pending request, cancel it
                String queryCancelRequest = "DELETE FROM lessons WHERE student_no = ? AND lesson_code = ? AND status = 'pending'";
                PreparedStatement cancelRequestStatement = connection.prepareStatement(queryCancelRequest);
                cancelRequestStatement.setInt(1, studentNo);
                cancelRequestStatement.setString(2, lessonNo);
                cancelRequestStatement.executeUpdate();

                // Increase rem_kont in lessons table
                String queryUpdateRemKont = "UPDATE new_lessons SET rem_kont = rem_kont + 1 WHERE lesson_code = ?";
                PreparedStatement updateRemKontStatement = connection.prepareStatement(queryUpdateRemKont);
                updateRemKontStatement.setString(1, lessonNo);
                updateRemKontStatement.executeUpdate();
            } else {
                System.out.println("No pending request found for student " + studentNo + " and lesson " + lessonNo);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTeacherDepartment(int teacherNo, String ilgi_alanlar) {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            // Update the ilgi_alanlar of the teacher
            String queryUpdateDepartment = "UPDATE teacher SET ilgi_alanlar = ? WHERE teacher_no = ?";
            PreparedStatement updateDepartmentStatement = connection.prepareStatement(queryUpdateDepartment);
            updateDepartmentStatement.setString(1, ilgi_alanlar);
            updateDepartmentStatement.setInt(2, teacherNo);
            updateDepartmentStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Lesson> getIncomingRequestsForTeacher(int teacherNo) {
        List<Lesson> incomingRequests = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM lessons WHERE teacher_no=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, teacherNo);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Lesson lesson = new Lesson(
                        resultSet.getString("lesson_name"),
                        resultSet.getString("lesson_code"),
                        resultSet.getString("ilgi_alanlar"),
                        resultSet.getInt("student_no"),
                        resultSet.getInt("teacher_no"),
                        resultSet.getString("status")
                );
                incomingRequests.add(lesson);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incomingRequests;
    }

    public void approveOrRejectRequest(String lessonCode, int studentNo, int teacherNo, String status, boolean asama2) {
        if (asama2 == false) {
            try {
                Connection connection = DriverManager.getConnection(url, user, dpassword);

                // Get lesson details
                String queryGetLesson = "SELECT * FROM lessons WHERE lesson_code = ? AND student_no = ? AND teacher_no = ?";
                PreparedStatement getLessonStatement = connection.prepareStatement(queryGetLesson);
                getLessonStatement.setString(1, lessonCode);
                getLessonStatement.setInt(2, studentNo);
                getLessonStatement.setInt(3, teacherNo);
                ResultSet lessonResultSet = getLessonStatement.executeQuery();

                if (lessonResultSet.next()) {
                    String currentStatus = lessonResultSet.getString("status");

                    if (!currentStatus.equals("Onayli") && !currentStatus.equals("Reddedilmis")) {
                        if (status.equals("Reddedilmis")) {
                            // Increment rem_kont for the corresponding lesson
                            String queryUpdateRemKont = "UPDATE new_lessons SET rem_kont = rem_kont + 1 WHERE lesson_code = ?";
                            PreparedStatement updateRemKontStatement = connection.prepareStatement(queryUpdateRemKont);
                            updateRemKontStatement.setString(1, lessonCode);
                            updateRemKontStatement.executeUpdate();
                        }
                    }
                }

                // Update lesson status
                String queryUpdateStatus = "UPDATE lessons SET status = ? WHERE lesson_code = ? AND student_no = ? AND teacher_no = ?";
                PreparedStatement updateStatusStatement = connection.prepareStatement(queryUpdateStatus);
                updateStatusStatement.setString(1, status);
                updateStatusStatement.setString(2, lessonCode);
                updateStatusStatement.setInt(3, studentNo);
                updateStatusStatement.setInt(4, teacherNo);
                updateStatusStatement.executeUpdate();

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ikinci asamada Kabul ya da Red islemi Yapilmaz");
        }
    }

    public void addLessonForTeacher(String lessonName, String lessonNo, String ilgi_alanlar, int totalKont, int remKont, int teacherNo) {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "INSERT INTO new_lessons (lesson_name, lesson_code, ilgi_alanlar, total_kont, rem_kont, teacher_no) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, lessonName);
            statement.setString(2, lessonNo);
            statement.setString(3, ilgi_alanlar);
            statement.setInt(4, totalKont);
            statement.setInt(5, remKont);
            statement.setInt(6, teacherNo);

            statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Lesson> getAllOfferedLessons() {
        List<Lesson> offeredLessons = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM new_lessons";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Lesson lesson = new Lesson(
                        resultSet.getString("lesson_name"),
                        resultSet.getString("lesson_code"),
                        resultSet.getString("ilgi_alanlar"),
                        resultSet.getInt("total_kont"),
                        resultSet.getInt("rem_kont"),
                        resultSet.getInt("teacher_no"));
                offeredLessons.add(lesson);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offeredLessons;
    }

    public void createLesson(String lessonName, String lessonNo, String lessonDepartment, int totalKont, int teacherNo) {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            // Insert the new lesson
            String queryInsertLesson = "INSERT INTO new_lessons (lesson_name, lesson_code, ilgi_alanlar, total_kont, rem_kont, teacher_no) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertLessonStatement = connection.prepareStatement(queryInsertLesson);
            insertLessonStatement.setString(1, lessonName);
            insertLessonStatement.setString(2, lessonNo);
            insertLessonStatement.setString(3, lessonDepartment);
            insertLessonStatement.setInt(4, totalKont);
            insertLessonStatement.setInt(5, totalKont); // Set rem_kont initially equal to total_kont
            insertLessonStatement.setInt(6, teacherNo);
            insertLessonStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOcrLessons(List<Lesson> lessons, int studentNo) {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            for (Lesson lesson : lessons) {
                String query = "INSERT INTO old_lessons (lesson_code, lesson_name,student_no, grade) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, lesson.getLessonCode());
                statement.setString(2, lesson.getLessonName());
                statement.setInt(3, studentNo);
                statement.setString(4, lesson.getStatus());

                statement.executeUpdate();
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void priorityAssignStudentsToLessons(List<TeacherPriority> teacherPriorities) {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            // Sort teacher priorities in ascending order based on priority value
            Collections.sort(teacherPriorities, Comparator.comparingInt(TeacherPriority::getPriority));

            // Loop through the teacher priorities
            for (TeacherPriority priority : teacherPriorities) {
                int teacherNo = priority.getTeacherNo();

                // Get all lessons for the teacher from new_lessons table
                String queryGetLessons = "SELECT * FROM new_lessons WHERE teacher_no = ?";
                PreparedStatement getLessonsStatement = connection.prepareStatement(queryGetLessons);
                getLessonsStatement.setInt(1, teacherNo);
                ResultSet lessonsResultSet = getLessonsStatement.executeQuery();

                // Loop through the lessons in new_lessons table
                while (lessonsResultSet.next()) {
                    String lessonCode = lessonsResultSet.getString("lesson_code");
                    String lessonName = lessonsResultSet.getString("lesson_name");
                    int remKont = lessonsResultSet.getInt("rem_kont");
                    String ilgiAlan = lessonsResultSet.getString("ilgi_alanlar");

                    // If rem_kont is greater than 0, there are available spots for the lesson
                    if (remKont > 0) {
                        // Get the students with the highest academic average who don't have any "Onayli" lessons
                        String queryGetStudents
                                = "SELECT * FROM student "
                                + "WHERE NOT EXISTS ("
                                + "SELECT 1 FROM lessons "
                                + "WHERE student.student_no = lessons.student_no "
                                + "AND status = 'Onayli'"
                                + ") "
                                + "ORDER BY academic_average DESC "
                                + "LIMIT ?";
                        PreparedStatement getStudentsStatement = connection.prepareStatement(queryGetStudents);
                        getStudentsStatement.setInt(1, remKont);
                        ResultSet studentsResultSet = getStudentsStatement.executeQuery();

                        // Loop through the selected students
                        while (studentsResultSet.next()) {
                            int studentNo = studentsResultSet.getInt("student_no");

                            // Insert a new row in the lessons table with the student and lesson information
                            String queryInsertLesson
                                    = "INSERT INTO lessons (lesson_code, lesson_name, ilgi_alanlar, student_no, teacher_no, status) "
                                    + "VALUES (?, ?, ?, ?, ?, 'Onayli')";
                            PreparedStatement insertLessonStatement = connection.prepareStatement(queryInsertLesson);
                            insertLessonStatement.setString(1, lessonCode);
                            insertLessonStatement.setString(2, lessonName);
                            insertLessonStatement.setString(3, ilgiAlan);
                            insertLessonStatement.setInt(4, studentNo);
                            insertLessonStatement.setInt(5, teacherNo);
                            insertLessonStatement.executeUpdate();

                            // Decrement rem_kont in the new_lessons table
                            String queryUpdateRemKont
                                    = "UPDATE new_lessons SET rem_kont = rem_kont - 1 "
                                    + "WHERE lesson_code = ?";
                            PreparedStatement updateRemKontStatement = connection.prepareStatement(queryUpdateRemKont);
                            updateRemKontStatement.setString(1, lessonCode);
                            updateRemKontStatement.executeUpdate();
                        }
                    }
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void randomlyAssignStudentsToLessons() {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            // Get all students
            String queryGetStudents = "SELECT * FROM student";
            PreparedStatement getStudentsStatement = connection.prepareStatement(queryGetStudents);
            ResultSet studentsResultSet = getStudentsStatement.executeQuery();

            // Loop through the students
            while (studentsResultSet.next()) {
                int studentNo = studentsResultSet.getInt("student_no");

                // Check if the student already has lessons assigned
                String queryCheckExistingAssignments = "SELECT * FROM lessons WHERE student_no = ?";
                PreparedStatement checkExistingAssignmentsStatement = connection.prepareStatement(queryCheckExistingAssignments);
                checkExistingAssignmentsStatement.setInt(1, studentNo);
                ResultSet existingAssignmentsResultSet = checkExistingAssignmentsStatement.executeQuery();

                if (existingAssignmentsResultSet.next()) {
                    continue; // Skip this student if they already have lessons assigned
                }

                // Get a random number of lessons for the student (between 2 and 3)
                Random random = new Random();
                int numLessons = random.nextInt(2) + 2;

                for (int i = 0; i < numLessons; i++) {
                    // Retrieve a random available lesson
                    String queryGetRandomLesson = "SELECT * FROM new_lessons WHERE rem_kont > 0 ORDER BY RANDOM() LIMIT 1";
                    PreparedStatement getRandomLessonStatement = connection.prepareStatement(queryGetRandomLesson);
                    ResultSet randomLessonResultSet = getRandomLessonStatement.executeQuery();

                    if (randomLessonResultSet.next()) {
                        String lessonCode = randomLessonResultSet.getString("lesson_code");
                        int teacherNo = randomLessonResultSet.getInt("teacher_no");
                        String lessonName = randomLessonResultSet.getString("lesson_name");
                        String ilgiAlanlar = randomLessonResultSet.getString("ilgi_alanlar");

                        // Check if this assignment already exists
                        String queryCheckExistingAssignment = "SELECT * FROM lessons WHERE lesson_code = ? AND student_no = ? AND teacher_no = ?";
                        PreparedStatement checkExistingAssignmentStatement = connection.prepareStatement(queryCheckExistingAssignment);
                        checkExistingAssignmentStatement.setString(1, lessonCode);
                        checkExistingAssignmentStatement.setInt(2, studentNo);
                        checkExistingAssignmentStatement.setInt(3, teacherNo);
                        ResultSet existingAssignmentResultSet = checkExistingAssignmentStatement.executeQuery();

                        if (!existingAssignmentResultSet.next()) {
                            // Insert a new row in the lessons table with the student and lesson information
                            String queryInsertLesson
                                    = "INSERT INTO lessons (lesson_code, student_no, teacher_no, status, lesson_name, ilgi_alanlar) "
                                    + "VALUES (?, ?, ?, 'Onayli', ?, ?)";
                            PreparedStatement insertLessonStatement = connection.prepareStatement(queryInsertLesson);
                            insertLessonStatement.setString(1, lessonCode);
                            insertLessonStatement.setInt(2, studentNo);
                            insertLessonStatement.setInt(3, teacherNo);
                            insertLessonStatement.setString(4, lessonName);
                            insertLessonStatement.setString(5, ilgiAlanlar);
                            insertLessonStatement.executeUpdate();

                            // Decrement rem_kont in the new_lessons table
                            String queryUpdateRemKont = "UPDATE new_lessons SET rem_kont = rem_kont - 1 WHERE lesson_code = ?";
                            PreparedStatement updateRemKontStatement = connection.prepareStatement(queryUpdateRemKont);
                            updateRemKontStatement.setString(1, lessonCode);
                            updateRemKontStatement.executeUpdate();
                        }
                    }
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignStudentsToSelectedLessons(List<String> selectedLessonCodes, List<Integer> selectedTeacherNumbers, List<TeacherPriority> teacherPriority) {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            // Sort teacher priorities in ascending order based on priority value
            Collections.sort(teacherPriority, Comparator.comparingInt(TeacherPriority::getPriority));

            // Loop through the selected lessons
            for (int i = 0; i < selectedLessonCodes.size(); i++) {
                String lessonCode = selectedLessonCodes.get(i);
                int teacherNo = selectedTeacherNumbers.get(i);

                // Get lesson details
                String queryGetLessonDetails = "SELECT lesson_name, ilgi_alanlar, rem_kont FROM new_lessons WHERE lesson_code = ?";
                PreparedStatement getLessonDetailsStatement = connection.prepareStatement(queryGetLessonDetails);
                getLessonDetailsStatement.setString(1, lessonCode);
                ResultSet lessonDetailsResultSet = getLessonDetailsStatement.executeQuery();

                if (lessonDetailsResultSet.next()) {
                    String lessonName = lessonDetailsResultSet.getString("lesson_name");
                    String ilgiAlanlar = lessonDetailsResultSet.getString("ilgi_alanlar");
                    int remKont = lessonDetailsResultSet.getInt("rem_kont");

                    // Check if there are available spots for the lesson
                    if (remKont > 0) {
                        // Get the students with the highest academic average who don't have any "Onayli" lessons
                        String queryGetStudents
                                = "SELECT * FROM student "
                                + "WHERE NOT EXISTS ("
                                + "SELECT 1 FROM lessons "
                                + "WHERE student.student_no = lessons.student_no "
                                + "AND status = 'Onayli'"
                                + ") "
                                + "ORDER BY academic_average DESC "
                                + "LIMIT ?";
                        PreparedStatement getStudentsStatement = connection.prepareStatement(queryGetStudents);
                        getStudentsStatement.setInt(1, remKont);
                        ResultSet studentsResultSet = getStudentsStatement.executeQuery();

                        // Loop through the selected students
                        while (studentsResultSet.next()) {
                            int studentNo = studentsResultSet.getInt("student_no");

                            // Insert a new row in the lessons table with the student and lesson information
                            String queryInsertLesson
                                    = "INSERT INTO lessons (lesson_code, lesson_name, ilgi_alanlar, teacher_no, student_no, status) "
                                    + "VALUES (?, ?, ?, ?, ?, 'Onayli')";
                            PreparedStatement insertLessonStatement = connection.prepareStatement(queryInsertLesson);
                            insertLessonStatement.setString(1, lessonCode);
                            insertLessonStatement.setString(2, lessonName);
                            insertLessonStatement.setString(3, ilgiAlanlar);
                            insertLessonStatement.setInt(4, teacherNo);
                            insertLessonStatement.setInt(5, studentNo);
                            insertLessonStatement.executeUpdate();

                            // Decrement rem_kont in the new_lessons table
                            String queryUpdateRemKont
                                    = "UPDATE new_lessons SET rem_kont = rem_kont - 1 "
                                    + "WHERE lesson_code = ?";
                            PreparedStatement updateRemKontStatement = connection.prepareStatement(queryUpdateRemKont);
                            updateRemKontStatement.setString(1, lessonCode);
                            updateRemKontStatement.executeUpdate();
                        }
                    }
                }
            }

            // Reset priorities starting from 1
            for (int i = 0; i < teacherPriority.size(); i++) {
                teacherPriority.get(i).setPriority(i + 1);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Lesson> getStudentOldLessons(int studentNo) {
        List<Lesson> lessonsList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM old_lessons WHERE student_no=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, studentNo);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String lessonName = resultSet.getString("lesson_name");
                String lessonCode = resultSet.getString("lesson_code");
                int teacherNo = resultSet.getInt("teacher_no");
                String status = resultSet.getString("grade");

                Lesson lesson = new Lesson(lessonName, lessonCode, null, studentNo, teacherNo, status);
                lessonsList.add(lesson);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonsList;
    }

    public List<Lesson> getTeacherLessons(int teacherNo) {
        List<Lesson> lessonsList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM new_lessons WHERE teacher_no=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, teacherNo);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String lessonName = resultSet.getString("lesson_name");
                String lessonCode = resultSet.getString("lesson_code");
                String ilgiAlanlar = resultSet.getString("ilgi_alanlar");
                int totalKont = resultSet.getInt("total_kont");
                int remKont = resultSet.getInt("rem_kont");

                Lesson lesson = new Lesson(lessonName, lessonCode, ilgiAlanlar, totalKont, remKont, teacherNo);
                lessonsList.add(lesson);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonsList;
    }

    public List<Lesson> getTeacherOldLessons(int teacherNo) {
        List<Lesson> lessonsList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM old_lessons WHERE teacher_no = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, teacherNo);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String lessonName = resultSet.getString("lesson_name");
                String lessonCode = resultSet.getString("lesson_code");
                int studentNo = resultSet.getInt("student_no");
                int teacherNumber = resultSet.getInt("teacher_no");
                String grade = resultSet.getString("grade");

                Lesson lesson = new Lesson(lessonName, lessonCode, studentNo, teacherNumber, grade);
                lessonsList.add(lesson);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonsList;
    }

    public List<Lesson> getOldLessons() {
        List<Lesson> lessonsList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "SELECT * FROM old_lessons";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String lessonName = resultSet.getString("lesson_name");
                String lessonCode = resultSet.getString("lesson_code");
                int studentNo = resultSet.getInt("student_no");
                int teacherNo = resultSet.getInt("teacher_no");
                String grade = resultSet.getString("grade");

                Lesson lesson = new Lesson(lessonName, lessonCode, studentNo, teacherNo, grade);
                lessonsList.add(lesson);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lessonsList;
    }

    public RandomLessonsLists getRandomLessonsList() {

        List<String> dersIsimleri = new ArrayList<>();
        List<String> dersKodlari = new ArrayList<>();
        List<String> dersNotlari = new ArrayList<>();

        String[] dersIsimleriArray = {
            "Bilgisayar Organizasyonu ve Mimarisi", "Veritabani Yonetimi", "Programlama Laboratuvari - II",
            "Sistem Programlama", "Olasilik ve Raslanti Degiskenleri", "Elektronik ve Uygulamalari"
        };

        String[] dersKodlariArray = {
            "BLM206", "BLM208", "BLM210",
            "BLM212", "MUH202", "MUH204"
        };

        Random random = new Random();
        //String[] notlar = {"AA", "BA", "BB", "CB", "CC", "DC", "DD", "FF","AA", "BA", "BB", "CB", "CC", "DC", "DD","N"};
        String[] notlar = {"AA", "BA", "BB", "CB", "AA", "BA", "BB", "CB", "CC", "DC", "DD", "FF"};

        for (int i = 0; i < dersIsimleriArray.length; i++) {
            dersIsimleri.add(dersIsimleriArray[i]);
            dersKodlari.add(dersKodlariArray[i]);
            int randomIndex = random.nextInt(notlar.length);
            dersNotlari.add(notlar[randomIndex]);
        }

        // Oluşturulan listeleri yazdırma
        System.out.println("Ders Isimleri:");
        for (String dersIsimi : dersIsimleri) {
            System.out.println(dersIsimi);
        }

        System.out.println("\nDers Kodlari:");
        for (String dersKodu : dersKodlari) {
            System.out.println(dersKodu);
        }

        System.out.println("\nRastgele Degerler:");
        for (String rastgeleDeger : dersNotlari) {
            System.out.println(rastgeleDeger);
        }

        return new RandomLessonsLists(dersIsimleri, dersKodlari, dersNotlari);
    }

    public void addRandomLessonsToOldLessons(ArrayList<Student> randomStudents) {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);
            Random random = new Random();

            for (Student student : randomStudents) {
                RandomLessonsLists randomLessonsLists = getRandomLessonsList();
                HashSet<String> assignedLessonCodes = new HashSet<>();

                // Retrieve the specific teacher (teacher_no = 210201141)
                int specificTeacherNo = 210201141;

                // Loop through all the lessons in randomLessonsLists
                for (int i = 0; i < randomLessonsLists.getDersIsimleri().size(); i++) {
                    String randomLessonName = randomLessonsLists.getDersIsimleri().get(i);
                    String randomLessonCode = randomLessonsLists.getDersKodlari().get(i);
                    String randomLessonGrade = randomLessonsLists.getDersNotlari().get(i);

                    // Check if the lesson with the same code has already been assigned
                    if (assignedLessonCodes.contains(randomLessonCode)) {
                        continue; // Skip this iteration if lesson code is already assigned
                    }

                    assignedLessonCodes.add(randomLessonCode);

                    // Insert the random lesson into the old_lessons table
                    String queryInsertLesson = "INSERT INTO old_lessons (lesson_name, lesson_code, grade, student_no, teacher_no) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertLessonStatement = connection.prepareStatement(queryInsertLesson);
                    insertLessonStatement.setString(1, randomLessonName);
                    insertLessonStatement.setString(2, randomLessonCode);
                    insertLessonStatement.setString(3, randomLessonGrade);
                    insertLessonStatement.setInt(4, student.getStudentNo());
                    insertLessonStatement.setInt(5, specificTeacherNo); // Use specific teacher_no
                    insertLessonStatement.executeUpdate();
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRandomLessonsToNewLessons(int numRandomLessons) {
        List<String> lessonCodes = Arrays.asList("CSE 315", "CSE 326", "CSE 426", "CSE 447", "CSE 457", "CSE 464", "CSE 472", "CSE 474", "CSE 483", "CSE 489");
        List<String> lessonNames = Arrays.asList("Internet Teknolojileri", "Gömülü Sistem Programlama", "Ses İşleme ve Tanıma", "Yazılım Mimarisi", "Derleyici Tasarım", "Veri Bilimi ve Büyük Veri Analizine Giriş", "Eş Zamanlı Programlama", "Otomotiv Ağlarına Giriş", "Bilgisayar Grafikleri", "İnsan Bilgisayar Etkileşimi");
        List<String> ilgiAlani = Arrays.asList("Bilgisayar Ağları", "Veritabanı Yönetimi", "Yazılım Mühendisliği", "Veri Bilimi", "Bilgisayar Grafikleri", "Mobil Uygulama Geliştirme", "Oyun Programlama", "Büyük Veri Analizi", "Yapay Zeka", "Doğal Dil İşleme", "Bilgisayar Güvenliği", "Makine Öğrenimi");

        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);
            Random random = new Random();

            for (int i = 0; i < numRandomLessons; i++) {
                String lessonCode;
                String lessonName;
                String ilgiAlan;
                int totalKont = 10;
                int remKont = 10;

                // Retrieve a random teacher number
                String queryGetRandomTeacherNo = "SELECT teacher_no FROM teacher ORDER BY RANDOM() LIMIT 1";
                PreparedStatement getRandomTeacherNoStatement = connection.prepareStatement(queryGetRandomTeacherNo);
                ResultSet randomTeacherNoResultSet = getRandomTeacherNoStatement.executeQuery();

                int teacherNo = 0;
                if (randomTeacherNoResultSet.next()) {
                    teacherNo = randomTeacherNoResultSet.getInt("teacher_no");
                }

                do {
                    lessonCode = lessonCodes.get(random.nextInt(lessonCodes.size()));
                    lessonName = lessonNames.get(random.nextInt(lessonNames.size()));
                    ilgiAlan = ilgiAlani.get(random.nextInt(ilgiAlani.size()));
                } while (lessonAlreadyExists(connection, lessonCode, teacherNo));

                // Insert the random lesson into the new_lessons table
                String queryInsertLesson = "INSERT INTO new_lessons (lesson_code, lesson_name, ilgi_alanlar, total_kont, rem_kont, teacher_no) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertLessonStatement = connection.prepareStatement(queryInsertLesson);
                insertLessonStatement.setString(1, lessonCode);
                insertLessonStatement.setString(2, lessonName);
                insertLessonStatement.setString(3, ilgiAlan);
                insertLessonStatement.setInt(4, totalKont);
                insertLessonStatement.setInt(5, remKont);
                insertLessonStatement.setInt(6, teacherNo);
                insertLessonStatement.executeUpdate();
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean lessonAlreadyExists(Connection connection, String lessonCode, int teacherNo) throws SQLException {
        String queryCheckLessonExists = "SELECT COUNT(*) FROM new_lessons WHERE lesson_code = ? AND teacher_no = ?";
        PreparedStatement checkLessonExistsStatement = connection.prepareStatement(queryCheckLessonExists);
        checkLessonExistsStatement.setString(1, lessonCode);
        checkLessonExistsStatement.setInt(2, teacherNo);
        ResultSet lessonExistsResultSet = checkLessonExistsStatement.executeQuery();
        lessonExistsResultSet.next();
        return lessonExistsResultSet.getInt(1) > 0;
    }

}
