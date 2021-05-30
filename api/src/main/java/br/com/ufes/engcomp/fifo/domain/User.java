package br.com.ufes.engcomp.fifo.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private String password;

    @ManyToOne
	@JoinColumn(name = "adress_id", nullable = false)
    private Adress adress;
     
    @Column(nullable = false)
    @Temporal(value=TemporalType.DATE)
    private Date createdAt;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy="user")
    @JsonIgnore
    private Set<Reservation> reservations;
    
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy="user")
    @JsonIgnore
    private Set<QueuePosition> queuesPositions;
    
    
    @PrePersist
    public void onPrePersist() {
    	setCreatedAt(new Date()); 
    }
    

}
