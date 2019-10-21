package com.foxploit.ignio.gateway.web.rest;

import com.foxploit.ignio.gateway.domain.User;
import com.foxploit.ignio.gateway.repository.UserRepository;
import com.foxploit.ignio.gateway.service.MailService;
import com.foxploit.ignio.gateway.service.dto.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AlertResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final MailService mailService;

    public AlertResource(UserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @PutMapping("/alert")
    public void alertUser(@RequestBody Alert alert){
        Optional<User> user = userRepository.findById(alert.getOwnerId());
        if(user.isPresent()){
            log.info("Alert owner of the device {} with appropriate details", alert.getDeviceId());
            mailService.sendAlertMail(user.get(), alert);
        }else{
            log.info("User associated with the id {} does not exists", alert.getOwnerId());
        }
    }

}
