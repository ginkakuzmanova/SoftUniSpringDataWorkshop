package softuni.workshop.service.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final String XML_PATH = "src\\main\\resources\\files\\xmls\\companies.xml";

    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, XmlParser xmlParser) {
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public void importCompanies() {
        //TODO seed in database

    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile(){
        String xml = "";
        try {
            xml = String.join("\n", Files.readAllLines(Path.of(XML_PATH)));
        } catch (IOException e) {
            return "There is something wrong with the file.";
        }
        return xml;
    }
}
