package com.example.FoodDeliveryApp.controller;

import com.example.FoodDeliveryApp.dto.DeliveryPartnerRequest;
import com.example.FoodDeliveryApp.service.DeliveryPartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/delivery-partners")
@RequiredArgsConstructor
public class DeliveryPartnerController {

    private final DeliveryPartnerService deliveryPartnerService;

    // View All Delivery Partners
    @GetMapping
    public String getAllDeliveryPartners(Model model) {

        model.addAttribute(
                "deliveryPartners",
                deliveryPartnerService.getAllDeliveryPartners());

        return "delivery-partner/list";
    }

    // Open Create Page
    @GetMapping("/create")
    public String createDeliveryPartnerPage(Model model) {

        model.addAttribute(
                "request",
                new DeliveryPartnerRequest());

        return "delivery-partner/create";
    }

    // Save Delivery Partner
    @PostMapping("/create")
    public String createDeliveryPartner(
            @ModelAttribute DeliveryPartnerRequest request) {

        deliveryPartnerService.createDeliveryPartner(request);

        return "redirect:/admin/delivery-partners";
    }

    // View One Delivery Partner
    @GetMapping("/{id}")
    public String getDeliveryPartnerById(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "deliveryPartner",
                deliveryPartnerService.getDeliveryPartnerById(id));

        return "delivery-partner/view";
    }

    // Delete Delivery Partner
    @GetMapping("/delete/{id}")
    public String deleteDeliveryPartner(
            @PathVariable Long id) {

        deliveryPartnerService.deleteDeliveryPartner(id);

        return "redirect:/admin/delivery-partners";
    }
}