package br.com.accounts.payable.repository;

import br.com.accounts.payable.domain.AccountsPayable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AccountsPayableRepository extends JpaRepository<AccountsPayable, Long> {

//    @Query(value = "select a.* from ACCOUNTS_PAYABLE a where to_date(cast(a.data_vencimento as text), 'yyyy-mm-dd') = :dataVencimento and lower(a.descricao) like %:descricao%", nativeQuery = true)
    @Query("select a from ACCOUNTS_PAYABLE a where a.dataVencimento = :dataVencimento and lower(a.descricao) like %:descricao%")
    List<AccountsPayable> findAllByDataVencimentoAndDescricaoLike(@Param("dataVencimento") LocalDate dataVencimento, @Param("descricao") String descricao);

    @Query("select SUM(a.valor) from ACCOUNTS_PAYABLE a where a.dataVencimento between :dataVencimentoIni and :dataVencimentoFim and a.situacao is true")
    Double findTotalAmount(@Param("dataVencimentoIni") LocalDate dataVencimentoIni, @Param("dataVencimentoFim") LocalDate dataVencimentoFim);
}
