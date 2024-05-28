package br.com.accounts.payable.dto;

import br.com.accounts.payable.domain.UserRole;

public record RegisterUserDTO(String login, String password, UserRole role) {
}
