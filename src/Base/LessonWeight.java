/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Base;

/**
 *
 * @author xandros
 */
public class LessonWeight {
    
    private String lessonName;
    private String lessonCode;
    private int teacherNo;
    private int weight;

    public LessonWeight(String lessonName, String lessonCode, int teacherNo, int weight) {
        this.lessonName = lessonName;
        this.lessonCode = lessonCode;
        this.teacherNo = teacherNo;
        this.weight = weight;
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

    public void setLessonCode(String lessonCode) {
        this.lessonCode = lessonCode;
    }

    public int getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(int teacherNo) {
        this.teacherNo = teacherNo;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public LessonWeight() {
    }
    
    
    
    
}
