/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import SQL.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 *
 * @author xandros
 */
public class InfoPopup extends javax.swing.JFrame {

    /**
     * Creates new form Notifications
     */
    public InfoPopup(Student student, boolean admin) {

        initComponents();

        InfoArea.setText("");

        LessonOperation lessonOperation = new LessonOperation();

        List<Lesson> studentOldLessons = lessonOperation.getStudentOldLessons(student.getStudentNo());

        List<Lesson> studentRequests = lessonOperation.getStudentRequestsByStudentNo(student.getStudentNo());

        List<String> studentOldLessonsName = new ArrayList<String>();
        List<String> studentOldLessonsCode = new ArrayList<String>();
        List<String> studentOldLessonsStatus = new ArrayList<String>();

        List<String> studentLessonsRequestName = new ArrayList<String>();
        List<String> studentLessonsRequestCode = new ArrayList<String>();
        List<String> studentLessonsRequestStatus = new ArrayList<String>();
        //List<Integer> studentLessonsRequestTeacherNo = new ArrayList<Integer>();

        for (int i = 0; i < studentOldLessons.size(); i++) {
            if (admin == false) {
                System.out.println("IF");
                studentOldLessonsName.add(studentOldLessons.get(i).getLessonName());
                studentOldLessonsCode.add(studentOldLessons.get(i).getLessonCode());
                studentOldLessonsStatus.add(studentOldLessons.get(i).getStatus());

            } else if (admin == true) {
                System.out.println("ELSE");
                studentOldLessonsName.add(studentOldLessons.get(i).getLessonName());
                studentOldLessonsCode.add(studentOldLessons.get(i).getLessonCode());
                studentOldLessonsStatus.add(studentOldLessons.get(i).getStatus());
            }
        }

        if (admin == true) {
            for (int i = 0; i < studentRequests.size(); i++) {
                studentLessonsRequestName.add(studentRequests.get(i).getLessonName());
                studentLessonsRequestCode.add(studentRequests.get(i).getLessonCode());
                studentLessonsRequestStatus.add(studentRequests.get(i).getStatus());
                //studentLessonsRequestTeacherNo.add(studentRequests.get(i).getTeacherNo());

            }
        }

        if ( (!studentOldLessonsName.isEmpty()) && (studentLessonsRequestName.isEmpty())) {
            InfoArea.setText("Ogrenci Numarasi: " + student.getStudentNo()
                    + "\nOgrenci Ad-Soyad: " + student.getStudentName()
                    + "\nOgrenci Bolumu: " + student.getIlgiAlanlar()
                    + /// Somethings need to be added
                    "\nGenel Not Ortalamasi: " + student.getAcademicAverage()
                    + "\nDersler: " + Arrays.toString(studentOldLessonsName.toArray())
                    + "\nDersler Kodu: " + Arrays.toString(studentOldLessonsCode.toArray())
                    + "\nDers Notu: " + Arrays.toString(studentOldLessonsStatus.toArray())
                    + "\nTalepler Sayisi: " + student.getRequests_count());

        } else if ((!studentOldLessonsName.isEmpty()) && (!studentLessonsRequestName.isEmpty())) {
            InfoArea.setText("Ogrenci Numarasi: " + student.getStudentNo()
                    + "\nOgrenci Ad-Soyad: " + student.getStudentName()
                    + "\nOgrenci Bolumu: " + student.getIlgiAlanlar()
                    + /// Somethings need to be added
                    "\nGenel Not Ortalamasi: " + student.getAcademicAverage()
                    + "\nDersler: " + Arrays.toString(studentOldLessonsName.toArray())
                    + "\nDersler Kodu: " + Arrays.toString(studentOldLessonsCode.toArray())
                    + "\nDers Notu: " + Arrays.toString(studentOldLessonsStatus.toArray())
                    + "\nTalepler Sayisi: " + student.getRequests_count()
                    + "\nTalepler: " + Arrays.toString(studentLessonsRequestName.toArray())
                    + "\nTalepler Ders Kodu: " + Arrays.toString(studentLessonsRequestCode.toArray())
                    + "\nTalep Durumu: " + Arrays.toString(studentLessonsRequestStatus.toArray()));
        } else if (!studentLessonsRequestName.isEmpty()) {
            InfoArea.setText("Ogrenci Numarasi: " + student.getStudentNo()
                    + "\nOgrenci Ad-Soyad: " + student.getStudentName()
                    + "\nOgrenci Bolumu: " + student.getIlgiAlanlar()
                    + /// Somethings need to be added
                    "\nGenel Not Ortalamasi: " + student.getAcademicAverage()
                    + "\nTalepler Sayisi: " + student.getRequests_count()
                    + "\nTalepler: " + Arrays.toString(studentLessonsRequestName.toArray())
                    + "\nTalepler Ders Kodu: " + Arrays.toString(studentLessonsRequestCode.toArray())
                    + "\nTalep Durumu: " + Arrays.toString(studentLessonsRequestStatus.toArray()));
        } else {
            InfoArea.setText("Ogrenci Numarasi: " + student.getStudentNo()
                    + "\nOgrenci Ad-Soyad: " + student.getStudentName()
                    + "\nOgrenci Bolumu: " + student.getIlgiAlanlar()
                    + /// Somethings need to be added
                    "\nGenel Not Ortalamasi: " + student.getAcademicAverage()
                    + "\nDersler: " + "Yok"
                    + "\nTalepler Sayisi: " + student.getRequests_count());
        }

    }

