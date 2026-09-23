package com.monger.ultrastar.proposals;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.song.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProposalServiceTest {

    private ProposalStorage storage;
    private ProposalService service;

    @BeforeEach
    public void setUp() {
        storage = new ProposalStorage();
        service = new ProposalService( storage );
    }

    @Test
    public void add_throwsExceptionIfSongIsNull() {
        Song song = null;
        Singer singer = new Singer("singer", 0);

        Exception e = assertThrows( RuntimeException.class, () -> service.add( song, singer ));
        assertEquals( "Must indicate a song", e.getMessage());
    }

    @Test
    public void add_throwsExceptionIfSingerIsNull() {
        Song song = new Song("title", "author", "language");
        Singer singer = null;

        Exception e = assertThrows( RuntimeException.class, () -> service.add( song, singer ));
        assertEquals( "Must indicate a singer", e.getMessage());
    }

    @Test
    public void add_aProposalIfBothSongAndSingerAreNotNull() {
        Song song = new Song("title", "author", "language");
        Singer singer = new Singer("singer", 0);

        service.add( song, singer );
        List<Proposal> proposals = storage.getProposals();
        assertFalse( proposals.isEmpty());
        assertEquals( new Proposal( song, singer), proposals.get(0));
    }

    @Test
    public void add_doesNothingIfTheProposalAlreadyExists() {
        Song song = new Song("title", "author", "language");
        Singer singer = new Singer("singer", 0);

        service.add( song, singer );
        List<Proposal> proposals = storage.getProposals();
        assertFalse( proposals.isEmpty());
        assertEquals( 1, proposals.size());

        service.add( song, singer );
        proposals = storage.getProposals();
        assertFalse( proposals.isEmpty());
        assertEquals( 1, proposals.size());
    }

    @Test
    public void remove_throwsExceptionIfProposalIsNull() {
        Exception e = assertThrows( RuntimeException.class, ()->service.remove( (Proposal)null ));
        assertEquals( "Proposal must not be null", e.getMessage());
    }

    @Test
    public void remove_removesTheProposalFromTheList() {
        Song song = new Song("title", "author", "language");
        Singer singer = new Singer("singer", 0);
        service.add( song, singer );

        List<Proposal> proposals = storage.getProposals();
        assertFalse( proposals.isEmpty() );

        service.remove( new Proposal( song, singer ));
        proposals = storage.getProposals();
        assertTrue( proposals.isEmpty() );
    }

    @Test
    public void remove_throwsExceptionIfSingerIsNull() {
        Exception e = assertThrows( RuntimeException.class, ()-> service.remove( (Singer)null ));
        assertEquals( "Singer must not be null", e.getMessage());
    }

    @Test
    public void remove_removesAllProposalsFromTheSinger() {
        Song song = new Song("title", "author", "language");
        Singer singer = new Singer("singer", 0);
        service.add( song, singer );

        List<Proposal> proposals = storage.getProposals();
        assertFalse( proposals.isEmpty() );

        service.remove( singer );
        proposals = storage.getProposals();
        assertTrue( proposals.isEmpty() );
    }

    @Test
    public void get_returnsTheListOfProposalsStored() {
        Song song = new Song("title", "author", "language");
        Singer singer = new Singer("singer", 0);
        service.add( song, singer );

        List<Proposal> expected = storage.getProposals();
        assertEquals( expected, service.get());
    }

}
