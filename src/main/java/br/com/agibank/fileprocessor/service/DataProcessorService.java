package br.com.agibank.fileprocessor.service;

import java.util.List;

public interface DataProcessorService {

	void processFileRecords(List<String> records, String filename);

}
