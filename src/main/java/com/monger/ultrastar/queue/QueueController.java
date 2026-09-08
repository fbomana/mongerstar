package com.monger.ultrastar.queue;

import java.util.List;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.singer.SingerStorage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queue")
public class QueueController {
	
	private final UltrastarQueue queue;
	private final SingerStorage singerStorage;
	
	public QueueController(UltrastarQueue queue, SingerStorage singerStorage) {
		this.queue = queue;
		this.singerStorage = singerStorage;
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
		Singer singer1 = singerStorage.getSinger( request.singer1() );
		Singer singer2 = singerStorage.getSinger( request.singer2() );

		queue.add( singer1, singer2, request.song() );
	}
	
	@PostMapping( value="/next")
	public void nextTurn()  {
		queue.nextTurn();
	}

	@PostMapping( value="/delay")
	public void delayTurn() {
		queue.delayTurn();
	}

	@PostMapping( value="/remove")
	public void delayTurn(@RequestBody Turn turn) {
		queue.removeTurn( turn );
	}
}
