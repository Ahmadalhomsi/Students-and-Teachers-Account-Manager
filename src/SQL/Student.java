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
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author xandros
 */
public class Student {
    
    
    private String studentName; // Student name
    private int studentNo;
    private String password;
    private String ilgiAlanlar;
    private double AcademicAverage;
    private String[] lessons_name;
    private String[] lessons_code;
    private String[] statuses;
    private int requests_count;


    // Constructor, getters, setters



    public Student(String studentName, int studentNo, String password, String ilgiAlanlar, double AcademicAverage, int requests_count) {
        this.studentName = studentName;
        this.studentNo = studentNo;
        this.password = password;
        this.ilgiAlanlar = ilgiAlanlar;
        this.AcademicAverage = AcademicAverage;
        this.requests_count = requests_count;
    }

    
        
    
    /// Sinan
    
    private static int nextStudentNumber = 210201001;
    public Student(String name, String surname, ArrayList<String> ilgiAlani2) {
        this.studentName = name+" " + surname;
        this.studentNo = nextStudentNumber;
        nextStudentNumber++;
        this.AcademicAverage = generateRandomGPA();
        this.password = generatePassword();
        this.ilgiAlanlar= Arrays.toString(getRandomIlgiAlani(ilgiAlani2, 5).toArray()); 
    }
    
    private ArrayList<String> getRandomIlgiAlani(ArrayList<String> ilgiAlanlari2, int count) {
        Random random = new Random();
        ArrayList<String> randomIlgiAlani = new ArrayList<>(ilgiAlanlari2);
        ArrayList<String> selectedIlgiAlani = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(randomIlgiAlani.size());
            selectedIlgiAlani.add(randomIlgiAlani.get(index));
            randomIlgiAlani.remove(index);
        }
        return selectedIlgiAlani;
    }
    
    
         private double generateRandomGPA() {
        Random random = new Random();
        double randomGPA;
         randomGPA=1.80 + (random.nextDouble() * (3.90 - 1.80));
        randomGPA = Math.round(randomGPA * 100.0) / 100.0;
        return randomGPA ;
    }

    private String generatePassword() {
        return studentName.toLowerCase();
    }
    
    @Override
    public String toString() {
        return "Student Number: " + studentNo + ", Name: " + studentName
                + ", GPA: " + AcademicAverage + ", Password: " + password;
    }
    

    public String[] getLessons_code() {
        return lessons_code;
    }

    public void setLessons_code(String[] lessons_code) {
        this.lessons_code = lessons_code;
    }

    

    

    

    public int getRequests_count() {
        return requests_count;
    }

    public void setRequests_count(int requests_count) {
        this.requests_count = requests_count;
    }

    public String getIlgiAlanlar() {
        return ilgiAlanlar;
    }

    public void setIlgiAlanlar(String ilgiAlanlar) {
        this.ilgiAlanlar = ilgiAlanlar;
    }

    
    

    public double getAcademicAverage() {
        return AcademicAverage;
    }

    public void setAcademicAverage(double academic_average) {
        this.AcademicAverage = academic_average;
    }

    public String[] getLessons_name() {
        return lessons_name;
    }

    public void setLessons_name(String[] lessons_name) {
        this.lessons_name = lessons_name;
    }


    public String[] getStatuses() {
        return statuses;
    }

    public void setStatuses(String[] statuses) {
        this.statuses = statuses;
    }


    
    

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(int studentNo) {
        this.studentNo = studentNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
