package catalog.core.repository;

import catalog.core.model.Contract;
import catalog.core.model.Genre;
import org.springframework.data.jpa.repository.Query;

public interface ContractRepository extends CatalogRepository<Contract, Long>{
//
//    @Query(value = "SELECT MovieId, COUNT(*) FROM `contract`\n" +
//            "GROUP BY MovieID\n" +
//            "ORDER BY MovieID DESC;", nativeQuery = true)
}

