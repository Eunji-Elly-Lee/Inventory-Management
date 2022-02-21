package services;

import dataaccess.*;
import java.util.List;
import java.util.logging.*;
import models.*;

/**
 * The java file of a service layer for managing all activities related to an inventory.
 * @author Eunji Elly Lee
 * @version Feb 21, 2022
 */
public class InventoryService {
    public Item getItem(int itemID) throws Exception {
        ItemDB itemDB = new ItemDB();
        Item item = itemDB.get(itemID);
        
        return item;
    }
    
    public Category getCategory(int categoryID) throws Exception {
        CategoryDB categoryDB = new CategoryDB();
        Category category = categoryDB.get(categoryID);  
        
        return category;
    }
    
    public List<Category> getAllCategories() throws Exception {
        CategoryDB categoryDB = new CategoryDB();
        List<Category> categories = categoryDB.getAll();
        
        return categories;
    }
    
    public void insertItem(String email, int categoryID, String itemName, double price)
            throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        
        CategoryDB categoryDB = new CategoryDB();
        Category category = categoryDB.get(categoryID);  
        
        ItemDB itemDB = new ItemDB();              
        Item item = new Item(0, itemName, price);        
        item.setOwner(user);
        item.setCategory(category);
        
        itemDB.insert(item);
    }
    
    public void updateItem(int itemID, int categoryID, String itemName, double price)
            throws Exception {
        CategoryDB categoryDB = new CategoryDB();
        Category category = categoryDB.get(categoryID);  
        
        ItemDB itemDB = new ItemDB();
        Item beforeItem = itemDB.get(itemID);
        Item afterItem = itemDB.get(itemID);
        afterItem.setItemName(itemName);
        afterItem.setPrice(price);
        afterItem.setCategory(category);
        
        itemDB.update(beforeItem, afterItem);
    }
    
    public void deleteItem(String email, int itemID) throws Exception {
        ItemDB itemDB = new ItemDB();
        Item item = itemDB.get(itemID);
        
        if(!item.getOwner().getEmail().equals(email)) {
            Logger.getLogger(InventoryService.class.getName()).log(Level.SEVERE, null,
                    "users cannot delete inventory items they do not own");
        } else {
            itemDB.delete(item);
        }
    }
    
    public void insertCategory(String categoryName) throws Exception {
        CategoryDB categoryDB = new CategoryDB();
        Category category = new Category(0, categoryName);
        
        categoryDB.insert(category);
    }
    
    public void updateCategory(int categoryID, String categoryName) throws Exception {
        CategoryDB categoryDB = new CategoryDB();
        Category category = categoryDB.get(categoryID);  
        category.setCategoryName(categoryName);
        
        categoryDB.update(category);
    }
}