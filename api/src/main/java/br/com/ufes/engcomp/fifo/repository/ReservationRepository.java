package br.com.ufes.engcomp.fifo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufes.engcomp.fifo.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
