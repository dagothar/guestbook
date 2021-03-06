package gb;

import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author dagothar
 */
@Controller
@RequestMapping(value = {"/", "home"})
public class HomeControler {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    Validator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String home(
            @RequestParam(value = "offset", defaultValue = "0") Long offset,
            @RequestParam(value = "limit", defaultValue = "3") Long limit,
            Model m
    ) {

        if (!m.containsAttribute("entry")) {
            Entry newEntry = new Entry(0, "", "", new LocalDateTime());
            m.addAttribute("entry", newEntry);
        }

        List<Entry> entries = entryRepository.findEntries(offset, limit);
        m.addAttribute("entries", entries);

        if (offset > 0) {
            Long prevOffset = offset - limit;
            prevOffset = (prevOffset > 0) ? prevOffset : 0;
            m.addAttribute("prevOffset", prevOffset);
        }

        if (offset < entryRepository.findAll().size() - limit) {
            Long nextOffset = offset + limit;
            m.addAttribute("nextOffset", nextOffset);
        }

        return "home";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addEntry(@Valid Entry entry, BindingResult result, RedirectAttributes attr) {

        if (result.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.entry", result);
            attr.addFlashAttribute("entry", entry);

            return "redirect:/";
        }

        entryRepository.addEntry(entry);

        return "redirect:/";
    }

}
