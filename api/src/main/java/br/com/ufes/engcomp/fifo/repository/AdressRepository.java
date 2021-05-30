package br.com.ufes.engcomp.fifo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufes.engcomp.fifo.domain.Adress;

public interface AdressRepository extends JpaRepository<Adress, Long>{

}
