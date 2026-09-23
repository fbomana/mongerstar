package com.monger.ultrastar.queue;

import java.util.List;

import com.monger.ultrastar.proposals.ProposalService;
import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.singer.SingerStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/queue")
public class QueueController {
	
	private final UltrastarQueue queue;
	private final SingerStorage singerStorage;
	private final QueueUpdatesEventManager updates;
	private final ProposalService proposalService;

	private final Logger logger = LoggerFactory.getLogger( QueueController.class );
	public QueueController(UltrastarQueue queue, SingerStorage singerStorage, QueueUpdatesEventManager updates, ProposalService proposalService ) {
		this.queue = queue;
		this.singerStorage = singerStorage;
		this.updates = updates;
		this.proposalService = proposalService;
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

		if ( request.singer2() == null ) {
			proposalService.add( request.song(), singer1 );
		}
		else {
			Singer singer2 = singerStorage.getSinger( request.singer2() );
			queue.add(singer1, singer2, request.song());
			updates.submitQueueUpdateEvent();
		}
	}
	
	@PostMapping( value="/next")
	public void nextTurn()  {
		queue.nextTurn();
		updates.submitQueueUpdateEvent();
	}

	@PostMapping( value="/delay")
	public void delayTurn() {
		queue.delayTurn();
		updates.submitQueueUpdateEvent();
	}

	@PostMapping( value="/remove")
	public void delayTurn(@RequestBody Turn turn) {
		queue.removeTurn( turn );
		updates.submitQueueUpdateEvent();
	}

	@GetMapping(value = "/events/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter streamEvents(@PathVariable("id") String id ) {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		updates.subscribe( emitter, id );
		return emitter;
	}

	@CrossOrigin
	@GetMapping(value = "/events/unsubscribe/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public void unsubscribeEvents(@PathVariable("id") String id ) {
		logger.info("Unsubscribe {}", id );
		updates.unsubscribe( id );
	}
}
