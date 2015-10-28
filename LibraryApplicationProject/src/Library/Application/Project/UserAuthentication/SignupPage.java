/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.UserAuthentication;

import Database.Connection.Helper.Interfaces.*;
import Database.Connection.Helper.DatabaseConnectionService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jordy
 */
public class SignupPage extends javax.swing.JFrame {

    /**
     * Creates new form SignupPage
     */
    public SignupPage() {
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

        jPanel1 = new javax.swing.JPanel();
        idLabel = new javax.swing.JLabel();
        idTextbox = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordTextbox = new javax.swing.JPasswordField();
        confirmLabel = new javax.swing.JLabel();
        confirmTextbox = new javax.swing.JPasswordField();
        signupButton = new javax.swing.JButton();
        invisibleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        idLabel.setText("Id");

        passwordLabel.setText("password");

        confirmLabel.setText("confirm");

        signupButton.setText("signup");
        signupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(passwordLabel)
                                .addGap(25, 25, 25)
                                .addComponent(passwordTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(confirmLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(confirmTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(49, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(idLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(idTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(signupButton)
                .addGap(93, 93, 93))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(idLabel)
                    .addComponent(idTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(confirmLabel)
                    .addComponent(confirmTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(signupButton)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        invisibleLabel.setForeground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invisibleLabel))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(invisibleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupButtonActionPerformed
        if (IsEmptyUserCreadentials()){
            invisibleLabel.setText(ErrorMessages.ERROR_MSG_INVALID);
            ClearAll();
            return;
        }
        try{
            if (IsUserIdTaken()){
                invisibleLabel.setText(ErrorMessages.ERROR_MSG_UNAME_TAKEN);
                ClearAll();
                return;
            }
            if (!IsUserStudentOrFaculty()){
                invisibleLabel.setText(ErrorMessages.ERROR_MSG_INVALID);
                ClearAll();
                return;
            }
            if (!IsPasswordAndConfirmationMatch()){
                invisibleLabel.setText(ErrorMessages.ERROR_MSG_PASSWORD_MISMATCH);
                ClearAll();
                return;
            }
            InsertUserToDatabase(DatabaseConnectionService.createHashService());
            ClearInvisibleLabel();
            ClearAll();
            this.dispose();
            new LoginPage().setVisible(true);
        }
        catch (Exception e){
            Logger.getLogger(SignupPage.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_signupButtonActionPerformed

    private Boolean IsEmptyUserCreadentials() {
        return (idTextbox.getText().isEmpty() || 
                getTextFromPasswordTextbox().isEmpty() ||
                getTextFromConfirmTextbox().isEmpty());
    }
    
    private String getTextFromPasswordTextbox(){
        return String.valueOf(passwordTextbox.getPassword());
    }
    
    private String getTextFromConfirmTextbox(){
        return String.valueOf(confirmTextbox.getPassword());
    }
    
    private Boolean IsUserIdTaken()throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){           
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
            String selectUser = String.format("SELECT * FROM Users " +
                    "WHERE userId= '%s'", idTextbox.getText());
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(selectUser)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel confirmLabel;
    private javax.swing.JPasswordField confirmTextbox;
    private javax.swing.JLabel idLabel;
    private javax.swing.JTextField idTextbox;
    private javax.swing.JLabel invisibleLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTextbox;
    private javax.swing.JButton signupButton;
    // End of variables declaration//GEN-END:variables

    private boolean IsUserStudentOrFaculty() {
        try{
            if (UserAuthenticationHelper.IsUserStudent(idTextbox.getText())){
                return true;
            }
            if (UserAuthenticationHelper.IsUserFaculty(idTextbox.getText())){
                return true;
            }
        }
        catch (Exception e){
            Logger.getLogger(SignupPage.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    private boolean IsPasswordAndConfirmationMatch() {
        return getTextFromPasswordTextbox().equals(getTextFromConfirmTextbox());
    }
    
    private void InsertUserToDatabase(IHashService hashService) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){           
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String password = getTextFromPasswordTextbox();
                String passwordHash = hashService.getHash(password);
                String insertIntoUser = String.format("INSERT INTO Users " +
                        "VALUES ('%s', '%s')", idTextbox.getText(), passwordHash);
                stmtExecutor.executeUpdate(insertIntoUser);
            }
        }
    }

    private void ClearAll() {
        idTextbox.setText("");
        passwordTextbox.setText("");
        confirmTextbox.setText("");
    }

    private void ClearInvisibleLabel() {
        invisibleLabel.setText("");
    }
}
