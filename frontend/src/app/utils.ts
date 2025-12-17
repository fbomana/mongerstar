export function getEndpointUrl( endpoint : string ) : string {
	let current = window.location.href;
	let serverPart = current.substring( 0, current.lastIndexOf(":"));
	return serverPart + ":8080" + endpoint;
}