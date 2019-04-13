package joannewang.account.web;

import joannewang.account.domain.Account;
import joannewang.account.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Account getAccount(@PathVariable("id") Long id) {
        return accountRepository.findOne(id);
    }

    @GetMapping
    @ResponseBody
    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Account addAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @PutMapping
    @ResponseBody
    public Account updateAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable("id") Long id) {
        accountRepository.delete(id);
    }

}
