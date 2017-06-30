package ResumeSpringPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
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


    /*Home Controller*/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    /*Registration controller*/

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String signupGet(Model model){
        model.addAttribute(new Person());
        return "register";
}
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String signupPost(@ModelAttribute Person person){
    emailSession = person.getEmail();
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
    public String toSend(@ModelAttribute Person person, Model model){

        Iterable<Person> values = personRepository.findByEmail(emailSession);
        model.addAttribute("values", values);
        return "display";
    }
    /*Display Controller*/
    @RequestMapping(value = "displayAll", method = RequestMethod.GET)
    public String DisplayAll( Model model){

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

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String SearchByName(Model model){
        model.addAttribute(new Person());
        return "search";
    }
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String SearchPostName(@ModelAttribute Person person, Model model){
        String nameSession = person.getFirstName();
        Iterable<Person> resultPerson = personRepository.findByFirstName(nameSession);
        model.addAttribute("values", resultPerson);
        return "display";
    }
}
