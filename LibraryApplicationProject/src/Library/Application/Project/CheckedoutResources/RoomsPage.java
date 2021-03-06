/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.CheckedoutResources;

import Library.Application.Project.UserAuthentication.UserType;
import javax.swing.JFrame;

/**
 *
 * @author jordy
 */
public class RoomsPage extends javax.swing.JFrame {

    private final JFrame m_ResourcesPage;
    private final String m_UserId;
    private final UserType m_UserType;
    
    /**
     * Creates new form RoomsPage
     * @param resourcesPage
     * @param userId
     * @param userType
     */
    public RoomsPage(JFrame resourcesPage, String userId, UserType userType) {
        initComponents();
        this.m_ResourcesPage = resourcesPage;
        this.m_UserId = userId;
        this.m_UserType = userType;
        
        if(userType.equals(UserType.Student)){
            confButton.setEnabled(false);
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

        jPanel1 = new javax.swing.JPanel();
        confButton = new javax.swing.JButton();
        studyButton = new javax.swing.JButton();
        goBackButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        confButton.setText("Conference Rooms");
        confButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confButtonActionPerformed(evt);
            }
        });

        studyButton.setText("Study Rooms");
        studyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studyButtonActionPerformed(evt);
            }
        });

        goBackButton.setText("Go Back");
        goBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(confButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(studyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(goBackButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(118, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(confButton)
                .addGap(44, 44, 44)
                .addComponent(studyButton)
                .addGap(54, 54, 54)
                .addComponent(goBackButton)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void confButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confButtonActionPerformed
        this.setVisible(false);
        new ConferenceRooms(this, m_UserId, m_UserType).setVisible(true);
    }//GEN-LAST:event_confButtonActionPerformed

    private void studyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studyButtonActionPerformed
        this.setVisible(false);
        new StudyRooms(this, m_UserId, m_UserType).setVisible(true);
    }//GEN-LAST:event_studyButtonActionPerformed

    private void goBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBackButtonActionPerformed
        this.dispose();
        m_ResourcesPage.setVisible(true);
    }//GEN-LAST:event_goBackButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confButton;
    private javax.swing.JButton goBackButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton studyButton;
    // End of variables declaration//GEN-END:variables
}
