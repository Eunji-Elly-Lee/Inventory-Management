package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import models.*;
import services.AccountService;

/**
 * The Servlet for the admin page.
 * @author Eunji Elly Lee
 * @version Feb 4, 2022
 */
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("uuid") != null) {
            request.setAttribute("uuid", request.getParameter("uuid")); 
            getServletContext().getRequestDispatcher("/WEB-INF/activation.jsp").forward(request,response);
            return;
        }
        
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        request.setAttribute("isNotEditing", true);
        displayPage(request, email);
        
        try {
            AccountService accountService = new AccountService();
            User user = accountService.getUser(email);
            
            if(user.getRole().getRoleId() == 1) {                
                getServletContext().getRequestDispatcher("/WEB-INF/sysAdmin.jsp").forward(request,response);
            } else if(user.getRole().getRoleId() == 3) {
                getServletContext().getRequestDispatcher("/WEB-INF/comAdmin.jsp").forward(request,response);
            }
        } catch(Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        AccountService accountService = new AccountService();
        String email = (String) session.getAttribute("email");
        String action = request.getParameter("action");
                
        try {
            switch(action) {
                case "addUser":
                    String newEmail = request.getParameter("newEmail");
                    String newPassword = request.getParameter("newPassword");
                    String newFirstName = request.getParameter("newFirstName");
                    String newLastName = request.getParameter("newLastName");
                    int newFamilyID = Integer.parseInt(request.getParameter("newFamily"));
                    int newRoleID = Integer.parseInt(request.getParameter("newRole"));
                    request.setAttribute("newEmail", newEmail);
                    request.setAttribute("newPassword", newPassword);  
                    request.setAttribute("newFirstName", newFirstName);        
                    request.setAttribute("newLastName", newLastName);
                    request.setAttribute("newFamily", newFamilyID);        
                    request.setAttribute("newRole", newRoleID);
                    
                    if(newEmail == null || newEmail.equals("") || newPassword == null ||
                            newPassword.equals("") || newFirstName == null || newFirstName.equals("") ||
                            newLastName == null || newLastName.equals("") || newFamilyID == 0 ||
                            newRoleID == 0) {
                        request.setAttribute("invalidInput", true);
                    } else {
                        List<User> users = accountService.getAllUsers();
                        boolean foundEmail = false;
                        
                        for(int i = 0; i < users.size(); i++) {
                            if(users.get(i).getEmail().equals(newEmail)) {
                                foundEmail = true;
                                break;
                            }
                        }
                        
                        if(foundEmail) {
                            request.setAttribute("failedAdd", true);
                        } else {
                            
                            String path = getServletContext().getRealPath("/WEB-INF");
                            String url = request.getRequestURL().toString();
                            accountService.registerAccount(newEmail, newFirstName, newLastName, newPassword,
                                    newFamilyID, newRoleID, path, url);
                                
                            request.setAttribute("newEmail", "");
                            request.setAttribute("newPassword", "");  
                            request.setAttribute("newFirstName", "");        
                            request.setAttribute("newLastName", "");
                            request.setAttribute("newFamilyID", 0);        
                            request.setAttribute("newRoleID", 0);                           
                            request.setAttribute("newUserName", newFirstName);
                            request.setAttribute("addedUser", true);
                        }                        
                    }
                    
                    request.setAttribute("isNotEditing", true);
                    break;                
                case "editUser":    
                    String selectedUserEmail = request.getParameter("selectedUser");
                    User selectedUser = accountService.getUser(selectedUserEmail);
                    request.setAttribute("editUserEmail", selectedUser.getEmail());
                    request.setAttribute("editEmail", selectedUser.getEmail());
                    request.setAttribute("editPassword", selectedUser.getPassword());        
                    request.setAttribute("editFirstName", selectedUser.getFirstName());
                    request.setAttribute("editLastName", selectedUser.getLastName());
                    request.setAttribute("editFamily", selectedUser.getFamily().getFamilyId());
                    request.setAttribute("editRole", selectedUser.getRole().getRoleId());
                    request.setAttribute("editUserName", selectedUser.getFirstName());
                    
                    if(selectedUser.getActive()) {
                        request.setAttribute("isActive", true);
                    }
                    
                    request.setAttribute("calledUser", true);
                    request.setAttribute("isNotEditing", false);      
                    break;
                case "updateUser":
                    String editEmail = request.getParameter("editUserEmail");
                    String editPassword = request.getParameter("editPassword");
                    String editFirstName = request.getParameter("editFirstName");
                    String editLastName = request.getParameter("editLastName");
                    int editFamilyID = Integer.parseInt(request.getParameter("editFamily"));
                    int editRoleID = Integer.parseInt(request.getParameter("editRole"));
                    String active = request.getParameter("isActive");
                    boolean isActive = false;
        
                    if(active != null) {
                        isActive = true;
                        request.setAttribute("isActive", true);
                    } else {
                        request.setAttribute("isActive", false);
                    }
                    
                    request.setAttribute("editUserEmail", editEmail);
                    request.setAttribute("editEmail", editEmail);
                    request.setAttribute("editPassword", editPassword);  
                    request.setAttribute("editFirstName", editFirstName);        
                    request.setAttribute("editLastName", editLastName);
                    request.setAttribute("editFamily", editFamilyID);        
                    request.setAttribute("editRole", editRoleID);
                    
                    if(editPassword == null || editPassword.equals("") || editFirstName == null ||
                            editFirstName.equals("") || editLastName == null || editLastName.equals("")) {
                        request.setAttribute("invalidInput", true);   
                        request.setAttribute("isNotEditing", false);
                    } else {
                        accountService.updateUser(editEmail, isActive, editFirstName, editLastName, editPassword,
                                editFamilyID, editRoleID);
                        
                        request.setAttribute("editEmail", "");
                        request.setAttribute("editPassword", "");  
                        request.setAttribute("editFirstName", "");        
                        request.setAttribute("editLastName", "");
                        request.setAttribute("editFamily", 1);        
                        request.setAttribute("editRole", 1);
                        request.setAttribute("updateUserName", editFirstName);                         
                        request.setAttribute("updatedUser", true);
                        request.setAttribute("isNotEditing", true);
                        request.setAttribute("isActive", false);
                    }
                  
                    break;
                case "deleteUser":
                    String pointedUserEmail = request.getParameter("pointedUser");
                    User deletedUser = accountService.getUser(pointedUserEmail);
                    accountService.deleteUser(pointedUserEmail);
                    
                    session.setAttribute("temporaryUser", deletedUser);
                    request.setAttribute("deleteUserName", deletedUser.getFirstName());
                    request.setAttribute("userForUndo", deletedUser);
                    request.setAttribute("deletedUser", true);
                    request.setAttribute("isNotEditing", true);    
                    break;
                case "undo":                    
                    User temporaryUser = (User) session.getAttribute("temporaryUser");
                    accountService.insertUser(temporaryUser.getEmail(), temporaryUser.getFirstName(),
                            temporaryUser.getLastName(), temporaryUser.getPassword(),
                            temporaryUser.getFamily().getFamilyId(), temporaryUser.getRole().getRoleId(), null);
                    
                    session.setAttribute("temporaryUser", null);
                    request.setAttribute("undoUserName", temporaryUser.getFirstName());
                    request.setAttribute("undoDeleting", true);
                    request.setAttribute("isNotEditing", true);
                    break;
            }
        } catch(Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        displayPage(request, email);
        
        try {
            User user = accountService.getUser(email);
            
            if(user.getRole().getRoleId() == 1) {                
                getServletContext().getRequestDispatcher("/WEB-INF/sysAdmin.jsp").forward(request,response);
            } else if(user.getRole().getRoleId() == 3) {
                getServletContext().getRequestDispatcher("/WEB-INF/comAdmin.jsp").forward(request,response);
            }
        } catch(Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void displayPage(HttpServletRequest request, String email)
            throws ServletException, IOException {
        AccountService accountService = new AccountService();
        
        try {
            User admin = accountService.getUser(email);
            List<User> users = null;
            List<Family> families = accountService.getAllFamilies();
            Family family = accountService.getFamily(admin.getFamily().getFamilyId());
            List<Role> roles = accountService.getAllRoles();
            
            if(admin.getRole().getRoleId() == 1) {                
                users = accountService.getAllUsers();
            } else if(admin.getRole().getRoleId() == 3) {
                users = accountService.getFamily(admin.getFamily().getFamilyId()).getUserList();
            }           
            
            request.setAttribute("admin", admin);
            request.setAttribute("users", users);
            request.setAttribute("families", families);
            request.setAttribute("family", family);
            request.setAttribute("roles", roles);
        } catch(Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}