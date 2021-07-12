/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithemba.ray_of_hope;

import javax.swing.*;

/**
 *
 * @author Sanele
 */
public class frmBeneficiaries extends javax.swing.JDialog {

    /**
     * Creates new form frmBeneficiaries
     * @param frmParent
     */
    public frmBeneficiaries(frmMain frmParent) {
        super(frmParent, "Beneficiaries", ModalityType.APPLICATION_MODAL);
        initComponents();
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        
        if(databaseInterface.mGetField("SELECT ROLE FROM Users_Login WHERE ID="+new frmLogin().mGetLoggedInUser()).equals("User/Client")) {
            dataMethods.mLoadToComboBox("SELECT ID_NUM FROM Principal_Members WHERE Acc_ID ="+frmLogin.mGetLoggedInUser(), cboPrincipalMember);
            
            if(!databaseInterface.mCheckDetailsExistence("SELECT * FROM Principal_Members WHERE Acc_ID="+frmLogin.mGetLoggedInUser())) {
                btnCreate.setEnabled(false);
                btnUpdate.setEnabled(false);
                btnDelete.setEnabled(false);
            }
        } else {
            dataMethods.mLoadToComboBox("SELECT ID_NUM FROM Principal_Members", cboPrincipalMember);
        }
        this.frmMn = frmParent;
        txtFirstName.requestFocusInWindow();
    }

    private final clsDatabaseInterface databaseInterface = new clsDatabaseInterface();
    private final clsDataMethods dataMethods = new clsDataMethods();
    private final clsValidateInput validate = new clsValidateInput();
    
    private final frmLogin frmLogin = new frmLogin();
    private final frmMain frmMn;
    
    private Long lngBeneficiaryIDNo = 0L;
    
    public void mSetBeneficiaryIDNo(Long lngID) {
        this.lngBeneficiaryIDNo = lngID;
    }
    
    public void mSetDetailsToGUI(Long lngID) {
        this.lngBeneficiaryIDNo = lngID;
        
        String[] arrBeneficiaryDetails = databaseInterface.mFetchRecordDetails(
        "SELECT FName, LName, ID_Num, PM_ID_Num, Relationship FROM Beneficiaries WHERE ID_Num ="+lngBeneficiaryIDNo);
        
        txtFirstName.setText(arrBeneficiaryDetails[0]);
        txtSurname.setText(arrBeneficiaryDetails[1]);
        txtIDNo.setText(arrBeneficiaryDetails[2]);
        cboPrincipalMember.setSelectedItem(arrBeneficiaryDetails[3]);
        txtRelationship.setText(arrBeneficiaryDetails[4]);
    }
    
    private String[] mGetDetailsFromGUI() {
        return new String[] {
           txtFirstName.getText().trim(), txtSurname.getText().trim(),
            txtIDNo.getText().trim(), 
            cboPrincipalMember.getSelectedItem().toString(),
            txtRelationship.getText().trim()
        };
    }
    
    private String mVerifyUserInput() {
        if(txtFirstName.getText().equals("")) {
            return "First Name required!!";
            
        } else if(txtSurname.getText().equals("")) {
            return "Surname required!!";
            
        } else if(txtIDNo.getText().equals("")) {
            return "ID Number required!!";
            
        } else if(txtIDNo.getText().length() != 13) {
            return "RSA ID Numbers are 13 digits";
            
        } else if(txtRelationship.getText().equals("")) {
            return "Specify relationship!!";
        }
        return "";
    }
    
