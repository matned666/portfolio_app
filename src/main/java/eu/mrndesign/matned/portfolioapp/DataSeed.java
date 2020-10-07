package eu.mrndesign.matned.portfolioapp;

import eu.mrndesign.matned.portfolioapp.dto.ProjectDTO;
import eu.mrndesign.matned.portfolioapp.dto.UserDTO;
import eu.mrndesign.matned.portfolioapp.model.*;
import eu.mrndesign.matned.portfolioapp.repository.GraphicRepository;
import eu.mrndesign.matned.portfolioapp.repository.ProjectRepository;
import eu.mrndesign.matned.portfolioapp.repository.UserRepository;
import eu.mrndesign.matned.portfolioapp.repository.UserRoleRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static eu.mrndesign.matned.portfolioapp.statics.Patterns.DATE_TIME_FORMATTER_ONLY_DATE;

@Component
public class DataSeed implements InitializingBean {

    private final UserRoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultAdminLogin;
    private final String defaultAdminPassword;
    private final GraphicRepository graphicRepository;
    private final ProjectRepository projectRepository;

    @Value("${default.user.username}")
    private String defaultUserName;

    @Value("${default.user.password}")
    private String defaultUserPassword;



    public DataSeed(UserRoleRepository roleRepository,
                    UserRepository userRepository,
                    PasswordEncoder passwordEncoder,
                    String defaultAdminLogin,
                    String defaultAdminPassword, GraphicRepository graphicRepository,
                    ProjectRepository projectRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultAdminLogin = defaultAdminLogin;
        this.defaultAdminPassword = defaultAdminPassword;
        this.graphicRepository = graphicRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void afterPropertiesSet() {
        for (UserRole.Role role : UserRole.Role.values()) {
            createRole(role);
        }
        createDefaultUser();
        addDefaultPictures();
        addDefaultProjects();
    }

    private void addDefaultProjects() {
        addProject("This website (Java, SpringBoot, Thymeleaf)",
                   "This is my portfolio web app. Pictures, applications and informations about me. It will be also possible to contact me by a form, buy graphics and play some webapps.",
                   "2020-09-29",
                   "https://mrndesign.herokuapp.com/");
        addProject("Final project SDA (Java, SpringBoot, Thymeleaf)",
                   "The final project from Software Development Academy - Java From scratch course. Login: admin@admin.pl, password: admin",
                   "2020-09-15",
                   "https://the-final-project.herokuapp.com/");
        addProject("A simple visit card (HTML5, CSS)",
                   "This domain will lead to a new webapp - a shop with training sessions.",
                   "2020-08-07",
                   "https://hr-humanrelations.pl");
        addProject("Dog's health book (Android, Java)",
                   "A simple android application written in Java. It is for dog owners. Released on google play.",
                   "2020-08-01",
                   "https://play.google.com/store/apps/details?id=pl.design.mrn.matned.dogmanagementapp");
        addProject("Weather API for Android (Android, Java)",
                   "The simplest app ever: check your weather :D, unfortunately it didnt go to google shop, maybe some day",
                   "2020-05-10",
                   "https://github.com/matned666/weather-App-");
        addProject("Cars management app (Android, Java)",
                   "Another Android application for cars fleet management (my first android app).",
                   "2020-03-20",
                   "https://github.com/matned666/First_android_app");
        addProject("Github repository (Java)",
                   "My repository with many demo programs and checkouts.",
                   "2019-10-20",
                   "https://github.com/matned666");
        addProject("This is my first gae :D saper in JSwong, JAVA",
                   "To run it, I suppose you should have Java installed. ",
                   "2019-12-24",
                   "https://github.com/matned666/saper-game");
        addProject("Old website with my art HTML",
                   "Simple HTML site. Much to change, but looks nice :D ",
                   "2010-06-01",
                   "http://artpicture.home.pl");



    }

    private void addProject(String title, String description, String releaseDate, String link) {
        if(!projectRepository.existByLink(link)) projectRepository.save(Project.apply(new ProjectDTO.ProjectDTOBuilder(title,link,description,releaseDate).build()));
    }

    private void addP(String s, String s1, String s2, LocalDate parse, int i, int i1) {
        if(!graphicRepository.existsByImageUrl(s1)) {
            Graphic graphic = new Graphic(s1, s);
            graphic.setCopiesMade(i);
            graphic.setDateOfPublication(parse);
            graphic.setDescription(s2);
            graphic.setNumberOfCopies(i1);
            graphicRepository.save(graphic);
        }
    }


    private void createDefaultUser() {
        if (userRepository.findByLogin(defaultAdminLogin).isEmpty()) {
            UserDTO defaultUserDTO = new UserDTO.RTDOBuilder()
                    .login(defaultAdminLogin)
                    .country(Countries.POLAND.getSymbol())
                    .preferEmails(true)
                    .build();
            String hashedPassword = passwordEncoder.encode(defaultAdminPassword);
            User defaultUser = User.apply(defaultUserDTO, hashedPassword);
            UserRole role = roleRepository.findByRoleName(UserRole.Role.ADMIN.roleName());
            defaultUser.getRoles().add(role);
            userRepository.save(defaultUser);
        }

        if (userRepository.findByLogin(defaultUserName).isEmpty()) {
            UserDTO defaultUserDTO = new UserDTO.RTDOBuilder()
                    .login(defaultUserName)
                    .country(Countries.POLAND.getSymbol())
                    .preferEmails(true)
                    .build();
            String hashedPassword = passwordEncoder.encode(defaultUserPassword);
            User defaultUser = User.apply(defaultUserDTO, hashedPassword);
            UserRole role = roleRepository.findByRoleName(UserRole.Role.USER.roleName());
            defaultUser.getRoles().add(role);
            userRepository.save(defaultUser);
        }
    }

    private void createRole(UserRole.Role role) {
        String roleCheck = "ROLE_" + role.name();
        if (!roleRepository.roleExists(roleCheck)) {
            roleRepository.save(UserRole.apply(role));
        }
    }



    private void addDefaultPictures() {
        addP("Mist 1",
                "/img/content/Mist 1S.jpg",
                "Mist picture set 1, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 2",
                "/img/content/Mist 2S.jpg",
                "Mist picture set 2, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                2);
        addP("Mist 3",
                "/img/content/Mist 3S.jpg",
                "Mist picture set 3, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 4",
                "/img/content/Mist 4S.jpg",
                "Mist picture set 4, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 5",
                "/img/content/Mist 5S.jpg",
                "Mist picture set 5, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 6",
                "/img/content/Mist 6S.jpg",
                "Mist picture set 6, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 7",
                "/img/content/Mist 7S.jpg",
                "Mist picture set 7, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 8",
                "/img/content/Mist 8S.jpg",
                "Mist picture set 8, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 9",
                "/img/content/Mist 9S.jpg",
                "Mist picture set 9, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 10",
                "/img/content/Mist 10S.jpg",
                "Mist picture set 10, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 11",
                "/img/content/Mist 11S.jpg",
                "Mist picture set 11, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 12",
                "/img/content/Mist 12S.jpg",
                "Mist picture set 12, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 13",
                "/img/content/Mist 13S.jpg",
                "Mist picture set 13, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Mist 14",
                "/img/content/Mist 14S.jpg",
                "Mist picture set 14, simple description TODO",
                LocalDate.parse("2010-09-10", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Darvin's Apple",
                "/img/content/Darvin's apple S.jpg",
                "Darvin's apple picture from Human states, simple description TODO",
                LocalDate.parse("2011-03-20", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("The accident",
                "/img/content/accident.jpg",
                "A picture from mental states series, simple description TODO",
                LocalDate.parse("2008-02-02", DATE_TIME_FORMATTER_ONLY_DATE),
                10,
                4);
        addP("The egistence",
                "/img/content/egistence.jpg",
                "A picture from mental states series, simple description TODO",
                LocalDate.parse("2008-02-02", DATE_TIME_FORMATTER_ONLY_DATE),
                10,
                4);
        addP("The egistence",
                "/img/content/egistence.jpg",
                "A picture from mental states series, simple description TODO",
                LocalDate.parse("2008-02-02", DATE_TIME_FORMATTER_ONLY_DATE),
                10,
                1);
        addP("The feeding",
                "/img/content/feeding.jpg",
                "A picture from different spaces series, simple description TODO",
                LocalDate.parse("2007-12-20", DATE_TIME_FORMATTER_ONLY_DATE),
                10,
                2);
        addP("The greed of power",
                "/img/content/greed_of_power.jpg",
                "A picture from mental states series, simple description TODO",
                LocalDate.parse("2008-02-02", DATE_TIME_FORMATTER_ONLY_DATE),
                10,
                4);
        addP("The walker",
                "/img/content/walker.jpg",
                "A picture from mental states series, simple description TODO",
                LocalDate.parse("2008-02-02", DATE_TIME_FORMATTER_ONLY_DATE),
                10,
                2);
        addP("Darvin's apple",
                "/img/content/darvin_apple.jpg",
                "A picture from human states series, size: 90x60cm, simple description TODO",
                LocalDate.parse("2010-03-11", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                3);
        addP("The book of destiny",
                "/img/content/book_of_destiny.jpg",
                "A picture from human states series, size: 150x60cm, simple description TODO",
                LocalDate.parse("2013-01-12", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                0);
        addP("Deep calm",
                "/img/content/buddha.jpg",
                "A picture from human states series, size: 90x90cm, simple description TODO",
                LocalDate.parse("2011-11-23", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                2);
        addP("The carnival is over",
                "/img/content/carnival_is_over.jpg",
                "A picture from human states series, size: 90x90cm, simple description TODO",
                LocalDate.parse("2011-11-23", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                2);
        addP("Deep den",
                "/img/content/deep_den.jpg",
                "A picture from human states series, size: 90x55cm, simple description TODO",
                LocalDate.parse("2012-02-01", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                2);
        addP("Dreaming",
                "/img/content/dreaming.jpg",
                "A picture from mental states series, size: 60x60cm, simple description TODO",
                LocalDate.parse("2008-02-02", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                3);
        addP("The fallen messiah",
                "/img/content/fallen_messiah.jpg",
                "A picture from mental states series, size: 220x80cm, simple description TODO",
                LocalDate.parse("2009-10-15", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                2);
        addP("Fields full of sounds",
                "/img/content/fields_full_of_sounds.jpg",
                "A poster for a cd for guitarist, Jacek Raczynski, simple description TODO",
                LocalDate.parse("2009-10-15", DATE_TIME_FORMATTER_ONLY_DATE),
                20,
                1);
        addP("Hermit's den",
                "/img/content/hermit_den.jpg",
                "A picture from human states series, size: 70x60cm, simple description TODO",
                LocalDate.parse("2010-08-20", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                4);
        addP("King is dead, long lives the king",
                "/img/content/king_is_dead.jpg",
                "A picture from human states series, size: 180x70cm, simple description TODO",
                LocalDate.parse("2012-09-01", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                0);
        addP("King of beggars",
                "/img/content/king_of_beggars.jpg",
                "A picture from magic pictures series, size: 50x160cm, simple description TODO",
                LocalDate.parse("2014-06-21", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                0);
        addP("Lessons of morality",
                "/img/content/lessons_of_morality.jpg",
                "A picture from human states series, size: 70x80cm, simple description TODO",
                LocalDate.parse("2011-06-01", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("The pious extasy",
                "/img/content/pious_extasy.jpg",
                "A picture from human states series, size: 90x90cm, simple description TODO",
                LocalDate.parse("2010-11-01", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                4);
        addP("Quenching the thirst",
                "/img/content/queching_the_thirst.jpg",
                "A picture from human states series, size: 50x120cm, simple description TODO",
                LocalDate.parse("2015-06-21", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Funeral of the last rhino",
                "/img/content/rhino.jpg",
                "A picture from human states series, size: 60x60, simple description TODO",
                LocalDate.parse("2017-06-01", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("St. Matthews nightmare",
                "/img/content/st_mathews_nightmare.jpg",
                "A picture from human states series, size: 80x60, simple description TODO",
                LocalDate.parse("2011-08-01", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                3);
        addP("Temple of truth and false",
                "/img/content/temple_of_truth_and_false.jpg",
                "A picture from human states series, size: 80x70, simple description TODO",
                LocalDate.parse("2011-03-01", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                3);
        addP("Temptation of Adam and Eve",
                "/img/content/temptation_of_adam_and_eve.jpg",
                "A picture from human states series, size: 100x100, simple description TODO",
                LocalDate.parse("2011-04-01", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                4);

        addP("The tower of Babel",
                "/img/content/tower_of_babel.jpg",
                "A picture from human states series, size: 50x120, simple description TODO",
                LocalDate.parse("2011-07-01", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                1);
        addP("Consumpion",
                "/img/content/CONSUMPTION copy S.jpg",
                "Consumption picture from Human states 200x60cm, simple description TODO",
                LocalDate.parse("2009-05-05", DATE_TIME_FORMATTER_ONLY_DATE),
                7,
                3);
    }
}
