package br.com.agibank.fileprocessor.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.agibank.fileprocessor.service.DataProcessorService;
import br.com.agibank.fileprocessor.service.FileReaderService;
import br.com.agibank.fileprocessor.service.FileReportWriter;

@Service
public class FileReaderServiceImpl implements FileReaderService {

	private static final Logger log = LoggerFactory.getLogger(FileReaderServiceImpl.class);

	@Autowired
	private DataProcessorService dataProcessorService;

	@Autowired
	private FileReportWriter fileReportWriter;

	@Override
	public void folderWatcher() throws Exception {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		String separator = FileSystems.getDefault().getSeparator();
		String home = "user.home";

		// Creates directory if not exists
		FileUtils.forceMkdir(new File(System.getProperty(home) + "/data/in"));

		Path path = Paths.get(System.getProperty(home) + "/data");
		Path in = Paths.get(System.getProperty(home) + "/data/in");

		in.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		WatchKey key;
		while ((key = watchService.take()) != null) {
			for (WatchEvent<?> event : key.pollEvents()) {
				String filename = event.context().toString();
				String extension = filename.substring(filename.lastIndexOf('.'));
				if (event.kind().name().equalsIgnoreCase("ENTRY_CREATE") && extension.equalsIgnoreCase(".dat"))
					processFiles(filename, path, separator);

			}
			key.reset();

		}
		log.warn("Left event while.");

	}

	synchronized void processFiles(String filename, Path path, String separator) throws Exception {
		String fullPath = path.toString() + separator + "in" + separator + filename;
		log.info("File: {}", fullPath);

		List<String> records = readFile(fullPath);

		// process records read
		if (!CollectionUtils.isEmpty(records)) {
			dataProcessorService.processFileRecords(records, filename);

			fileReportWriter.generateReport(filename, path.toString(), separator);

			renameFileProcessed(fullPath);

		}

	}

	private List<String> readFile(String pathFile) throws Exception {
		List<String> records = new ArrayList<>();
		try {
			File file = new File(pathFile);
			records = FileUtils.readLines(file, StandardCharsets.UTF_8.name());

			// remove eventual empty lines
			records = records.stream()
					.filter(x -> x != null && !x.equals(""))
					.collect(Collectors.toList());
		
		} catch (Exception e) {
			log.error("Error reading file: {}", e);

		}
		log.info("Lines read: {}", records);
		return records;

	}

	private void renameFileProcessed(String pathFile) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		LocalDateTime date = LocalDateTime.now();
		String newExtension = "." + dateFormat.format(date) + ".processed";

		File oldFilename = new File(pathFile);
		File newFilename = new File(pathFile + newExtension);

		try {
			FileUtils.moveFile(oldFilename, newFilename);

		} catch (IOException e) {
			log.error("Error renaming processed file: {}", e);

		}

	}

}
