/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Karina
 */
@Entity
@Table(name = "PERSONALTRAINERMODEL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "personalTrainer.findAll", query = "SELECT p FROM personalTrainer p")
    , @NamedQuery(name = "personalTrainer.findById", query = "SELECT p FROM personalTrainer p WHERE p.id = :id")
    , @NamedQuery(name = "personalTrainer.findByFirstname", query = "SELECT p FROM personalTrainer p WHERE p.firstname = :firstname")
    , @NamedQuery(name = "personalTrainer.findByLastname", query = "SELECT p FROM personalTrainer p WHERE p.lastname = :lastname")
    , @NamedQuery(name = "personalTrainer.findByCredentials", query = "SELECT p FROM personalTrainer p WHERE p.credentials = :credentials")
    , @NamedQuery(name = "personalTrainer.findByNameAndID", query = "SELECT p FROM personalTrainer p WHERE p.firstname = :firstname and p.id = :id")
    , @NamedQuery(name = "personalTrainer.findByNameAndLastName", query = "SELECT p FROM personalTrainer p WHERE p.firstname = :firstname and p.lastname = :lastname")
    , @NamedQuery(name = "personalTrainer.findByFirstNameAdvanced", query = "SELECT p FROM personalTrainer p WHERE  LOWER(p.firstname) LIKE  CONCAT('%', LOWER(:firstname), '%')")

})
    
public class personalTrainer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "LASTNAME")
    private String lastname;
    @Column(name = "CREDENTIALS")
    private String credentials;

    public personalTrainer() {
    }

    public personalTrainer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof personalTrainer)) {
            return false;
        }
        personalTrainer other = (personalTrainer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.personalTrainer[ id=" + id + " ]";
    }
    
}
