package br.com.marcos.todo_api.model; // Use o seu nome de pacote aqui

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    // --- GETTERS E SETTERS ---
    // ✅ VERIFIQUE SE VOCÊ TEM ESTES MÉTODOS EXATAMENTE ASSIM

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    // --- MÉTODOS DO USERDETAILS (JÁ DEVEM ESTAR CORRETOS) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.senha; // Este método usa o campo 'senha'
    }

    @Override
    public String getUsername() {
        return this.email; // Este método usa o campo 'email'
    }

    // ... o resto dos métodos do UserDetails (isAccountNonExpired, etc.)
}