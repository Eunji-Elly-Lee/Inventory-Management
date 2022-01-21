package services;

import dataaccess.UserDB;
import java.util.*;
import models.User;

/**
 * The java file of a service layer for managing all activities
 * related to an account.
 * @author Eunji Elly Lee
 * @version Jan 15, 2022
 */
public class AccountService {
    public User login(String email, String password) {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
            
            if(password.equals(user.getPassword())) {
                return user;
            }
        } catch(Exception e) {}
        
        return null;
    }
    
    public User getUser(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        
        return user;
    }
    
    public void resetPassword(String email, String path, String url) {
        UserDB userDB = new UserDB();
        
        try {
            User beforeUser = userDB.get(email);
            User afterUser = userDB.get(email);
            
            String uuid = UUID.randomUUID().toString();
            String link = url + "?uuid=" + uuid;        
            String to = beforeUser.getEmail();
            String subject = "Home nVentory Password";
            String template = path + "/emailTemplates/resetPassword.html";
               
            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", beforeUser.getFirstName());
            tags.put("lastname", beforeUser.getLastName());
            tags.put("link", link);
            
            afterUser.setUuid(uuid);
            userDB.update(beforeUser, afterUser);
            
            GmailService.sendMail(to, subject, template, tags);
        } catch(Exception e) {}
    }
    
    public boolean changePassword(String uuid, String password) {
        UserDB userDB = new UserDB();
        
        try {
            User beforeUser = userDB.getByUUID(uuid);
            User afterUser = userDB.getByUUID(uuid);
            afterUser.setPassword(password);
            afterUser.setUuid(null);
            userDB.update(beforeUser, afterUser);            
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}