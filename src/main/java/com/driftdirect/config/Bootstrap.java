package com.driftdirect.config;

import com.driftdirect.domain.ConfigSetting;
import com.driftdirect.domain.Country;
import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.championship.ChampionshipRules;
import com.driftdirect.domain.championship.driver.DriverParticipation;
import com.driftdirect.domain.championship.driver.DriverParticipationResults;
import com.driftdirect.domain.championship.judge.JudgeParticipation;
import com.driftdirect.domain.championship.judge.JudgeType;
import com.driftdirect.domain.championship.judge.PointsAllocation;
import com.driftdirect.domain.comment.Comment;
import com.driftdirect.domain.driver.DriverDetails;
import com.driftdirect.domain.driver.Team;
import com.driftdirect.domain.file.File;
import com.driftdirect.domain.news.News;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.person.PersonType;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.round.RoundDriverResult;
import com.driftdirect.domain.round.battle.Battle;
import com.driftdirect.domain.round.battle.BattleRound;
import com.driftdirect.domain.round.battle.BattleRoundRun;
import com.driftdirect.domain.round.playoff.PlayoffStage;
import com.driftdirect.domain.round.playoff.PlayoffTree;
import com.driftdirect.domain.round.qualifiers.QualifiedDriver;
import com.driftdirect.domain.round.qualifiers.Qualifier;
import com.driftdirect.domain.sponsor.Sponsor;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.domain.user.User;
import com.driftdirect.dto.championship.ChampionshipCreateDTO;
import com.driftdirect.dto.championship.judge.JudgeParticipationCreateDto;
import com.driftdirect.dto.championship.judge.PointsAllocationCreateDto;
import com.driftdirect.dto.championship.rules.RulesCreateDto;
import com.driftdirect.dto.comment.CommentCreateDto;
import com.driftdirect.dto.person.PersonCreateDto;
import com.driftdirect.dto.round.RoundCreateDto;
import com.driftdirect.dto.round.playoff.PlayoffBattleRoundDriverJudging;
import com.driftdirect.dto.round.playoff.PlayoffBattleRoundJudging;
import com.driftdirect.dto.round.qualifier.run.AwardedPointsCreateDto;
import com.driftdirect.dto.round.qualifier.run.RunJudgingCreateDto;
import com.driftdirect.dto.round.schedule.RoundScheduleEntryCreateDto;
import com.driftdirect.dto.round.track.TrackCreateDto;
import com.driftdirect.dto.user.UserCreateDTO;
import com.driftdirect.mapper.CountryMapper;
import com.driftdirect.repository.*;
import com.driftdirect.repository.championship.ChampionshipRepository;
import com.driftdirect.repository.championship.driver.DriverParticipationRepository;
import com.driftdirect.repository.championship.driver.DriverParticipationResultsRepository;
import com.driftdirect.repository.championship.judge.JudgeParticipationRepository;
import com.driftdirect.repository.round.RoundRepository;
import com.driftdirect.repository.round.track.TrackLayoutRepository;
import com.driftdirect.service.UserService;
import com.driftdirect.service.championship.ChampionshipService;
import com.driftdirect.service.championship.driver.DriverParticipationService;
import com.driftdirect.service.championship.judge.JudgeParticipationService;
import com.driftdirect.service.migration.D1nzMissingBattleMigrationService;
import com.driftdirect.service.migration.D1nzResultsMigrationService;
import com.driftdirect.service.round.RoundService;
import com.driftdirect.service.round.playoff.PlayoffService;
import com.driftdirect.service.round.qualifier.QualifierService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Paul on 11/10/2015.
 */
