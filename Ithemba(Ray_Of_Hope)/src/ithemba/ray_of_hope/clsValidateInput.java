/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ithemba.ray_of_hope;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Siviwe
 */
public class clsValidateInput {
    
    public String mValidateEmail(String strEmail) {
        Pattern patternEmail = Pattern.compile("^(.+)@(.+)$");
        Matcher matchEmail = patternEmail.matcher(strEmail);
        if(!matchEmail.matches()) {
            return "Invalid email address!!";
        }
        return "";
    }
    
    public String mCheckIfFieldIsOnlyDigits(String strContactNumber) {
        for (char c : strContactNumber.toCharArray()) {
            if (c != '.' && !Character.isDigit(c)) {
                return "Non-digits found!! Provide digits only.";
            }
        }
        return "";
    }
    
    public String mValidateContactNumber(String strContactNumber) {
        Pattern pattern = Pattern.compile("(0)?[0-9]{9}");
        Matcher matcher = pattern.matcher(strContactNumber);
               
        if(!matcher.matches()) {
            return "Invalid contact number!!"; 
        }
        return "";
    }
    
    public String mValidateDate(String strDate) {
        Pattern pattern = Pattern.compile("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$");
        Matcher matcher = pattern.matcher(strDate);
        
        if(!matcher.matches()) {
            return "Invalid date format!!";
        }
        return "";
    }
}
