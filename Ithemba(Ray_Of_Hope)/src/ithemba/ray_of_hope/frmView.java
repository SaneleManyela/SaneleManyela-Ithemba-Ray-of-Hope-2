/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithemba.ray_of_hope;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sanele
 */
public class frmView extends javax.swing.JDialog {

    /**
     * Creates new form frmView
     * @param frmParent
     * @param strView
     */
    public frmView(frmMain frmParent, String strView) {
        super(frmParent, strView, ModalityType.APPLICATION_MODAL);
        initComponents();
        
        this.strView = strView;
        
        switch(strView) {
            case "Covers":
                tblTable = dataMethods.mTable("SELECT ID, CoverAmount, Category, Premium FROM Covers", 
                        tblTable, dmTableModel);
                lblSearch.setVisible(false);
                txtSearch.setVisible(false);
                btnSearch.setVisible(false);
                this.setTitle("View Policy Covers");
                this.lblViewHeading.setText("Policy Covers");
                this.pack();
                break;
                
            case "Principals":
                if(databaseInterface.mGetField("SELECT Role FROM Users_Login WHERE ID ="+frmLogin.mGetLoggedInUser()).equals("User/Client")) {
                    
                    tblTable = dataMethods.mTable("SELECT ID_Num, FName, LName, DOB, Email, Cover_ID FROM Principal_Members WHERE Acc_ID ="+frmLogin.mGetLoggedInUser(),
                            tblTable, dmTableModel);
                    lblSearch.setVisible(false);
                    txtSearch.setVisible(false);
                    btnSearch.setVisible(false);
                    this.setTitle("View Your Policy Details");
                    this.lblViewHeading.setText("Your Policy Details");
                    this.pack();
                    
                } else {
                    
                    tblTable = dataMethods.mTable("SELECT ID_Num, FName, LName, DOB, Email, Cover_ID FROM Principal_Members", 
                            tblTable, dmTableModel);
                    this.setTitle("View Policy Details of Principal Members");
                    this.lblViewHeading.setText("Policy Details of Principal Members");
                    txtSearch.setToolTipText("Search by first name, by last name, or both.");
                    txtSearch.requestFocusInWindow();
                }
                break;
                
            case "Beneficiaries":
                 if(databaseInterface.mGetField("SELECT Role FROM Users_Login WHERE ID ="+frmLogin.mGetLoggedInUser()).equals("User/Client")) {
                     
                     tblTable = dataMethods.mTable("SELECT ID_Num, FName, LName, Relationship, PM_ID_Num FROM Beneficiaries WHERE PM_ID_Num ="+
                             databaseInterface.mGetField("SELECT ID_Num FROM Principal_Members WHERE Acc_ID="+frmLogin.mGetLoggedInUser()),
                             tblTable, dmTableModel);
                    lblSearch.setVisible(false);
                    txtSearch.setVisible(false);
                    btnSearch.setVisible(false);
                    this.setTitle("View Your Beneficiaries");
                    this.lblViewHeading.setText("Beneficiaries under You");
                    this.pack();
                    
                 } else {
                     
                    tblTable = dataMethods.mTable("SELECT ID_Num, FName, LName, Relationship, PM_ID_Num FROM Beneficiaries", tblTable, dmTableModel);
                    this.setTitle("View Beneficiaries");
                    this.lblViewHeading.setText("Registered Beneficiaries");
                    txtSearch.setToolTipText("Search by first name, by last name, or both.");
                    txtSearch.requestFocusInWindow();
                 }
                break;
        }
        
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
    }
    
    private DefaultTableModel dmTableModel = new DefaultTableModel();
    private String strView;
    
