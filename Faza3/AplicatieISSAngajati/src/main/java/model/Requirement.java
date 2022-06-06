package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Requirements", schema = "public")
public class Requirement implements Identifiable<Integer>{

    private Integer ID;
    private String titlu;
    private String descriere;
    private Weight weight;
    private Integer employeeID;
    private RequirementStatus status;

    public Requirement(String titlu, String descriere, Weight weight, Integer employeeID) {
        this.titlu = titlu;
        this.descriere = descriere;
        this.weight = weight;
        this.employeeID = employeeID;
        this.status = RequirementStatus.ACTIVE;
    }

    public Requirement(){};

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Override
    public Integer getID() {
        return null;
    }

    @Override
    public void setID(Integer id) {
        this.ID = id;
    }

    @Column(name = "titlu")
    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    @Column(name = "descriere")
    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Column(name = "weight")
    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    @Column(name = "employeeID")
    public Integer getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
    }

    @Column(name = "status")
    public RequirementStatus getStatus() {
        return status;
    }

    public void setStatus(RequirementStatus status) {
        this.status = status;
    }
}
