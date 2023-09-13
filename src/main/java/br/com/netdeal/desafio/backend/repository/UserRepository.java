package br.com.netdeal.desafio.backend.repository;

import br.com.netdeal.desafio.backend.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
