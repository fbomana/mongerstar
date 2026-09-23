import { Injectable } from '@angular/core';
import { getEndpointUrl } from '../utils';
import { Proposal } from './proposal';


@Injectable({
  providedIn: 'root'
})
export class ProposalsService {
    proposalsEndPoint : string = "/api/proposals"

    constructor() {
    }

    async getProposals():Promise<Proposal[]> {
        const data = await fetch(
            getEndpointUrl( this.proposalsEndPoint ),
            {
                method : "GET",
                cache : "no-store"
            }
        );
        return ( await data.json()) ?? [];
    }

    async removeProposal( proposal : Proposal ) {
        const data = await fetch(
            getEndpointUrl( this.proposalsEndPoint ),
            {
                method : "POST",
                body: JSON.stringify( proposal ),
                headers: {
                    "Content-Type": "application/json",
                },
                cache : "no-store"
            }
        );
    }
}