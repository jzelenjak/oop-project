package nl.tudelft.oopp.livechat.controllers;

import nl.tudelft.oopp.livechat.services.LectureSpeedService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vote")
public class UserLectureVotingController {

    private final LectureSpeedService service;

    /**
     * Constructor for lecture voting controller.
     * @param service lecture speed service
     */
    public UserLectureVotingController(LectureSpeedService service) {
        this.service = service;
    }

    // TODO reconsider user authentication
    /**
     * PUT Endpoint to vote on lecture speed.
     * @param uuid the id of the lecture
     * @param uid the id of the user
     * @param speed the type of the vote
     * @return 0 if successful, -1 otherwise
     */
    @PutMapping("/lectureSpeed")
    public int voteOnLectureSpeed(@RequestParam long uid, @RequestParam UUID uuid,
                                   @RequestBody String speed) {
        return service.setUserLectureSpeedVote(uid,uuid,speed);
    }

    /**
     * DELETE Endpoint to delete any question from the database (done by a moderator).
     * @param uuid the id of the lecture
     * @param modkey the moderator key
     * @return 0 if successful, -1 otherwise
     */
    @DeleteMapping("/resetLectureSpeedVote/{UUID}/{modkey}")
    public int delete(@PathVariable("modkey") UUID modkey, @PathVariable("UUID") UUID uuid) {
        return service.resetLectureSpeed(uuid, modkey);
    }
}
