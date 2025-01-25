package com.monger.ultrastar.queue;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
}
