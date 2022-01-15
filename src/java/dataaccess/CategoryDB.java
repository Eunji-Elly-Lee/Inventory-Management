package dataaccess;

import java.util.List;
import javax.persistence.*;
import models.*;

/**
 * Database access file that manages data for the categories.
 * It allows calling a particular category or a list of all categories
 * or inserting, updating, and deleting the categories.
 * @author Eunji Elly Lee
 * @version Jan 13, 2022
 */
public class CategoryDB {
    public Category get(int categoryID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Category category = em.find(Category.class, categoryID);
            return category;
        } finally {
            em.close();
        }
    }
    
    public List<Category> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Category> categories =
                    em.createNamedQuery("Category.findAll", Category.class).getResultList();
            return categories;
        } finally {
           em.close();
        }
    }
    
    public void insert(Category category) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(category);
            trans.commit();
        } catch(Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(Category category) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(category);
            trans.commit();
        } catch(Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Category category) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            ItemDB itemDB = new ItemDB();
            List<Item> items = category.getItemList();
            
            for(int i = 0; i < items.size(); i++) {
                itemDB.delete(items.get(i));
            }
            
            trans.begin();
            em.remove(em.merge(category));
            trans.commit();
        } catch(Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }  
}