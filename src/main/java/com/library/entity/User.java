package com.library.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
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

    private User(UserBuilder builder) {
        this.login = builder.login;
        this.password = builder.password;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.surname = builder.surname;
        this.phoneNumber = builder.phoneNumber;
        this.age = builder.age;
        this.fineAmount = builder.fineAmount;
        this.status = builder.status;
        this.role = builder.role;
        this.subscription = builder.subscription;
    }

    public static final class UserBuilder{
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

        public UserBuilder addId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder addLogin(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder addPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder addEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder addFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder addSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder addPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder addAge(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder addFineAmount(int fineAmount) {
            this.fineAmount = fineAmount;
            return this;
        }

        public UserBuilder addStatus(byte status) {
            this.status = status;
            return this;
        }

        public UserBuilder addRole(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder addSubscription(Subscription subscription) {
            this.subscription = subscription;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
