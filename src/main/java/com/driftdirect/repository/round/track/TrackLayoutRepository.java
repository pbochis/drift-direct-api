package com.driftdirect.repository.round.track;

import com.driftdirect.domain.round.track.Track;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 12/13/2015.
 */
public interface TrackLayoutRepository extends JpaRepository<Track, Long> {
}
