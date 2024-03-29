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

/**
 *
 * @author xandros
 */
public class NotificationsRead extends javax.swing.JFrame {

    /**
     * Creates new form Notifications
     */
    
    Student loggedStudent;
    Teacher loggedTeacher;
    Message message;
    
    public NotificationsRead(Student loggedStudent) {
        this.loggedStudent = loggedStudent;
        initComponents();
        List<Message> messagesForRead =getMessages(loggedStudent.getStudentNo());
        
         MessageArea.setText("");
        for (int i = 0; i < messagesForRead.size(); i++) {
            MessageArea.setText(MessageArea.getText() + 
                  "(" + messagesForRead.get(i).getSender_id() + ")  " +
                    messagesForRead.get(i).getMessage_content() + "\n" +
                    "[ " + messagesForRead.get(i).getTimestamp() + " ]\n\n");
        }
        
    }

    public NotificationsRead() {
        initComponents();
    }

    public NotificationsRead(Teacher loggedTeacher) {
        this.loggedTeacher = loggedTeacher;
        initComponents();
        
        List<Message> messagesForRead =getMessages(loggedTeacher.getTeacherNo());
        
         MessageArea.setText("");
        for (int i = 0; i < messagesForRead.size(); i++) {
            MessageArea.setText(MessageArea.getText() + 
                  "(" + messagesForRead.get(i).getSender_id() + ")  " +
                    messagesForRead.get(i).getMessage_content() + "\n" +
                    "[ " + messagesForRead.get(i).getTimestamp() + " ]\n\n");
        }
    }
    
    

    
    String url = "jdbc:postgresql://localhost:5432/chat_app";
    String user = "postgres";
    String dpassword = "ahmad";
    

public List<Message> getMessages(int userId) {
    List<Message> messages = new ArrayList<>();

    try {
        Connection connection = DriverManager.getConnection(url, user, dpassword);

        String query = "SELECT * FROM messages WHERE receiver_id = ? OR sender_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, userId);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Message message = new Message(
                resultSet.getInt("message_id"),
                resultSet.getInt("sender_id"),
                resultSet.getInt("receiver_id"),
                resultSet.getString("message_content"),
                resultSet.getTimestamp("timestamp")
            );

            messages.add(message);
        }

        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return messages;
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
        MessageArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setType(java.awt.Window.Type.POPUP);

        Panel.setBackground(new java.awt.Color(34, 40, 44));
        Panel.setToolTipText("");
        Panel.setPreferredSize(new java.awt.Dimension(1000, 100));

        MessageArea.setEditable(false);
        MessageArea.setBackground(new java.awt.Color(34, 40, 44));
        MessageArea.setColumns(20);
        MessageArea.setRows(5);
        MessageArea.setText("OCR TEXT");
        MessageArea.setFocusable(false);
        jScrollPane7.setViewportView(MessageArea);

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
            java.util.logging.Logger.getLogger(NotificationsRead.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotificationsRead.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotificationsRead.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotificationsRead.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NotificationsRead().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea MessageArea;
    private javax.swing.JPanel Panel;
    private javax.swing.JScrollPane jScrollPane7;
    // End of variables declaration//GEN-END:variables
}
