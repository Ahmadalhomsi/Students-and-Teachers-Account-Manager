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
import java.awt.Color;

/**
 *
 * @author xandros
 */
public class NotificationsSend extends javax.swing.JFrame {

    /**
     * Creates new form Notifications
     */
    Student loggedStudent;
    Teacher loggedTeacher;
    int maxCharacter;

    public NotificationsSend(Student loggedStudent, int maxCharacter) {
        this.loggedStudent = loggedStudent;
        this.maxCharacter = maxCharacter;
        initComponents();
        MaxCharacterLimitLabel.setText("⋆" + maxCharacter + " Karakter Sınırılı Mesaj (Yonetici Tarafindan)");
    }

    public NotificationsSend(Teacher loggedTeacher, int maxCharacter) {
        this.loggedTeacher = loggedTeacher;
        this.maxCharacter = maxCharacter;
        initComponents();
        MaxCharacterLimitLabel.setText("⋆" + maxCharacter + " Karakter Sınırılı Mesaj (Yonetici Tarafindan)");
    }

    public NotificationsSend() {
        initComponents();
    }

    String url = "jdbc:postgresql://localhost:5432/chat_app";
    String user = "postgres";
    String dpassword = "ahmad";

    // Sending a message
    public void sendMessage(int senderId, int receiverId, String messageContent) {
        try {
            Connection connection = DriverManager.getConnection(url, user, dpassword);

            String query = "INSERT INTO messages (sender_id, receiver_id, message_content, timestamp) VALUES (?, ?, ?, NOW())";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, senderId);
            statement.setInt(2, receiverId);
            statement.setString(3, messageContent);

            statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        SendMessageButton = new javax.swing.JButton();
        SendMessageField = new javax.swing.JTextField();
        IdNumberField = new javax.swing.JTextField();
        ReceiverType = new javax.swing.JComboBox<>();
        MaxCharacterLimitLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setType(java.awt.Window.Type.POPUP);

        Panel.setBackground(new java.awt.Color(34, 40, 44));
        Panel.setToolTipText("");
        Panel.setPreferredSize(new java.awt.Dimension(1000, 100));

        SendMessageButton.setBackground(new java.awt.Color(102, 0, 102));
        SendMessageButton.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        SendMessageButton.setText("Mesaj Gonder");
        SendMessageButton.setPreferredSize(new java.awt.Dimension(100, 22));
        SendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendMessageButtonActionPerformed(evt);
            }
        });

        SendMessageField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SendMessageField.setText("Merhaba");
        SendMessageField.setPreferredSize(new java.awt.Dimension(300, 22));
        SendMessageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendMessageFieldActionPerformed(evt);
            }
        });

        IdNumberField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        IdNumberField.setText("Hoca numarasi");
        IdNumberField.setPreferredSize(new java.awt.Dimension(300, 22));
        IdNumberField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IdNumberFieldActionPerformed(evt);
            }
        });

        ReceiverType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hoca", "Ogrenci" }));
        ReceiverType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReceiverTypeActionPerformed(evt);
            }
        });

        MaxCharacterLimitLabel.setText("⋆ 255 Karakter Sınırılı Mesaj (Yonetici Tarafindan)");

        javax.swing.GroupLayout PanelLayout = new javax.swing.GroupLayout(Panel);
        Panel.setLayout(PanelLayout);
        PanelLayout.setHorizontalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ReceiverType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IdNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addComponent(SendMessageField, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(SendMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(MaxCharacterLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        PanelLayout.setVerticalGroup(
            PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLayout.createSequentialGroup()
                .addGap(211, 211, 211)
                .addComponent(ReceiverType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IdNumberField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SendMessageField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SendMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MaxCharacterLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(267, Short.MAX_VALUE))
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

    private void SendMessageFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendMessageFieldActionPerformed

    }//GEN-LAST:event_SendMessageFieldActionPerformed

    private void SendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendMessageButtonActionPerformed
        // TODO add your handling code here:
        if (SendMessageField.getText().length() < maxCharacter) {
            if (ReceiverType.getSelectedIndex() == 1) // Ogrenci
            {
                sendMessage(loggedTeacher.getTeacherNo(), Integer.parseInt(IdNumberField.getText()), SendMessageField.getText());
            } else if (ReceiverType.getSelectedIndex() == 0) /// Hoca
            {
                sendMessage(loggedStudent.getStudentNo(), Integer.parseInt(IdNumberField.getText()), SendMessageField.getText());
            }
            this.dispose();
        }
        else
        {
            MaxCharacterLimitLabel.setForeground(Color.red);
        }


    }//GEN-LAST:event_SendMessageButtonActionPerformed

    private void IdNumberFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IdNumberFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IdNumberFieldActionPerformed

    private void ReceiverTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReceiverTypeActionPerformed
        // TODO add your handling code here:
        if (ReceiverType.getSelectedIndex() == 0)
            IdNumberField.setText("Hoca Numarasi");

        else if (ReceiverType.getSelectedIndex() == 1)
            IdNumberField.setText("Ogrenci Numarasi");
    }//GEN-LAST:event_ReceiverTypeActionPerformed

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
            java.util.logging.Logger.getLogger(NotificationsSend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotificationsSend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotificationsSend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotificationsSend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new NotificationsSend().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IdNumberField;
    private javax.swing.JLabel MaxCharacterLimitLabel;
    private javax.swing.JPanel Panel;
    private javax.swing.JComboBox<String> ReceiverType;
    private javax.swing.JButton SendMessageButton;
    private javax.swing.JTextField SendMessageField;
    // End of variables declaration//GEN-END:variables
}
