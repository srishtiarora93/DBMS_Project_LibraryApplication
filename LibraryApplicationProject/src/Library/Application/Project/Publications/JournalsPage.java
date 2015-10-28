/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.Publications;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jordy
 */
public class JournalsPage extends javax.swing.JFrame {
    private DefaultTableModel m_Model;
    private final JFrame m_PublicationsPage;
    private final String m_UserId;
    private final UserType m_UserType;
    /**
     * Creates new form JournalsPage
     * @param publicationsPage
     * @param userId
     * @param UserType
     */
    public JournalsPage(JFrame publicationsPage,
            String userId,
            UserType UserType) {
        initComponents();
        this.m_PublicationsPage = publicationsPage;
        this.m_UserId = userId;
        this.m_UserType = UserType;
        CreateTableModel();
        PopulateJournalsTable();
    }
    
    private void CreateTableModel() {
        m_Model = new DefaultTableModel();
        m_Model.addColumn("Identifier");
        m_Model.addColumn("Type");
        m_Model.addColumn("Title");
        journalsTable.setModel(m_Model);
    }
    
    private void PopulateJournalsTable() {
        try {
            AddEcopyJournalsToTable();
            AddHardcopyJournalsToTable();
        }
        catch (Exception e) {
            Logger.getLogger(JournalsPage.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void AddEcopyJournalsToTable() throws Exception{
        PublicationsHelper.AddBooksToTable("ISSN", "TITLE", "EcopyJournals", PublicationType.Ecopy, m_Model);
    }

    private void AddHardcopyJournalsToTable() throws Exception{
        PublicationsHelper.AddBooksToTable("ISSN", "TITLE", "HardcopyJournals", PublicationType.Hardcopy, m_Model);
    }
    
    private String getTableName(String bookType) {
        if (bookType.equals(PublicationType.Ecopy.toString())) return "EcopyJournals";
        return "HardcopyJournals";
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
        journalsTable = new javax.swing.JTable();
        goBackButton = new javax.swing.JButton();
        detailsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        journalsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(journalsTable);

        goBackButton.setText("go back");
        goBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goBackButtonActionPerformed(evt);
            }
        });

        detailsButton.setText("details");
        detailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(goBackButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(detailsButton)
                .addGap(64, 64, 64))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(goBackButton)
                    .addComponent(detailsButton))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void detailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsButtonActionPerformed
        int selectedRow = journalsTable.getSelectedRow();
        String bookId = (String)journalsTable.getModel().getValueAt(selectedRow, 0);
        String bookType = (String)journalsTable.getModel().getValueAt(selectedRow, 1);
        String tableName = getTableName(bookType);
        this.setVisible(false);
        new PublicationDetailsPage(this, tableName, m_UserId, bookId, m_UserType).setVisible(true);
    }//GEN-LAST:event_detailsButtonActionPerformed

    private void goBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goBackButtonActionPerformed
        this.dispose();
        m_PublicationsPage.setVisible(true);
    }//GEN-LAST:event_goBackButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton detailsButton;
    private javax.swing.JButton goBackButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable journalsTable;
    // End of variables declaration//GEN-END:variables
}
