package gb;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author dagothar
 */
@Controller
@RequestMapping(value = {"/", "home"})
public class HomeControler {

    @Autowired
    private EntryRepository entryRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView home(
            @RequestParam(value = "offset", defaultValue = "0") Long offset,
            @RequestParam(value = "limit", defaultValue = "5") Long limit,
            ModelAndView mav
    ) {
        List<Entry> entries = entryRepository.findEntries(offset, limit);
        mav.addObject("entries", entries);

        if (offset > 0) {
            Long prevPageId = offset - limit;
            prevPageId = (prevPageId > 0 ? prevPageId : 0);
            mav.addObject("prevPageId", prevPageId);
        }

        if (entries.size() == limit) {
            Long nextPageId = offset + limit;
            mav.addObject("nextPageId", nextPageId);
        }

        mav.setViewName("home");

        return mav;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView addEntry(@Valid ModelAndView mav) {
        mav.setViewName("home");

        return mav;
    }
}