    private void mRegister() {
        if(mVerifyUserInput().equals("")) {
            if(validate.mCheckIfFieldIsOnlyDigits(txtIDNo.getText().trim()).equals("")) {
                
                if(databaseInterface.mCreateNewRecord("INSERT INTO Beneficiaries (FName, LName, ID_Num, PM_ID_Num, Relationship)"
                        + "VALUES('"+mGetDetailsFromGUI()[0]+"','"+mGetDetailsFromGUI()[1]+"','"+
                        mGetDetailsFromGUI()[2]+"','"+mGetDetailsFromGUI()[3]
                        +"','"+mGetDetailsFromGUI()[4]+"')")) {
                    
                    JOptionPane.showMessageDialog(this, "Beneficiary updated", 
                            "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                }                
            } else {
                JOptionPane.showMessageDialog(this, validate.mCheckIfFieldIsOnlyDigits(txtIDNo.getText().trim()), 
                        "WARNING", JOptionPane.WARNING_MESSAGE);
            }
            
        } else{
            JOptionPane.showMessageDialog(this, mVerifyUserInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mUpdateBeneficiary() {
        frmSelector selector = new frmSelector(frmMn, "Beneficiary");
        selector.mBeneficiariesInstance(this);
        selector.setVisible(true);
    }
    
    private void mSaveUpdate() {
        if(mVerifyUserInput().equals("")) {
            if(validate.mCheckIfFieldIsOnlyDigits(txtIDNo.getText().trim()).equals("")) {
            
                if(databaseInterface.mUpdateRecordDetails("UPDATE Beneficiaries SET FName='"+mGetDetailsFromGUI()[0]
                        +"', LName='"+mGetDetailsFromGUI()[1]+"', ID_Num ='"+mGetDetailsFromGUI()[2]+
                    "', PM_ID_Num ='"+mGetDetailsFromGUI()[3]+"', Relationship ='"+mGetDetailsFromGUI()[4]+
                        "' WHERE ID_Num="+lngBeneficiaryIDNo)) {
                    
                    JOptionPane.showMessageDialog(this, "Beneficiary details have been updated", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } else {
                JOptionPane.showMessageDialog(this, validate.mCheckIfFieldIsOnlyDigits(txtIDNo.getText().trim()), 
                        "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else{
            JOptionPane.showMessageDialog(this, mVerifyUserInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mDeleteBeneficiary() {
        frmSelector selector = new frmSelector(frmMn, "Delete Beneficiary");
        selector.mBeneficiariesInstance(this);
        selector.setVisible(true);
        
        if(lngBeneficiaryIDNo != 0L) {
            if(databaseInterface.mDeleteRecord("DELETE FROM Beneficiaries WHERE ID_Num ="+lngBeneficiaryIDNo)) {
            
                JOptionPane.showMessageDialog(this, "Beneficiary details have been deleted",
                    "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void mClear() {
        txtFirstName.setText("");
        txtSurname.setText("");
        txtIDNo.setText("");
        txtRelationship.setText("");
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
        lblIcon = new javax.swing.JLabel();
        lblBeneficiariesHeading = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblSurname = new javax.swing.JLabel();
        txtSurname = new javax.swing.JTextField();
        lblIDNo = new javax.swing.JLabel();
        txtIDNo = new javax.swing.JTextField();
        lblPrincipalMember = new javax.swing.JLabel();
        cboPrincipalMember = new javax.swing.JComboBox<>();
        lblRelationship = new javax.swing.JLabel();
        txtRelationship = new javax.swing.JTextField();
        btnCreate = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jpMainPanel.setBackground(new java.awt.Color(204, 0, 0));

        jpContainer.setBackground(new java.awt.Color(255, 255, 255));

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ithemba/ray_of_hope/icon.jpg"))); // NOI18N

        lblBeneficiariesHeading.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBeneficiariesHeading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBeneficiariesHeading.setText("Beneficiaries");

        lblFirstName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFirstName.setText("First Name");

        lblSurname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSurname.setText("Surname");

        lblIDNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblIDNo.setText("ID No.");

        lblPrincipalMember.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPrincipalMember.setText("Principal Member");

        lblRelationship.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRelationship.setText("Relationship");

        btnCreate.setBackground(new java.awt.Color(255, 255, 255));
        btnCreate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCreate.setText("Register");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 255, 255));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(255, 255, 255));
        btnClear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpContainerLayout = new javax.swing.GroupLayout(jpContainer);
        jpContainer.setLayout(jpContainerLayout);
        jpContainerLayout.setHorizontalGroup(
            jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpContainerLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpContainerLayout.createSequentialGroup()
                        .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpContainerLayout.createSequentialGroup()
                                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSurname)
                                    .addComponent(lblIDNo)
                                    .addComponent(lblPrincipalMember)
                                    .addComponent(lblRelationship)
                                    .addGroup(jpContainerLayout.createSequentialGroup()
                                        .addComponent(btnCreate)
                                        .addGap(23, 23, 23)
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jpContainerLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                                        .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtIDNo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cboPrincipalMember, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRelationship, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jpContainerLayout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(23, 23, 23)
                                        .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jpContainerLayout.createSequentialGroup()
                                .addComponent(lblFirstName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(lblBeneficiariesHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jpContainerLayout.setVerticalGroup(
            jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpContainerLayout.createSequentialGroup()
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(lblBeneficiariesHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFirstName)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSurname)
                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIDNo)
                    .addComponent(txtIDNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrincipalMember)
                    .addComponent(cboPrincipalMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRelationship)
                    .addComponent(txtRelationship, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnDelete)
                    .addComponent(btnClear)
                    .addComponent(btnUpdate))
                .addGap(61, 61, 61))
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
            .addComponent(jpMainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        mRegister();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if(btnUpdate.getText().equals("Update")) {
            mUpdateBeneficiary();
            btnUpdate.setText("Save");
            
        } else if(btnUpdate.getText().equals("Save")) {
            mSaveUpdate();
            btnUpdate.setText("Update");
            
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        mDeleteBeneficiary();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        mClear();
    }//GEN-LAST:event_btnClearActionPerformed

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
            java.util.logging.Logger.getLogger(frmBeneficiaries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmBeneficiaries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmBeneficiaries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmBeneficiaries.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmBeneficiaries dialog = new frmBeneficiaries(new frmMain(""));
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
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboPrincipalMember;
    private javax.swing.JPanel jpContainer;
    private javax.swing.JPanel jpMainPanel;
    private javax.swing.JLabel lblBeneficiariesHeading;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblIDNo;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblPrincipalMember;
    private javax.swing.JLabel lblRelationship;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtIDNo;
    private javax.swing.JTextField txtRelationship;
    private javax.swing.JTextField txtSurname;
    // End of variables declaration//GEN-END:variables
}
