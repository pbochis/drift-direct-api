package com.driftdirect.service.round;

import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.championship.driver.DriverParticipation;
import com.driftdirect.domain.championship.driver.DriverParticipationResults;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.RoundDriverResult;
import com.driftdirect.domain.round.RoundScheduleEntry;
import com.driftdirect.domain.round.qualifiers.QualifiedDriver;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.domain.round.track.Track;
import com.driftdirect.dto.person.PersonShortShowDto;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.RoundShowDto;
import com.driftdirect.dto.round.RoundUpdateDto;
import com.driftdirect.dto.round.playoff.graphic.PlayoffTreeGraphicDisplayDto;
import com.driftdirect.dto.round.schedule.RoundScheduleEntryCreateDto;
import com.driftdirect.dto.round.schedule.RoundScheduleEntryUpdateDto;
import com.driftdirect.dto.round.track.TrackCreateDto;
import com.driftdirect.dto.round.track.TrackUpdateDto;
import com.driftdirect.mapper.PersonMapper;
import com.driftdirect.mapper.round.RoundMapper;
import com.driftdirect.mapper.round.playoff.PlayoffMapper;
import com.driftdirect.repository.FileRepository;
import com.driftdirect.repository.championship.driver.DriverParticipationRepository;
import com.driftdirect.repository.round.RoundDriverResultRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.RoundScheduleRepository;
import com.driftdirect.repository.round.qualifier.QualifiedDriverRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Paul on 11/14/2015.
 */
@Transactional
@Service
public class RoundService {
    private RoundRepository roundRepository;
    private RoundScheduleRepository roundScheduleRepository;
    private FileRepository fileRepository;
    private RoundDriverResultRepository roundDriverResultRepository;
    private QualifiedDriverRepository qualifiedDriverRepository;
    private DriverParticipationRepository driverParticipationRepository;

    @Autowired
    public RoundService(RoundDriverResultRepository roundDriverResultRepository,
                        RoundRepository roundRepository,
                        FileRepository fileRepository,
                        RoundScheduleRepository roundScheduleRepository,
                        QualifiedDriverRepository qualifiedDriverRepository,
                        DriverParticipationRepository driverParticipationRepository) {
        this.roundRepository = roundRepository;
        this.fileRepository = fileRepository;
        this.roundScheduleRepository = roundScheduleRepository;
        this.roundDriverResultRepository = roundDriverResultRepository;
        this.qualifiedDriverRepository = qualifiedDriverRepository;
        this.driverParticipationRepository = driverParticipationRepository;
    }

    public Round createFromDto(Championship c, RoundCreateDto dto) {
        Round round = new Round();
        round.setChampionship(c);
        return populateAndSave(round, dto);
    }

    public Round updateRound(RoundUpdateDto dto) {
        Round round = roundRepository.findOne(dto.getId());
        round.setName(dto.getName());
        round.setTicketsUrl(dto.getTicketsUrl());
        round.setLogo(fileRepository.findOne(dto.getLogo()));
        round.setLiveStream(dto.getLiveStream());
        populateTrack(round.getTrack(), dto.getTrack());
        updateRoundSchedule(round, dto);
        round = roundRepository.save(round);
        return round;
    }

    private void updateRoundSchedule(Round round, RoundUpdateDto dto) {
        SortedSet<RoundScheduleEntry> schedule = new TreeSet<>();
        DateTime startDate = null;
        DateTime endDate = null;
        for (RoundScheduleEntryUpdateDto entryUpdateDto : dto.getScheduele()) {
            RoundScheduleEntry entry;
            if (entryUpdateDto.getId() == null) {
                entry = new RoundScheduleEntry();
                entry.setRound(round);
            } else {
                entry = roundScheduleRepository.findOne(entryUpdateDto.getId());
            }
            entry.setName(entryUpdateDto.getName());
            entry.setStartDate(entryUpdateDto.getStartDate());
            entry.setEndDate(entryUpdateDto.getEndDate());
            schedule.add(roundScheduleRepository.save(entry));
            startDate = (startDate == null || entry.getStartDate().isBefore(startDate)) ? entry.getStartDate() : startDate;
            endDate = (endDate == null || entry.getEndDate().isAfter(endDate)) ? entry.getEndDate() : endDate;
        }
        round.setStartDate(startDate);
        round.setEndDate(endDate);
        round.setScheduele(schedule);
    }

    private Round populateAndSave(Round round, RoundCreateDto dto) {
        round.setName(dto.getName());
        round.setLogo(fileRepository.findOne(dto.getLogo()));
        round.setTicketsUrl(dto.getTicketsUrl());
        round.setLiveStream(dto.getLiveStream());
        round.setTrack(createTrack(dto.getTrack()));
        round = roundRepository.save(round);
        for (RoundScheduleEntryCreateDto schedule : dto.getScheduele()) {
            addRoundSchedule(round, schedule);
        }
        return round;
    }

