package softuni.workshop.web.models;

import com.google.gson.annotations.Expose;

public class CompanyViewModel {
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyViewModel() {
    }
}
