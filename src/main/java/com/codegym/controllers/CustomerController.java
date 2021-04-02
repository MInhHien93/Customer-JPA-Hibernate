package com.codegym.controllers;

import com.codegym.models.entities.Customer;
import com.codegym.models.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    public ICustomerService customerService;

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm() {
        ModelAndView mav = new ModelAndView("/customer/create");
        mav.addObject("customer", new Customer());
        return mav;
    }

    @PostMapping("/create-customer")
    public ModelAndView save(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView mav = new ModelAndView("/customer/create");
        mav.addObject("customer", customer);
        mav.addObject("message", "New Customer created successfully!");
        return mav;
    }

    @GetMapping("/customers")
    public ModelAndView showListCustomers() {
        List<Customer> customers = customerService.findAll();
        ModelAndView mav = new ModelAndView("/customer/index");
        mav.addObject("customers", customers);
        return mav;
    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer.getId() != null) {
            ModelAndView mav = new ModelAndView("/customer/edit");
            mav.addObject("customer", customer);
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("/error.404");
            return mav;
        }
    }

    @PostMapping("/edit-customer")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView mav = new ModelAndView("/customer/edit");
        mav.addObject("customer", customer);
        mav.addObject("message", "Customer updated successfully!");
        return mav;
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            ModelAndView mav = new ModelAndView("/customer/delete");
            mav.addObject("customer", customer);
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("/error.404");
            return mav;
        }
    }

    @PostMapping("/delete-customer")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.delete(customer.getId());
        return "redirect:/customers";
    }
}
