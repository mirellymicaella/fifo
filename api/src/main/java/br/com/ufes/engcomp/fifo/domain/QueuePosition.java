package br.com.ufes.engcomp.fifo.domain;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="queues")
public class QueuePosition {

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
    
    @Column(nullable = false)
    private Integer position;
    
    @PrePersist
    public void onPrePersist() {
    	setDate(new Date());
    }

}
