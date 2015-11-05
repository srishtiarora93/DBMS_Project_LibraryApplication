/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Library.Application.Project.UserAuthentication;

import Database.Connection.Helper.Interfaces.*;
import Database.Connection.Helper.DatabaseConnectionService;
import Library.Application.Project.Dues.DuesPage;
import Library.Application.Project.UserHomePage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jordy
 */
public class LoginPage extends javax.swing.JFrame {

    /**
     * Creates new form LoginPage
     */
    public LoginPage() {
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

        signupPanel = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        usernameTextbox = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        signupButton = new javax.swing.JButton();
        signupLabel = new javax.swing.JLabel();
        passwordTextbox = new javax.swing.JPasswordField();
        invisibleLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        usernameLabel.setText("username");

        passwordLabel.setText("password");

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        signupButton.setText("signup");
        signupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupButtonActionPerformed(evt);
            }
        });

        signupLabel.setText("new user?");

        javax.swing.GroupLayout signupPanelLayout = new javax.swing.GroupLayout(signupPanel);
        signupPanel.setLayout(signupPanelLayout);
        signupPanelLayout.setHorizontalGroup(
            signupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signupPanelLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(signupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(usernameLabel)
                    .addGroup(signupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(signupLabel)
                        .addComponent(passwordLabel)))
                .addGap(18, 18, 18)
                .addGroup(signupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameTextbox, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(passwordTextbox))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signupPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(signupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(signupButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42))
        );
        signupPanelLayout.setVerticalGroup(
            signupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(signupPanelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(signupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(usernameTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameLabel))
                .addGap(21, 21, 21)
                .addGroup(signupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(loginButton)
                .addGap(18, 18, 18)
                .addGroup(signupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(signupButton)
                    .addComponent(signupLabel))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        invisibleLabel.setForeground(new java.awt.Color(255, 0, 102));
        invisibleLabel.setText("    ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(signupPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(invisibleLabel)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(signupPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(invisibleLabel)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signupButtonActionPerformed
        this.dispose();
        new SignupPage().setVisible(true);
    }//GEN-LAST:event_signupButtonActionPerformed

    private void ClearUsernamePasswordTextBox() {
        usernameTextbox.setText("");
        passwordTextbox.setText("");
    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        try{
            if (IsValidUser(DatabaseConnectionService.createHashService())){
                if (UserAuthenticationHelper.IsUserStudent(usernameTextbox.getText())){
                    String studentNo = usernameTextbox.getText();
                    if (UserAuthenticationHelper.IsStudentAccountHeld(studentNo)){
                        ClearUsernamePasswordTextBox();
                        this.setVisible(false);
                        new DuesPage(this, studentNo, UserType.Student).setVisible(true);
                        return;
                    }
                    this.dispose();
                    new UserHomePage(studentNo, UserType.Student).setVisible(true);
                }
                if (UserAuthenticationHelper.IsUserFaculty(usernameTextbox.getText())){
                    String facultyNo = usernameTextbox.getText();
                    this.dispose();
                    new UserHomePage(facultyNo, UserType.Faculty).setVisible(true);
                }
            }
            ClearUsernamePasswordTextBox();
            invisibleLabel.setText(ErrorMessages.ERROR_MSG_INVALID);
        }
        catch (Exception e){
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    private boolean IsValidUser(IHashService hashService) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){           
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String findUserQuery = String.format("SELECT U.USERID, U.PASSWORD " + 
                        "FROM Users U " +
                        "WHERE U.USERID = '%s'", usernameTextbox.getText());
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(findUserQuery)){
                    if (resultSet.isEmpty()) return false;
                    String password = getPasswordFromTextbox();
                    resultSet.moveToFirstRow();
                    String passwordHash = resultSet.getString("PASSWORD").trim();
                    return hashService.isMatch(password, passwordHash);
                }
            }
        }
    }
    private String getPasswordFromTextbox(){
        return String.valueOf(passwordTextbox.getPassword());
    }

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
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel invisibleLabel;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTextbox;
    private javax.swing.JButton signupButton;
    private javax.swing.JLabel signupLabel;
    private javax.swing.JPanel signupPanel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextbox;
    // End of variables declaration//GEN-END:variables
}
