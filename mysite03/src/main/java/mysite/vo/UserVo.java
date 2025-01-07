package mysite.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserVo {
    private Long id;

    @NotEmpty(message = "{NotEmpty.user.name}")
    @Size(min = 2, max = 8, message = "{Size.user.name}")
    private String name;

    @NotEmpty(message = "{NotEmpty.user.email}")
    @Email(message = "{Email.user.email}")
    private String email;

    @NotEmpty(message = "{NotEmpty.user.password}")
    @Size(min = 4, max = 16, message = "{Size.user.password}")
    private String password;

    @NotEmpty(message = "{NotEmpty.user.gender}")
    private String gender;

    private String joinDate;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "UserVo [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
                + gender + ", joinDate=" + joinDate + ", role=" + role + "]";
    }
}
