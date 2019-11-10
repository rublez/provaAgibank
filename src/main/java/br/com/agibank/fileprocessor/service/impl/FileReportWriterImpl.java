package br.com.agibank.fileprocessor.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.agibank.fileprocessor.domain.Item;
import br.com.agibank.fileprocessor.domain.Sale;
import br.com.agibank.fileprocessor.repository.ClientRepository;
import br.com.agibank.fileprocessor.repository.SaleRepository;
import br.com.agibank.fileprocessor.repository.SalesmanRepository;
import br.com.agibank.fileprocessor.service.FileReportWriter;

@Component
public class FileReportWriterImpl implements FileReportWriter {
	private static final Logger log = LoggerFactory.getLogger(FileReportWriterImpl.class);

	@Autowired
	private SalesmanRepository salesmanRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private SaleRepository saleRepository;

	@Override
	public void generateReport(String filename, String fullPath, String separator) {

		Long nrClients = countClients(filename);
		Long nrSalesman = countSalesman(filename);

		Map<String, Object> worstAndBetterSale = findWorstAndBetterSale(filename);
		String worstSalesman = (String) worstAndBetterSale.get("worstSalesman");
		Long bestSaleId = (Long) worstAndBetterSale.get("bestSaleId");

		StringBuilder report = new StringBuilder();

		report.append("Filename: " + filename + "\n\n");
		report.append("Number of Clients in file: " + nrClients + "\n");
		report.append("Number of Salesman in file: " + nrSalesman + "\n");
		report.append("SaleId of the most expensive sale in file: " + bestSaleId + "\n");
		report.append("Worst Salesman in file: " + worstSalesman + "\n");

		log.info("Report: \n{}", report);

		String fullFilename = buildUrl(fullPath, filename, separator);
		writaDataOnFile(report, fullFilename);

	}

	private void writaDataOnFile(StringBuilder report, String filename) {

		File file = new File(filename);
		try {
			FileUtils.writeStringToFile(file, report.toString(), StandardCharsets.UTF_8.name());
			log.info("Report file written: {}", filename);

		} catch (IOException e) {
			log.error("Error creating report file: {}", e);

		}

	}

	private String buildUrl(String fullPath, String filename, String separator) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		LocalDateTime date = LocalDateTime.now();
		String newFilename = filename.substring(0, filename.lastIndexOf('.')) + "_" + dateFormat.format(date) + ".dat.done";

		return fullPath + separator + "out" + separator + newFilename;

	}

	private Long countClients(String filename) {
		Long clients = clientRepository.findClientByFilename(filename).stream().distinct().count();

		log.warn("Unique Clients read: {}", clients);
		return clients;

	}

	private Long countSalesman(String filename) {
		Long salesman = salesmanRepository.findSalesmanByFilename(filename).stream().distinct().count();

		log.warn("Unique Salesman read: {}", salesman);
		return salesman;

	}

	private Map<String, Object> findWorstAndBetterSale(String filename) {
		List<Sale> sales = saleRepository.findSaleByFilename(filename);
		Map<String, Object> results = new HashMap<>();

		Double bestSale = 0.0;
		Double worstSale = 0.0;
		String worstSalesman = null;
		Long bestSaleId = 0L;

		for (Sale sale : sales) {
			List<Item> items = sale.getItems();
			Double salesValue = 0.0;
			for (Item item : items)
				salesValue = item.getItemQty() * item.getPrice();

			if (salesValue > bestSale) {
				bestSale = salesValue;
				bestSaleId = sale.getSaleId();

			}

			if (worstSale == 0.0 || salesValue < worstSale) {
				worstSale = salesValue;
				worstSalesman = sale.getSalesmanName();

			}

		}
		results.put("worstSalesman", worstSalesman);
		results.put("bestSaleId", bestSaleId);

		log.info("Best sale value: ${}", bestSale);
		return results;

	}

}
