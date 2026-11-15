package moutawakil.latifa.demoescqrsaxor.query.repository;


import moutawakil.latifa.demoescqrsaxor.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation,Long> {
    List<Operation> findByAccountId(String accountId);
}
