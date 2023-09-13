package br.com.netdeal.desafio.backend.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;


@Entity
@Table(name = EntityConstants.TB_NAME_USER)
public class User {
    @Id
    @GeneratedValue
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "password")
    String password;

    @Column(name = "score")
    int score;

    @OneToMany
    @JoinColumn(name = "user_id") // Nome da coluna que irá armazenar a chave estrangeira
    private List<User> users;

    @Column(name = "password_security_level")
    String passwordSecurityLevel;

    public User() {
    }

    public List<User> getUsers() {
        return users;
    }

    public User(String name, String password, int score, String passwordSecurityLevel) {
        this.name = name;
        this.password = password;
        this.score = score;
        this.passwordSecurityLevel = passwordSecurityLevel;
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

    public String getPasswordSecurityLevel() {
        return passwordSecurityLevel;
    }

    public void setPasswordSecurityLevel(String passwordSecurityLevel) {
        this.passwordSecurityLevel = passwordSecurityLevel;
    }
}
