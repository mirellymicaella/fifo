package br.com.ufes.engcomp.fifo.domain;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="reservations")
public class Reservation {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
	@JoinColumn(name = "restaurant_id",nullable = false)
    private Restaurant restaurant;
    
	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
    private User user;

	@Column(nullable = false)
    private Integer peopleNumber;

	@Column(nullable = false)
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date date;

    @PrePersist
    public void onPrePersist() {
    	setDate(new Date());
    }


}
