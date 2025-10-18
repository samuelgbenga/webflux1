package com.example.reactiveprog1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class UserWebClient {

    private final UserMapper userMapper;

    WebClient client = WebClient.create("http://localhost:8080");



    // print out the user

    public void printSomething(){
        Flux<User> userFlux = client.get()
                .uri("/api/user")
                .retrieve()
                .bodyToFlux(User.class);

        Flux<UserResponse> userResponseFlux= userFlux.log().map(userMapper::toUserResponse);

        userResponseFlux.subscribe(System.out::println);
    }


}