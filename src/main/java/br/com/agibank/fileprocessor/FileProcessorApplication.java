package br.com.agibank.fileprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = "br.com.agibank.fileprocessor")
@EnableAutoConfiguration
public class FileProcessorApplication {

	private static final Logger log = LoggerFactory.getLogger(FileProcessorApplication.class);

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(FileProcessorApplication.class, args);

	}

}
