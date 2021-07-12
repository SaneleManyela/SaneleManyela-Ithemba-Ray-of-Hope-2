/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithemba.ray_of_hope;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import javax.swing.*; 

/**
 *
 * @author Siviwe
 */
public class frmRegisterAndManagePolicies extends javax.swing.JDialog {

    /**
     * Creates new form frmRegisterAndManagePolicies
     * @param frmParent
     */
    public frmRegisterAndManagePolicies(frmMain frmParent) {
        super(frmParent, "Principal Member Policy", ModalityType.APPLICATION_MODAL);
        initComponents();
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        dataMethods.mLoadToComboBox("SELECT DISTINCT CoverAmount FROM Covers ORDER BY CoverAmount ASC", cboCover);
        dataMethods.mLoadToComboBox("SELECT Premium FROM Covers ORDER BY Premium ASC", cboPremium);
        this.frmMn = frmParent;
        txtDateOfBirth.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtFirstName.requestFocusInWindow();
    }
    
    private final clsDatabaseInterface databaseInterface = new clsDatabaseInterface();
    private final clsDataMethods dataMethods = new clsDataMethods();
    private final clsValidateInput validate = new clsValidateInput();
    
    private final frmLogin frmLogin = new frmLogin();
    private final frmMain frmMn;
    
    private Long lngPrincipalMemberIDNo;
    
    public void mSetPrincipalIDNo(Long lngID) {
        this.lngPrincipalMemberIDNo = lngID;
    }
    
    public void mSetPrincipalMemberAccID(int intAccID) {
        mRegister(intAccID);
    }
    
    public void mSetPrincipalMemberDetailsToGUI(Long lngID) {
        this.lngPrincipalMemberIDNo = lngID;
        
        if(this.lngPrincipalMemberIDNo != 0L) {
            String[] arrPrincipalMemberDetails = databaseInterface.mFetchRecordDetails("SELECT FName, LName, ID_Num, DOB, Address, Tel, Email, Cover_ID "
                    + "FROM Principal_Members WHERE ID_Num ="+lngPrincipalMemberIDNo);
        
            txtFirstName.setText(arrPrincipalMemberDetails[0]);
            txtSurname.setText(arrPrincipalMemberDetails[1]);
            txtIDNo.setText(arrPrincipalMemberDetails[2]);
            txtDateOfBirth.setText(arrPrincipalMemberDetails[3]);
            txtAddress.setText(arrPrincipalMemberDetails[4]); 
            txtPhoneNo.setText(arrPrincipalMemberDetails[5]);
            txtEmail.setText(arrPrincipalMemberDetails[6]);
            cboCover.setSelectedItem(databaseInterface.mGetField("SELECT CoverAmount FROM Covers WHERE ID ="+arrPrincipalMemberDetails[7]));
            cboPremium.setSelectedItem(databaseInterface.mGetField("SELECT Premium FROM Covers WHERE ID ="+arrPrincipalMemberDetails[7]));
        }
    }
    
    private String[] mGetPolicyDetailsFromGUI() {
        return new String[] {
            txtIDNo.getText().trim(), txtFirstName.getText().trim(),
            txtSurname.getText().trim(), txtDateOfBirth.getText().trim(),
            txtAddress.getText().trim(), txtPhoneNo.getText().trim(),
            txtEmail.getText().trim(), cboCover.getSelectedItem().toString(),
            cboPremium.getSelectedItem().toString() 
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
            return "RSA ID numbers are 13 digits!!";
            
        }else if(txtAddress.getText().equals("")) {
            return "Address required!!";
            
        } else if(txtPhoneNo.getText().equals("")) {
            return "Phone Number required!!";
            
        } else if(txtPhoneNo.getText().length() != 10) {
            return "A Phone number must be 10 digits!!";
            
        } else if(txtEmail.getText().equals("")) {
            return "Email required!!";
            
        } else if(txtDateOfBirth.getText().equals("")) {
            return "Date of Birth required";   
        }
        return "";
    }
    