@Component
@Transactional
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final String APPLICATION_INIT = "init";
    private final String BAYPARK_RESULTS_REDONE = "baypark_results";


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    File fRom;
    File fCiob;
    File bImg;
    Country c;
    Team t;
    Sponsor demon;
    Sponsor raceTech;
    User organizer;
    List<CommentCreateDto> comments = new ArrayList<>();
    Role adminRole;
    Role orgRole;
    Role judgeRole;
    List<CommentCreateDto> someComments = new ArrayList<>();
    Set<Sponsor> sponsors = new HashSet<>();
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
    private QualifierService qualifierService;
    @Autowired
    private JudgeParticipationService judgeParticipationService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PlayoffService playoffService;
    @Autowired
    private ChampionshipService championshipService;
    @Autowired
    private DriverParticipationService driverParticipationService;
    @Autowired
    private D1nzResultsMigrationService d1nzResultsMigrationService;
    @Autowired
    private D1nzMissingBattleMigrationService d1nzMissingBattleMigrationService;


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

    private void runMigrationServices() {
        d1nzResultsMigrationService.doMigration();
        d1nzMissingBattleMigrationService.doMigration();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        runMigrationServices();
        if (configSettingRepository.findByKey(APPLICATION_INIT) == null){
            System.out.println("********************************** STARTED APP BOOTSTRAP ******************************");
            if (Arrays.asList(this.environment.getActiveProfiles()).contains("dev")){
                try {
                    initDevelopementDatabase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (configSettingRepository.findByKey(BAYPARK_RESULTS_REDONE) == null) {
                    Round round = roundRepository.findOne(7L);
                    Championship championship = round.getChampionship();
                    for (RoundDriverResult roundResult : round.getRoundResults()) {
                        driverParticipationService.addResult(championship, roundResult);
                    }
                    ConfigSetting configSetting = new ConfigSetting();
                    configSetting.setKey(BAYPARK_RESULTS_REDONE);
                    configSettingRepository.save(configSetting);
                }
                try {
                    initProductionDatabase();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }

            ConfigSetting configSetting = new ConfigSetting();
            configSetting.setKey(APPLICATION_INIT);
            configSettingRepository.save(configSetting);
        }
    }

    private User createUser(Role role, String email, String password, String username, String firstName, String lastName, PersonType personType, File picture) throws IOException, MessagingException {
        UserCreateDTO user = new UserCreateDTO();
        user.setRole(role.getId());
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        PersonCreateDto person = new PersonCreateDto();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        if (picture != null) {
            person.setProfilePicture(picture.getId());
        }
        person.setCountry(CountryMapper.map(c));
        person.setPersonType(personType.name());
        user.setPerson(person);
        return userService.createFromDto(user);
    }

    private void initProductionDatabase() throws IOException, MessagingException {
        adminRole = roleRepository.save(new Role(Authorities.ROLE_ADMIN));
        orgRole = roleRepository.save(new Role(Authorities.ROLE_ORGANIZER));
        judgeRole = roleRepository.save(new Role(Authorities.ROLE_JUDGE));

        c = new Country();
        c.setName("New Zeeland");
        c.setFlag(saveFile("/prod/nzflag.png"));
        c = countryRepository.save(c);

        File picture = saveFile("/prod/user.png");

        User admin = createUser(adminRole, "paul.bochis@gmail.com", "blackmonster32gtr", "tengudrift", "Florin", "Cozmuta", PersonType.Admin, null);
        User organizer = createUser(orgRole, "email@example.com", "driftdirect1234", "brendonwhite", "Brendon", "White", PersonType.Organizer, null);
        User lineJudge = createUser(judgeRole, "email2@example.com", "driftdirect1234", "brendanduncker", "Brendan", "Duncker", PersonType.Judge, saveFile("/prod/lineJudge.png"));
        User angleJudge = createUser(judgeRole, "email3@example.com", "driftdirect1234", "nickteeboon", "Nick", "Teeboon", PersonType.Judge, saveFile("/prod/angleJudge.png"));
        User styleJudge = createUser(judgeRole, "email4@example.com", "driftdirect1234", "kylejackways", "Kyle", "Jackways", PersonType.Judge, saveFile("/prod/styleJudge.png"));
        //create champs
        List<Person> drivers = createDrivers(32);

        Championship qualifierDemo = createProductionDemoChampionship("D1nz - Demo Qualifiers Judging", saveFile("/prod/qualdemo.png"), organizer, lineJudge, angleJudge, styleJudge);
        if (qualifierDemo != null) {
            Round qualifierDemoRound = roundRepository.findAll().get(0);
            for (Person p : drivers) {
                qualifierService.registerDriver(qualifierDemoRound.getId(), p.getId());
            }
        }
        Championship playoffDemo = createProductionDemoChampionship("D1nz - Demo Battle Judging", saveFile("/prod/playoffdemo.png"), organizer, lineJudge, angleJudge, styleJudge);
        if (playoffDemo != null) {
            Round playoffDemoRound = roundRepository.findAll().stream().filter(round -> round.getChampionship().getId().equals(playoffDemo.getId())).findFirst().get();
            List<Qualifier> qualifiers = new ArrayList<>();
            for (Person p : drivers) {
                qualifiers.add(qualifierService.registerDriver(playoffDemoRound.getId(), p.getId()));
            }
            for (Qualifier qualifier : qualifiers) {
                for (JudgeParticipation jp : playoffDemo.getJudges()) {
                    submitRunJudging(qualifier, qualifier.getFirstRun().getId(), jp);
                    submitRunJudging(qualifier, qualifier.getSecondRun().getId(), jp);
                }
            }
        }
    }

    private Championship createProductionDemoChampionship(String name, File logo, User organizer, User lineJudge, User angleJudge, User styleJudge) {
        ChampionshipCreateDTO championship = new ChampionshipCreateDTO();
        championship.setName(name);
        championship.setLogo(logo.getId());
        championship.setBackgroundImage(saveFile("/prod/blackBackground.png").getId());
        championship.setTicketsUrl("https://www.iticket.co.nz/Search?q=d1nz");

        RulesCreateDto rules = new RulesCreateDto();
        rules.setRules("Demo rules");
        rules.setVideoUrl("https://www.youtube.com/watch?v=MseohDk4dqs");
        championship.setRules(rules);
        championship.addRound(productionDemoRound());

        JudgeParticipationCreateDto judge1 = new JudgeParticipationCreateDto();
        judge1.setJudgeType(JudgeType.LINE);
        judge1.setJudge(lineJudge.getPerson().getId());
        judge1.addPointsAllocation(createPointsAllocation("Line", 35));

        JudgeParticipationCreateDto judge2 = new JudgeParticipationCreateDto();
        judge2.setJudgeType(JudgeType.ANGLE);
        judge2.setJudge(angleJudge.getPerson().getId());
        judge2.addPointsAllocation(createPointsAllocation("Angle", 35));

        JudgeParticipationCreateDto judge3 = new JudgeParticipationCreateDto();
        judge3.setJudgeType(JudgeType.STYLE);
        judge3.setJudge(styleJudge.getPerson().getId());
        judge3.addPointsAllocation(createPointsAllocation("Style", 30));

        championship.addJudge(judge1);
        championship.addJudge(judge2);
        championship.addJudge(judge3);

        try {
            return championshipService.createFromDto(championship, organizer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PointsAllocationCreateDto createPointsAllocation(String name, int maxPoints) {
        PointsAllocationCreateDto dto = new PointsAllocationCreateDto();
        dto.setName(name);
        dto.setMaxPoints(maxPoints);
        return dto;
    }

    private RoundCreateDto productionDemoRound() {
        RoundCreateDto round = new RoundCreateDto();
        round.setName("Demo round");
        round.setLogo(saveFile("/prod/round2.png").getId());
        round.setTicketsUrl("https://www.iticket.co.nz/events/2016/jan/the-demon-energy-d1nz-national-drifting-championship-round-2");
        TrackCreateDto track = new TrackCreateDto();
        track.setVideoUrl("https://www.iticket.co.nz/events/2016/jan/the-demon-energy-d1nz-national-drifting-championship-round-2");
        track.setLayout(saveFile("/prod/trackLayout.png").getId());
        track.setDescription("Demo");
        track.setJudgingCriteria("Demo");
        round.setTrack(track);

        round.addRoundScheduleEntry(buildScheduleEntryDto("Demo schedule 1", createDate(2016, 1, 8, 11, 5), createDate(2016, 1, 8, 11, 40)));
        round.addRoundScheduleEntry(buildScheduleEntryDto("Demo schedule 2", createDate(2016, 1, 8, 11, 40), createDate(2016, 1, 8, 12, 15)));
//        round.addRoundScheduleEntry(buildScheduleEntryDto(
//                "Practice Sessions Alternate",
//                createDate(2016, 1, 8, 12, 15),
//                createDate(2016, 1, 8, 17, 30)));
//        round.addRoundScheduleEntry(buildScheduleEntryDto(
//                "Afternoon Track Break",
//                createDate(2016, 1, 8, 17, 30),
//                createDate(2016, 1, 8, 18, 0)));
//
//        round.addRoundScheduleEntry(buildScheduleEntryDto(
//                "Pro Sport Qualifying",
//                createDate(2016, 1, 8, 18, 0),
//                createDate(2016, 1, 8, 19, 0)));
//
//        round.addRoundScheduleEntry(buildScheduleEntryDto(
//                "Pro Championship Qualifying",
//                createDate(2016, 1, 8, 19, 0),
//                createDate(2016, 1, 8, 20, 0)));
//
//        round.addRoundScheduleEntry(buildScheduleEntryDto(
//                "Hot Laps & Open Practice",
//                createDate(2016, 1, 8, 8, 0),
//                createDate(2016, 1, 8, 8, 30)));
//
//        round.addRoundScheduleEntry(buildScheduleEntryDto(
//                "Practice Sessions",
//                createDate(2016, 1, 9, 11, 5),
//                createDate(2016, 1, 9, 15, 30)));
//
//        round.addRoundScheduleEntry(buildScheduleEntryDto(
//                "D1 Pro Sport Series - Top 16 Battle Competition",
//                createDate(2016, 1, 9, 15, 30),
//                createDate(2016, 1, 9, 16, 45)));
//
//        round.addRoundScheduleEntry(buildScheduleEntryDto(
//                "Free Pit walk & Driver signing",
//                createDate(2016, 1, 9, 16, 45),
//                createDate(2016, 1, 9, 18, 30)));
//
//        round.addRoundScheduleEntry(buildScheduleEntryDto(
//                "D1 Pro Championship - Top 32 Battle Competition",
//                createDate(2016, 1, 9, 18, 30),
//                createDate(2016, 1, 9, 22, 0)));
//
//        round.addRoundScheduleEntry(buildScheduleEntryDto(
//                "Podium presentation (Pitlane)",
//                createDate(2016, 1, 9, 22, 0),
//                createDate(2016, 1, 9, 23, 0)));
        return round;
    }

    private DateTime createDate(int year, int month, int day, int hour, int minutes) {
        return new DateTime(year, month, day, hour, minutes, DateTimeZone.forID("Pacific/Auckland"));
    }

    private RoundScheduleEntryCreateDto buildScheduleEntryDto(String name, DateTime startDate, DateTime endDate) {
        RoundScheduleEntryCreateDto entryCreateDto = new RoundScheduleEntryCreateDto();
        entryCreateDto.setName(name);
        entryCreateDto.setStartDate(startDate);
        entryCreateDto.setEndDate(endDate);
        return entryCreateDto;
    }

    private void initDevelopementDatabase() throws IOException {
        fCiob = saveFile("/img/cioban.jpg");
        fRom = saveFile("/img/rom.jpg");
        bImg = saveFile("/img/drift.jpg");

        initDevUsersAndRoles();
        initOthers();
        initChampionshipAndRounds();
    }

    private String getRules(){
        ClassPathResource r = new ClassPathResource("/mock/rules.txt");
        try {
            return FileUtils.readFileToString(r.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Rules could not be read for some reason";
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

        Country c2 = new Country();
        c2.setName("New Zeeland");
        c2.setFlag(fRom);
        c2 = countryRepository.save(c2);

        demon = createSponsor("Demon", "Most rad energy drink", "http://www.demonenergy.co.nz/", saveFile("/img/demon.jpg"));
        raceTech = createSponsor("RaceTech", "Race innovators", "http://racetech.co.nz/shop/index.php?route=common/home", saveFile("/img/racetech.jpg"));

        sponsors.add(demon);
        sponsors.add(raceTech);

        t = new Team();
        t.setName("Team 1");
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
        c.setPublished(true);
        c.setBackgroundImage(backgroundImage);
        ChampionshipRules rules = new ChampionshipRules();
        rules.setRules(getRules());
        rules.setVideoUrl("http://www.youtube.com");
        c.setRules(rules);
        c.setOrganizer(organizer.getPerson());
        c.addNews(createNews("FANGA DAN WINS FIRST MANFEILD DRIFTING TITLE", "A shocking outcome", "http://www.d1nz.com/d1nz-news/309-fanga-dan-wins-first-manfeild-drifting-title", backgroundImage));
        c.addNews(createNews("D1 PRO-SPORT: BLAIR GRIBBLE-BOWRING WINS ON DEBUT", "at Manfeild Raceway in Feilding", "http://www.d1nz.com/d1nz-news/308-d1-pro-sport-blair-gribble-bowring-wins-on-debut", backgroundImage));
        c.addNews(createNews("WHITTAKER TOPS QUALIFYING AT MANFEILD - DEMON D1NZ ROUND 1 2015", "sponsored by redbull", "http://d1nz.com/d1nz-news/307-whittaker-tops-qualifying-at-manfeild-r1-2015", backgroundImage));
        if (sponsors != null)
            for (Sponsor sponsor : sponsors) {
                c.addSponsor(sponsor);
            }
        return championshipRepository.save(c);
    }

    private void createSchedule(Round round, String name, DateTime startDate, DateTime endDate) {
        RoundScheduleEntryCreateDto s = new RoundScheduleEntryCreateDto();
        s.setName(name);
        s.setStartDate(startDate);
        s.setEndDate(endDate);
        roundService.addRoundSchedule(round, s);
    }

    private void addTrack(Round round, File layout, String description, String videoUrl, String judgingCriteria) {
        TrackCreateDto track = new TrackCreateDto();
        track.setLayout(layout.getId());
        track.setDescription(description);
        track.setVideoUrl(videoUrl);
        track.setJudgingCriteria(judgingCriteria);
        roundService.addTrack(round, track);
    }

    private Round createRound(String name, String ticketsUrl, String liveStream, Championship c, File logo, int year, int month) {
        Round r = new Round();
        r.setName(name);
        r.setChampionship(c);
        r.setLogo(logo);
        r.setTicketsUrl(ticketsUrl);
        r.setLiveStream(liveStream);
        r = roundRepository.save(r);
        createSchedule(r, "Registration", new DateTime(year, month, 1, 10, 0), new DateTime(year, month, 1, 11, 0));
        createSchedule(r, "Registration", new DateTime(year, month, 1, 12, 0), new DateTime(year, month, 1, 13, 0));
        createSchedule(r, "Registration", new DateTime(year, month, 1, 14, 0), new DateTime(year, month, 1, 15, 0));
        createSchedule(r, "Registration", new DateTime(year, month, 1, 16, 0), new DateTime(year, month, 1, 17, 0));
        createSchedule(r, "Qualifications", new DateTime(year, month, 2, 10, 0), new DateTime(year, month, 2, 20, 0));
        createSchedule(r, "Playoff", new DateTime(year, month, 3, 10, 0), new DateTime(year, month, 3, 20, 0));
        return r;
    }

    private Person createPerson(String name, String description, File picture, PersonType personType) {
        Person person = new Person();
        person.setFirstName(name.split(" ")[0]);
        person.setLastName(name.split(" ")[1]);
        person.setCountry(c);
        person.setProfilePicture(picture);
        person.setPersonType(personType);
        person.addToGallery(saveFile("/img/kimi.jpg"));
        person.addToGallery(saveFile("/img/cioban.jpg"));
//        person.setCareerStartDate(new DateTime(2010, 1,1, 0, 0));
        person.setDescription(description);
//        person.setBirthDate(new DateTime(1993, 12, 3, 0, 0));
        return personRepository.save(person);
    }

    private Person createDriver(String firstName, String lastName, File picture) {
        return createDriver(firstName + " " + lastName, picture);
    }

    private Person createDriver(String name, File picture) {
        DriverDetails d = new DriverDetails();
//        d.setModel("GT86");
//        d.setMake("Toyota");
//        d.setTires("Continental");
//        d.setWheels("Some brand");
//        d.setOther("This guy is super good");
//        d.setEngine("4.2 Petrol Bi-Turbo");
//        d.setSteeringAngle("2");
//        d.setSuspensionMods("43");
//        d.setHorsePower(1200);
//        d.setTeam(t);
//        d.setSponsors(sponsors);
        d = driverDetailsRepository.save(d);
        Person p = createPerson(name, "", picture, PersonType.Driver);
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
        result.setTotalPoints((float) points);
        result.setParticipation(participation);
        return resultsRepository.save(result);
    }

    private List<Person> createDrivers(int size) {
        String[] firstNames = {"Victor", "Bogdan", "Adolph", "Ahmed", "Aldo", "John", "Malcolm", "Joshua", "Jim", "Paul", "Alex", "Tom", "Marcus", "Wayne", "Shane", "Christian", "Greg"};
        String[] lastNames = {"Guzman", "Fit", "Gallagher", "McClane", "Plit", "DelRey", "Paquito", "Marquiz", "Velasquez"};
        List<Person> drivers = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            drivers.add(createDriver(firstNames[r.nextInt(firstNames.length)], lastNames[r.nextInt(lastNames.length)], null));
        }
        return drivers;
    }

    private void initChampionshipAndRounds() throws IOException {
        Championship c1 = createChampionship("DN1Z", saveFile("/img/championship_test.png"), bImg, demon, raceTech);
        Championship c2 = createChampionship("Romania Drift Allstars", saveFile("/img/allstars.jpg"), bImg);
        //TODO: refactor this
        Round r1 = createRound("Manfield", "https://www.iticket.co.nz/events/2015/nov/the-demon-energy-d1nz-national-drifting-championship-round-1", "http://www.twitch.tv/sing_sing", c1, saveFile("/img/manfield.jpg"), 2015, 11);
        addTrack(r1, saveFile("/img/track.png"), "This tack is deadly. People will fall of cliffs", "http://www.youtube.com", "Pass = not dead");
        Round r2 = createRound("Baypark", "https://www.iticket.co.nz/events/2016/jan/the-demon-energy-d1nz-national-drifting-championship-round-2", null, c1, saveFile("/img/baypark.jpg"), 2016, 1);
        addTrack(r2, saveFile("/img/track.png"), "This tack is deadly. People will fall of cliffs", "http://www.youtube.com", "Pass = not dead");
        Round r3 = createRound("Round 1 - C2", "www.google.com", null, c2, fCiob, 2016, 12);

        Person driver1 = createDriver("Florin Cozmuta", saveFile("/img/kimi.jpg"));
        Person driver2 = createDriver("Bochis Paul", saveFile("/img/kimi.jpg"));
        Person driver3 = createDriver("Vlad Iancu", saveFile("/img/kimi.jpg"));
        Person driver4 = createDriver("Tommy Hilfinger", saveFile("/img/kimi.jpg"));
        Person driver5 = createDriver("Samsung Daniel", saveFile("/img/kimi.jpg"));

        createDriverParticipation(driver1, c1);
        createDriverParticipation(driver2, c1);
        createDriverParticipation(driver3, c1);
        createDriverParticipation(driver4, c1);
        createDriverParticipation(driver5, c1);

        Person judge1 = createPerson("Diana V", "Drifitng judge", saveFile("/img/j1.jpg"), PersonType.Judge);
        createUser("line", "line", "judge@judge.org", judgeRole, judge1.getId());
        createJudgeParticipation(judge1, c1, JudgeType.LINE, 30, 10);

        Person judge2 = createPerson("Andra B", "Drifting judge", saveFile("/img/j2.jpg"), PersonType.Judge);
        createUser("angle", "angle", "judge@angle.org", judgeRole, judge2.getId());
        createJudgeParticipation(judge2, c1, JudgeType.ANGLE, 30, 10);

        Person judge3 = createPerson("Ioana f", "Drifting judge", saveFile("/img/j3.jpg"), PersonType.Judge);
        createUser("style", "style", "judge@style.org", judgeRole, judge3.getId());
        createJudgeParticipation(judge3, c1, JudgeType.STYLE, 20);

        initComments();
        List<Person> drivers = createDrivers(20);

        judgeQualifiers(c1, r1, drivers, 0, 3);
//        judgeQualifiers(c1, r2, drivers, 3, 0);
    }

    private void judgeQualifiers(Championship championship, Round round, List<Person> drivers, int remainder, int zeros) throws IOException {
        List<Qualifier> qualifiers = new ArrayList<>();
        for (Person p : drivers) {
            qualifiers.add(qualifierService.registerDriver(round.getId(), p.getId()));
        }
        for (int i = 0; i < qualifiers.size() - remainder; i++) {
            Qualifier qualifier = qualifiers.get(i);
            for (JudgeParticipation jp : championship.getJudges()) {
                if (zeros > 0) {
                    submitRunJudging(qualifier, qualifier.getFirstRun().getId(), jp, true);
                    submitRunJudging(qualifier, qualifier.getSecondRun().getId(), jp, true);
                } else {
                    submitRunJudging(qualifier, qualifier.getFirstRun().getId(), jp);
                    submitRunJudging(qualifier, qualifier.getSecondRun().getId(), jp);
                }
            }
            zeros--;
        }
    }

    private void mockPlayoffs(Long roundId, List<Person> judges) throws IOException {
        initSomeComments();
        playoffService.generatePlayoffTree(roundId);
        Round round = roundRepository.findOne(roundId);
        PlayoffTree tree = round.getPlayoffTree();
        for (PlayoffStage stage : tree.getPlayoffStages()) {
            for (Battle battle : stage.getBattles()) {
                BattleRound battleRound = battle.getBattleRounds().get(0);
                for (Person judge : judges) {
                    playoffService.submitPlayoffJudging(
                            judge,
                            battle.getId(),
                            createJudging(battleRound, battleRound.getFirstRun(), battle.getDriver1(), battle.getDriver2()));
                }

                for (Person judge : judges) {
                    playoffService.submitPlayoffJudging(
                            judge,
                            battle.getId(),
                            createJudging(battleRound, battleRound.getSecondRun(), battle.getDriver1(), battle.getDriver2()));
                }
            }
        }
    }

    private PlayoffBattleRoundJudging createJudging(BattleRound round, BattleRoundRun run, QualifiedDriver driver1, QualifiedDriver driver2) {
        PlayoffBattleRoundJudging judging = new PlayoffBattleRoundJudging();

        PlayoffBattleRoundDriverJudging driver1Judging = new PlayoffBattleRoundDriverJudging();
        driver1Judging.setPoints(6);
        driver1Judging.setComments(someComments);
        driver1Judging.setQualifiedDriverId(driver1.getId());

        PlayoffBattleRoundDriverJudging driver2Judging = new PlayoffBattleRoundDriverJudging();
        driver2Judging.setPoints(4);
        driver2Judging.setComments(someComments);
        driver2Judging.setQualifiedDriverId(driver2.getId());

        judging.setDriver1(driver1Judging);
        judging.setDriver2(driver2Judging);
        judging.setRoundId(round.getId());
        judging.setRunId(run.getId());

        return judging;
    }

    private void initSomeComments() {
        comments.add(createComment("Good run", true));
        comments.add(createComment("Nice slide", true));

        comments.add(createComment("Weels off trqack", false));
        comments.add(createComment("Oversteered too much. He cannot drive properly", false));

    }
    private void initComments(){
        comments.add(createComment("Good run", true));
        comments.add(createComment("Nice slide", true));
        comments.add(createComment("Torque was cool", true));
//        comments.add(createComment("He was not afraid to commit to the spin", true));
        comments.add(createComment("No smoke from tires", true));
        comments.add(createComment("Propper  gear shifting", true));
//        comments.add(createComment("Even though track was wet he did not oversteer", true));
//        comments.add(createComment("Understeered for coolness efect and that pleased the public", true));
//        comments.add(createComment("Got out of his car, waved to the crowd while sliding off the track. Coolest death ever.", true));


        comments.add(createComment("Weels off trqack", false));
        comments.add(createComment("Oversteered too much. He cannot drive properly", false));
        comments.add(createComment("His eyes were full of tears", false));
        comments.add(createComment("I chocked on the gas ", false));
        comments.add(createComment("he exagerated with the speed and spun out of control", false));
        comments.add(createComment("His mom is not hot enough", false));
        comments.add(createComment("Bet he can't get laid", false));
        comments.add(createComment("Should have driven a propper car, not an opel corsa", false));
        comments.add(createComment("OMG WTF IS THIS PLS GO BACK HGOME", false));
        comments.add(createComment("Rus gtfo", false));
        comments.add(createComment("CykA bLEA", false));

    }

    private void submitRunJudging(Qualifier qualifier, Long runId, JudgeParticipation judge) throws IOException {
        submitRunJudging(qualifier, runId, judge, false);
    }

    private void submitRunJudging(Qualifier qualifier, Long runId, JudgeParticipation judge, boolean zero) throws IOException {
        Random r = new Random();
        RunJudgingCreateDto dto = new RunJudgingCreateDto();
        List<AwardedPointsCreateDto> awardedPoints = new ArrayList<>();
        for (PointsAllocation allocation : judge.getPointsAllocations()) {
            AwardedPointsCreateDto points = new AwardedPointsCreateDto();
            if (zero) {
                points.setAwardedPoints(0);
            } else {
                points.setAwardedPoints(r.nextInt(allocation.getMaxPoints()) + 1);
            }
            points.setPointsAllocation(allocation.getId());
            awardedPoints.add(points);
        }
        dto.setAwardedPoints(awardedPoints);
//        dto.setComments(comments);
        qualifierService.submitRunJudging(qualifier.getId(), runId, dto, judge.getJudge());
    }

    private CommentCreateDto createComment(String commentText, boolean positive) {
        Comment comment = new Comment();
        comment.setComment(commentText);
        comment.setPositive(positive);
        comment = commentRepository.save(comment);
        CommentCreateDto dto = new CommentCreateDto();
        dto.setId(comment.getId());
        return dto;
    }

    private User createUser(String userName, String password, String email, Role role, Long personId) {
        UserCreateDTO user = new UserCreateDTO();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role.getId());
        try {
            return userService.createFromDto(user, personId);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User createUser(String userName, String password, String firstName, String lastName, String email, Role role) {
        UserCreateDTO user = new UserCreateDTO();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role.getId());
        PersonCreateDto person = new PersonCreateDto();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        switch (role.getAuthority()) {
            case Authorities.ROLE_ADMIN:
                person.setPersonType("Admin");
                break;
            case Authorities.ROLE_JUDGE:
                person.setPersonType("Judge");
                break;
            case Authorities.ROLE_ORGANIZER:
                person.setPersonType("Organizer");
                break;
        }
        user.setPerson(person);
        try {
            return userService.createFromDto(user);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void initDevUsersAndRoles() {
        adminRole = roleRepository.save(new Role(Authorities.ROLE_ADMIN));
        orgRole = roleRepository.save(new Role(Authorities.ROLE_ORGANIZER));
        judgeRole = roleRepository.save(new Role(Authorities.ROLE_JUDGE));

//        createUser("judge", "judge", "Judge", "drifting", "paul.caca@caca.com", "ROLE_JUDGE");
        this.organizer = createUser("org", "org", "Flo", "Coz", "paul.asdca@caca.com", orgRole);
        createUser("admin", "admin", "Flo", "Coz", "paul.cafds@caca.com", adminRole);
    }
}
