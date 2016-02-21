package com.driftdirect.dto.round;

import com.driftdirect.dto.round.schedule.RoundScheduleEntryUpdateDto;
import com.driftdirect.dto.round.track.TrackUpdateDto;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 11/14/2015.
 */
public class RoundUpdateDto {
    private long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    private Long logo;

    private String liveStream;

    @NotNull
    @NotEmpty
    private String ticketsUrl;

    private TrackUpdateDto track;

    private List<RoundScheduleEntryUpdateDto> scheduele = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    public String getTicketsUrl() {
        return ticketsUrl;
    }

    public void setTicketsUrl(String ticketsUrl) {
        this.ticketsUrl = ticketsUrl;
    }

    public TrackUpdateDto getTrack() {
        return track;
    }

    public void setTrack(TrackUpdateDto track) {
        this.track = track;
    }

    public List<RoundScheduleEntryUpdateDto> getScheduele() {
        return scheduele;
    }

    public void setScheduele(List<RoundScheduleEntryUpdateDto> scheduele) {
        this.scheduele = scheduele;
    }

    public void addRoundScheduleEntry(RoundScheduleEntryUpdateDto entry) {
        this.scheduele.add(entry);
    }

    public String getLiveStream() {
        return liveStream;
    }

    public void setLiveStream(String liveStream) {
        this.liveStream = liveStream;
    }
}
