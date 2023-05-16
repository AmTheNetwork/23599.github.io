package com.aircondition.refrigerationsys.Controller;

import com.aircondition.refrigerationsys.Model.AirCon;
import com.aircondition.refrigerationsys.Repository.AirRepository;
import com.aircondition.refrigerationsys.Service.AirService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class AirController {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private AirRepository airRepository;

    @Autowired
    AirService airService;

    @RequestMapping(value = {"/view"}, method = RequestMethod.GET)
    public String showClaimsList(Model model) {
        System.out.println("I am inside the view controller");
        List<AirCon> viewclient = airService.listAll();
        model.addAttribute("viewclient", viewclient);
        return "admin/view-client";
    }

    @GetMapping("/query")
    public String showQueries(Model model) {
        List<AirCon> viewclient = airRepository.findAll();
        model.addAttribute("viewclient", viewclient);
        return "admin/client-queries-reply";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam String email, @RequestParam String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Meeting Request");
        mailMessage.setText(message);
        emailSender.send(mailMessage);
        return "admin/dashboard";
    }


    @RequestMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Optional<AirCon> meeting = airService.findClientById(id);
        byte[] imageBytes = meeting.get().getEquipmentPhoto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }


    @RequestMapping(value = {"/client"}, method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("refrigeration", new AirCon());
        System.out.println("I am inside client form");
        return "client-claim";
    }


    @PostMapping("/clientform")
    public String submitForm(@ModelAttribute("refrigeration") @Valid AirCon refrigeration,
                             BindingResult bindingResult,
                             @RequestParam(value = "file", required = false) MultipartFile equipmentPhoto) throws IOException {
        if (bindingResult.hasErrors()) {
            System.out.println("Binding result has errors");
        }

        if (!equipmentPhoto.isEmpty()) {
            String contentType = equipmentPhoto.getContentType();
            if (contentType.equals("image/jpeg") || contentType.equals("image/png")) {
                byte[] imageBytes = equipmentPhoto.getBytes();
                refrigeration.setEquipmentPhoto(imageBytes);
            } else {
                bindingResult.rejectValue("equipmentPhoto", "error.equipmentPhoto", "Invalid file type");
                return "homepage";
            }
        }

        airRepository.save(refrigeration);

        return "homepage";
    }


    @GetMapping("/edit/{id}")
    public String editBooking(@PathVariable(value = "id") long id, Model model) {
        AirCon refrigeration = airRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
        model.addAttribute("refrigeration", refrigeration);
        return "admin/update";
    }

    @PostMapping("/update/{id}")
    public String updateBooking(@PathVariable(value = "id") long id, @Valid AirCon refrigeration, BindingResult bindingResult, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        System.out.println("i am inside update controller");
        if (bindingResult.hasErrors()) {
            refrigeration.setId(id);
            return "admin/dashboard";
        } else {
            if (!imageFile.isEmpty()) {
                String contentType = imageFile.getContentType();
                if (contentType.startsWith("image/")) {
                    if (imageFile.getSize() <= 1_000_000) {
                        byte[] imageBytes = imageFile.getBytes();
                        refrigeration.setEquipmentPhoto(imageBytes);

                    } else {
                        bindingResult.rejectValue("imageFile", "error.imageFile", "File size should be less than or equal to 1MB");
                        refrigeration.setId(id);
                        return "admin/dashboard";
                    }
                } else {
                    bindingResult.rejectValue("imageFile", "error.imageFile", "Invalid file type. Please upload an image file.");
                    refrigeration.setId(id);
                    return "admin/dashboard";
                }
            }

            airRepository.save(refrigeration);
            return "redirect:/view";
        }
    }




        @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable(value = "id") long id) {
        AirCon airCon = airRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
        airRepository.delete(airCon);
        return "redirect:/view";
    }



}



