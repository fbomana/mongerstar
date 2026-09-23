package com.monger.ultrastar.proposals;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.singer.SingerStorage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proposals")
public class ProposalController {
    private final ProposalService service;
    private final SingerStorage singers;

    public ProposalController( ProposalService service, SingerStorage singers  ) {
        this.service = service;
        this.singers = singers;
    }

    @GetMapping("")
    public List<Proposal> getProposals() {
        return service.get();
    }

    @PostMapping("")
    public void deleteProposal( @RequestBody RemoveProposalRequest request ) {
        Singer singer = singers.getSinger( request.singer().getName());
        service.remove( new Proposal( request.song(), singer ));
    }
}
