package com.tvhson.postservice.service;

import com.tvhson.postservice.payload.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface APIClient {
    @GetMapping("api/users/exists/{id}")
    boolean existsById(@PathVariable Long id);
    @GetMapping("api/users/{id}")
    UserDto getUserById(@PathVariable Long id);
}