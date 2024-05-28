package br.com.accounts.payable.dto;

import br.com.accounts.payable.domain.AccountsPayable;
import java.time.LocalDate;

public record AccountsPayableResponseDTO(Long id, LocalDate dataVencimento, LocalDate dataPagamento, Double valor, String descricao, boolean situacao) {
    public AccountsPayableResponseDTO(AccountsPayable accountsPayable){
        this(accountsPayable.getId(), accountsPayable.getDataVencimento(), accountsPayable.getDataPagamento(), accountsPayable.getValor(), accountsPayable.getDescricao(), accountsPayable.isSituacao());
    }

}
