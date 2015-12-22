package com.driftdirect.config;

import com.driftdirect.domain.ConfigSetting;
import com.driftdirect.domain.Country;
import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.championship.ChampionshipRules;
import com.driftdirect.domain.championship.driver.DriverParticipation;
import com.driftdirect.domain.championship.driver.DriverParticipationResults;
import com.driftdirect.domain.championship.judge.JudgeType;
import com.driftdirect.domain.driver.DriverDetails;
import com.driftdirect.domain.driver.Team;
import com.driftdirect.domain.file.File;
import com.driftdirect.domain.news.News;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.person.PersonType;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.sponsor.Sponsor;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.championship.judge.JudgeParticipationCreateDto;
import com.driftdirect.dto.championship.judge.PointsAllocationCreateDto;
import com.driftdirect.dto.round.RoundScheduleCreateDto;
import com.driftdirect.dto.round.track.TrackCreateDto;
import com.driftdirect.dto.user.UserCreateDTO;
import com.driftdirect.repository.*;
import com.driftdirect.repository.championship.ChampionshipRepository;
import com.driftdirect.repository.championship.driver.DriverParticipationRepository;
import com.driftdirect.repository.championship.driver.DriverParticipationResultsRepository;
import com.driftdirect.repository.championship.judge.JudgeParticipationRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.track.TrackLayoutRepository;
import com.driftdirect.service.UserService;
import com.driftdirect.service.championship.judge.JudgeParticipationService;
import com.driftdirect.service.round.RoundService;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Paul on 11/10/2015.
 */
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final String APPLICATION_INIT = "init";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    File fRom;
    File fCiob;
    File bImg;
    Country c;
    Team t;
    Sponsor demon;
    Sponsor raceTech;
    User organizer;

    private Environment environment;
    private ConfigSettingRepository configSettingRepository;
    private RoleRepository roleRepository;
    private UserService userService;
    private ChampionshipRepository championshipRepository;
    private RoundService roundService;
    private FileRepository fileRepository;
    private PersonRepository personRepository;
    private DriverParticipationRepository driverParticipationRepository;
    private JudgeParticipationRepository judgeParticipationRepository;
    private DriverParticipationResultsRepository resultsRepository;
    private RoundRepository roundRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private SponsorRepository sponsorRepository;
    @Autowired
    private DriverDetailsRepository driverDetailsRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TrackLayoutRepository trackLayoutRepository;

    @Autowired
    private JudgeParticipationService judgeParticipationService;
    @Autowired
    public Bootstrap(
            ConfigSettingRepository configSettingRepository, RoleRepository roleRepository,
            UserService userService, Environment environment, ChampionshipRepository championshipRepository,
            RoundService roundService, FileRepository fileRepository, PersonRepository personRepository,
            DriverParticipationRepository driverParticipationRepository,
            JudgeParticipationRepository judgeParticipationRepository,
            DriverParticipationResultsRepository resultsRepository,
            RoundRepository roundRepository) {
        this.configSettingRepository = configSettingRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.environment = environment;
        this.championshipRepository = championshipRepository;
        this.roundService = roundService;
        this.fileRepository = fileRepository;
        this.personRepository = personRepository;
        this.driverParticipationRepository = driverParticipationRepository;
        this.judgeParticipationRepository = judgeParticipationRepository;
        this.roundRepository = roundRepository;
        this.resultsRepository = resultsRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        if (configSettingRepository.findByKey(APPLICATION_INIT) == null){
            System.out.println("********************************** STARTED APP BOOTSTRAP ******************************");
            if (Arrays.asList(this.environment.getActiveProfiles()).contains("dev")){
                initDevelopementDatabase();
            }

            ConfigSetting configSetting = new ConfigSetting();
            configSetting.setKey(APPLICATION_INIT);
            configSettingRepository.save(configSetting);
        }
    }

    private void initDevelopementDatabase(){
        fCiob = saveFile("/img/cioban.jpg");
        fRom = saveFile("/img/rom.jpg");
        bImg = saveFile("/img/drift.jpg");

        initDevUsersAndRoles();
        initOthers();
        initChampionshipAndRounds();
    }

    private File saveFile(String path) {
        File f;
        try {
            ClassPathResource r = new ClassPathResource(path);
            f = new File();
            f.setName(r.getFilename());
            f.setData(IOUtils.toByteArray(r.getInputStream()));
            f = fileRepository.save(f);
            return f;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Sponsor createSponsor(String name, String description, String url, File logo) {
        Sponsor s = new Sponsor();
        s.setName(name);
        s.setUrl(url);
        s.setDescription(description);
        s.setLogo(logo);
        return sponsorRepository.save(s);
    }

    private void initOthers() {
        c = new Country();
        c.setName("Romania");
        c.setFlag(fRom);
        c = countryRepository.save(c);
        demon = createSponsor("Demon", "Most rad energy drink", "http://www.demonenergy.co.nz/", saveFile("/img/demon.jpg"));
        raceTech = createSponsor("RaceTech", "Race innovators", "http://racetech.co.nz/shop/index.php?route=common/home", saveFile("/img/racetech.jpg"));

        t = new Team();
        t.setName("Team 1");
        t.setSponsors(new HashSet<>(Arrays.asList(demon)));
        t = teamRepository.save(t);
    }

    private News createNews(String name, String description, String url, File logo) {
        News news = new News();
        news.setName(name);
        news.setDescription(description);
        news.setLogo(logo);
        news.setUrl(url);
        return news;
    }

    private Championship createChampionship(String name, File logo, File backgroundImage, Sponsor... sponsors) {
        Championship c = new Championship();
        c.setName(name);
        c.setTicketsUrl("http://www.d1nz.com");
        c.setLogo(logo);
        c.setBackgroundImage(backgroundImage);
        ChampionshipRules rules = new ChampionshipRules();
        rules.setRules("Rules of the championship");
        rules.setVideoUrl("http://www.youtube.com");
        c.setRules(rules);
        c.setOrganizer(organizer.getPerson());
        c.addNews(createNews("FANGA DAN WINS FIRST MANFEILD DRIFTING TITLE", "A shocking outcome", "http://d1nz.com/d1nz-news/309-fanga-dan-wins-first-manfeild-drifting-title", backgroundImage));
        c.addNews(createNews("D1 PRO-SPORT: BLAIR GRIBBLE-BOWRING WINS ON DEBUT", "at Manfeild Raceway in Feilding", "http://d1nz.com/d1nz-news/308-d1-pro-sport-blair-gribble-bowring-wins-on-debut", backgroundImage));
        c.addNews(createNews("WHITTAKER TOPS QUALIFYING AT MANFEILD - DEMON D1NZ ROUND 1 2015", "sponsored by redbull", "http://d1nz.com/d1nz-news/307-whittaker-tops-qualifying-at-manfeild-r1-2015", backgroundImage));
        if (sponsors != null)
            for (Sponsor sponsor : sponsors) {
                c.addSponsor(sponsor);
            }
        return championshipRepository.save(c);
    }

    private void createSchedule(Long roundId, String name, DateTime startDate, DateTime endDate) {
        RoundScheduleCreateDto s = new RoundScheduleCreateDto();
        s.setName(name);
        s.setStartDate(startDate);
        s.setEndDate(endDate);
        roundService.addRoundSchedule(roundId, s);
    }

    private void addTrack(Round round, File layout, String description, String videoUrl, String judgingCriteria) {
        TrackCreateDto track = new TrackCreateDto();
        track.setLayout(layout.getId());
        track.setDescription(description);
        track.setVideoUrl(videoUrl);
        track.setJudgingCriteria(judgingCriteria);
        roundService.addTrack(round.getId(), track);
    }

    private Round createRound(String name, String ticketsUrl, String liveStream, Championship c, File logo, int year, int month) {
        Round r = new Round();
        r.setName(name);
        r.setChampionship(c);
        r.setLogo(logo);
        r.setTicketsUrl(ticketsUrl);
        r.setLiveStream(liveStream);
        r = roundRepository.save(r);
        createSchedule(r.getId(), "Registration", new DateTime(year, month, 1, 10, 0), new DateTime(year, month, 1, 10, 0));
        createSchedule(r.getId(), "Qualifications", new DateTime(year, month, 2, 10, 0), new DateTime(year, month, 2, 20, 0));
        createSchedule(r.getId(), "Playoff", new DateTime(year, month, 3, 10, 0), new DateTime(year, month, 3, 20, 0));
        return r;
    }

    private Person createPerson(String name, String description, File picture, PersonType personType) {
        Person person = new Person();
        person.setFirstName(name.split(" ")[0]);
        person.setLastName(name.split(" ")[1]);
        person.setCountry(c);
        person.setProfilePicture(picture != null ? picture : fCiob);
        person.setPersonType(personType);
        person.setCareerStartDate(new DateTime(2010, 1,1, 0, 0));
        person.setPortfolio("Winner of the first d1nz championship");
        person.setDescription(description);
        person.setBirthDate(new DateTime(1993, 12, 3, 0, 0));
        return personRepository.save(person);
    }

    private Person createDriver(String name, File picture) {
        DriverDetails d = new DriverDetails();
        d.setModel("GT86");
        d.setMake("Toyota");
        d.setTires("Continental");
        d.setWheels("Some brand");
        d.setOther("This guy is super good");
        d.setEngine("4.2 Petrol Bi-Turbo");
        d.setSteeringAngle("2");
        d.setSuspensionMods("43");
        d.setHorsePower(1200);
        d.setTeam(t);
        d = driverDetailsRepository.save(d);
        Person p = createPerson(name, "A newcommer to the scene, Florin has managed to climb to the top of the charts in a record time. It's a pleasure watching him slide on the road", picture, PersonType.Driver);
        p.setDriverDetails(d);
        return personRepository.save(p);
    }

    private DriverParticipation createDriverParticipation(Person person, Championship championship) {
        DriverParticipation participation = new DriverParticipation();
        participation.setDriver(person);
        participation.setChampionship(championship);
        return driverParticipationRepository.save(participation);
    }


    private void createJudgeParticipation(Person judge, Championship championship, JudgeType type, Integer... maximumPoints) {
        List<PointsAllocationCreateDto> pointAllocations = new ArrayList<>();
        for (Integer maxPoints : maximumPoints) {
            PointsAllocationCreateDto pointAllocation = new PointsAllocationCreateDto();
            pointAllocation.setMaxPoints(maxPoints);
            pointAllocation.setName(type.getTitle().split(" ")[0] + "_" + maxPoints);
            pointAllocations.add(pointAllocation);
        }
        JudgeParticipationCreateDto participation = new JudgeParticipationCreateDto();
        participation.setJudge(judge.getId());
        participation.setJudgeType(type);
        participation.setPointsAllocations(pointAllocations);
        try {
            judgeParticipationService.addJudge(championship, participation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DriverParticipationResults createResult(DriverParticipation participation, int rank, int points) {
        DriverParticipationResults result = new DriverParticipationResults();
        result.setRank(rank);
        result.setTotalPoints(points);
        result.setParticipation(participation);
        return resultsRepository.save(result);
    }

    private void initChampionshipAndRounds(){
        Championship c1 = createChampionship("DN1Z", saveFile("/img/championship_test.png"), bImg, demon, raceTech);
        Championship c2 = createChampionship("Romania Drift Allstars", saveFile("/img/allstars.jpg"), bImg);
        Round r1 = createRound("Manfield", "https://www.iticket.co.nz/events/2015/nov/the-demon-energy-d1nz-national-drifting-championship-round-1", "http://www.twitch.tv/sing_sing", c1, saveFile("/img/manfield.jpg"), 2015, 11);
        addTrack(r1, saveFile("/img/track.png"), "This tack is deadly. People will fall of cliffs", "http://www.youtube.com", "Pass = not dead");
        Round r2 = createRound("Baypark", "https://www.iticket.co.nz/events/2016/jan/the-demon-energy-d1nz-national-drifting-championship-round-2", null, c1, saveFile("/img/baypark.jpg"), 2016, 1);
        addTrack(r2, saveFile("/img/track.png"), "This tack is deadly. People will fall of cliffs", "http://www.youtube.com", "Pass = not dead");
        Round r4 = createRound("Round 1 - C2", "www.google.com", null, c2, fCiob, 2016, 12);

        Person driver1 = createDriver("Florin Cozmuta", saveFile("/img/kimi.jpg"));
        Person judge1 = createPerson("Diana V", "Drifitng judge", saveFile("/img/j1.jpg"), PersonType.Judge);
        Person judge2 = createPerson("Andra B", "Drifting judge", saveFile("/img/j2.jpg"), PersonType.Judge);
        Person judge3 = createPerson("Ioana f", "Drifting judge", saveFile("/img/j3.jpg"), PersonType.Judge);

//        DriverParticipation driverParticipation1 = createDriverParticipation(driver1, c1);
//        DriverParticipation driverParticipation2 = createDriverParticipation(driver2, c1);

        createJudgeParticipation(judge1, c1, JudgeType.LINE, 30, 10);
        createJudgeParticipation(judge2, c1, JudgeType.ANGLE, 30, 10);
        createJudgeParticipation(judge3, c1, JudgeType.STYLE, 20);

//        createResult(driverParticipation1, 1, 200);
//        createResult(driverParticipation2, 2, 150);
    }

    private void initDevUsersAndRoles(){
        Role adminRole = roleRepository.save(new Role(Authorities.ROLE_ADMIN));
        Role orgRole = roleRepository.save(new Role(Authorities.ROLE_ORGANIZER));
        Role judgeRole = roleRepository.save(new Role(Authorities.ROLE_JUDGE));

        UserCreateDTO judge = new UserCreateDTO();
        judge.setUsername("judge");
        judge.setPassword("judge");
        judge.setFirstName("Judge");
        judge.setLastName("Drifting");
        judge.setEmail("paul.bochis@caca.com");
        judge.setRoles(new HashSet<>(Arrays.asList("ROLE_JUDGE")));
        try {
            userService.createFromDto(judge);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserCreateDTO organizer = new UserCreateDTO();
        organizer.setUsername("org");
        organizer.setPassword("org");
        organizer.setEmail("paul.bochis@asd.com");
        organizer.setFirstName("Flo");
        organizer.setLastName("Coz");
        organizer.setRoles(new HashSet<>(Arrays.asList(Authorities.ROLE_ORGANIZER)));
        try {
            this.organizer = userService.createFromDto(organizer);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserCreateDTO admin = new UserCreateDTO();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setFirstName("App");
        admin.setLastName("Admin");
        admin.setEmail("paul.bochis@gmail.com");
        admin.setRoles(new HashSet<>(Arrays.asList("ROLE_ADMIN")));
        try {
            userService.createFromDto(admin);
            int a = 2;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
