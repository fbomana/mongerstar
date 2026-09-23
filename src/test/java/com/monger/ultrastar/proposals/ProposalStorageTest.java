package com.monger.ultrastar.proposals;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.song.Song;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProposalStorageTest {

    @Test
    public void newProposalStorageIsCreatedEmptyu() {
        ProposalStorage storage = new ProposalStorage();
        List<Proposal> proposals = storage.getProposals();
        assertNotNull( proposals );
        assertTrue( proposals.isEmpty() );
    }

    @Test
    public void addProposal_addsANewProposalToTheStore() {
        Song song = new Song("title", "author", "language");
        Singer singer = new Singer( "singer", 0 );
        Proposal proposal = new Proposal( song, singer );
        ProposalStorage storage = new ProposalStorage();
        storage.addProposal( proposal );
        List<Proposal> proposals = storage.getProposals();
        assertNotNull( proposals );
        assertEquals( 1, proposals.size());
        assertEquals( proposal, proposals.get( 0 ));
    }

    @Test
    public void addProposal_onlyKeepsOneIfTheSameProposalIsAddedTwice() {
        Song song = new Song("title", "author", "language");
        Singer singer = new Singer( "singer", 0 );
        Proposal proposal = new Proposal( song, singer );
        ProposalStorage storage = new ProposalStorage();
        storage.addProposal( proposal );
        storage.addProposal( proposal );
        List<Proposal> proposals = storage.getProposals();
        assertNotNull( proposals );
        assertEquals( 1, proposals.size());
        assertEquals( proposal, proposals.get( 0 ));
    }

    @Test
    public void removeProposal_doesNothingIfTheStorageIsEmpty() {
        Song song = new Song("title", "author", "language");
        Singer singer = new Singer( "singer", 0 );
        Proposal proposal = new Proposal( song, singer );
        ProposalStorage storage = new ProposalStorage();
        storage.removeProposal( proposal );
        List<Proposal> proposals = storage.getProposals();
        assertNotNull( proposals );
        assertTrue( proposals.isEmpty() );
    }

    @Test
    public void removeProposal_doesNothingIfThePropposalIsNotInTheStorage() {
        Song song = new Song("title", "author", "language");
        Song song2 = new Song("title2", "author2", "language");
        Singer singer = new Singer( "singer", 0 );
        Proposal proposal = new Proposal( song, singer );
        Proposal proposal2 = new Proposal( song2, singer );
        ProposalStorage storage = new ProposalStorage();
        storage.addProposal( proposal );
        storage.removeProposal( proposal2 );
        List<Proposal> proposals = storage.getProposals();
        assertNotNull( proposals );
        assertEquals( 1, proposals.size());
        assertEquals( proposal, proposals.get( 0 ));
    }

    @Test
    public void removeProposal_removesTheProposalIfIsThere() {
        Song song = new Song("title", "author", "language");
        Song song2 = new Song("title2", "author2", "language");
        Singer singer = new Singer( "singer", 0 );
        Proposal proposal = new Proposal( song, singer );
        Proposal proposal2 = new Proposal( song2, singer );
        ProposalStorage storage = new ProposalStorage();
        storage.addProposal( proposal );
        storage.addProposal( proposal2 );
        storage.removeProposal( proposal2 );
        List<Proposal> proposals = storage.getProposals();
        assertNotNull( proposals );
        assertEquals( 1, proposals.size());
        assertEquals( proposal, proposals.get( 0 ));
    }

    @Test
    public void removeProposals_removesAllProposalsOfTheSinger() {
        Song song = new Song("title", "author", "language");
        Song song2 = new Song("title2", "author2", "language");
        Singer singer = new Singer( "singer", 0 );
        Singer singer1 = new Singer( "singer1", 0 );
        Proposal proposal = new Proposal( song, singer );
        Proposal proposal2 = new Proposal( song2, singer );
        Proposal proposal3 = new Proposal( song2, singer1 );
        ProposalStorage storage = new ProposalStorage();
        storage.addProposal( proposal );
        storage.addProposal( proposal2 );
        storage.addProposal( proposal3 );
        storage.removeProposals( singer );
        List<Proposal> proposals = storage.getProposals();
        assertNotNull( proposals );
        assertEquals( 1, proposals.size());
        assertEquals( proposal3, proposals.get( 0 ));
    }


}
