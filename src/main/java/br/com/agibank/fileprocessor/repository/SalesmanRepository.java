package br.com.agibank.fileprocessor.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.agibank.fileprocessor.domain.Salesman;

@Repository
public interface SalesmanRepository extends CrudRepository<Salesman, String> {

	List<Salesman> findSalesmanByFilename(String filename);

	List<Salesman> findSalesmanByFilenameAndName(String filename, String name);
}
