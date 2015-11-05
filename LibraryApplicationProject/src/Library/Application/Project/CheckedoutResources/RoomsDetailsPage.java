/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.CheckedoutResources;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jordy
 */
public class RoomsDetailsPage extends javax.swing.JFrame {

    private DefaultTableModel m_Model;
    private final JFrame m_RoomsPage;
    private final String m_TableName;
    private final String m_UserId;
    private final String m_RoomNo;
    private final String m_StartDate;
    
    /**
     * Creates new form RoomsDetailsPage
     * @param roomsPage
     * @param tableName
     * @param userId
     * @param roomNo
     * @param startDate
     */
    public RoomsDetailsPage(JFrame roomsPage, String tableName,
    String userId, String roomNo, String startDate) {
        initComponents();
        this.m_RoomsPage = roomsPage;
        this.m_TableName = tableName;
        this.m_UserId = userId;
        this.m_RoomNo = roomNo;
        this.m_StartDate = startDate;
        
        CreateTableModel();
        PopulateDetailTable();
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
        detailsTable = new javax.swing.JTable();
        gobackButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        detailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(detailsTable);

        gobackButton.setText("Go Back");
        gobackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gobackButtonActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(173, Short.MAX_VALUE)
                .addComponent(gobackButton)
                .addGap(156, 156, 156))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(gobackButton)
                .addGap(37, 37, 37))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void gobackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gobackButtonActionPerformed
        this.dispose();
        m_RoomsPage.setVisible(true);
    }//GEN-LAST:event_gobackButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable detailsTable;
    private javax.swing.JButton gobackButton;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void CreateTableModel() {
        m_Model = new DefaultTableModel();
        m_Model.addColumn("");
        m_Model.addColumn("");
        detailsTable.setModel(m_Model);
    }

    private void PopulateDetailTable() {
        try{
            CheckedoutResourcesHelper.AddRoomDetailToTable(m_TableName, m_UserId, 
                    m_RoomNo, m_StartDate, m_Model);
        }
        catch (Exception e) {
            Logger.getLogger(RoomsDetailsPage.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}