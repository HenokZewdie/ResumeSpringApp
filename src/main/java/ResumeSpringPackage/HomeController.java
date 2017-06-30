package ResumeSpringPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class HomeController {

    String emailSession;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private UserRepository userRepository;


    /*Home Controller*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginName(Principal principal, Person person, Model model){
        person.setEmail(principal.getName());
        System.out.println(person.getEmail());
        return "login";
    }
    /*Registration controller*/

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String signupGet(Model model){
        model.addAttribute(new Person());
        return "register";
}
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute Person person, User user){
        emailSession = person.getEmail();
        user.setEnabled(true);
    user.setUsername(person.getEmail());
    user.setPassword(person.getPassword());
    userRepository.save(user);
    person.setDate(new Date());
    personRepository.save(person);
     return "redirect:/display";
}
       /*Education controller*/
    @RequestMapping(value = "/education", method = RequestMethod.GET)
    public String EducationGet(Model model){
        model.addAttribute("education", new Education());
        return "education";
    }
    @RequestMapping(value = "/education", method = RequestMethod.POST)
    public String EducationPost(@ModelAttribute Education education, Model model){
        education.setEmail(emailSession);
        educationRepository.save(education);
        model.addAttribute("education", new Education());
        return "education";
    }
    /*Experience controller*/
    @RequestMapping(value = "/experience", method = RequestMethod.GET)
    public String ExperienceGet(Model model){
        model.addAttribute("experience", new Experience());
        return "experience";
    }
    @RequestMapping(value = "/experience", method = RequestMethod.POST)
    public String ExperiencePost(@ModelAttribute Experience experience, Model model){
        experience.setEmail(emailSession);
        experienceRepository.save(experience);
        model.addAttribute("experience", new Experience());
        return "experience";
    }
    /*Skill controller*/
    @RequestMapping(value = "/skill", method = RequestMethod.GET)
    public String SkillGet(Model model){
        model.addAttribute("skill", new Skill());
        return "skill";
    }
    @RequestMapping(value = "/skill", method = RequestMethod.POST)
    public String SkillPost(@ModelAttribute Skill skill, Model model){
        skill.setEmail(emailSession);
        skillRepository.save(skill);
        model.addAttribute("skill", new Skill());
        return "skill";
    }
    /*Display Controller*/
    @RequestMapping(value = "display", method = RequestMethod.GET)
    public String toSend( Person person, Model model){

        Iterable<Person> values = personRepository.findByEmail(emailSession);
        model.addAttribute("newValue", values);
        return "display";
    }
    /*Display Controller*/

    @RequestMapping(value = "loginSuccess", method = RequestMethod.GET)
    public String loginSuccess( Model model, Person person, Principal principal){

        person.setEmail(principal.getName());
        String emailSession1 = person.getEmail();
        Iterable<Person> values = personRepository.findByEmail(emailSession1);
        Iterable<Education> Educvalues = educationRepository.findByEmail(emailSession1);
        Iterable<Experience> Expvalues = experienceRepository.findByEmail(emailSession1);
        Iterable<Skill> Skillvalues = skillRepository.findByEmail(emailSession1);
        model.addAttribute("values", values);
        model.addAttribute("Educvalues", Educvalues);
        model.addAttribute("Expvalues", Expvalues);
        model.addAttribute("Skillvalues", Skillvalues);
        return "displayAll";
    }

    @RequestMapping(value = "searchAll", method = RequestMethod.GET)
    public String SearchAll( Model model, Person person, Principal principal){


        String emailSession1 = person.getEmail();
        Iterable<Person> values = personRepository.findByEmail(emailSession1);
        Iterable<Education> Educvalues = educationRepository.findByEmail(emailSession1);
        Iterable<Experience> Expvalues = experienceRepository.findByEmail(emailSession1);
        Iterable<Skill> Skillvalues = skillRepository.findByEmail(emailSession1);
        model.addAttribute("values", values);
        model.addAttribute("Educvalues", Educvalues);
        model.addAttribute("Expvalues", Expvalues);
        model.addAttribute("Skillvalues", Skillvalues);
        return "displayAll";
    }
    /*Search by email*/
    @RequestMapping(value = "displayAll", method = RequestMethod.GET)
    public String DisplayAll( Model model, Person person){


        Iterable<Person> values = personRepository.findByEmail(emailSession);
        Iterable<Education> Educvalues = educationRepository.findByEmail(emailSession);
        Iterable<Experience> Expvalues = experienceRepository.findByEmail(emailSession);
        Iterable<Skill> Skillvalues = skillRepository.findByEmail(emailSession);
        model.addAttribute("values", values);
        model.addAttribute("Educvalues", Educvalues);
        model.addAttribute("Expvalues", Expvalues);
        model.addAttribute("Skillvalues", Skillvalues);
        return "displayAll";
    }

    /*Search by Name*/
    @RequestMapping(value = "/search", method = RequestMethod.GET)// , params={"company"} to use a text box for two buttons named "name and comapny"
    public String SearchByName(Model model){
        model.addAttribute(new Person());
        return "search";
    }
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String SearchPostName(@ModelAttribute Person person, Model model){
        String nameSession = person.getFirstName();
        Iterable<Person> newVal = personRepository.findByFirstName(nameSession);
        model.addAttribute("newValue", newVal);
        return "displaySearch";
    }
    @RequestMapping(value = "/searchrole", method = RequestMethod.GET)
    public String Searchrole(Model model){
        model.addAttribute(new Person());
        return "redirect:/searchrole";
    }
    @RequestMapping(value = "/searchrole", method = RequestMethod.POST)
    public String Searchrolepost(@ModelAttribute Person person, Model model){
        String companySession = person.getRole();
        Iterable<Person> newVal = personRepository.findByRole(companySession);
        model.addAttribute("searchrole", newVal);
        return "displaySearch";
    }

  /*  @RequestMapping(value = "/company", method = RequestMethod.GET)
    public String SearchCompany(Model model){
        model.addAttribute(new Experience());
        return "redirect:/company";
    }
    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public String SearchCompanypost(@ModelAttribute Experience experience, Model model){
        String companySession = experience.getCompany();
        Iterable<Experience> newVal = experienceRepository.findByCompany(companySession);
        model.addAttribute("searchCompany", newVal);
        return "displaySearch";
    }*/
}
