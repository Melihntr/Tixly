package com.tixly.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tixly.ticket.models.request.WhoAmIRequest;
import com.tixly.ticket.models.response.WhoAmIResponse;

@SpringBootApplication
public class TicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketApplication.class, args);
    }

    @RestController
    public class WhoIamController {

        @PostMapping("/whoiam")
        public WhoAmIResponse getWhoIam(@RequestBody WhoAmIRequest request) {
            WhoAmIResponse response = new WhoAmIResponse();
            response.setName(request.getName());
            return response;
        }
    }
}
