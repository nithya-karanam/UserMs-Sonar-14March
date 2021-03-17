package com.infy.UserMS;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.infy.UserMS.dto.CartDTO;
import com.infy.UserMS.entity.CartEntity;
import com.infy.UserMS.repository.CartRepository;

@SpringBootApplication
public class UserMsApplication {

//@GetMapping(value="/publish/{message}")
//public String publish(@PathVariable("message") String msg) {
////	msg=this.message;
//	kafkaTemplate.send(topic,msg);
//	return "Published Successfully";
//}
	public static void main(String[] args) {
		SpringApplication.run(UserMsApplication.class, args);
	}

}
