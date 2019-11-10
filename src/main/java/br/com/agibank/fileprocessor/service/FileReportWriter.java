package br.com.agibank.fileprocessor.service;

public interface FileReportWriter {

	void generateReport(String filename, String fullPath, String separator);

}
