package com.example.BackendShop.dto;

import com.example.BackendShop.exception.InternalError;

public class CredentialDTO {
    private String email;
    private String password;

    public CredentialDTO() {

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

    public void check() throws InternalError {
        if (this.email==null) {
            throw new InternalError(1,"EmailNull");
        }
        if (this.password==null) {
            throw new InternalError(1,"PasswordNull");
        }
    }
}
