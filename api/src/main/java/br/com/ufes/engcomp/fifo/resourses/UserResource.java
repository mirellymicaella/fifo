package br.com.ufes.engcomp.fifo.resourses;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.ufes.engcomp.fifo.domain.Adress;
import br.com.ufes.engcomp.fifo.domain.User;
import br.com.ufes.engcomp.fifo.repository.AdressRepository;
import br.com.ufes.engcomp.fifo.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AdressRepository adressRepository;
	
	@GetMapping
	public List<User> index(){
		return userRepository.findAll();
	}
	
	@GetMapping("/reservations/{userId}")
	public  ResponseEntity<String> reservations(@PathVariable("userId") Long userId) throws JsonProcessingException{
		User user = userRepository.findById(userId).orElse(null);
		
		if(user == null)
			return new ResponseEntity<String>("Usuário não existe.", HttpStatus.NOT_FOUND);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		String res = objectMapper.writeValueAsString(user.getReservations());
		
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	@GetMapping("/queues/{userId}")
	public  ResponseEntity<String> queues(@PathVariable("userId") Long userId)  throws JsonProcessingException{
		User user = userRepository.findById(userId).orElse(null);
		
		if(user == null)
			return new ResponseEntity<String>("Usuário não existe.", HttpStatus.NOT_FOUND);
	
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		String res = objectMapper.writeValueAsString(user.getQueuesPositions());
		
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
		
	@PostMapping
	public ResponseEntity<String> create(@RequestBody ObjectNode req) throws JsonProcessingException {
		Adress adress = adressRepository.findById(req.get("adressId").asLong()).orElse(null);

		if(adress == null)
			return new ResponseEntity<String>("Endereço não existe.", HttpStatus.NOT_FOUND);
		
		User user = new User();
		
		user.setName(req.get("name").asText());
		user.setEmail(req.get("email").asText());		
		user.setPassword(req.get("password").asText());
		user.setPhone(req.get("phone").asText());
		user.setAdress(adress);
		userRepository.save(user);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		String res = objectMapper.writeValueAsString(user);
		
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<String> show(@PathVariable("userId") Long userId) throws JsonProcessingException {
		User user = userRepository.findById(userId).orElse(null);
		
		if(user == null)
			return new ResponseEntity<String>("Usuário não existe.", HttpStatus.NOT_FOUND);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		String res = objectMapper.writeValueAsString(user);
		
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<String> update(@PathVariable("userId") Long userId,@RequestBody  User u) throws JsonProcessingException { 
		User user = userRepository.findById(userId).orElse(null);
		
		if(user == null)
			return new ResponseEntity<String>("Usuário não existe.", HttpStatus.NOT_FOUND);
		
		user.setName(u.getName());
		user.setEmail(u.getEmail());
		user.setPassword(u.getPassword());
		user.setPhone(u.getPhone());
		user.setAdress(u.getAdress());
				
		userRepository.save(user);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss"));
		String res = objectMapper.writeValueAsString(user);
		
		return new ResponseEntity<String>(res, HttpStatus.OK);	
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String>  delete(@PathVariable("userId") Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		if(user == null)
			return new ResponseEntity<String>("Usuário não existe.", HttpStatus.NOT_FOUND);
		
		userRepository.deleteById(userId);
		
		return new ResponseEntity<String>("Usuário deletado com sucesso!", HttpStatus.OK);
	}
			
}
