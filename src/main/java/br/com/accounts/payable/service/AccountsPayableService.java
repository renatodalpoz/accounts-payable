package br.com.accounts.payable.service;

import br.com.accounts.payable.domain.AccountsPayable;
import br.com.accounts.payable.repository.AccountsPayableRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

@Service
public class AccountsPayableService {

    @Autowired
    private AccountsPayableRepository accountsPayableRepository;

    public void importarCsv(MultipartFile file) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> lines = reader.readAll();
            if (!lines.isEmpty()) lines.remove(0);
            for (String[] line : lines) {
                AccountsPayable accountsPayable = new AccountsPayable();
                accountsPayable.setDataVencimento(LocalDate.parse(line[0]));
                accountsPayable.setDataPagamento(LocalDate.parse(line[1]));
                accountsPayable.setValor(Double.parseDouble(line[2]));
                accountsPayable.setDescricao(line[3]);
                accountsPayableRepository.save(accountsPayable);
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
