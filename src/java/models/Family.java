package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.*;

/**
 * The entity file of the family table to which an user belongs.
 * @author Eunji Elly Lee
 * @version Jan 13, 2022
 */
@Entity
@Table(name = "family")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Family.findAll", query = "SELECT f FROM Family f")
    , @NamedQuery(name = "Family.findByFamilyId", query = "SELECT f FROM Family f WHERE f.familyId = :familyId")
    , @NamedQuery(name = "Family.findByFamilyName", query = "SELECT f FROM Family f WHERE f.familyName = :familyName")})
public class Family implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "family_id")
    private Integer familyId;
    @Basic(optional = false)
    @Column(name = "family_name")
    private String familyName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "family", fetch = FetchType.EAGER)
    private List<User> userList;

    public Family() {
    }

    public Family(Integer familyId) {
        this.familyId = familyId;
    }

    public Family(Integer familyId, String familyName) {
        this.familyId = familyId;
        this.familyName = familyName;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (familyId != null ? familyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Family)) {
            return false;
        }
        Family other = (Family) object;
        if ((this.familyId == null && other.familyId != null) || (this.familyId != null && !this.familyId.equals(other.familyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Family[ familyId=" + familyId + " ]";
    }
    
}