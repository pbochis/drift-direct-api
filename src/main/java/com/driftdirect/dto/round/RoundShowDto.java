package com.driftdirect.dto.round;

import com.driftdirect.dto.round.qualifier.QualifierShortDto;
import com.driftdirect.dto.round.track.TrackDto;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Paul on 11/14/2015
 */
public class RoundShowDto {
    private long id;
    private String name;
    private String liveStream;
    private DateTime startDate;
    private DateTime endDate;
    private String ticketsUrl;
    private TrackDto track;

    private QualifierShortDto currentDriver;

    private List<RoundScheduleEntryShowDto> schedule;

    private List<QualifierShortDto> qualifiers;

    public List<QualifierShortDto> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(List<QualifierShortDto> qualifiers) {
        this.qualifiers = qualifiers;
    }

    public List<RoundScheduleEntryShowDto> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<RoundScheduleEntryShowDto> schedule) {
        this.schedule = schedule;
    }

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

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public String getTicketsUrl() {
        return ticketsUrl;
    }

    public void setTicketsUrl(String ticketsUrl) {
        this.ticketsUrl = ticketsUrl;
    }

    public TrackDto getTrack() {
        return track;
    }

    public void setTrack(TrackDto track) {
        this.track = track;
    }

    public String getLiveStream() {
        return liveStream;
    }

    public void setLiveStream(String liveStream) {
        this.liveStream = liveStream;
    }

    public QualifierShortDto getCurrentDriver() {
        return currentDriver;
    }

    public void setCurrentDriver(QualifierShortDto currentDriver) {
        this.currentDriver = currentDriver;
    }
}
