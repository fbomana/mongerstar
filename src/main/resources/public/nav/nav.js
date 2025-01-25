let Nav = function({ render }) {
	let state = {
		options:["queue", "songs"],
		selected: "queue",
		render
	}
	
	let representation = () => {
		let html = "";
		for ( const option of state.options ) {
			let divClass = option === state.selected ? "nav-selected": "nav-normal";
			html += `<div class='${divClass}'>${option}</div>`;
		}
		return html;
	}
	
	return representation;
}

export default Nav;
		
		