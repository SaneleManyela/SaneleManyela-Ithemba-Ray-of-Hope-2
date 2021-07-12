/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithemba.ray_of_hope;

import javax.swing.JOptionPane;


/**
 *
 * @author Sanele
 */
public class frmCovers extends javax.swing.JDialog {

    /**
     * Creates new form frmCovers
     * @param frmParent
     */
    public frmCovers(frmMain frmParent) {
        super(frmParent, "Policy Covers", ModalityType.APPLICATION_MODAL);
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.frmMn = frmParent;
        txtCoverAmount.requestFocusInWindow();
    }
    
    private int intCoverID;
    
    private final clsDatabaseInterface databaseInterface = new clsDatabaseInterface();
    private final clsValidateInput validate = new clsValidateInput();
    
    private final frmMain frmMn;
    
    public void mSetDetailsToGUI(int intCoverID) {
        this.intCoverID = intCoverID;
        txtCoverAmount.setText(databaseInterface.mGetField("SELECT CoverAmount FROM Covers WHERE ID ="+this.intCoverID));
        txtCoverCategory.setText(databaseInterface.mGetField("SELECT Category FROM Covers WHERE ID ="+this.intCoverID));
        txtPremium.setText(databaseInterface.mGetField("SELECT Premium FROM Covers WHERE ID="+this.intCoverID));
    }
    
    private String[] mGetDetailsFromGUI() {
        return new String[] {
          txtCoverAmount.getText().trim(), txtCoverCategory.getText().trim(),
            txtPremium.getText().trim()
        };
    }
    
