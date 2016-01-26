package com.driftdirect.domain.person;

import com.driftdirect.domain.Country;
import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.driver.DriverDetails;
import com.driftdirect.domain.file.File;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Paul on 11/20/2015.
 */
@Entity
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String nick;
    private String telephone;

    @Column(name = "birth_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime birthDate;

    private String website;
    @ManyToOne
    private Country country;

    @Column(name = "career_start_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime careerStartDate;

    @Lob
    private String description;

    @Lob
    private String portfolio;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    @OneToOne
    private File profilePicture;

    //null when the person.personType != PersonType.Driver
    @OneToOne(optional = true)
    private DriverDetails driverDetails;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "organizer")
    private List<Championship> championships;

    public DriverDetails getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(DriverDetails driverDetails) {
        this.driverDetails = driverDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public DateTime getCareerStartDate() {
        return careerStartDate;
    }

    public void setCareerStartDate(DateTime careerStartDate) {
        this.careerStartDate = careerStartDate;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PersonType getPersonType() {
        return personType;
    }

    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    public File getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(File profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public List<Championship> getChampionships() {
        return championships;
    }

    public void setChampionships(List<Championship> championships) {
        this.championships = championships;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Person)){
            return false;
        }
        return this.id.equals(((Person)(obj)).getId());
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

