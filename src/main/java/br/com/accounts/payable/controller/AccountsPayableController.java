package br.com.accounts.payable.controller;

import br.com.accounts.payable.domain.AccountsPayable;
import br.com.accounts.payable.dto.AccountSituationDTO;
import br.com.accounts.payable.dto.AccountsPayableDTO;
import br.com.accounts.payable.dto.AccountsPayableResponseDTO;
import br.com.accounts.payable.dto.TotalAmountDTO;
import br.com.accounts.payable.repository.AccountsPayableRepository;
import br.com.accounts.payable.service.AccountsPayableService;
import com.opencsv.exceptions.CsvException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("accounts")
public class AccountsPayableController {

    @Autowired
    private AccountsPayableRepository accountsPayableRepository;

    @Autowired
    private AccountsPayableService accountsPayableService;

    @PostMapping("/new")
    public ResponseEntity create(@RequestBody @Valid AccountsPayableDTO body) {
        try {
            log.info("CREATE ACCOUNT PAYABLE:: {}", body.toString());
            var accountsPayable = AccountsPayable.builder()
                    .dataVencimento(LocalDate.parse(body.getDataVencimento()))
                    .dataPagamento(LocalDate.parse(body.getDataPagamento()))
                    .valor(Double.parseDouble(body.getValor()))
                    .descricao(body.getDescricao())
                    .build();
            this.accountsPayableRepository.save(accountsPayable);
            return ResponseEntity.ok("CREATE ACCOUNT PAYABLE");
        } catch (Exception e) {
            log.error("ERROR CREATE ACCOUNT PAYABLE:: {} ", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody @Valid AccountsPayableDTO body) {
        try {
            log.info("UPDATE ACCOUNT PAYABLE:: {}", body.toString());
            Optional<AccountsPayable> accountsPayable = this.accountsPayableRepository.findById(body.getId());
            if (accountsPayable.isPresent()) {
                accountsPayable.get().setDataVencimento(LocalDate.parse(body.getDataVencimento()));
                accountsPayable.get().setDataPagamento(LocalDate.parse(body.getDataPagamento()));
                accountsPayable.get().setValor(Double.parseDouble(body.getValor()));
                accountsPayable.get().setDescricao(body.getDescricao());
                this.accountsPayableRepository.save(accountsPayable.get());
                return ResponseEntity.ok("UPDATE ACCOUNT PAYABLE SUCESS");
            } else
                return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("ERROR UPDATE CREATE ACCOUNT PAYABLE:: {} ", e.getMessage(), e);
            throw e;
        }
    }

    @PutMapping("/situation")
    public ResponseEntity updateSituation(@RequestBody @Valid AccountSituationDTO body) {
        try {
            log.info("SITUATION UPDATE:: {}", body.toString());
            Optional<AccountsPayable> accountsPayable = this.accountsPayableRepository.findById(body.getId());
            if (accountsPayable.isPresent()) {
                accountsPayable.get().setSituacao(body.isSituacao());
                this.accountsPayableRepository.save(accountsPayable.get());
                return ResponseEntity.ok("SITUATION UPDATE SUCESS");
            } else
                return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("ERROR SITUATION UPDATE:: {} ", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountsPayableResponseDTO>> getAllAccountsPageable(
            @RequestParam("dataVencimento") String dataVencimento, @RequestParam("descricao") String descricao){
        List<AccountsPayableResponseDTO> accountsPayableResponseDTOList =
                this.accountsPayableRepository.findAllByDataVencimentoAndDescricaoLike(LocalDate.parse(dataVencimento), descricao.toLowerCase()).stream().map(AccountsPayableResponseDTO::new).toList();
        return ResponseEntity.ok(accountsPayableResponseDTOList);
    }

    @GetMapping("/account")
    public ResponseEntity<List<AccountsPayableResponseDTO>> getAccountId(@RequestParam("id") Long id){
        List<AccountsPayableResponseDTO> accountsPayable = this.accountsPayableRepository.findById(id).stream().map(AccountsPayableResponseDTO::new).toList();
        return ResponseEntity.ok(accountsPayable);
    }

    @GetMapping("/amount")
    public ResponseEntity<TotalAmountDTO> getTotalAmount(@RequestParam("dataVencimentoIni") String dataVencimentoIni, @RequestParam("dataVencimentoFim") String dataVencimentoFim){
        return ResponseEntity.ok(new TotalAmountDTO(this.accountsPayableRepository.findTotalAmount(LocalDate.parse(dataVencimentoIni), LocalDate.parse(dataVencimentoFim))));
    }

    @GetMapping
    public ResponseEntity<List<AccountsPayableResponseDTO>> getAllAccounts(){
        List<AccountsPayableResponseDTO> accountsPayableResponseDTOList = this.accountsPayableRepository.findAll().stream().map(AccountsPayableResponseDTO::new).toList();
        return ResponseEntity.ok(accountsPayableResponseDTOList);
    }

    @PostMapping("/import")
    public ResponseEntity importAccountsCSV(@RequestParam("file") MultipartFile file) throws IOException, CsvException {
        this.accountsPayableService.importarCsv(file);
        return ResponseEntity.ok().build();
    }
}
