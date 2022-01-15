package dataaccess;

import java.util.List;
import javax.persistence.*;
import models.*;

/**
 * Database access file that manages data for the users.
 * It allows calling a particular user or a list of all users
 * or inserting, updating, and deleting the users.
 * @author Eunji Elly Lee
 * @version Jan 13, 2022
 */
public class UserDB {
    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }
    }
    
    public User getByUUID(String uuid) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();        
        
        try {
            User user = em.createNamedQuery("User.findByUuid", User.class)
                    .setParameter("uuid", uuid).getSingleResult();
            return user;
        } finally {
            em.close();
        }
    }
    
    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;
        } finally {
           em.close();
        }
    }
    
    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Family family = user.getFamily();
            family.getUserList().add(user);
            
            Role role = user.getRole();
            role.getUserList().add(user);
            
            trans.begin();
            em.persist(user);
            em.merge(family);
            em.merge(role);
            trans.commit();
        } catch(Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(User beforeUser, User afterUser) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Family beforeFamily = beforeUser.getFamily();
            Family afterFamily = afterUser.getFamily();
            
            Role beforeRole = beforeUser.getRole();
            Role afterRole = afterUser.getRole();
            
            trans.begin();
            
            if(beforeFamily.getFamilyId() != afterFamily.getFamilyId()) {
                beforeFamily.getUserList().remove(beforeUser);
                afterFamily.getUserList().add(afterUser);
                
                em.merge(beforeFamily);
                em.merge(afterFamily);               
            }
            
            if(beforeRole.getRoleId() != afterRole.getRoleId()) {
                beforeRole.getUserList().remove(beforeUser);
                afterRole.getUserList().add(afterUser);
                
                em.merge(beforeRole);
                em.merge(afterRole);
            }
            
            em.merge(afterUser);
            trans.commit();
        } catch(Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            ItemDB itemDB = new ItemDB();
            List<Item> items = user.getItemList();
            
            for(int i = 0; i < items.size(); i++) {
                itemDB.delete(items.get(i));
            }
            
            Family family = user.getFamily();    
            family.getUserList().remove(user);
            
            Role role = user.getRole();
            role.getUserList().remove(user);
            
            trans.begin();
            em.remove(em.merge(user));
            em.merge(family);
            em.merge(role);
            trans.commit();
        } catch(Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}