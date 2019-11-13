package br.com.agibank.fileProcessor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.agibank.fileprocessor.service.FileReaderService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class FileProcessorApplicationTests {

	@Autowired
	private FileReaderService fileReaderService;

	static String filename = "dados.dat";
	static String home = "user.home";

	@Test
	void contextLoads() {
		assertTrue(true);

	}

	@Test
	public void sendFile() throws Exception {
		copyFileOkToTest();
		String separator = FileSystems.getDefault().getSeparator();
		Path testPath = Paths.get(System.getProperty(home) + "/data/test/");

		String filename = "dados.dat";
		String report = fileReaderService.processFiles(filename, testPath, separator);

		assertTrue(null != report);
		assertTrue(report.contains("Number of Clients in file: 2"));
		assertTrue(report.contains("Number of Salesman in file: 3"));
		assertTrue(report.contains("SaleId of the most expensive sale in file: 7"));
		assertTrue(report.contains("Worst Salesman in file: Gerald"));

	}

	private static void copyFileOkToTest() throws IOException {
		FileUtils.copyFileToDirectory(new File("dados.dat"), new File(System.getProperty(home) + "/data/test/in/"));

	}

	@BeforeEach
	public void cleanFolders() throws IOException {
		File testFolder = new File(FileUtils.getUserDirectoryPath() + "/data/test/");
		FileUtils.deleteQuietly(testFolder);

	}

	@AfterAll
	static public void removeTestFolder() throws IOException {
		File testFolder = new File(FileUtils.getUserDirectoryPath() + "/data/test/");
		FileUtils.deleteQuietly(testFolder);

	}

}
