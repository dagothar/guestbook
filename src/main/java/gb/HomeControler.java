package gb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dagothar
 */
@Controller
public class HomeControler {

    @RequestMapping(value = "/")
    public String main() {
        return "home";
    }

}