    private void mRegister(int intLoginID) {
        if(mVerifyUserInput().equals("")) {
            
            if(databaseInterface.mCheckDetailsExistence("SELECT * FROM Principal_Members WHERE ID_Num ="+txtIDNo.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Principal Member Policy already exists!", "WARNING", JOptionPane.WARNING_MESSAGE);
                
            } else if(validate.mCheckIfFieldIsOnlyDigits(txtIDNo.getText().trim()).equals("")
                    && validate.mCheckIfFieldIsOnlyDigits(txtPhoneNo.getText().trim()).equals("")
                    && validate.mValidateContactNumber(txtPhoneNo.getText().trim()).equals("")
                    && validate.mValidateDate(txtDateOfBirth.getText().trim()).equals("")
                    && validate.mValidateEmail(txtEmail.getText().trim()).equals("")) {
                try {
                    if(Period.between((LocalDate.parse(txtDateOfBirth.getText().trim())),
                            LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))).getYears() < 18 
                        || Period.between((LocalDate.parse(txtDateOfBirth.getText().trim())),
                                LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))).getYears() >= 65) {
            
                        JOptionPane.showMessageDialog(this, "A Principal Member is required to be 18 years or above but must be less than 65 years ",
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                    
                    } else if(databaseInterface.mCreateNewRecord("INSERT INTO Principal_Members (ID_Num, FName, LName, DOB, Address, Tel, Email, Cover_ID, Acc_ID)"
                                + "VALUES('"+mGetPolicyDetailsFromGUI()[0]+"','"+mGetPolicyDetailsFromGUI()[1]+"','"+mGetPolicyDetailsFromGUI()[2]
                                    +"','"+mGetPolicyDetailsFromGUI()[3]+"','"+mGetPolicyDetailsFromGUI()[4]+"','"+mGetPolicyDetailsFromGUI()[5]
                                        +"','"+mGetPolicyDetailsFromGUI()[6]+"','"+databaseInterface.mGetField(
                                        "SELECT ID FROM Covers WHERE CoverAmount ="+mGetPolicyDetailsFromGUI()[7]+
                                            " AND Premium ="+mGetPolicyDetailsFromGUI()[8])+"','"+intLoginID+"')")) {
                    
                        JOptionPane.showMessageDialog(this, "Principal Member policy is registered.", "MESSAGE",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                        if(JOptionPane.showConfirmDialog(this, "Register beneficiaries?", "Register Beneficiaries",
                                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        
                            new frmBeneficiaries(frmMn).setVisible(true);
                        }
                    }
                }catch(DateTimeException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                } 
            } else if(!validate.mCheckIfFieldIsOnlyDigits(txtIDNo.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, "ID Number:\n "+validate.mCheckIfFieldIsOnlyDigits(txtIDNo.getText().trim()), "WARNING", JOptionPane.WARNING_MESSAGE);
                
            }else if(!validate.mCheckIfFieldIsOnlyDigits(txtPhoneNo.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, "Phone Number:\n "+validate.mCheckIfFieldIsOnlyDigits(txtPhoneNo.getText().trim()), "WARNING", JOptionPane.WARNING_MESSAGE);
                
            } else if(!validate.mValidateContactNumber(txtPhoneNo.getText().trim()).equals("")) {
               JOptionPane.showMessageDialog(this, validate.mValidateContactNumber(txtPhoneNo.getText().trim()), "WARNING", JOptionPane.WARNING_MESSAGE);
                
            }  else if(!validate.mValidateEmail(txtEmail.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateEmail(txtEmail.getText().trim()), "WARNING", JOptionPane.WARNING_MESSAGE);
                
            }else if(!validate.mValidateDate(txtDateOfBirth.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateDate(txtDateOfBirth.getText().trim()), "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, mVerifyUserInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mRegistration(int intLoginID) {
        if(databaseInterface.mGetField("SELECT Role FROM Users_Login WHERE ID ="+frmLogin.mGetLoggedInUser()).equals("User/Client")) {
            mRegister(intLoginID);
                     
        } else {
            if(mVerifyUserInput().equals("")) {
                if(JOptionPane.showConfirmDialog(this, "Create a login account for this member first",
                    "Create Login Account", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
                    new frmLoginAccount(frmMn, this).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Provide policy details first!", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void mUpdatePrincipalMember() {
        if(databaseInterface.mGetField("SELECT Role FROM Users_Login WHERE ID ="+frmLogin.mGetLoggedInUser()).equals("Administrator")) {
            frmSelector selector = new frmSelector(frmMn, "Principal");
            selector.mRegisterAndManagePoliciesInstance(this);
            selector.setVisible(true);
            
        } else {
            if(frmLogin.mGetPrincipalIDNo() != 0L) {
                mSetPrincipalMemberDetailsToGUI(frmLogin.mGetPrincipalIDNo());
                btnUpdate.setText("Save");
            } else {
                mSetPrincipalMemberDetailsToGUI(databaseInterface.mGetIdentityNumber("SELECT ID_Num FROM Principal_Members WHERE Acc_ID ="+frmLogin.mGetLoggedInUser()));
                btnUpdate.setText("Save");
            }
        }
    }
    
    private void mSaveUpdateDetails() {
        if(mVerifyUserInput().equals("")) {
            if(validate.mCheckIfFieldIsOnlyDigits(txtIDNo.getText().trim()).equals("")
                    && validate.mCheckIfFieldIsOnlyDigits(txtPhoneNo.getText().trim()).equals("")
                    && validate.mValidateContactNumber(txtPhoneNo.getText().trim()).equals("")
                    && validate.mValidateEmail(txtEmail.getText().trim()).equals("")
                    && validate.mValidateDate(txtDateOfBirth.getText().trim()).equals("")) {
                
                if(databaseInterface.mUpdateRecordDetails("UPDATE Principal_Members SET ID_Num ="+mGetPolicyDetailsFromGUI()[0]+", FName='"+mGetPolicyDetailsFromGUI()[1]
                            +"', LName ='"+mGetPolicyDetailsFromGUI()[2]+"', DOB='"+mGetPolicyDetailsFromGUI()[3]+"', Address='"+mGetPolicyDetailsFromGUI()[4]
                            +"', Tel='"+mGetPolicyDetailsFromGUI()[5]+"', Email='"+mGetPolicyDetailsFromGUI()[6]
                            +"', Cover_ID ="+databaseInterface.mGetField("SELECT ID FROM Covers WHERE CoverAmount="+mGetPolicyDetailsFromGUI()[7]+
                                    " AND Premium ="+mGetPolicyDetailsFromGUI()[8])+" WHERE ID_Num ="+lngPrincipalMemberIDNo)) {
                    
                    JOptionPane.showMessageDialog(this, "Member Policy details updated.", "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                }
                
            } else if(!validate.mCheckIfFieldIsOnlyDigits(txtIDNo.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, "ID Number:\n "+validate.mCheckIfFieldIsOnlyDigits(txtIDNo.getText().trim()),
                        "WARNING", JOptionPane.WARNING_MESSAGE);
                
            } else if(!validate.mCheckIfFieldIsOnlyDigits(txtPhoneNo.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, "Phone Number:\n "+validate.mCheckIfFieldIsOnlyDigits(txtPhoneNo.getText().trim()),
                        "WARNING", JOptionPane.WARNING_MESSAGE);
                
            } else if(!validate.mValidateContactNumber(txtPhoneNo.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateContactNumber(txtPhoneNo.getText().trim()),
                        "WARNING", JOptionPane.WARNING_MESSAGE);
                
            } else if(!validate.mValidateEmail(txtEmail.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateEmail(txtEmail.getText().trim()),
                        "WARNING", JOptionPane.WARNING_MESSAGE);
                
            } else if(!validate.mValidateDate(txtDateOfBirth.getText().trim()).equals("")) {
                JOptionPane.showMessageDialog(this, validate.mValidateEmail(txtEmail.getText().trim()),
                        "WARNING", JOptionPane.WARNING_MESSAGE);
            }
            
        } else {
            JOptionPane.showMessageDialog(this, mVerifyUserInput(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void mDeletePrincipalPolicy() {
        if(databaseInterface.mGetField("SELECT Role FROM Users_Login WHERE ID ="+frmLogin.mGetLoggedInUser()).equals("Administrator")) {
            lngPrincipalMemberIDNo = 0L;
            frmSelector selector = new frmSelector(frmMn, "Delete Principal");
            selector.mRegisterAndManagePoliciesInstance(this);
            selector.setVisible(true); 
            
            if(databaseInterface.mCheckDetailsExistence("SELECT * FROM Beneficiaries WHERE PM_ID_Num="+lngPrincipalMemberIDNo)) {
                
                if(databaseInterface.mDeleteRecord("DELETE FROM Beneficiaries WHERE PM_ID_Num ="+lngPrincipalMemberIDNo) 
                                    && databaseInterface.mDeleteRecord("DELETE FROM Principal_Members WHERE ID_Num ="+lngPrincipalMemberIDNo)) {
                
                    JOptionPane.showMessageDialog(this, 
                            "Details relating to this Principal Member Policy have been deleted.",
                                "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                } 
            } else {
                if(lngPrincipalMemberIDNo != 0L) {
                    if(databaseInterface.mDeleteRecord("DELETE FROM Principal_Members WHERE ID_Num ="+lngPrincipalMemberIDNo)) {
                
                        JOptionPane.showMessageDialog(this, 
                                "Principal Membership Policy has been deleted.",
                                    "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            
        } else {
            if(databaseInterface.mCheckDetailsExistence("SELECT * FROM Beneficiaries WHERE PM_ID_Num="+
                    databaseInterface.mGetIdentityNumber("SELECT ID_NUM FROM Principal_Members WHERE Acc_ID="+frmLogin.mGetLoggedInUser()))) {
                
                if(databaseInterface.mDeleteRecord("DELETE FROM Beneficiaries WHERE PM_ID_Num ="+
                        databaseInterface.mGetIdentityNumber("SELECT ID_NUM FROM Principal_Members WHERE Acc_ID="+frmLogin.mGetLoggedInUser()))
                    && databaseInterface.mDeleteRecord("DELETE FROM Principal_Members WHERE ID_Num ="+
                            databaseInterface.mGetIdentityNumber("SELECT ID_NUM FROM Principal_Members WHERE Acc_ID="+frmLogin.mGetLoggedInUser()))) {
                
                    JOptionPane.showMessageDialog(this, "Policy details deleted.",
                            "MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                if(databaseInterface.mDeleteRecord("DELETE FROM Principal_Members WHERE ID_Num ="+
                        databaseInterface.mGetIdentityNumber("SELECT ID_NUM FROM Principal_Members WHERE Acc_ID="+frmLogin.mGetLoggedInUser()))) {
                
                    JOptionPane.showMessageDialog(this, 
                        "Principal Membership Policy has been deleted.",
                            "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    private void mClearGUITextFields() {
        txtFirstName.setText("");
        txtSurname.setText("");
        txtIDNo.setText("");
        txtDateOfBirth.setText("");
        txtAddress.setText(""); 
        txtPhoneNo.setText("");
        txtEmail.setText("");
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
        lblPolicies = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        lblSurname = new javax.swing.JLabel();
        txtSurname = new javax.swing.JTextField();
        lblIDNo = new javax.swing.JLabel();
        txtIDNo = new javax.swing.JTextField();
        lblDateOfBirth = new javax.swing.JLabel();
        txtDateOfBirth = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        spAddressPane = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        lblPhoneNo = new javax.swing.JLabel();
        txtPhoneNo = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblCover = new javax.swing.JLabel();
        cboCover = new javax.swing.JComboBox<>();
        lblPremium = new javax.swing.JLabel();
        cboPremium = new javax.swing.JComboBox<>();
        btnCreate = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jpMainPanel.setBackground(new java.awt.Color(204, 0, 0));

        jpContainer.setBackground(new java.awt.Color(255, 255, 255));

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ithemba/ray_of_hope/icon.jpg"))); // NOI18N

        lblPolicies.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPolicies.setText("Policies");

        lblFirstName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFirstName.setText("First Name");

        lblSurname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSurname.setText("Surname");

        lblIDNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblIDNo.setText("ID No.");

        lblDateOfBirth.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDateOfBirth.setText("Date of Birth");

        lblAddress.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAddress.setText("Address");

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        spAddressPane.setViewportView(txtAddress);

        lblPhoneNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPhoneNo.setText("Phone No.");

        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEmail.setText("Email");

        lblCover.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCover.setText("Cover");

        lblPremium.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPremium.setText("Premium");

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
        btnUpdate.setMaximumSize(new java.awt.Dimension(83, 23));
        btnUpdate.setMinimumSize(new java.awt.Dimension(83, 23));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setMaximumSize(new java.awt.Dimension(83, 23));
        btnDelete.setMinimumSize(new java.awt.Dimension(83, 23));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(255, 255, 255));
        btnClear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnClear.setText("Clear");
        btnClear.setMaximumSize(new java.awt.Dimension(83, 23));
        btnClear.setMinimumSize(new java.awt.Dimension(83, 23));
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
                .addGap(20, 20, 20)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblSurname)
                        .addComponent(lblFirstName)
                        .addComponent(lblIDNo)
                        .addComponent(lblDateOfBirth)
                        .addComponent(lblAddress)
                        .addComponent(lblPhoneNo)
                        .addComponent(lblCover)
                        .addComponent(lblEmail)
                        .addComponent(lblPremium)
                        .addComponent(btnCreate)))
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(lblPolicies)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpContainerLayout.createSequentialGroup()
                        .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpContainerLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpContainerLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(spAddressPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(txtFirstName)
                                    .addComponent(txtSurname)
                                    .addComponent(txtIDNo)
                                    .addComponent(txtDateOfBirth)
                                    .addComponent(txtPhoneNo)
                                    .addComponent(txtEmail)
                                    .addComponent(cboCover, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboPremium, 0, 140, Short.MAX_VALUE))))
                        .addGap(20, 20, 20))))
        );
        jpContainerLayout.setVerticalGroup(
            jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpContainerLayout.createSequentialGroup()
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(lblFirstName))
                    .addGroup(jpContainerLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(lblPolicies)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSurname)
                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIDNo)
                    .addComponent(txtIDNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateOfBirth)
                    .addComponent(txtDateOfBirth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAddress)
                    .addComponent(spAddressPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPhoneNo)
                    .addComponent(txtPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail))
                .addGap(18, 18, 18)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCover)
                    .addComponent(cboCover, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPremium)
                    .addComponent(cboPremium, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
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
        mRegistration(frmLogin.mGetLoggedInUser());
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if(btnUpdate.getText().equals("Update")){
            mUpdatePrincipalMember();
            btnUpdate.setText("Save");
            
        } else if(btnUpdate.getText().equals("Save")) {
            mSaveUpdateDetails();
            btnUpdate.setText("Update");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        mDeletePrincipalPolicy();
    }//GEN-LAST:event_btnDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(frmRegisterAndManagePolicies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmRegisterAndManagePolicies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmRegisterAndManagePolicies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmRegisterAndManagePolicies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmRegisterAndManagePolicies dialog = new frmRegisterAndManagePolicies(new frmMain(""));
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
    private javax.swing.JComboBox<String> cboCover;
    private javax.swing.JComboBox<String> cboPremium;
    private javax.swing.JPanel jpContainer;
    private javax.swing.JPanel jpMainPanel;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblCover;
    private javax.swing.JLabel lblDateOfBirth;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblIDNo;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblPhoneNo;
    private javax.swing.JLabel lblPolicies;
    private javax.swing.JLabel lblPremium;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JScrollPane spAddressPane;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtDateOfBirth;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtIDNo;
    private javax.swing.JTextField txtPhoneNo;
    private javax.swing.JTextField txtSurname;
    // End of variables declaration//GEN-END:variables
}
