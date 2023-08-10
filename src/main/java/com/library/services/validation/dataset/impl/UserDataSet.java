package com.library.services.validation.dataset.impl;

import com.library.services.validation.dataset.DataSet;
import com.library.utils.UtilProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The class contains user data to validate
 */
@AllArgsConstructor
@Getter
@Setter
public class UserDataSet implements DataSet {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String surname;
    private String phone;
    private String age;

    @Override
    public boolean isBlank() {
        return UtilProvider.isEmpty(username) && UtilProvider.isEmpty(email) && UtilProvider.isEmpty(firstName)
                && UtilProvider.isEmpty(surname) && UtilProvider.isEmpty(phone) && UtilProvider.isEmpty(age);
    }
}
