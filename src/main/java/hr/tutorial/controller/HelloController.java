package hr.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import hr.tutorial.model.Greeting;

@EnableScheduling
@Controller
public class HelloController {
	@Autowired
	private SimpMessagingTemplate template;

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting hello(String message) {
		Greeting greeting = new Greeting("Hello ! " + message);
		System.out.println(greeting);

		this.template.convertAndSend("/topic/greetings", new Greeting("just a minute..."));
		this.template.convertAndSend("/topic/greetings", new Greeting("just a minute..."));
		this.template.convertAndSend("/topic/greetings", new Greeting("just a minute..."));
		return greeting;
	}

	@GetMapping("/tutorial")
	public String root() {
		return "tutorial/index.html";
	}

	@Scheduled(fixedRate = 5000)
	public void hello() throws Exception {
		System.out.println("hello");
		this.template.convertAndSend("/topic/greetings", new Greeting("Hello !"));
	}
}
