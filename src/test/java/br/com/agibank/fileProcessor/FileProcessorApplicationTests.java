package br.com.agibank.fileProcessor;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tngtech.junit.dataprovider.DataProvider;

import br.com.agibank.fileprocessor.service.impl.FileReportWriterServiceImpl;

@SpringBootTest
class FileProcessorApplicationTests {

//	@Autowired
//	private DataProcessorServiceImpl dataProcessorService;
//
//	@Autowired
//	private FileReaderServiceImpl fileReaderService;

	@Autowired
	private FileReportWriterServiceImpl fileReportWriter;

	static String filename = "dados.dat";
	static String home = "user.home";

	@DataProvider
	private List<String> records() {

		return new ArrayList<>(Arrays.asList(
				"001ç1234567891234çPedroç50000", 
				"001ç1234567891234çGeraldç60000",
				"001ç3245678865434çPauloç40000.99", 
				"002ç2345675434544345çJose da SilvaçRural",
				"002ç2345675433444345çEduardo PereiraçRural", 
				"003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo",
				"003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro", 
				"003ç07ç[1-75-10,2-33-150,3-40-0.10,4-25-100]çPaulo"
			)

		);

	}

	@Test
	void contextLoads() {
	}

	@ParameterizedTest
	@MethodSource("copyFileOkToTest")
	public void sendFile(boolean ok) {
		String separator = FileSystems.getDefault().getSeparator();
		Path testPath = Paths.get(System.getProperty(home) + "/data/test/");

		String filename = "dados.dat";
		String report = fileReportWriter.generateReport(filename, testPath.toString(), separator);

		assertThat(null != report);
		assertThat(report.contains("Worst"));

	}

	private static boolean copyFileOkToTest() throws IOException {
		FileUtils.copyFile(new File("/dados.dat"), new File(System.getProperty(home) + "/data/test/in"));

		return true;

	}

	@BeforeEach
	public void cleanFolders() throws IOException {
		Path in = Paths.get(System.getProperty(home) + "/data/test/");
		File testFolder = new File(System.getProperty(home) + "/data/test/");
		FileUtils.forceDelete(testFolder);

	}
}
