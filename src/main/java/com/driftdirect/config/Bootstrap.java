package com.driftdirect.config;

import com.driftdirect.domain.ConfigSetting;
import com.driftdirect.domain.Country;
import com.driftdirect.domain.championship.Championship;
import com.driftdirect.domain.championship.ChampionshipParticipation;
import com.driftdirect.domain.championship.ChampionshipParticipationType;
import com.driftdirect.domain.driver.DriverDetails;
import com.driftdirect.domain.driver.Team;
import com.driftdirect.domain.file.File;
import com.driftdirect.domain.person.Person;
import com.driftdirect.domain.person.PersonType;
import com.driftdirect.domain.round.Round;
import com.driftdirect.domain.sponsor.Sponsor;
import com.driftdirect.domain.user.Authorities;
import com.driftdirect.domain.user.Role;
import com.driftdirect.dto.user.UserCreateDTO;
import com.driftdirect.repository.*;
import com.driftdirect.repository.championship.ChampionshipParticipationRepository;
import com.driftdirect.repository.championship.ChampionshipRepository;
import com.driftdirect.service.RoundService;
import com.driftdirect.service.UserService;
import org.apache.commons.io.IOUtils;
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
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Paul on 11/10/2015.
 */
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final String APPLICATION_INIT = "init";
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    File fRom;
    File fCiob;
    Country c;
    Team t;
    private Environment environment;
    private ConfigSettingRepository configSettingRepository;
    private RoleRepository roleRepository;
    private UserService userService;
    private ChampionshipRepository championshipService;
    private RoundService roundService;
    private FileRepository fileRepository;
    private PersonRepository personRepository;
    private ChampionshipParticipationRepository championshipParticipationRepository;
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
    public Bootstrap(
            ConfigSettingRepository configSettingRepository,RoleRepository roleRepository,
            UserService userService, Environment environment, ChampionshipRepository championshipService,
            RoundService roundService, FileRepository fileRepository, PersonRepository personRepository,
            ChampionshipParticipationRepository championshipParticipationRepository,
            RoundRepository roundRepository) {
        this.configSettingRepository = configSettingRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.environment = environment;
        this.championshipService = championshipService;
        this.roundService = roundService;
        this.fileRepository = fileRepository;
        this.personRepository = personRepository;
        this.championshipParticipationRepository = championshipParticipationRepository;
        this.roundRepository = roundRepository;
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
        try {
            ClassPathResource r = new ClassPathResource("/template/cioban.jpg");
            fCiob = new File();
            fCiob.setName(r.getFilename());
            fCiob.setData(IOUtils.toByteArray(r.getInputStream()));
            fCiob = fileRepository.save(fCiob);

            r = new ClassPathResource("/template/rom.jpg");
            fRom = new File();
            fRom.setName(r.getFilename());
            fRom.setData(IOUtils.toByteArray(r.getInputStream()));
            fRom = fileRepository.save(fRom);

        } catch (IOException e) {
            System.err.println(e);
        }
        initDevUsersAndRoles();
        initOthers();
        initChampionshipAndRounds();
    }

    private void initOthers() {
        c = new Country();
        c.setName("Romania");
        c.setFlag(fRom);
        c = countryRepository.save(c);

        Sponsor s = new Sponsor();
        s.setName("Sponsor1");
        s.setEmail("email@default.co");
        s.setTelephoneNr("0743122132");
        s.setUrl("www.sponsor.noDomain");
        s = sponsorRepository.save(s);

        t = new Team();
        t.setName("Team 1");
        t.setSponsors(new HashSet<>(Arrays.asList(s)));
        t = teamRepository.save(t);
    }

    private Championship createChampionship(String name, File f) {
        Championship c = new Championship();
        c.setName(name);
        c.setTicketsUrl("www.tickets.com");
        c.setInformation("This is a drifting championship, mate");
        c.setRules("These are the rules of drifting");
        c.setLogo(f);
        c.setBackgroundImage(f);
        return championshipService.save(c);
    }

    private Round createRound(String name, Championship c, File f) {
        Round r = new Round();
        r.setName(name);
        r.setChampionship(c);
        r.setLogo(f);
        return roundRepository.save(r);
    }

    private Person createDriver(String firstName, String lastName) {
        DriverDetails d = new DriverDetails();
        d.setModel("GT86");
        d.setMake("Toyota");
        d.setTires("tires");
        d.setWheels("wheels");
        d.setOther("oder");
        d.setEngine("engige");
        d.setSteeringAngle("2");
        d.setSuspensionMods("43");
        d.setHorsePower(1200);
        d.setTeam(t);
        d = driverDetailsRepository.save(d);
        Person p = new Person();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setCountry(c);
        p.setProfilePicture(fCiob);
        p.setDriverDetails(d);
        p.setPersonType(PersonType.Driver);
        p.setYearsExperience(4);
        p.setDescription("conducator auto autorizat");
        return personRepository.save(p);
    }

    private void initChampionshipAndRounds(){
        File f = fileRepository.findOne((long) 1);
        Championship c1 = createChampionship("DN1Z", f);
        Championship c2 = createChampionship("DN2Z", f);

        Round r1 = createRound("Round 1 - C1", c1, f);
        Round r2 = createRound("Round 2 - C2", c2, f);

        Person p1 = createDriver("Vlad", "Iancu");
        Person p2 = createDriver("Paul", "Bochis");
        ChampionshipParticipation cp1 = new ChampionshipParticipation();
        cp1.setPerson(p1);
        cp1.setChampionship(c1);
        cp1.setParticipationType(ChampionshipParticipationType.DRIVER);
        cp1 = championshipParticipationRepository.save(cp1);
        ChampionshipParticipation cp2 = new ChampionshipParticipation();
        cp2.setPerson(p2);
        cp2.setChampionship(c1);
        cp2.setParticipationType(ChampionshipParticipationType.DRIVER);
        cp2 = championshipParticipationRepository.save(cp2);
    }

    private void initDevUsersAndRoles(){
        Role adminRole = roleRepository.save(new Role(Authorities.ROLE_ADMIN));
        Role orgRole = roleRepository.save(new Role(Authorities.ROLE_ORGANIZER));
        Role judgeRole = roleRepository.save(new Role(Authorities.ROLE_JUDGE));

        UserCreateDTO judge = new UserCreateDTO();
        judge.setUsername("judge");
        judge.setPassword("judge");
        judge.setEmail("paul.bochis@caca.com");
        judge.setRoles(new HashSet<>(Arrays.asList("ROLE_JUDGE")));
        try {
            userService.createFromDto(judge);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserCreateDTO admin = new UserCreateDTO();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("paul.bochis@gmail.com");
        admin.setRoles(new HashSet<>(Arrays.asList("ROLE_ADMIN")));
        try {
            userService.createFromDto(admin);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
