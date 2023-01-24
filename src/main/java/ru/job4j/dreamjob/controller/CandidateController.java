package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.CandidateService;


@ThreadSafe
@Controller
@RequestMapping("/candidates") /* Работать с кандидатами будем по URI /candidates/** */
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates/list";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "candidates/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Candidate candidate) {
        candidateService.save(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var candidateOptional = candidateService.findById(id);
        if (candidateOptional.isEmpty()) {
            model.addAttribute("message", "Кандидат с указанным идентификатором не найден");
            return "errors/404";
        }
        model.addAttribute("candidate", candidateOptional.get());
        return "candidates/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Candidate candidate, Model model) {
        var isUpdated = candidateService.update(candidate);
        if (!isUpdated) {
            model.addAttribute("message", "Кандидат с указанным идентификатором не найден");
            return "errors/404";
        }
        return "redirect:/candidates";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = candidateService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Кандидат с указанным идентификатором не найден");
            return "errors/404";
        }
        return "redirect:/candidates";
    }
}



//package ru.job4j.dreamjob.controller;
//
//import net.jcip.annotations.ThreadSafe;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import ru.job4j.dreamjob.model.Candidate;
//import ru.job4j.dreamjob.service.CandidateService;
//import ru.job4j.dreamjob.service.CityService;
//
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//@ThreadSafe
//@Controller
//public class CandidateController {
//
//    private final CandidateService candidateService;
//    private final CityService cityService;
//
//    public CandidateController(CandidateService candidateService, CityService cityService) {
//        this.candidateService = candidateService;
//        this.cityService = cityService;
//    }
//
//    @GetMapping("/candidates")
//    public String candidates(Model model) {
//        model.addAttribute("candidates", candidateService.findAll());
//        return "candidates";
//    }
//
//    @GetMapping("/formAddCandidate")
//    public String addCandidate(Model model) {
//        model.addAttribute("cities", cityService.getAllCities());
//        return "addCandidate";
//    }
//
//    @PostMapping("/createCandidate")
//    public String createCandidate(@ModelAttribute Candidate candidate,
//                                  @RequestParam("file") MultipartFile file) throws IOException {
//        candidate.setPhoto(file.getBytes());
//        candidate.setCreated(LocalDateTime.now());
//        candidateService.add(candidate);
//        return "redirect:/candidates";
//    }
//
//    @GetMapping("/formUpdateCandidate/{candidateId}")
//    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
//        model.addAttribute("cities", cityService.getAllCities());
//        model.addAttribute("candidate", candidateService.findById(id));
//        return "updateCandidate";
//    }
//
//    @PostMapping("/updateCandidate")
//    public String updateCandidate(@ModelAttribute Candidate candidate,
//                                  @RequestParam("file") MultipartFile file) throws IOException {
//        candidate.setPhoto(file.getBytes());
//        candidate.setCreated(LocalDateTime.now());
//        candidateService.update(candidate);
//        return "redirect:/candidates";
//    }
//
//    @GetMapping("/photoCandidate/{candidateId}")
//    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) {
//        Candidate candidate = candidateService.findById(candidateId);
//        return ResponseEntity.ok()
//                .headers(new HttpHeaders())
//                .contentLength(candidate.getPhoto().length)
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(new ByteArrayResource(candidate.getPhoto()));
//    }
//}
