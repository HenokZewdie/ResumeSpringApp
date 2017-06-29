package ResumeSpringPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.Date;


@Controller
public class HomeController {

    String emailSession;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ExperienceRepository experienceRepository;


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
    /*Display Controller*/
   @RequestMapping(value = "display", method = RequestMethod.GET)
    public String toSend(@ModelAttribute Person person, Model model){

        Iterable<Person> values = personRepository.findByEmail(emailSession);
        model.addAttribute("values", values);
        return "display";
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
        return "education";
    }
}
