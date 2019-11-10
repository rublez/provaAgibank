package br.com.agibank.fileprocessor.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.agibank.fileprocessor.service.FileReaderService;

@Component
public class FileProcessorBean implements ApplicationRunner {

	@Autowired
	FileReaderService fileReaderService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		fileReaderService.folderWatcher();

	}

}
