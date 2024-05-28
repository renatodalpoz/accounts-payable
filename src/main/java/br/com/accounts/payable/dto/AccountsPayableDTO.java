package br.com.accounts.payable.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class AccountsPayableDTO {

    private Long id;

    @JsonProperty("data_vencimento")
    private String dataVencimento;

    @JsonProperty("data_pagamento")
    private String dataPagamento;

    @JsonProperty("valor")
    private String valor;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("situacao")
    private String situacao;
}
