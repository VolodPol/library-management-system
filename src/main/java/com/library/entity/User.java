package com.library.entity;

import java.util.Objects;

public class User extends Entity {
    private int id;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String surname;
    private String phoneNumber;
    private int age;
    private int fineAmount;
    private byte status;
    private Role role;
    private Subscription subscription;

    public User() {

    }

    public User(String login, String password, String email, String firstName,
                String surname, String phoneNumber, int age, int fineAmount,
                byte status, Role role, Subscription subscription) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.fineAmount = fineAmount;
        this.status = status;
        this.role = role;
        this.subscription = subscription;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int createTime) {
        this.age = createTime;
    }

    public int getFineAmount() {
        return fineAmount;
    }
    public void setFineAmount(int fineAmount) {
        this.fineAmount = fineAmount;
    }

    public byte isStatus() {
        return status;
    }
    public void setStatus(byte status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public Subscription getSubscription() {
        return subscription;
    }
    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (age != user.age) return false;
        if (fineAmount != user.fineAmount) return false;
        if (status != user.status) return false;
        if (!Objects.equals(login, user.login)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(firstName, user.firstName)) return false;
        if (!Objects.equals(surname, user.surname)) return false;
        if (!Objects.equals(phoneNumber, user.phoneNumber)) return false;
        if (role != user.role) return false;
        return subscription == user.subscription;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + fineAmount;
        result = 31 * result + (int) status;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (subscription != null ? subscription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", fineAmount=" + fineAmount +
                ", status=" + status +
                ", role=" + role +
                ", subscription=" + subscription +
                '}';
    }
}
