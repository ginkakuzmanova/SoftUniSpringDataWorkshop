package softuni.workshop.service.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.CompanyDto;
import softuni.workshop.data.dtos.CompanyRootDto;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.util.XmlParser;
import softuni.workshop.web.models.CompanyViewModel;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final String XML_PATH = "src\\main\\resources\\files\\xmls\\companies.xml";

    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper mapper) {
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
    }

    @Override
    public void importCompanies() throws JAXBException {
        CompanyRootDto companyRootDto =
                this.xmlParser.unmarshalling(CompanyRootDto.class, XML_PATH);

        for (CompanyDto companyDto : companyRootDto.getCompanyDtoList()) {
            Company company = this.mapper.map(companyDto, Company.class);

            this.companyRepository.saveAndFlush(company);
        }
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() {
        String xml = "";
        try {
            xml = String.join("\n", Files.readAllLines(Path.of(XML_PATH)));
        } catch (IOException e) {
            return "There is something wrong with the file.";
        }

        return xml;
    }

    @Override
    public List<CompanyViewModel> findAll() {
        return this.companyRepository.findAll()
                .stream()
                .map(company -> this.mapper.map(company, CompanyViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Autowired
    public String companyToJson(Gson gson) {
        return gson.toJson(this.findAll());
    }
}
