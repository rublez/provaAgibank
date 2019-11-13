package br.com.agibank.fileprocessor.service;

import java.nio.file.Path;

public interface FileReaderService {

	void folderWatcher() throws Exception;

	String processFiles(String filename, Path path, String separator) throws Exception;

}
