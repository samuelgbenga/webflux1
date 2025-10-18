package com.example.reactiveprog1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public Mono<UserResponse> saveUser(@RequestBody UserRequest request) {
        try {

            log.info("saving user >>>>");
            return userService.saveUser(request);
        }catch (Exception e){
            log.error("could not save user >>>error");
            return Mono.error(e);
        }
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserResponse> retrieveUsers() {
       // return userService.retrieveUsers();
        return userService.retrieveUsers()
                .onBackpressureBuffer(10, BufferOverflowStrategy.DROP_OLDEST)
                .delayElements(Duration.ofMillis(100))
                .log();
    }

}