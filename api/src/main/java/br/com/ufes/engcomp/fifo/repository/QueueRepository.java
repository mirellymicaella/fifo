package br.com.ufes.engcomp.fifo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufes.engcomp.fifo.domain.QueuePosition;

public interface QueueRepository extends JpaRepository<QueuePosition, Long>{

}
