package softuni.workshop.data.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CompanyRootDto {

    private List<CompanyDto> companyDtoList;

    public CompanyRootDto() {
    }

    @XmlElement(name = "company")
    public List<CompanyDto> getCompanyDtoList() {
        return companyDtoList;
    }

    public void setCompanyDtoList(List<CompanyDto> companyDtoList) {
        this.companyDtoList = companyDtoList;
    }
}
