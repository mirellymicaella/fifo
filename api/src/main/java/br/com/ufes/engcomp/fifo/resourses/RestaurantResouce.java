package br.com.ufes.engcomp.fifo.resourses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ufes.engcomp.fifo.domain.Restaurant;

import br.com.ufes.engcomp.fifo.repository.RestaurantRepository;

@RestController
@RequestMapping("/restaurants")
public class RestaurantResouce {
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@GetMapping
	public List<Restaurant> index(){
		return restaurantRepository.findAll();
	}
	
	@GetMapping("/reservations/{restaurantId}")
	public  ResponseEntity<String> reservations(@PathVariable("restaurantId") Long restaurantId) throws JsonProcessingException{

		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);		
		if(restaurant == null) 
			return new ResponseEntity<String>("Restaurante não existe.", HttpStatus.NOT_FOUND);
	
		ObjectMapper objectMapper = new ObjectMapper();
		String res = objectMapper.writeValueAsString(restaurant.getReservations());
		
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	@GetMapping("/queues/{restaurantId}")
	public  ResponseEntity<String> queue(@PathVariable("restaurantId") Long restaurantId) throws JsonProcessingException{

		Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);		
		if(restaurant == null) 
			return new ResponseEntity<String>("Restaurante não existe.", HttpStatus.NOT_FOUND);
	
		ObjectMapper objectMapper = new ObjectMapper();
		String res = objectMapper.writeValueAsString(restaurant.getQueue());
		
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	
	@PostMapping
	public Restaurant create(@RequestBody Restaurant restaurant) {
		return restaurantRepository.save(restaurant);
	}
	
//	@PostMapping
//	public ResponseEntity<String> create(@RequestBody ObjectNode req) throws JsonProcessingException {
//		
//		Restaurant restaurant = new Restaurant();
//		
////		LocalDateTime localDateTime = LocalDateTime.parse(req.get("date").asText());
////		Date date = Timestamp.valueOf(localDateTime);
////		restaurant.setDate(date);
//
//		restaurant.setPeopleNumber(req.get("peopleNumber").asInt());
//		
//		restaurant.setRestaurantId(req.get("restaurantId").asLong());
//
//		if(restaurant == null) return new ResponseEntity<String>("Usuário não existe.", HttpStatus.NOT_FOUND);
//		
//		queueRepository.save(queue);
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//		String res = objectMapper.writeValueAsString(restaurant);
//
//		
//		return new ResponseEntity<String>(res, HttpStatus.CREATED);
//	}
	
	@GetMapping("/{restaurantId}")
	public Restaurant show(@PathVariable("restaurantId") Long restaurantId) {
		return restaurantRepository.findById(restaurantId).orElse(null);
	}
	
	@PutMapping("/{restaurantId}")
	public Restaurant update(@PathVariable("restaurantId") Long restaurantId,@RequestBody  Restaurant restaurant) {
		
		return restaurantRepository.findById(restaurantId)
			.map( r -> {	
				r.setName(restaurant.getName());
				r.setEmail(restaurant.getEmail());
				r.setPassword(restaurant.getPassword());
				r.setPhone(restaurant.getPhone());
				r.setAdress(restaurant.getAdress());
				r.setRating(restaurant.getRating());
				r.setAvailableTables(restaurant.getAvailableTables());
				r.setAvailableReservations(restaurant.getAvailableReservations());
				
				return restaurantRepository.save(r);
			}).orElse(null);
						
	}
	
	@DeleteMapping("/{restaurantId}")
	public void delete(@PathVariable("restaurantId") Long restaurantId) {
		restaurantRepository.deleteById(restaurantId);
	}

}
