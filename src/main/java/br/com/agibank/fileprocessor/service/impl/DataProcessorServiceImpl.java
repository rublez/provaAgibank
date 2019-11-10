package br.com.agibank.fileprocessor.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agibank.fileprocessor.builder.ClientBuilder;
import br.com.agibank.fileprocessor.builder.ItemBuilder;
import br.com.agibank.fileprocessor.builder.SaleBuilder;
import br.com.agibank.fileprocessor.builder.SalesmanBuilder;
import br.com.agibank.fileprocessor.domain.Client;
import br.com.agibank.fileprocessor.domain.Item;
import br.com.agibank.fileprocessor.domain.Sale;
import br.com.agibank.fileprocessor.domain.Salesman;
import br.com.agibank.fileprocessor.repository.ClientRepository;
import br.com.agibank.fileprocessor.repository.SaleRepository;
import br.com.agibank.fileprocessor.repository.SalesmanRepository;
import br.com.agibank.fileprocessor.service.DataProcessorService;

@Service
public class DataProcessorServiceImpl implements DataProcessorService {
	private static final Logger log = LoggerFactory.getLogger(DataProcessorServiceImpl.class);

	@Autowired
	private SalesmanRepository salesmanRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private SaleRepository saleRepository;

	@Override
	public void processFileRecords(List<String> records, String filename) {

		records.forEach(r -> {
			String startsWith = r.substring(0, r.indexOf('ç'));
			switch (startsWith) {
				case "001":
					processSalesman(r, filename);
					break;

				case "002":
					processClient(r, filename);
					break;

				case "003":
					processSale(r, filename);
					break;

				default:
					log.warn("The expected kind of data not found in this line. Ignored.");
					break;

			}

		});

	}


	private void processSalesman(String record, String filename) {
		String[] values = splitValues(record);
		Salesman salesman = new Salesman();

		if (values.length == 4) {
			salesman = new SalesmanBuilder()
						.withCpf(values[1])
						.withName(values[2])
						.withSalary(Double.valueOf(values[3]))
						.withFilename(filename)
					.build();

			log.info("Salesman: {}", salesman);
			salesmanRepository.save(salesman);

		}
		else
			log.warn("Salesman register is corrupted.");

	}

	private void processClient(String record, String filename) {
		String[] values = splitValues(record);
		Client client = new Client();

		if (values.length == 4) {
			client = new ClientBuilder()
						.withCnpj(values[1])
						.withName(values[2])
						.withBusinessArea(values[3])
						.withFilename(filename)
					.build();

			log.info("Client: {}", client);
			clientRepository.save(client);

		}
		else
			log.warn("Client register is corrupted.");

	}

	private void processSale(String record, String filename) {
		String[] values = splitValues(record);

		if (values.length == 4) {
			List<Item> items  = splitItems(values[2]);

			Sale sale = new SaleBuilder()
						.withSaleId(Long.valueOf(values[1]))
						.withItems(items)
						.withSalesmanName(values[3])
						.withFilename(filename)
					.build();

			log.info("Sale: {}", sale);
			saleRepository.save(sale);

		}
		else
			log.warn("Sale register is corrupted.");

	}

	private String[] splitValues(String record) {
		return record.split("ç");

	}

	private List<Item> splitItems(String itemsString) {
		List<Item> items = new ArrayList<>();

		String itemsRaw = itemsString.replace("[", "").replace("]", ""); 						// os ítens na Sale, sem os colchetes
		List<String> itemsRawSplitted = new ArrayList<>(Arrays.asList(itemsRaw.split(","))); 	// os campos do item

		for (String itemStr : itemsRawSplitted) {

			// !possible here
			//List<String> fields = new ArrayList<>(Arrays.asList(itemStr.split("-")));
			//items = fields.stream().map(Item::new).collect(Collectors.toList());

			String[] fields = itemStr.split("-");
			if(fields.length == 3) {
				Item item = new ItemBuilder()
						.withItemId(Long.valueOf(fields[0]))
						.withItemQty(Integer.valueOf(fields[1]))
						.withPrice(Double.valueOf(fields[2]))
					.build();

				items.add(item);

			}
			else
				log.warn("Item is corrupted.");

		}
		return items;

	}

}
