import {Singer} from "../singers-view/singer"
import {Song} from "../songs-view/song"

export interface Turn {
	singer1 : Singer,
	singer2 : Singer,
	song : Song,
	completed : boolean
}