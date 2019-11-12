package br.com.agibank.fileprocessor.service;

public interface FileReportWriterService {

	void generateReport(String filename, String fullPath, String separator);

}
