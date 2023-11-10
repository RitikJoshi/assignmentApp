package com.assignment6.assignmentApp.controller;

import com.assignment6.assignmentApp.model.Customer;
import com.assignment6.assignmentApp.service.AuthService;
import com.assignment6.assignmentApp.service.CustomerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    CustomerService customerService;
    AuthService authService;
    Customer model =new Customer();

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public CustomerController(CustomerService customerService, AuthService authService) {
        this.customerService = customerService;
        this.authService = authService;
    }

    @GetMapping("/show")
    public String showForm(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer",customer);
        return "create";
    }

    @PostMapping(value = "/create",consumes = "application/x-www-form-urlencoded")
    public String createCustomer(Customer customer) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization",authService.getBearerToken());

        // Create a request entity with the customer data and headers
//        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, headers);
        Map<String, String> map=new HashMap<>();
        String fName=customer.getFirst_name().trim();
        map.put("first_name", fName);
        String lName=customer.getLast_name().trim();
        map.put("last_name",lName);
        map.put("phone",customer.getPhone().trim());
        map.put("email",customer.getEmail().trim());
        map.put("street",customer.getStreet().trim());
        map.put("city",customer.getCity().trim());
        map.put("address",customer.getAddress().trim());
        map.put("state",customer.getState().trim());
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Check the response status and handle success or failure accordingly
        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
            // Successfully created
            return "redirect:/customer/list";
        } else if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {

        }

        return "redirect:/customer/list"; // Redirect to the customer list page
    }

    @GetMapping("/list")
    public String getCustomerList(ModelMap model) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authService.getBearerToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list",
                HttpMethod.GET,
                entity,
                String.class
        );
        List<Customer> list=new ArrayList<>();
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            //Customer[] customers = responseEntity.getBody();
            //return Arrays.asList(customers);
            String res = responseEntity.getBody();
            JsonArray arr = JsonParser.parseString(res).getAsJsonArray();

            for(JsonElement x:arr){
                if(!x.isJsonNull()) {
                    JsonObject temp = x.getAsJsonObject();
                    list.add(populateCustomerList(temp));
                }
            }
            model.addAttribute("list",list);
            return "list";
        }
        return "error";
    }

    private Customer populateCustomerList(JsonObject temp) {
        Customer customer=new Customer();

        JsonElement uuidElement = temp.get("uuid");
        if (uuidElement != null && !uuidElement.isJsonNull()) {
            customer.setAddress(uuidElement.getAsString());
        }

        // Check and set the 'address' field
        JsonElement addressElement = temp.get("address");
        if (addressElement != null && !addressElement.isJsonNull()) {
            customer.setAddress(addressElement.getAsString());
        }

        // Check and set the 'first_name' field
        JsonElement firstNameElement = temp.get("first_name");
        if (firstNameElement != null && !firstNameElement.isJsonNull()) {
            customer.setFirst_name(firstNameElement.getAsString());
        }

        // Check and set the 'last_name' field
        JsonElement lastNameElement = temp.get("last_name");
        if (lastNameElement != null && !lastNameElement.isJsonNull()) {
            customer.setLast_name(lastNameElement.getAsString());
        }

        // Check and set the 'street' field
        JsonElement streetElement = temp.get("street");
        if (streetElement != null && !streetElement.isJsonNull()) {
            customer.setStreet(streetElement.getAsString());
        }

        // Check and set the 'phone' field
        JsonElement phoneElement = temp.get("phone");
        if (phoneElement != null && !phoneElement.isJsonNull()) {
            customer.setPhone(phoneElement.getAsString());
        }

        // Check and set the 'email' field
        JsonElement emailElement = temp.get("email");
        if (emailElement != null && !emailElement.isJsonNull()) {
            customer.setEmail(emailElement.getAsString());
        }

        // Check and set the 'state' field
        JsonElement stateElement = temp.get("state");
        if (stateElement != null && !stateElement.isJsonNull()) {
            customer.setState(stateElement.getAsString());
        }

        // Check and set the 'city' field
        JsonElement cityElement = temp.get("city");
        if (cityElement != null && !cityElement.isJsonNull()) {
            customer.setCity(cityElement.getAsString());
        }

        return customer;
    }


    @PostMapping("/update/{uuid}")
    public String updateCustomer(@PathVariable UUID uuid, @ModelAttribute Customer customer) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authService.getBearerToken());

        // Create a request entity with the customer data and headers
        HttpEntity<Customer> entity = new HttpEntity<>(customer, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update&uuid=" + uuid,
                HttpMethod.POST,
                entity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Successfully updated
            return "redirect:/customer/list";
        } else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            return "customer/list";
        } else if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {
            return "customer/list";
        } else {
            return "redirect:/customer/list";
        }
    }


    @PostMapping("/delete/{uuid}")
    public String deleteCustomer(@PathVariable UUID uuid) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authService.getBearerToken());

        // Set up the request entity with headers
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete&uuid=" + uuid,
                HttpMethod.POST,
                entity,
                String.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Successfully deleted
            return "redirect:/customer/list";
        } else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            return "customer/list";
        } else if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {
            return "customer/list";
        } else {
            return "redirect:/customer/list";
        }
    }


}

