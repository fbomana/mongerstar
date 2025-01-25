let Queue = function({ render }) {
	let state = {
		queue:[],
		render
	}

	let representation = () => { 
		return `<ul>${printQueueRows( state.queue )}</ul>`
	}

	let loadQueue = () => {
		fetch('http://localhost:8080/queue')
			.then(response => response.json())
			.then( json => state.queue = json)
			.then( a => state.render( representation() ))
			.catch(err => console.log('Solicitud fallida', err));
	}
	
	loadQueue();
	window.setInterval( loadQueue, 10000 );

  return representation
}

let printQueueRows = (queue) =>{
return `
${queue.map( turn => `<li class="${ turn.completed ? 'completa':'pendiente'}">${ turn.song.title }<br>${turn.singer1.name} - ${turn.singer2.name}</li>`).reduce((ac, cv) => ac + cv, '')}
`}

export default Queue;