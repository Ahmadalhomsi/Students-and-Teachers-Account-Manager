/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQL;

/**
 *
 * @author xandros
 */
public class Lesson {

    private String lessonName;
    private String lessonCode;
    private int totalKont;
    private int remKont;
    private int studentNo;
    private int teacherNo;
    private String status;
    private String ilgiAlanlar;

    // Constructors, getters, setters, and any additional methods go here...

    public Lesson(String lessonName, String lesson_code, String ilgiAlanlar, int totalKont, int remKont, int studentNo, int teacherNo, String status) {
        this.lessonName = lessonName;
        this.lessonCode = lesson_code;
        this.ilgiAlanlar = ilgiAlanlar;
        this.totalKont = totalKont;
        this.remKont = remKont;
        this.studentNo = studentNo;
        this.teacherNo = teacherNo;
        this.status = status;
    }

    public Lesson(String lessonName, String lessonCode, String ilgiAlanlar, int totalKont, int remKont, int teacherNo) {
        this.lessonName = lessonName;
        this.lessonCode = lessonCode;
        this.ilgiAlanlar = ilgiAlanlar;
        this.totalKont = totalKont;
        this.remKont = remKont;
        this.teacherNo = teacherNo;
        this.ilgiAlanlar = ilgiAlanlar;
    }

    public Lesson(String lessonName, String lessonCode, String ilgiAlanlar, int studentNo, int teacherNo, String status) {
        this.lessonName = lessonName;
        this.lessonCode = lessonCode;
        this.studentNo = studentNo;
        this.teacherNo = teacherNo;
        this.status = status;
        this.ilgiAlanlar = ilgiAlanlar;
    }

    public Lesson(String lessonName, String lessonCode) {
        this.lessonName = lessonName;
        this.lessonCode = lessonCode;
    }

    public Lesson(String lessonName, String lessonCode, int studentNo, int teacherNo, String status) {
        this.lessonName = lessonName;
        this.lessonCode = lessonCode;
        this.studentNo = studentNo;
        this.teacherNo = teacherNo;
        this.status = status;
    }
    
    
    
    
    
    public Lesson() {
    }

    public String getIlgiAlanlar() {
        return ilgiAlanlar;
    }

    public void setIlgiAlanlar(String ilgiAlanlar) {
        this.ilgiAlanlar = ilgiAlanlar;
    }


    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonCode() {
        return lessonCode;
    }

    public void setLessonCode(String lesson_code) {
        this.lessonCode = lesson_code;
    }
    
    
    public int getTotalKont() {
        return totalKont;
    }

    public void setTotalKont(int totalKont) {
        this.totalKont = totalKont;
    }

    public int getRemKont() {
        return remKont;
    }

    public void setRemKont(int remKont) {
        this.remKont = remKont;
    }

    public int getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(int studentNo) {
        this.studentNo = studentNo;
    }

    public int getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(int teacherNo) {
        this.teacherNo = teacherNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
