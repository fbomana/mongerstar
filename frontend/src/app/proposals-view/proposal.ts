import {Singer} from "../singers-view/singer"
import {Song} from "../songs-view/song"

export interface Proposal {
    song : Song,
    singer : Singer
}