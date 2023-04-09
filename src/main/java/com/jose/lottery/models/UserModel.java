package com.jose.lottery.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author jose
 */
@Entity
@Table(name = "TB_User")
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true, length = 70)
    private String name;
    @Column(nullable = false, unique = true, length = 8)
    private String identificationDocumentNumber;
    @Column(nullable = false, unique = true, length = 9)
    private String taxIdentificationNumber;
    @Column(nullable = false, length = 70)
    private String address;
    @Column(nullable = false, length = 8)
    private String postalCode;
    @Column(nullable = false, unique = true, length = 70)
    private String email;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BallotModel> ballots = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificationDocumentNumber() {
        return identificationDocumentNumber;
    }

    public void setIdentificationDocumentNumber(String identificationDocumentNumber) {
        this.identificationDocumentNumber = identificationDocumentNumber;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<BallotModel> getBallots() {
        return ballots;
    }

    public void setBallots(Set<BallotModel> ballots) {
        this.ballots = ballots;
    }

}
