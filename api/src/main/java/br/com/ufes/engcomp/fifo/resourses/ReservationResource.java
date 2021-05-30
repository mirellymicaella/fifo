package br.com.ufes.engcomp.fifo.resourses;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.ufes.engcomp.fifo.domain.Reservation;
import br.com.ufes.engcomp.fifo.domain.Restaurant;
import br.com.ufes.engcomp.fifo.domain.User;
import br.com.ufes.engcomp.fifo.repository.ReservationRepository;
import br.com.ufes.engcomp.fifo.repository.RestaurantRepository;
import br.com.ufes.engcomp.fifo.repository.UserRepository;

@RestController
@RequestMapping("/reservations")
public class ReservationResource {
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private UserRepository userRepository ;
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@GetMapping
	public List<Reservation> index(){
		return reservationRepository.findAll();
	}
		
	@PostMapping
	public ResponseEntity<String>  create(@RequestBody ObjectNode req) throws JsonProcessingException {
		
		Restaurant restaurant = restaurantRepository.findById(req.get("restaurantId").asLong()).orElse(null);
		if(restaurant == null)
			return new ResponseEntity<String>("Restaurante não existe.", HttpStatus.NOT_FOUND);
		
		User user = userRepository.findById(req.get("userId").asLong()).orElse(null);
		if(user == null) 
			return new ResponseEntity<String>("Usuário não existe.", HttpStatus.NOT_FOUND);
		
		LocalDateTime localDateTime = LocalDateTime.parse(req.get("date").asText());
		Date date = Timestamp.valueOf(localDateTime);
	
		Reservation reservation = new Reservation();
		
		reservation.setDate(date);
   		reservation.setPeopleNumber(req.get("peopleNumber").asInt());
		reservation.setRestaurant(restaurant);
		reservation.setUser(user);
		
		reservationRepository.save(reservation);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		String res = objectMapper.writeValueAsString(reservation);

		return new ResponseEntity<String>(res, HttpStatus.CREATED);
	}
		
	@GetMapping("/{reservationId}")
	public ResponseEntity<String> show(@PathVariable("reservationId") Long reservationId) throws JsonProcessingException {
		Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
		if(reservation == null)
			return new ResponseEntity<String>("Reserva não existe.", HttpStatus.NOT_FOUND);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		String res = objectMapper.writeValueAsString(reservation);

		return new ResponseEntity<String>(res, HttpStatus.CREATED);
	}
	
	@PutMapping("/{reservationId}")
	public ResponseEntity<String>  update(@PathVariable("reservationId") Long reservationId,@RequestBody  Reservation r) throws JsonProcessingException {
		Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
		if(reservation == null)
			return new ResponseEntity<String>("Reserva não existe.", HttpStatus.NOT_FOUND);
		
		reservation.setDate(r.getDate());
		reservation.setPeopleNumber(r.getPeopleNumber());		
				
		reservationRepository.save(reservation);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		String res = objectMapper.writeValueAsString(reservation);

		return new ResponseEntity<String>(res, HttpStatus.CREATED);
						
	}
	
	@DeleteMapping("/{reservationId}")
	public  ResponseEntity<String> delete(@PathVariable("reservationId") Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
		if(reservation == null)
			return new ResponseEntity<String>("Reserva não existe.", HttpStatus.NOT_FOUND);
		
		reservationRepository.deleteById(reservationId);
		return new ResponseEntity<String>("Reserva deletada com sucesso!", HttpStatus.OK);
		
	}
	
	
}
