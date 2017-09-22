package gva.model.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class UserDto implements Serializable {
    private static final long serialVersionUID = 8689486590613531863L;
    @NotBlank
    private String name;
    @NotBlank
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank
    private String password;
}
