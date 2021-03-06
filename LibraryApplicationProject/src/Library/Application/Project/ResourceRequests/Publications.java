/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.ResourceRequests;

import Library.Application.Project.UserAuthentication.UserType;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jordy
 */
public class Publications extends javax.swing.JFrame {
    private final JFrame m_ResRequestsPage;
    private final String m_UserId;
    private final UserType m_UserType;
    private DefaultTableModel m_Model;

    /**
     * Creates new form Publications
     * @param resRequestsPage
     * @param userId
     * @param userType
     */
    public Publications(JFrame resRequestsPage, 
            String userId,
            UserType userType) {
        initComponents();
        this.m_ResRequestsPage = resRequestsPage;
        this.m_UserId = userId;
        this.m_UserType = userType;
        
        CreateTableModel();
        PopulatePubTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pubTable = new javax.swing.JTable();
        goBackButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pubTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(pubTable);

        goBackButton.setText("Go Back");
        goBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(goBackButton)
                .addContainerGap(171, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(goBackButton)
                .addGap(19, 19, 19))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void goBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBackButtonActionPerformed
        this.dispose();
        m_ResRequestsPage.setVisible(true);
    }//GEN-LAST:event_goBackButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton goBackButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable pubTable;
    // End of variables declaration//GEN-END:variables

    private void CreateTableModel() {
        m_Model = new DefaultTableModel();
        m_Model.addColumn("Identifier");
        m_Model.addColumn("Checkout Date");
        m_Model.addColumn("Return Date");
        pubTable.setModel(m_Model);
    }

    private void PopulatePubTable() {
        try {
            ResourceRequestsHelper.AddPublicationsToTable(m_UserId, m_Model);
        }
        catch (Exception e) {
            Logger.getLogger(Publications.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
