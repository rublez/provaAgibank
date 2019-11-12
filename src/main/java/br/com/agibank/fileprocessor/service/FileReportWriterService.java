package br.com.agibank.fileprocessor.service;

public interface FileReportWriterService {

	String generateReport(String filename, String fullPath, String separator);

}
