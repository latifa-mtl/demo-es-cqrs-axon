package moutawakil.latifa.demoescqrsaxor.query.repository;

import moutawakil.latifa.demoescqrsaxor.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}