package br.com.ufes.engcomp.fifo.domain;


import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="adresses")
public class Adress {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String country;

    private String state;

    private String city;

    private String district;
    
    private String street;
    
    private int zipCode;
    
    private int number;
    
    private String landmark;

}
