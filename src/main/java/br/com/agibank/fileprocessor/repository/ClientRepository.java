package br.com.agibank.fileprocessor.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.agibank.fileprocessor.domain.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, String> {

	List<Client> findClientByFilename(String filename);

}
