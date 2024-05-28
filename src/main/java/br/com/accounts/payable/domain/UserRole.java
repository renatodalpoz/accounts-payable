package br.com.accounts.payable.domain;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("admin"),
    CONSULT("consult");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

}
