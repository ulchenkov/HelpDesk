package org.launchcode.helpdesk.controllers.tickets;

import org.launchcode.helpdesk.controllers.AbstractBaseController;
import org.launchcode.helpdesk.data.*;
import org.launchcode.helpdesk.helpers.SDHelper;
import org.launchcode.helpdesk.models.Comment;
import org.launchcode.helpdesk.models.Ticket;
import org.launchcode.helpdesk.models.User;
import org.launchcode.helpdesk.models.dto.CommentDTO;
import org.launchcode.helpdesk.models.enums.Role;
import org.launchcode.helpdesk.models.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("tickets")
public class TicketController extends AbstractBaseController {

    private final String basePath = "/tickets/";

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PriorityRepository priorityRepository;

    @Autowired
    CommentRepository commentRepository;


    @GetMapping
    public String index(Model model) {
        SDHelper.initializeModel(model, this.basePath, "", "main-table");
        Set<Ticket> tickets = new HashSet<>();
        User user = getAuthenticatedUser();

        if (user.getRoles().contains(Role.IT_SUPPORT)) {

        }
        ArrayList<Status> statuses = new ArrayList<>();
        statuses.add(Status.ASSIGNED);

        tickets.addAll(ticketRepository.findByRequesterAndStatusIn(user, statuses));
        tickets.addAll(ticketRepository.findByCreatedByAndStatusIn(user, statuses));
        model.addAttribute("tickets", tickets);
        return "index";
    }

    @GetMapping("create")
    public String displayCreateForm(Model model) {
        SDHelper.initializeModel(model, this.basePath, "create", "create-view-ticket");
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("priorities", priorityRepository.findAll());

        User user = getAuthenticatedUser();

        Ticket ticket = new Ticket();
        ticket.setCreatedBy(user);
        ticket.setRequester(user);
        model.addAttribute(ticket);

        return "index";
    }

    @PostMapping("create")
    public String processCreateForm(
            @ModelAttribute @Valid Ticket ticket,
            Errors errors,
            Model model
    ) {
        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "create", "create-view-ticket");
            model.addAttribute("users", userRepository.findAll());
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("priorities", priorityRepository.findAll());

            return "index";
        }
        ticket.setStatus(Status.ASSIGNED);
        ticketRepository.save(ticket);
        return "redirect:" + this.basePath;
    }

    @GetMapping("view")
    public String displayViewForm(Model model, @RequestParam(required = true) Integer id) {
        if (id == null) {
            return "redirect:" + this.basePath;
        }
        Optional<Ticket> opt = ticketRepository.findById(id);
        if (opt.isEmpty()) {
            SDHelper.initializeModel(model, this.basePath, "view", "fragments", "error");
            model.addAttribute("errorText", "Wrong ticket ID: " + id);
            return "index";
        }

        SDHelper.initializeModel(model, this.basePath,"view", "create-view-ticket");
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("priorities", priorityRepository.findAll());
        model.addAttribute("ticket", opt.get());

        User author = getAuthenticatedUser();
        Comment newComment = new Comment();
        newComment.setAuthor(author);
        newComment.setTicket(opt.get());
        model.addAttribute("newComment", newComment);

        return "index";
    }

    @PostMapping("view")
    public String processViewForm(
            @ModelAttribute @Valid Ticket updatedTicket,
            Errors errors,
            @RequestParam int id,
            Model model
    ) {
        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "view", "create-view-ticket");
            model.addAttribute("users", userRepository.findAll());
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("priorities", priorityRepository.findAll());

            User author = userRepository.findById(1).get();
            Comment newComment = new Comment();
            newComment.setAuthor(author);
            newComment.setTicket(ticketRepository.findById(id).get());
            model.addAttribute("newComment", newComment);

            return "index";
        }

        Ticket ticket = ticketRepository.findById(id).get();
        ticket.update(updatedTicket);
        ticketRepository.save(ticket);
        return "redirect:" + this.basePath + "view?id=" + ticket.getId();
    }

    @PostMapping("add-comment")
    public String processAddComment(
            @ModelAttribute @Valid CommentDTO newComment,
            Errors errors,
            Model model
    ) {
        if (errors.hasErrors()) {
            return "redirect:" + this.basePath + "view?id=" + newComment.getTicket().getId();
        }

        Ticket ticket = newComment.getTicket();
        Comment comment = new Comment(newComment);
        commentRepository.save(comment);
        return "redirect:" + this.basePath + "view?id=" + ticket.getId();
    }

    @GetMapping("change-status")
    public String processChangeStatus(
            @RequestParam(required = false) Integer ticketId,
            @RequestParam(required = false) Integer statusId,
            Model model
    ) {
        if (ticketId == null || statusId == null) {
            return "redirect:" + this.basePath;
        }

        Optional<Ticket> optTicket = ticketRepository.findById(ticketId);
        if (optTicket.isEmpty()) {
            SDHelper.initializeModel(model, this.basePath, "view", "fragments", "error");
            model.addAttribute("errorText", "Wrong ticket ID: " + ticketId);
            return "index";
        }
        if (statusId < 0 || statusId > 4) {
            SDHelper.initializeModel(model, this.basePath, "view", "fragments", "error");
            model.addAttribute("errorText", "Wrong status ID: " + statusId);
            return "index";
        }

        Ticket ticket = optTicket.get();
        ticket.setStatus(Status.values()[statusId]);
        ticketRepository.save(ticket);
        return "redirect:" + this.basePath + "view?id=" + ticketId;
    }
}
