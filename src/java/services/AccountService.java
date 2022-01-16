package services;

import dataaccess.UserDB;
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
}