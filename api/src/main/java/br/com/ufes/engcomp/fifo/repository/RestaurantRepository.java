package br.com.ufes.engcomp.fifo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ufes.engcomp.fifo.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

}


