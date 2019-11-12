package br.com.agibank.fileProcessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tngtech.junit.dataprovider.DataProvider;

import br.com.agibank.fileprocessor.service.FileReaderService;
import br.com.agibank.fileprocessor.service.FileReportWriterService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class FileProcessorApplicationTests {

//	@Autowired
//	private DataProcessorServiceImpl dataProcessorService;
//
	@Autowired
	private FileReaderService fileReaderService;

	@Autowired
	private FileReportWriterService fileReportWriter;

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
		assertTrue(true);

	}

	@Test
	void testHello() {
		String ok = fileReaderService.testString();

		assertTrue(ok.equals("Hello!"));
	}

	@Test
//	@MethodSource("copyFileOkToTest")
	public void sendFile() throws Exception {
		copyFileOkToTest();
		String separator = FileSystems.getDefault().getSeparator();
		Path testPath = Paths.get(System.getProperty(home) + "/data/test/");

		String filename = "dados.dat";
		String done = fileReaderService.processFiles(filename, testPath, separator);

		assertThat(null != done);
		assertThat(done.equals("allDone"));


	}

	private static boolean copyFileOkToTest() throws IOException {
		FileUtils.copyFileToDirectory(new File("dados.dat"), new File(System.getProperty(home) + "/data/test/in/"));

		return true;

	}

	@BeforeEach
	public void cleanFolders() throws IOException {
		Path in = Paths.get(System.getProperty(home) + "/data/test/");
		File testFolder = new File(System.getProperty(home) + "/data/test/");
		FileUtils.deleteDirectory(testFolder);

	}




}
