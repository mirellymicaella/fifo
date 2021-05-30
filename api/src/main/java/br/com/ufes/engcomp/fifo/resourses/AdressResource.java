package br.com.ufes.engcomp.fifo.resourses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.ufes.engcomp.fifo.domain.Adress;
import br.com.ufes.engcomp.fifo.repository.AdressRepository;

@RestController
@RequestMapping("/adresses")
public class AdressResource {
	@Autowired
	private AdressRepository adressRepository;
	
	@GetMapping
	public List<Adress> index(){
		return adressRepository.findAll();
	}
	
	@PostMapping
	public Adress create(@RequestBody Adress adress) {
		return adressRepository.save(adress);
	}
	
	@GetMapping("/{adressId}")
	public Adress show(@PathVariable("adressId") Long adressId) {
		return adressRepository.findById(adressId).orElse(null);
	}
	
	@PutMapping("/{adressId}")
	public Adress update(@PathVariable("adressId") Long adressId,@RequestBody  Adress adress) {
		
		return adressRepository.findById(adressId)
			.map( u -> {	
				u.setCountry(adress.getCountry());
				u.setState(adress.getState());
				u.setCity(adress.getCity());
				u.setDistrict(adress.getDistrict());
				u.setStreet(adress.getStreet());
				u.setZipCode(adress.getZipCode());
				u.setZipCode(adress.getZipCode());
				u.setNumber(adress.getNumber());
				u.setLandmark(adress.getLandmark());
				
				return adressRepository.save(u);
			}).orElse(null);
						
	}
	
	@DeleteMapping("/{adressId}")
	public void delete(@PathVariable("adressId") Long adressId) {
		adressRepository.deleteById(adressId);
	}
	
}
