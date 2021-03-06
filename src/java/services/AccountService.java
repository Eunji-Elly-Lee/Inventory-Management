package services;

import dataaccess.*;
import java.util.*;
import models.*;

/**
 * The java file of a service layer for managing all activities related to an account.
 * @author Eunji Elly Lee
 * @version June 3, 2022
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
    
    public List<User> getAllUsers() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        
        return users;
    }
    
    public Family getFamily(int familyID) throws Exception {
        FamilyDB familyDB = new FamilyDB();
        Family family = familyDB.get(familyID);
        
        return family;
    }
    
    public List<Family> getAllFamilies() throws Exception {
        FamilyDB familyDB = new FamilyDB();
        List<Family> families = familyDB.getAll();
        
        return families;
    }
    
    public Role getRole(int roleID) throws Exception {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(roleID);
        
        return role;
    }
    
    public List<Role> getAllRoles() throws Exception {
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        
        return roles;
    }
    
    public void insertUser(String email, String firstName, String lastName,
            String password, int familyID, int roleID, String uuid) throws Exception {
        FamilyDB familyDB = new FamilyDB();
        Family family = familyDB.get(familyID);
        
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(roleID);
        
        UserDB userDB = new UserDB();
        User user = new User(email, false, firstName, lastName, password);
        
        user.setFamily(family);
        user.setRole(role);
        user.setUuid(uuid);
        
        userDB.insert(user);
    }
    
    public void updateUser(String email, boolean active, String firstName, String lastName,
            String password, int familyID, int roleID) throws Exception {
        FamilyDB familyDB = new FamilyDB();
        Family family = familyDB.get(familyID);
        
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(roleID); 
        
        UserDB userDB = new UserDB();
        User beforeUser = userDB.get(email);
        User afterUser = userDB.get(email);
        
        afterUser.setActive(active);
        afterUser.setFirstName(firstName);
        afterUser.setLastName(lastName);
        afterUser.setPassword(password);
        afterUser.setFamily(family);
        afterUser.setRole(role);
        
        userDB.update(beforeUser, afterUser);
    }
    
    public void deleteUser(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        
        userDB.delete(user);
    }
    
    public void insertFamily(String familyName) throws Exception {
        FamilyDB familyDB = new FamilyDB();
        Family family = new Family(0, familyName);
        
        familyDB.insert(family);
    }
    
    public void updateFamily(int familyID, String familyName) throws Exception {
        FamilyDB familyDB = new FamilyDB();
        Family family = familyDB.get(familyID);
        family.setFamilyName(familyName);
        
        familyDB.update(family);
    }
    
    public void registerAccount(String email, String firstName, String lastName,
            String password, int familyID, int roleID, String path, String url) {
        try {
            String uuid = UUID.randomUUID().toString();
            String link = url + "?uuid=" + uuid;        
            String to = email;
            String subject = "Home Inventory Account Activation";
            String template = path + "/emailTemplates/registerAccount.html";
               
            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", firstName);
            tags.put("lastname", lastName);
            tags.put("link", link);
            
            insertUser(email, firstName, lastName, password, familyID, roleID, uuid);
            JavaMailService.sendMail(to, subject, template, tags);
        } catch(Exception e) {}
    }
    
    public boolean activateAccount(String uuid,String email, String password,
            String path, String url) {
        UserDB userDB = new UserDB();
        
        try {
            User beforeUser = userDB.getByUUID(uuid);
            
            if(password.equals(beforeUser.getPassword())) {
                User afterUser = userDB.getByUUID(uuid);
                afterUser.setActive(true);
                afterUser.setUuid(null);
                userDB.update(beforeUser, afterUser); 
                
                String to = email;
                String subject = "Welcome to Home Inventory";
                String template = path + "/emailTemplates/welcome.html";
                String link = url.substring(0, url.lastIndexOf("/"));
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", afterUser.getFirstName());
                tags.put("lastname", afterUser.getLastName());
                tags.put("link", link);

                JavaMailService.sendMail(to, subject, template, tags);
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        }
    }
    
    public void resetPassword(String email, String path, String url) {
        UserDB userDB = new UserDB();
        
        try {
            User beforeUser = userDB.get(email);
            User afterUser = userDB.get(email);
            
            String uuid = UUID.randomUUID().toString();
            String link = url + "?uuid=" + uuid;        
            String to = beforeUser.getEmail();
            String subject = "Home Inventory Password";
            String template = path + "/emailTemplates/resetPassword.html";
               
            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", beforeUser.getFirstName());
            tags.put("lastname", beforeUser.getLastName());
            tags.put("link", link);
            
            afterUser.setUuid(uuid);
            userDB.update(beforeUser, afterUser);
            
            JavaMailService.sendMail(to, subject, template, tags);
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