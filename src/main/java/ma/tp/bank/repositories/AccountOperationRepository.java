package ma.tp.bank.repositories;

import ma.tp.bank.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository
        extends JpaRepository<AccountOperation, Long> {

    List<AccountOperation> findByAccountIdOrderByOperationDateDesc(
            String accountId
    );
}