    private String mVerifyInput() {
        try {
            if(txtCoverAmount.getText().trim().equals("")) {
                return "Cover amount is required!!";
            
            } else if(Integer.parseInt(txtCoverAmount.getText().trim()) < 0) {
                return "Cover amount cannot be negative";
            
            } else if(txtCoverCategory.getText().trim().equals("")) {
                return "Cover category is required!!";
            
            } else if(txtPremium.getText().trim().equals("")) {
                return "Premium amount is required";
            
            } else if(Integer.parseInt(txtPremium.getText().trim()) < 0) {
                return "Premium amount cannot be negative";
            }
        } catch(NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return "";
    }
    
    private void mCreateCover() {
        if(mVerifyInput().equals("")) {
            if(validate.mCheckIfFieldIsOnlyDigits(txtCoverAmount.getText().trim()).equals("")
                    && validate.mCheckIfFieldIsOnlyDigits(txtPremium.getText().trim()).equals("")) {
                
                if(databaseInterface.mCheckDetailsExistence("SELECT CoverAmount, Category FROM Covers WHERE CoverAmount="+mGetDetailsFromGUI()[0]+
                        " AND Category ='"+mGetDetailsFromGUI()[1]+"'")) {
                    
                    JOptionPane.showMessageDialog(this, "Policy cover is already available", "WARNING", JOptionPane.WARNING_MESSAGE);
                    
                } else {
                    
                    if(databaseInterface.mCreateNewRecord("INSERT INTO Covers (CoverAmount, Category, Premium)"
                            + " VALUES('"+mGetDetailsFromGUI()[0]+"','"+mGetDetailsFromGUI()[1]+"','"+mGetDetailsFromGUI()[2]+"')")) {
                        JOptionPane.showMessageDialog(this, "Cover Policy added.", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                        
                    }
                }
            } else if(!validate.mCheckIfFieldIsOnlyDigits(txtCoverAmount.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mCheckIfFieldIsOnlyDigits(txtCoverAmount.getText().trim()), "WARNING", JOptionPane.WARNING_MESSAGE);
                
            } else if(!validate.mCheckIfFieldIsOnlyDigits(txtPremium.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mCheckIfFieldIsOnlyDigits(txtPremium.getText().trim()), "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, mVerifyInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mUpdateCover() {
        frmSelector selector = new frmSelector(frmMn, "Covers");
        selector.mCoversInstance(this);
        selector.setVisible(true);
    }
    
    private void mSaveUpdate() {
        if(mVerifyInput().equals("")) {
            if(validate.mCheckIfFieldIsOnlyDigits(txtCoverAmount.getText().trim()).equals("")
                    && validate.mCheckIfFieldIsOnlyDigits(txtPremium.getText().trim()).equals("")) {
                
                if(databaseInterface.mUpdateRecordDetails("UPDATE Covers SET CoverAmount="+
                        mGetDetailsFromGUI()[0]+", Category ='"+mGetDetailsFromGUI()[1]
                            +"', Premium ="+mGetDetailsFromGUI()[2]+" WHERE ID ="+intCoverID)) {
                
                    JOptionPane.showMessageDialog(this, "Cover details updated", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if(!validate.mCheckIfFieldIsOnlyDigits(txtCoverAmount.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mCheckIfFieldIsOnlyDigits(txtCoverAmount.getText().trim()), "WARNING", JOptionPane.WARNING_MESSAGE);
            
            } else if(!validate.mCheckIfFieldIsOnlyDigits(txtPremium.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mCheckIfFieldIsOnlyDigits(txtPremium.getText().trim()), "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, mVerifyInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mClearGUITextFields () {
        txtCoverAmount.setText("");
        txtCoverCategory.setText("");
        txtPremium.setText("");
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
        lblCoversHeading = new javax.swing.JLabel();
        lblCoverAmount = new javax.swing.JLabel();
        txtCoverAmount = new javax.swing.JTextField();
        lblCoverCategory = new javax.swing.JLabel();
        txtCoverCategory = new javax.swing.JTextField();
        lblPremium = new javax.swing.JLabel();
        txtPremium = new javax.swing.JTextField();
        btnCreate = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jpMainPanel.setBackground(new java.awt.Color(204, 0, 0));

        jpContainer.setBackground(new java.awt.Color(255, 255, 255));

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ithemba/ray_of_hope/icon.jpg"))); // NOI18N

        lblCoversHeading.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCoversHeading.setText("Covers");

        lblCoverAmount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCoverAmount.setText("Cover Amount");

        lblCoverCategory.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCoverCategory.setText("Cover Category");

        lblPremium.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPremium.setText("Premium");

        btnCreate.setBackground(new java.awt.Color(255, 255, 255));
        btnCreate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCreate.setText("Create");
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
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(lblCoversHeading)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCoverAmount)
                            .addComponent(lblPremium)
                            .addComponent(lblCoverCategory)
                            .addComponent(btnCreate))
                        .addGap(14, 14, 14)
                        .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCoverAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCoverCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPremium, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpContainerLayout.createSequentialGroup()
                                .addComponent(btnUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(27, 27, 27))
        );
        jpContainerLayout.setVerticalGroup(
            jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(lblCoversHeading)))
                .addGap(35, 35, 35)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCoverAmount)
                    .addComponent(txtCoverAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCoverCategory)
                    .addComponent(txtCoverCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPremium)
                    .addComponent(txtPremium, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnUpdate)
                    .addComponent(btnClear))
                .addContainerGap(90, Short.MAX_VALUE))
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

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        mCreateCover();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if(btnUpdate.getText().equals("Update")) {
            mUpdateCover();
            if(mVerifyInput().equals("")) {
                btnUpdate.setText("Save");
            }
            
        } else if(btnUpdate.getText().equals("Save")) {
            mSaveUpdate();
            btnUpdate.setText("Update");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        mClearGUITextFields();
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
            java.util.logging.Logger.getLogger(frmCovers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCovers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCovers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCovers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmCovers dialog = new frmCovers(new frmMain(""));
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
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel jpContainer;
    private javax.swing.JPanel jpMainPanel;
    private javax.swing.JLabel lblCoverAmount;
    private javax.swing.JLabel lblCoverCategory;
    private javax.swing.JLabel lblCoversHeading;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblPremium;
    private javax.swing.JTextField txtCoverAmount;
    private javax.swing.JTextField txtCoverCategory;
    private javax.swing.JTextField txtPremium;
    // End of variables declaration//GEN-END:variables
}
