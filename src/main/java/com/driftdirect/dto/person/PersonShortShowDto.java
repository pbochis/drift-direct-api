package com.driftdirect.dto.person;

import com.driftdirect.dto.person.driver.DriverDetailsShortShowDto;
import org.joda.time.DateTime;

/**
 * Created by Paul on 11/23/2015.
 */
// will be used in lists
public class PersonShortShowDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String nick;
    private DateTime birthDate;
    private String description;
    private Long profilePicture;

    private DriverDetailsShortShowDto driverDetails;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Long profilePicture) {
        this.profilePicture = profilePicture;
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public DriverDetailsShortShowDto getDriverDetails() {
        return driverDetails;
    }

    public void setDriverDetails(DriverDetailsShortShowDto driverDetails) {
        this.driverDetails = driverDetails;
    }
}
