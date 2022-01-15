package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Role;

/**
 * Database access file that manages data for the roles.
 * It allows calling a particular role or a list of all roles.
 * @author Eunji Elly Lee
 * @version Jan 13, 2022
 */
public class RoleDB {
    public Role get(int roleID) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Role role = em.find(Role.class, roleID);
            return role;
        } finally {
            em.close();
        }
    }
    
    public List<Role> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Role> roles =
                    em.createNamedQuery("Role.findAll", Role.class).getResultList();
            return roles;
        } finally {
           em.close();
        }
    }
}