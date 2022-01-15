package dataaccess;

import java.util.List;
import javax.persistence.*;
import models.*;

/**
 * Database access file that manages data for the families.
 * It allows calling a particular family or a list of all families
 * or inserting, updating, and deleting the families.
 * @author Eunji Elly Lee
 * @version Jan 13, 2022
 */
public class FamilyDB {
    public Family get(int companyID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Family company = em.find(Family.class, companyID);
            return company;
        } finally {
            em.close();
        }
    }
    
    public List<Family> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Family> companies =
                    em.createNamedQuery("Family.findAll", Family.class).getResultList();
            return companies;
        } finally {
           em.close();
        }
    }
    
    public void insert(Family family) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(family);
            trans.commit();
        } catch(Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(Family family) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(family);
            trans.commit();
        } catch(Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Family family) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            UserDB userDB = new UserDB();
            List<User> users = family.getUserList();
            
            for(int i = 0; i < users.size(); i++) {
                userDB.delete(users.get(i));
            }
            
            trans.begin();
            em.remove(em.merge(family));
            trans.commit();
        } catch(Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }  
}