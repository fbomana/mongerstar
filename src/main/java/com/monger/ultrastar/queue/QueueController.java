package com.monger.ultrastar.queue;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queue")
public class QueueController {
	
	private UltrastarQueue queue;
	
	public QueueController( UltrastarQueue queue ) {
		this.queue = queue;
	}

	@GetMapping("")
	public List<Turn> getTurns() {
		return queue.getTurns();
	}
	
	@GetMapping("/turn")
	public Turn getTurn() {
		return queue.getCurrentTurn();
	}
	
	@PostMapping( value="/turn",consumes =  MediaType.APPLICATION_JSON_VALUE) 
	public void addTurn(  @RequestBody NewTurnRequest request ) {
		queue.add(request.singer1(), request.singer2(), request.song() );
	}
	
	@PostMapping( value="/next")
	public void nextTurn()  {
		queue.nextTurn();
	}
}