    public RoundUpdateDto getEditModel(Long roundId) {
        Round round = roundRepository.findOne(roundId);
        RoundUpdateDto dto = new RoundUpdateDto();
        dto.setId(roundId);
        dto.setName(round.getName());
        dto.setLogo(round.getLogo().getId());
        dto.setTicketsUrl(round.getTicketsUrl());
        TrackUpdateDto track = new TrackUpdateDto();
        track.setId(round.getTrack().getId());
        track.setVideoUrl(round.getTrack().getVideoUrl());
        track.setJudgingCriteria(round.getTrack().getJudgingCriteria());
        track.setLayout(round.getTrack().getLayout().getId());
        track.setDescription(round.getTrack().getDescription());
        dto.setTrack(track);
        for (RoundScheduleEntry scheduleEntry : round.getScheduele()) {
            RoundScheduleEntryUpdateDto entryUpdateDto = new RoundScheduleEntryUpdateDto();
            entryUpdateDto.setId(scheduleEntry.getId());
            entryUpdateDto.setStartDate(scheduleEntry.getStartDate());
            entryUpdateDto.setEndDate(scheduleEntry.getEndDate());
            entryUpdateDto.setName(scheduleEntry.getName());
            dto.addRoundScheduleEntry(entryUpdateDto);
        }
        return dto;
    }

    private void populateTrack(Track track, TrackCreateDto trackCreateDto) {
        track.setDescription(trackCreateDto.getDescription());
        track.setLayout(fileRepository.findOne(trackCreateDto.getLayout()));
        track.setVideoUrl(trackCreateDto.getVideoUrl());
        track.setJudgingCriteria(trackCreateDto.getJudgingCriteria());
    }

    public Track createTrack(TrackCreateDto trackCreateDto) {
        Track track = new Track();
        populateTrack(track, trackCreateDto);
        return track;
    }

    public void addTrack(Round round, TrackCreateDto trackCreateDto) {
        Track track = new Track();
        track.setDescription(trackCreateDto.getDescription());
        track.setLayout(fileRepository.findOne(trackCreateDto.getLayout()));
        track.setVideoUrl(trackCreateDto.getVideoUrl());
        track.setJudgingCriteria(trackCreateDto.getJudgingCriteria());
        round.setTrack(track);
        roundRepository.save(round);
    }

    public void addRoundSchedule(Round round, RoundScheduleEntryCreateDto dto) {
        RoundScheduleEntry roundScheduleEntry = new RoundScheduleEntry();
        roundScheduleEntry.setName(dto.getName());
        roundScheduleEntry.setStartDate(dto.getStartDate());
        roundScheduleEntry.setEndDate(dto.getEndDate());
        roundScheduleEntry.setRound(round);
        roundScheduleEntry = roundScheduleRepository.save(roundScheduleEntry);
        updateRoundDates(round, roundScheduleEntry);
    }

    private void updateRoundDates(Round round, RoundScheduleEntry scheduleEntry) {
        boolean updateRound = false;
        if (round.getStartDate() == null && round.getEndDate() == null) {
            round.setStartDate(scheduleEntry.getStartDate());
            round.setEndDate(scheduleEntry.getEndDate());
            updateRound = true;
        } else if (round.getStartDate() != null && round.getStartDate().isAfter(scheduleEntry.getStartDate())) {
            round.setStartDate(scheduleEntry.getStartDate());
            updateRound = true;
        } else if (round.getEndDate() != null && round.getEndDate().isBefore(scheduleEntry.getEndDate())) {
            round.setEndDate(scheduleEntry.getEndDate());
            updateRound = true;
        }
        if (updateRound) {
            roundRepository.save(round);
        }
    }

    public void delete(Long id){
        roundRepository.delete(id);
    }

    public List<PersonShortShowDto> getPersonsForRegisterDesk(Long roundId){
        Round round = roundRepository.findOne(roundId);
        List<Person> drivers = round.getChampionship().getDrivers().stream().map(e -> e.getDriver()).collect(Collectors.toList());
        for (Qualifier qualifier: round.getQualifiers()){
            drivers.remove(qualifier.getDriver());
        }
        return drivers.stream().map(PersonMapper::mapShort).collect(Collectors.toList());
    }

