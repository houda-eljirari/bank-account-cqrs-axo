package ma.tp.bank.repositories;

import ma.tp.bank.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
        extends JpaRepository<Account, String> {
}
