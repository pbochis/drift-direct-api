package com.driftdirect.dto.round;


import com.driftdirect.dto.round.track.TrackCreateDto;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Paul on 11/14/2015.
 */
public class RoundCreateDto {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    private Long logo;

    @NotNull
    @NotEmpty
    private String ticketsUrl;

    private TrackCreateDto track;

    private List<RoundScheduleEntryCreateDto> scheduele;

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicketsUrl() {
        return ticketsUrl;
    }

    public void setTicketsUrl(String ticketsUrl) {
        this.ticketsUrl = ticketsUrl;
    }

    public List<RoundScheduleEntryCreateDto> getScheduele() {
        return scheduele;
    }

    public void setScheduele(List<RoundScheduleEntryCreateDto> scheduele) {
        this.scheduele = scheduele;
    }

    public TrackCreateDto getTrack() {
        return track;
    }

    public void setTrack(TrackCreateDto track) {
        this.track = track;
    }
}