    private final clsDatabaseInterface databaseInterface = new clsDatabaseInterface();
    private final clsDataMethods dataMethods = new clsDataMethods();
    frmLogin frmLogin = new frmLogin();
    
    
    private void mSearch() {
        if(!txtSearch.getText().equals("")) {
            if(strView.equals("Principals")){ 
                
                if(txtSearch.getText().contains(" ")) {
                
                    String strFName = txtSearch.getText().substring(0,
                         txtSearch.getText().indexOf(" ")).trim();
                
                    String strLName = txtSearch.getText().substring(txtSearch.getText().indexOf(" "), 
                            txtSearch.getText().length()).trim();
                
                    dmTableModel = new DefaultTableModel();
                    tblTable = dataMethods.mTable(
                            "SELECT ID_Num, FName, LName, DOB, Email, Cover_ID FROM Principal_Members WHERE FName LIKE '%"+strFName+"%' AND LName LIKE '%"+strLName+"%'",
                            tblTable, dmTableModel);
                
                } else {
                    dmTableModel = new DefaultTableModel();
                    tblTable = dataMethods.mTable(
                            "SELECT ID_Num, FName, LName, DOB, Email, Cover_ID FROM Principal_Members WHERE FName LIKE '%"+txtSearch.getText().trim()+"%' OR LName LIKE '%"+txtSearch.getText().trim()+"%'",
                            tblTable, dmTableModel);
                }
                
            } else if(strView.equals("Beneficiaries")) {
                
                if(txtSearch.getText().contains(" ")) {
                
                    String strFName = txtSearch.getText().substring(0,
                         txtSearch.getText().indexOf(" ")).trim();
                
                    String strLName = txtSearch.getText().substring(txtSearch.getText().indexOf(" "), 
                            txtSearch.getText().length()).trim();
                
                    dmTableModel = new DefaultTableModel();
                    tblTable = dataMethods.mTable(
                            "SELECT ID_Num, FName, LName, Relationship, PM_ID_Num FROM Beneficiaries WHERE FName LIKE '%"+strFName+"%' AND LName LIKE '%"+strLName+"%'",
                            tblTable, dmTableModel);
                
                } else {
                    dmTableModel = new DefaultTableModel();
                    tblTable = dataMethods.mTable(
                            "SELECT ID_Num, FName, LName, Relationship, PM_ID_Num FROM Beneficiaries WHERE FName LIKE '%"+txtSearch.getText().trim()+"%' OR LName LIKE '%"+txtSearch.getText().trim()+"%'",
                            tblTable, dmTableModel);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Enter word to search.", "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mClearSearch() {
        dmTableModel = new DefaultTableModel();
        if(strView.equals("Principals")) {
            tblTable = dataMethods.mTable("SELECT ID_Num, FName, LName, DOB, Email, Cover_ID FROM Principal_Members",
                        tblTable, dmTableModel);
        } else if(strView.equals("Beneficiaries")) {
            tblTable = dataMethods.mTable("SELECT ID_Num, FName, LName, Relationship, PM_ID_Num FROM Beneficiaries", 
                    tblTable, dmTableModel);
        }
        txtSearch.setText("");
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
        spTablePane = new javax.swing.JScrollPane();
        tblTable = new javax.swing.JTable();
        lblIcon = new javax.swing.JLabel();
        lblViewHeading = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jpMainPanel.setBackground(new java.awt.Color(204, 0, 0));

        jpContainer.setBackground(new java.awt.Color(255, 255, 255));

        tblTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        spTablePane.setViewportView(tblTable);

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ithemba/ray_of_hope/icon.jpg"))); // NOI18N

        lblViewHeading.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblViewHeading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblViewHeading.setText("View ");

        lblSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSearch.setText("Search");

        btnSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpContainerLayout = new javax.swing.GroupLayout(jpContainer);
        jpContainer.setLayout(jpContainerLayout);
        jpContainerLayout.setHorizontalGroup(
            jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spTablePane, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpContainerLayout.createSequentialGroup()
                                .addComponent(lblSearch)
                                .addGap(18, 18, 18)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnSearch))
                            .addGroup(jpContainerLayout.createSequentialGroup()
                                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(lblViewHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpContainerLayout.setVerticalGroup(
            jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpContainerLayout.createSequentialGroup()
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lblViewHeading)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addGap(18, 18, 18)
                .addComponent(spTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if(btnSearch.getText().equals("Search")) {
            mSearch();
            btnSearch.setText("Clear");
        } else {
            mClearSearch();
            btnSearch.setText("Search");
        }
    }//GEN-LAST:event_btnSearchActionPerformed

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
            java.util.logging.Logger.getLogger(frmView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmView dialog = new frmView(new frmMain(""), "");
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
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel jpContainer;
    private javax.swing.JPanel jpMainPanel;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblViewHeading;
    private javax.swing.JScrollPane spTablePane;
    private javax.swing.JTable tblTable;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
