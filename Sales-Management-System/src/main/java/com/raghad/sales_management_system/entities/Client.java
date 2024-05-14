package com.raghad.sales_management_system.entities;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Email;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "The first name must be non-blank")
    private String firstName;
    @NotBlank(message = "The last name must be non-blank")
    private String lastName;
    @Length(min = 10, max = 10, message = "The mobile number must have 10 digits")
    private String mobileNumber;
    @Email(message = "The Email address must be valid")
    private String emailAddress;
    private String address;

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Client))
            return false;

        var anotherClient = (Client) o;
        return anotherClient.id == this.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getAddress() {
        return this.address;
    }
}
