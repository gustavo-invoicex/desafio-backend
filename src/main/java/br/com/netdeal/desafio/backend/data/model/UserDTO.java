package br.com.netdeal.desafio.backend.data.model;

import java.util.List;

public class UserDTO {
    private Long id;
    private String name;
    private String password;

    private List<UserDTO> users;

    private Long userId;

    private int score;

    private PasswordSecurityLevel passwordSecurityLevel;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String password, Long userId, int score, PasswordSecurityLevel passwordSecurityLevel, List<UserDTO> users) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userId = userId;
        this.score = score;
        this.passwordSecurityLevel = passwordSecurityLevel;
        this.users = users;
    }

    public UserDTO(Long id, String name, String password, int score, PasswordSecurityLevel passwordSecurityLevel, List<UserDTO> users) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.score = score;
        this.passwordSecurityLevel = passwordSecurityLevel;
        this.users = users;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public PasswordSecurityLevel getPasswordSecurityLevel() {
        return passwordSecurityLevel;
    }

    public void setPasswordSecurityLevel(PasswordSecurityLevel passwordSecurityLevel) {
        this.passwordSecurityLevel = passwordSecurityLevel;
    }

    public Long getUserId() {
        return userId;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
