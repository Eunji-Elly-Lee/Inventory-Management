package dataaccess;

import java.util.List;
import javax.persistence.*;
import models.*;

/**
 * Database access file that manages data for the items.
 * It allows calling a particular item or a list of all items
 * or inserting, updating, and deleting the items.
 * @author Eunji Elly Lee
 * @version Jan 13, 2022
 */
public class ItemDB {
    public Item get(int itemID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Item item = em.find(Item.class, itemID);
            return item;
        } finally {
            em.close();
        }
    }
    
    public List<Item> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Item> items = em.createNamedQuery("Item.findAll", Item.class).getResultList();
            return items;
        } finally {
           em.close();
        }
    }
    
    public void insert(Item item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            User user = item.getOwner();            
            user.getItemList().add(item);
            
            Category category = item.getCategory();
            category.getItemList().add(item);
            
            trans.begin();
            em.persist(item);
            em.merge(user);
            em.merge(category);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(Item beforeItem, Item afterItem) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Category beforeCategory = beforeItem.getCategory();
            Category afterCategory = afterItem.getCategory();
            
            trans.begin();
            
            if(beforeCategory.getCategoryId() != afterCategory.getCategoryId()) {
                beforeCategory.getItemList().remove(beforeItem);
                afterCategory.getItemList().add(afterItem);
                
                em.merge(beforeCategory);
                em.merge(afterCategory);
            }
            
            em.merge(afterItem);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Item item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            User user = item.getOwner();
            user.getItemList().remove(item);
            
            Category category = item.getCategory();
            category.getItemList().remove(item);
            
            trans.begin();
            em.remove(em.merge(item));
            em.merge(user);
            em.merge(category);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }  
}