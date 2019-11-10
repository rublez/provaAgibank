package br.com.agibank.fileprocessor.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.agibank.fileprocessor.domain.Sale;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {

	List<Sale> findSaleByFilename(String filename);

}
