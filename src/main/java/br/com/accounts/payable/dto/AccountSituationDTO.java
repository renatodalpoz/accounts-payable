package br.com.accounts.payable.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class AccountSituationDTO {

    private Long id;
    private boolean situacao;
}
