package com.monger.ultrastar.proposals;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.song.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalService {
    private final ProposalStorage storage;

    @Autowired
    public ProposalService( ProposalStorage storage ) {
        this.storage = storage;
    }

    public void add(Song song, Singer singer ) {
        if ( song == null ) {
            throw new RuntimeException("Must indicate a song");
        }
        if ( singer == null ) {
            throw new RuntimeException("Must indicate a singer");
        }

        storage.addProposal( new Proposal( song, singer ));
    }

    public void remove( Proposal proposal ) {
        if ( proposal == null ) {
            throw new RuntimeException("Proposal must not be null");
        }
        storage.removeProposal( proposal );
    }

    public void remove( Singer singer ) {
        if ( singer == null ) {
            throw new RuntimeException("Singer must not be null");
        }
        storage.removeProposals( singer );
    }

    public List<Proposal> get() {
        return storage.getProposals();
    }
}
