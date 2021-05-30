package br.com.ufes.engcomp.fifo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufes.engcomp.fifo.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
