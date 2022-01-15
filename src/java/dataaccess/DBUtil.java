package dataaccess;

import javax.persistence.*;

/**
 * The utility file that is commonly used for database access and
 * contains the EntityManagerFactory.
 * @author Eunji Elly Lee
 * @version Jan 13, 2022
 */
public class DBUtil {
    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("InventoryManagementPU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}