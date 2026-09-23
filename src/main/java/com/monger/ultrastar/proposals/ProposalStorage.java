package com.monger.ultrastar.proposals;

import com.monger.ultrastar.singer.Singer;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ProposalStorage {
    private Set<Proposal> proposals;

    public ProposalStorage() {
        proposals = new HashSet<>();
    }

    public List<Proposal> getProposals() {
        return this.proposals.stream().toList();
    }

    public void addProposal( Proposal proposal ) {
        proposals.add( proposal );
    }

    public void removeProposal( Proposal proposal ) {
        proposals.remove( proposal );
    }

    public void removeProposals( Singer singer ) {
        proposals.removeAll( proposals.stream().filter( (p)->p.singer().equals( singer )).toList() );
    }

}
