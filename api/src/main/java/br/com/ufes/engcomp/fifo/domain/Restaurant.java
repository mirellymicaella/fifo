package br.com.ufes.engcomp.fifo.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name="restaurants")
public class Restaurant {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private  String name;
	
    private  String email;

    private String phone;

    @ManyToOne
	@JoinColumn(name = "adress_id")
    private Adress adress;

    private float rating;
    
    private String password;
    
    private int availableTables;
    
    private int availableReservations;
    
    @Temporal(value=TemporalType.DATE)
    private Date createdAt;
    
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy="restaurant")
    @JsonIgnore
    private Set<Reservation> reservations;
    
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy="restaurant")
    @JsonIgnore
    private Set<QueuePosition> queue;
    
    @PrePersist
    public void onPrePersist() {
    	setCreatedAt(new Date());
    }
    
}
