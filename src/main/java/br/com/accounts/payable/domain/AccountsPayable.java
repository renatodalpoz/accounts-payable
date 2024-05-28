package br.com.accounts.payable.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "ACCOUNTS_PAYABLE")
@Table(name = "ACCOUNTS_PAYABLE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountsPayable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @Column(name = "situacao")
    private boolean situacao = false;
}
