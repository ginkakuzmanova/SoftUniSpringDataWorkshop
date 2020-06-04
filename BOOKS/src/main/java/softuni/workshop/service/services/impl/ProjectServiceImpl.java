package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.ProjectDto;
import softuni.workshop.data.dtos.ProjectRootDto;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.util.XmlParser;
import softuni.workshop.web.models.ProjectViewModel;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {

    private static final String XML_PATH = "src\\main\\resources\\files\\xmls\\projects.xml";

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper mapper) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
    }

    @Override
    public void importProjects() throws JAXBException {
        ProjectRootDto projectRootDto = this.xmlParser.unmarshalling(ProjectRootDto.class, XML_PATH);
        for (ProjectDto projectDto : projectRootDto.getProjectDtoList()) {
            Project project = this.mapper.map(projectDto, Project.class);
            Company company = this.companyRepository.findCompanyByName(projectDto.getCompanyDto().getName());
            project.setCompany(company);

            this.projectRepository.saveAndFlush(project);
        }
    }

    @Override
    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() {
        String xml = "";
        try {
            xml = String.join("\n", Files.readAllLines(Path.of(XML_PATH)));
        } catch (IOException e) {
            return "There is something wrong with the file.";
        }
        return xml;
    }

    @Override
    public String exportFinishedProjects() {
        StringBuilder builder = new StringBuilder();

        Set<ProjectViewModel> projectsSet = this.findAllFinishedProjects();
        for (ProjectViewModel pr : projectsSet) {
            builder.append(String.format("Project Name: %s", pr.getName()))
                    .append(System.lineSeparator())
                    .append(String.format("   Description: %s", pr.getDescription()))
                    .append(System.lineSeparator())
                    .append(String.format("   Payment: %.2f", pr.getPayment()))
                    .append(System.lineSeparator());
        }

        return builder.toString();
    }

    @Override
    public Set<ProjectViewModel> findAllFinishedProjects() {
        return this.projectRepository.findAllByFinishedIsTrue()
                .stream().map(project ->
                        this.mapper.map(project, ProjectViewModel.class)
                ).collect(Collectors.toSet());
    }
}
