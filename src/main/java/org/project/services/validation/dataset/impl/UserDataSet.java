package org.project.services.validation.dataset.impl;

import org.project.services.validation.dataset.DataSet;
import static org.project.utils.UtilProvider.isEmpty;

public class UserDataSet implements DataSet {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String surname;
    private String phone;
    private String age;

    public UserDataSet(String username, String email, String password, String firstName, String surname, String phone, String age) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.surname = surname;
        this.phone = phone;
        this.age = age;
    }

    @Override
    public boolean isBlank() {
        return isEmpty(username) && isEmpty(email) && isEmpty(firstName)
                && isEmpty(surname) && isEmpty(phone) && isEmpty(age);
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }
    @SuppressWarnings("unused")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }
    @SuppressWarnings("unused")
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
}
