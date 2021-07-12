/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithemba.ray_of_hope;

import javax.swing.*;

/**
 *
 * @author Siviwe
 */
public class frmLoginAccount extends javax.swing.JDialog {

    /**
     * Creates new form frmLoginAccount
     * @param frmParent
     * @param strAction
     */
    public frmLoginAccount(JFrame frmParent, String strAction) {
        super(frmParent, strAction, ModalityType.APPLICATION_MODAL);
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        if(strAction.equals("Sign Up")) {
            this.setTitle("Login Account Sign Up");
            txtUsername.requestFocusInWindow();
            
        } else if(strAction.equals("Update")) {
            this.setTitle("Update Login Account");
            this.lblSignUpHeading.setText("Update Account");
            cboUserRole.setEnabled(false);
            btnSignUp.setText("Update");
            mSetDetailsToGUI();
        }
    }
    
    public frmLoginAccount(JFrame frmParent, frmRegisterAndManagePolicies frmInstantiator){
        super(frmParent, "Sign Up Member", ModalityType.APPLICATION_MODAL);
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        txtUsername.requestFocusInWindow();
        this.registerPolicy = frmInstantiator;
    }
    
    private frmRegisterAndManagePolicies registerPolicy;
    
    private final clsDatabaseInterface dataMethods = new clsDatabaseInterface();
    private final frmLogin frmLogin = new frmLogin();
    
    private String mCheckInput() {
        if(txtUsername.getText().equals("")) {
            return "Please provide an account username!";
            
        } else if(txtPassword.getText().equals("")) {
            return "Please provide an account password";
        }
        return "";
    }
    
    private void mSetDetailsToGUI() {
        txtUsername.setText(dataMethods.mGetField("SELECT Username FROM Users_Login WHERE ID ="+frmLogin.mGetLoggedInUser()));
        txtPassword.setText(dataMethods.mGetField("SELECT Password FROM Users_Login WHERE ID ="+frmLogin.mGetLoggedInUser()));
    }
    
    private void mSignUp() {
        if(mCheckInput().equals("")){
            if(dataMethods.mCheckDetailsExistence("SELECT Username FROM Users_Login WHERE Username ='"+txtUsername.getText().trim()+"'")) {
                
                JOptionPane.showMessageDialog(this, "Account username already exists.",
                        "WARNING", JOptionPane.WARNING_MESSAGE);
                
            } else if(dataMethods.mCreateNewRecord("INSERT INTO Users_Login (Username, Password, Role)"
                        + "VALUES('"+txtUsername.getText().trim()+"','"+txtPassword.getText().trim()
                            +"','"+cboUserRole.getSelectedItem().toString()+"')")) {
                    
                    if(frmLogin.mGetLoggedInUser() != 0) {
                        
                        JOptionPane.showMessageDialog(this, "Principal login account created", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                        registerPolicy.mSetPrincipalMemberAccID(Integer.parseInt(
                                dataMethods.mGetField("SELECT ID FROM Users_Login ORDER BY ID DESC LIMIT 1")));
                        
                    } else {
                        JOptionPane.showMessageDialog(this, "Login account sucessfully registered.", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
        } else {
            JOptionPane.showMessageDialog(this, mCheckInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mUpdateAccount() {
       if(mCheckInput().equals("")) {
           if(!dataMethods.mGetField("SELECT Username FROM Users_Login WHERE ID ='"+
                frmLogin.mGetLoggedInUser()+"'").equals(txtUsername.getText().trim()) && 
                    dataMethods.mCheckDetailsExistence("SELECT Username FROM Users_Login"
                        + " WHERE Username='"+txtUsername.getText().trim()+"'")) {
                        
                JOptionPane.showMessageDialog(this, "Username exists, Provide an alternative.", 
                    "WARNING", JOptionPane.WARNING_MESSAGE);
                        
            } else {
                if(dataMethods.mUpdateRecordDetails("UPDATE Users_Login SET Username ='"+txtUsername.getText().trim()+
                        "', Password ='"+txtPassword.getText().trim()+"' WHERE ID ="+frmLogin.mGetLoggedInUser())) {
                    
                    JOptionPane.showMessageDialog(this, "Account Updated.", "MESSAGE",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
       } else {
           JOptionPane.showMessageDialog(this, mCheckInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
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

        jpMainPanel = new javax.swing.JPanel();
        jpContainer = new javax.swing.JPanel();
        lblSignUpHeading = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        btnSignUp = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        lblRole = new javax.swing.JLabel();
        cboUserRole = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jpMainPanel.setBackground(new java.awt.Color(204, 0, 0));

        jpContainer.setBackground(new java.awt.Color(255, 255, 255));

        lblSignUpHeading.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSignUpHeading.setText("Sign Up");

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ithemba/ray_of_hope/icon.jpg"))); // NOI18N
        lblIcon.setText("jLabel1");

        lblUsername.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblUsername.setText("Username");

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPassword.setText("Password");

        btnSignUp.setBackground(new java.awt.Color(255, 255, 255));
        btnSignUp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSignUp.setText("Sign Up");
        btnSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpActionPerformed(evt);
            }
        });

        btnClose.setBackground(new java.awt.Color(255, 255, 255));
        btnClose.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lblRole.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRole.setText("Role");

        cboUserRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User/Client", "Administrator" }));

        javax.swing.GroupLayout jpContainerLayout = new javax.swing.GroupLayout(jpContainer);
        jpContainer.setLayout(jpContainerLayout);
        jpContainerLayout.setHorizontalGroup(
            jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(lblSignUpHeading)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jpContainerLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addComponent(btnSignUp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsername)
                            .addComponent(lblPassword)
                            .addComponent(lblRole))
                        .addGap(126, 126, 126)
                        .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(cboUserRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(50, 50, 50))
        );
        jpContainerLayout.setVerticalGroup(
            jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpContainerLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSignUpHeading)
                    .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUsername))
                .addGap(35, 35, 35)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassword))
                .addGap(35, 35, 35)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRole)
                    .addComponent(cboUserRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSignUp)
                    .addComponent(btnClose))
                .addGap(66, 66, 66))
        );

        javax.swing.GroupLayout jpMainPanelLayout = new javax.swing.GroupLayout(jpMainPanel);
        jpMainPanel.setLayout(jpMainPanelLayout);
        jpMainPanelLayout.setHorizontalGroup(
            jpMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpMainPanelLayout.setVerticalGroup(
            jpMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpActionPerformed
        if(btnSignUp.getText().equals("Sign Up")) {
            mSignUp();
            
        } else if(btnSignUp.getText().equals("Update")) {
            mUpdateAccount();
        }
    }//GEN-LAST:event_btnSignUpActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

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
            java.util.logging.Logger.getLogger(frmLoginAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLoginAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLoginAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLoginAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frmLoginAccount dialog = new frmLoginAccount(new javax.swing.JFrame(), "");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnSignUp;
    private javax.swing.JComboBox<String> cboUserRole;
    private javax.swing.JPanel jpContainer;
    private javax.swing.JPanel jpMainPanel;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblSignUpHeading;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