    public RoundShowDto findRound(long id){
        Round round = roundRepository.findOne(id);
        Championship c = round.getChampionship();
        List<Qualifier> qualifiers = round.getQualifiers();
        qualifiers.sort(new Comparator<Qualifier>() {
            @Override
            public int compare(Qualifier o1, Qualifier o2) {
                DriverParticipation p1 = driverParticipationRepository.findByChampionshipIdAndDriverId(c.getId(), o1.getDriver().getId());
                DriverParticipation p2 = driverParticipationRepository.findByChampionshipIdAndDriverId(c.getId(), o2.getDriver().getId());
                //compare p2 to p1 because we need reverse
                return p1.compareTo(p2);
            }
        });
        List<Qualifier> results = new ArrayList<>(round.getQualifiers());
        results.sort((q1, q2) -> {
            float dif = q2.getFinalScore() - q1.getFinalScore();
            if (dif != 0) {
                return dif > 0 ? 1 : -1;
            }
            dif = Math.min(q2.getFirstRun().getTotalPoints(), q2.getSecondRun().getTotalPoints()) - Math.min(q1.getFirstRun().getTotalPoints(), q1.getSecondRun().getTotalPoints());
            if (dif != 0) {
                return dif > 0 ? 1 : -1;
            }
            float driver1ChampPoints = getChampionshipPoints(q1);
            float driver2ChampPoints = getChampionshipPoints(q2);
            return ((driver2ChampPoints - driver1ChampPoints) > 0) ? 1 : -1;
        });
        return RoundMapper.map(round, qualifiers, results);
    }

    public boolean finishQualifiers(Long roundId) {
        //TODO:  need to lock this so that there are no duplicates generated.
        Round round = roundRepository.findOne(roundId);
        // replace this magic number with a minimum size for qualified drivers
        if (round.getQualifiers().size() < 16)
            return false;
        for (Qualifier qualifier : round.getQualifiers()) {
            if (qualifier.getFirstRun().getJudgings().size() != 3 || qualifier.getSecondRun().getJudgings().size() != 3) {
                return false;
            }
        }
        Championship championship = round.getChampionship();
        List<Qualifier> qualifiers = round.getQualifiers();
        qualifiers.sort((o1, o2) -> {
            float dif = o2.getFinalScore() - o1.getFinalScore();
            if (dif != 0) {
                return dif > 0 ? 1 : -1;
            }
            dif = Math.min(o2.getFirstRun().getTotalPoints(), o2.getSecondRun().getTotalPoints()) - Math.min(o1.getFirstRun().getTotalPoints(), o1.getSecondRun().getTotalPoints());
            if (dif != 0) {
                return dif > 0 ? 1 : -1;
            }
            float driver1ChampPoints = getChampionshipPoints(o1);
            float driver2ChampPoints = getChampionshipPoints(o2);
            return driver2ChampPoints - driver1ChampPoints > 0 ? 1 : -1;
        });

        for (int i = 0; i < qualifiers.size(); i++) {
            Qualifier driver = qualifiers.get(i);
            int place = i + 1;
            boolean dnq = true;
            if (place <= championship.getPlayoffSize() && driver.getFinalScore() > 0) {
                createQualifiedDriver(driver.getDriver(), place, round);
                dnq = false;
            }
            createRoundResult(driver.getDriver(), place, round, dnq);
        }
        return true;
    }

    private float getChampionshipPoints(Qualifier qualifier) {
        Championship c = qualifier.getRound().getChampionship();
        DriverParticipation participation = driverParticipationRepository.findByChampionshipIdAndDriverId(c.getId(), qualifier.getDriver().getId());
        if (participation == null) {
            return 0;
        }
        DriverParticipationResults results = participation.getResults();
        if (results == null) {
            return 0;
        }
        return results.getTotalPoints();
    }

    private void createRoundResult(Person driver, int place, Round round, boolean dnq) {
        RoundDriverResult roundResult = new RoundDriverResult();
        roundResult.setDriver(driver);
        roundResult.setQualifierRanking(place);
        roundResult.setRound(round);
        roundResult.setQualifierPoints(getPointsForRanking(place, dnq));
        roundDriverResultRepository.save(roundResult);
    }

    public float getPointsForRanking(int place, boolean dnq) {
        if (dnq)
            return 1;
        if (place == 1)
            return 7;
        if (place == 2)
            return 6;
        if (place == 3)
            return 5;
        if (place >= 4 && place <= 8)
            return 4;
        if (place >= 9 && place <= 16)
            return 3;
        if (place >= 17 && place <= 32)
            return 2;
        return 0;
    }

    private void createQualifiedDriver(Person driver, int ranking, Round round) {
        QualifiedDriver qualifiedDriver = new QualifiedDriver();
        qualifiedDriver.setDriver(driver);
        qualifiedDriver.setRanking(ranking);
        qualifiedDriver.setRound(round);
        qualifiedDriverRepository.save(qualifiedDriver);
    }

    public PlayoffTreeGraphicDisplayDto getPlayoffs(Long roundId) {
        Round round = roundRepository.findOne(roundId);
        return PlayoffMapper.mapPlayoffForDisplay(round.getPlayoffTree());
    }
}
