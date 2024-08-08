package Librar.Library.Modes.Dtos;

import jakarta.validation.constraints.NotEmpty;

public class PatronDto {

    private Long id;
    @NotEmpty

    private String name;
    @NotEmpty

    private String contactInfo;

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for contactInfo
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