    public InfoPopup(Teacher teacher) {

        initComponents();

        InfoArea.setText("");

        LessonOperation lessonOperation = new LessonOperation();
        List<Lesson> teacherLessons = lessonOperation.getTeacherLessons(teacher.getTeacherNo());
        
        
        List<Lesson> teacherOldLessons = lessonOperation.getTeacherOldLessons(teacher.getTeacherNo());

        List<String> teacherLessonsName = new ArrayList<String>();
        List<String> teacherLessonsCode = new ArrayList<String>();
        List<String> teacherLessonsIlgiAlanlar = new ArrayList<String>();
        List<Integer> teacherLessonsTotalKont = new ArrayList<Integer>();
        List<Integer> teacherLessonsRemKont = new ArrayList<Integer>();
        
        
        List<String> teacherOldLessonsName = new ArrayList<String>();
        List<String> teacherOldLessonsCode = new ArrayList<String>();

        for (int i = 0; i < teacherLessons.size(); i++) {
            teacherLessonsName.add(teacherLessons.get(i).getLessonName());
            teacherLessonsCode.add(teacherLessons.get(i).getLessonCode());
            teacherLessonsIlgiAlanlar.add(teacherLessons.get(i).getIlgiAlanlar());
            teacherLessonsTotalKont.add(teacherLessons.get(i).getTotalKont());
            teacherLessonsRemKont.add(teacherLessons.get(i).getRemKont());
        }
        
        for (int i = 0; i < teacherOldLessons.size(); i++) {
            teacherOldLessonsName.add(teacherOldLessons.get(i).getLessonName());
            teacherOldLessonsCode.add(teacherOldLessons.get(i).getLessonCode());
        }

        if ( (!teacherLessonsName.isEmpty()) && (teacherOldLessonsName.isEmpty()) ) {
            InfoArea.setText("Sicil Numarasi: " + teacher.getTeacherNo()
                    + "\nHoca Ad-Soyad: " + teacher.getTeacherName()
                    + "\nHoca ilgi alanlar: " + teacher.getIlgiAlanlar()
                    /// Somethings need to be added
                    + "\nDersler: " + Arrays.toString(teacherLessonsName.toArray())
                    + "\nDersler Kodu: " + Arrays.toString(teacherLessonsCode.toArray())
                    + "\nilgili Alanlar: " + Arrays.toString(teacherLessonsIlgiAlanlar.toArray())
                    + "\nKontenjanlar: " + Arrays.toString(teacherLessonsTotalKont.toArray()) + "/" + Arrays.toString(teacherLessonsRemKont.toArray()));
        } 
        else if( (!teacherLessonsName.isEmpty()) && (!teacherOldLessonsName.isEmpty()) )
        {
            InfoArea.setText("Sicil Numarasi: " + teacher.getTeacherNo()
                    + "\nHoca Ad-Soyad: " + teacher.getTeacherName()
                    + "\nHoca ilgi alanlar: " + teacher.getIlgiAlanlar()
                    /// Somethings need to be added
                    + "\nDersler: " + Arrays.toString(teacherLessonsName.toArray())
                    + "\nDersler Kodu: " + Arrays.toString(teacherLessonsCode.toArray())
                    + "\nilgili Alanlar: " + Arrays.toString(teacherLessonsIlgiAlanlar.toArray())
                    + "\nKontenjanlar: " + Arrays.toString(teacherLessonsTotalKont.toArray()) + "/" + Arrays.toString(teacherLessonsRemKont.toArray())
                    + "\nEski Dersler: " + Arrays.toString(teacherOldLessonsName.toArray())
                    + "\nEski Dersler Kodu: " + Arrays.toString(teacherOldLessonsCode.toArray()));
        }
        
        else if( (teacherLessonsName.isEmpty()) && (!teacherOldLessonsName.isEmpty()) )
        {
            InfoArea.setText("Sicil Numarasi: " + teacher.getTeacherNo()
                    + "\nHoca Ad-Soyad: " + teacher.getTeacherName()
                    + "\nHoca ilgi alanlar: " + teacher.getIlgiAlanlar()
                    /// Somethings need to be added
                    + "\nEski Dersler: " + Arrays.toString(teacherOldLessonsName.toArray())
                    + "\nEski Dersler Kodu: " + Arrays.toString(teacherOldLessonsCode.toArray()));
        }
        
        else {
            InfoArea.setText("Sicil Numarasi: " + teacher.getTeacherNo()
                    + "\nHoca Ad-Soyad: " + teacher.getTeacherName()
                    + "\nHoca ilgi alanlar: " + teacher.getIlgiAlanlar()
                    /// Somethings need to be added
                    + "\nDersler: " + "Yok");
        }
    }

    public InfoPopup() {
        initComponents();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        InfoArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setType(java.awt.Window.Type.POPUP);

        Panel.setBackground(new java.awt.Color(34, 40, 44));
        Panel.setToolTipText("");
        Panel.setPreferredSize(new java.awt.Dimension(1000, 100));

        InfoArea.setEditable(false);
        InfoArea.setBackground(new java.awt.Color(34, 40, 44));
        InfoArea.setColumns(20);
        InfoArea.setRows(5);
        InfoArea.setText("OCR TEXT");
        InfoArea.setFocusable(false);
        jScrollPane7.setViewportView(InfoArea);

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(297, 297, 297))
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(766, 613));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InfoPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InfoPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InfoPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InfoPopup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InfoPopup().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea InfoArea;
    private javax.swing.JPanel Panel;
    private javax.swing.JScrollPane jScrollPane7;
    // End of variables declaration//GEN-END:variables
}
