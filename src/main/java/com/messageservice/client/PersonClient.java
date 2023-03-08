package com.messageservice.client;

import com.messageservice.entity.person.Person;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${feign.name}", url = "${feign.url}")
public interface PersonClient {

    @GetMapping(path = "/find/{id}", produces = "application/json")
    Person getPersonById(@PathVariable ("id") Integer id,
                         @RequestHeader("Authorization") String jwt) throws FeignException;
}
