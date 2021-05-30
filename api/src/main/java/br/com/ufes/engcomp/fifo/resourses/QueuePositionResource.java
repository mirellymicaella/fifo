package br.com.ufes.engcomp.fifo.resourses;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.ufes.engcomp.fifo.domain.QueuePosition;
import br.com.ufes.engcomp.fifo.domain.Restaurant;
import br.com.ufes.engcomp.fifo.domain.User;
import br.com.ufes.engcomp.fifo.repository.QueueRepository;
import br.com.ufes.engcomp.fifo.repository.RestaurantRepository;
import br.com.ufes.engcomp.fifo.repository.UserRepository;

@RestController
@RequestMapping("/queue_positions")
public class QueuePositionResource {
	@Autowired
	private QueueRepository queueRepository;
	@Autowired
	private UserRepository userRepository ;
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@GetMapping
	public List<QueuePosition> index(){
		return queueRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<String> create(@RequestBody ObjectNode req) throws JsonProcessingException, ParseException {
		
		User user = userRepository.findById(req.get("userId").asLong()).orElse(null);
		if(user == null) 
			return new ResponseEntity<String>("Usuário não existe.", HttpStatus.NOT_FOUND);
		
		Restaurant restaurant = restaurantRepository.findById(req.get("restaurantId").asLong()).orElse(null);
		if(restaurant == null) 
			return new ResponseEntity<String>("Restaurante não existe.", HttpStatus.NOT_FOUND);
		
		LocalDateTime localDateTime = LocalDateTime.parse(req.get("date").asText());
		Date date = Timestamp.valueOf(localDateTime);
		
		QueuePosition queue = new QueuePosition();

	    queue.setDate(date);
		queue.setPeopleNumber(req.get("peopleNumber").asInt());
		queue.setRestaurant(restaurant);
		queue.setUser(user);
		
		int position = 0;
		for(QueuePosition q: restaurant.getQueue())
			position += q.getPeopleNumber();
		
		queue.setPosition(position+ 1);
	
		queueRepository.save(queue);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		String res = objectMapper.writeValueAsString(queue);

		
		return new ResponseEntity<String>(res, HttpStatus.CREATED);
	}
	
	@GetMapping("/{queueId}")
	public ResponseEntity<String> show(@PathVariable("queueId") Long queueId) throws JsonProcessingException {
		QueuePosition queue = queueRepository.findById(queueId).orElse(null);
		if(queue == null) 
			return new ResponseEntity<String>("Posição de fila não existe.", HttpStatus.NOT_FOUND);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		String res = objectMapper.writeValueAsString(queue);
		
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	@PutMapping("/{queueId}")
	public ResponseEntity<String> update(@PathVariable("queueId") Long queueId,@RequestBody  QueuePosition q) throws JsonProcessingException {
		
		QueuePosition queue = queueRepository.findById(queueId)
			.map( r -> {	
				r.setDate(q.getDate());
				r.setPeopleNumber(q.getPeopleNumber());		
				
				return queueRepository.save(r);
			}).orElse(null);

		if(queue == null) 
			return new ResponseEntity<String>("Posição de fila não existe.", HttpStatus.NOT_FOUND);
			
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		String res = objectMapper.writeValueAsString(queue);
		
		return new ResponseEntity<String>(res, HttpStatus.OK);
						
	}
	
	@DeleteMapping("/{queueId}")
	public ResponseEntity<String> delete(@PathVariable("queueId") Long queueId) {
		QueuePosition queue = queueRepository.findById(queueId).orElse(null);
		if(queue == null) 
			return new ResponseEntity<String>("Posição de fila não existe.", HttpStatus.NOT_FOUND);
				
		queueRepository.deleteById(queueId);
		
		return new ResponseEntity<String>("Posição deletada com sucesso!", HttpStatus.OK);
		
	}
	
	
}
