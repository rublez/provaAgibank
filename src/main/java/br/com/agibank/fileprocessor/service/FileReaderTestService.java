package br.com.agibank.fileprocessor.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Profile("test")
@Service
public class FileReaderTestService implements FileReaderService {
	private static final Logger log = LoggerFactory.getLogger(FileReaderTestService.class);

	@Autowired
	DataProcessorService dataProcessorService;

	@Override
	public void folderWatcher() throws Exception {
		log.info("Tests running!");

	}

	@Override
	public String testString() {
		return ("Hello!");

	}

	@Override
	public String processFiles(String filename, Path path, String separator) throws Exception {
			String fullPath = path.toString() + separator + "in" + separator + filename;
			log.info("File: {}", fullPath);

			List<String> records = readFile(fullPath);

		// process records read
		if (!CollectionUtils.isEmpty(records)) {
			dataProcessorService.processFileRecords(records, filename);

			renameFileProcessed(fullPath);

		}
		return "allDone";

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
