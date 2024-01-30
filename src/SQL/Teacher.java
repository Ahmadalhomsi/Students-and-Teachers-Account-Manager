/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;

/**
 *
 * @author xandros
 */
public class Teacher {

    private String teacherName;
    private int teacherNo;
    private String password;
    private String ilgiAlanlar;
    private String[] lessons_name;
    private String[] lessons_code;
    private Integer[] total_kont;
    private Integer[] rem_kont;


    public Teacher(String teacherName, int teacherNo, String password, String ilgiAlanlar) {
        this.teacherName = teacherName;
        this.teacherNo = teacherNo;
        this.password = password;
        this.ilgiAlanlar = ilgiAlanlar;
    }

    public Teacher(String teacherName, int teacherNo, String password, String ilgi_alanlar, String[] lessons_name, String[] lessons_code, Integer[] total_kont, Integer[] rem_kont) {
        this.teacherName = teacherName;
        this.teacherNo = teacherNo;
        this.password = password;
        this.ilgiAlanlar = ilgi_alanlar;
        this.lessons_name = lessons_name;
        this.lessons_code = lessons_code;
        this.total_kont = total_kont;
        this.rem_kont = rem_kont;
    }
    
    private static int numara = 41001;

    public Teacher(String teacherName, String teacherSurname, String ilgiAlanlari) {
        this.teacherNo = numara++;
        this.teacherName = teacherName + " " + teacherSurname;
        this.ilgiAlanlar = ilgiAlanlari;
        this.password = teacherName.toLowerCase();
    }

    public String toString() {
        return "Akademisyen No: " + teacherNo + "\nAd-Soyad : " + teacherName + "\nİlgi Alanları: " + ilgiAlanlar + "\nŞifre: " + password;
    }

    public String[] getLessons_name() {
        return lessons_name;
    }

    public void setLessons_name(String[] lessons_name) {
        this.lessons_name = lessons_name;
    }

    public String[] getLessons_code() {
        return lessons_code;
    }

    public void setLessons_code(String[] lessons_code) {
        this.lessons_code = lessons_code;
    }

    

    

    public Integer[] getTotal_kont() {
        return total_kont;
    }

    public void setTotal_kont(Integer[] total_kont) {
        this.total_kont = total_kont;
    }

    public Integer[] getRem_kont() {
        return rem_kont;
    }

    public void setRem_kont(Integer[] rem_kont) {
        this.rem_kont = rem_kont;
    }

    public String getIlgiAlanlar() {
        return ilgiAlanlar;
    }

    public void setIlgiAlanlar(String ilgiAlanlar) {
        this.ilgiAlanlar = ilgiAlanlar;
    }

    


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(int teacherNo) {
        this.teacherNo = teacherNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }














}
