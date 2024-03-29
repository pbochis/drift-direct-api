package com.driftdirect.domain.round;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.file.File;
import com.driftdirect.domain.news.ImageLink;
import com.driftdirect.domain.round.playoff.PlayoffTree;
import com.driftdirect.domain.round.qualifiers.QualifiedDriver;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.domain.round.track.Track;
import org.hibernate.annotations.SortNatural;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Paul on 11/14/2015.
 */
@Entity
public class Round implements Comparable<Round> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String ticketsUrl;
    private String liveStream;

    @Column(name = "start_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startDate;

    @Column(name = "end_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime endDate;

    @OneToOne(fetch = FetchType.EAGER)
    private File logo;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Track track;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championship;

    @OneToOne
    private Qualifier currentDriver;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    @SortNatural
    private SortedSet<RoundScheduleEntry> scheduele = new TreeSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Qualifier> qualifiers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QualifiedDriver> qualifiedDrivers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoundDriverResult> roundResults = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    private PlayoffTree playoffTree;

    @OneToMany()
    @JoinTable(
            name = "round_gallery",
            joinColumns = {@JoinColumn(name = "round_id")},
            inverseJoinColumns = {@JoinColumn(name = "file_id")}
    )
    private List<File> gallery = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "round_highlights",
            joinColumns = {@JoinColumn(name = "round_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_link_id")}
    )
    private List<ImageLink> highlights = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "round_official_galleries",
            joinColumns = {@JoinColumn(name = "round_id")},
            inverseJoinColumns = {@JoinColumn(name = "image_link_id")}
    )
    private List<ImageLink> officialGalleries = new ArrayList<>();

    public Long getId() {
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

    public SortedSet<RoundScheduleEntry> getScheduele() {
        return scheduele;
    }

    public void setScheduele(SortedSet<RoundScheduleEntry> scheduele) {
        this.scheduele.clear();
        this.scheduele.addAll(scheduele);
    }

    public void addScheduele(RoundScheduleEntry scheduleEntry) {
        this.scheduele.add(scheduleEntry);
    }

    public void removeScheduele(RoundScheduleEntry scheduleEntry) {
        this.scheduele.remove(scheduleEntry);
    }

    public Championship getChampionship() {
        return championship;
    }

    public void setChampionship(Championship championship) {
        this.championship = championship;
    }

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    public String getTicketsUrl() {
        return ticketsUrl;
    }

    public void setTicketsUrl(String ticketsUrl) {
        this.ticketsUrl = ticketsUrl;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public String getLiveStream() {
        return liveStream;
    }

    public void setLiveStream(String liveStream) {
        this.liveStream = liveStream;
    }

    public List<Qualifier> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(List<Qualifier> qualifiers) {
        this.qualifiers.clear();
        this.qualifiers.addAll(qualifiers);
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public int compareTo(Round o) {
        if (id == o.getId()) {
            return 0;
        }
        if (startDate == null || o.getStartDate() == null) {
            return 0;
        }
        if (startDate.isBefore(o.getStartDate())) {
            return -1;
        }
        return 1;
    }

    public boolean isEnded() {
        return this.endDate.isBefore(DateTime.now());
    }

    public boolean isOngoing() {
        return this.startDate.isBefore(DateTime.now()) && this.endDate.isAfter(DateTime.now());
    }

    public boolean isFuture() {
        return this.startDate.isAfter(DateTime.now());
    }

    public PlayoffTree getPlayoffTree() {
        return playoffTree;
    }

    public void setPlayoffTree(PlayoffTree playoffTree) {
        this.playoffTree = playoffTree;
    }

    public List<QualifiedDriver> getQualifiedDrivers() {
        return qualifiedDrivers;
    }

    public void setQualifiedDrivers(List<QualifiedDriver> qualifiedDrivers) {
        this.qualifiedDrivers.addAll(qualifiedDrivers);
    }

    public List<RoundDriverResult> getRoundResults() {
        return roundResults;
    }

    public void setRoundResults(List<RoundDriverResult> roundResults) {
        this.roundResults.addAll(roundResults);
    }

    public Qualifier getCurrentDriver() {
        return currentDriver;
    }

    public void setCurrentDriver(Qualifier currentDriver) {
        this.currentDriver = currentDriver;
    }

    public List<File> getGallery() {
        return gallery;
    }

    public void setGallery(List<File> gallery) {
        this.gallery.clear();
        this.gallery.addAll(gallery);
    }

    public void addToGallery(File file) {
        this.gallery.add(file);
    }

    public List<ImageLink> getOfficialGalleries() {
        return officialGalleries;
    }

    public void setOfficialGalleries(List<ImageLink> officialGalleries) {
        this.officialGalleries.clear();
        this.officialGalleries.addAll(officialGalleries);
    }

    public List<ImageLink> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<ImageLink> highlights) {
        this.highlights.clear();
        this.highlights.addAll(highlights);
    }

    public void addHighlight(ImageLink highlight) {
        this.highlights.add(highlight);
    }

    public void addOfficialGallery(ImageLink officialGallery) {
        this.officialGalleries.add(officialGallery);
    }
}
