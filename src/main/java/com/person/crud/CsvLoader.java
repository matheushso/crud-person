package com.person.crud;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.person.crud.domain.entity.Person;
import com.person.crud.domain.repository.PersonRepository;

@Component
public class CsvLoader {

	private static final String FILE_NAME = "mock_data.csv";

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private PersonRepository personRepository;

	@PostConstruct
	@Transactional
	public void loadCsvData() {
		try {
			Resource resource = resourceLoader.getResource("file:" + FILE_NAME);
			String filePath = resource.getFile().getAbsolutePath();

			Reader reader = new FileReader(filePath);
			CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
			CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();
			List<String[]> fields = csvReader.readAll();

			for (String[] field : fields) {
				Person person = new Person();

				person.setNameCompany(field[1] + " " + field[2]);
				person.setEmail(field[3]);
				person.setCpfCnpj(field[4]);

				personRepository.save(person);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
