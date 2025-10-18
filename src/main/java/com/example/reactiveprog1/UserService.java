package com.example.reactiveprog1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    private final UserRepository userRepository;


    public Mono<UserResponse> saveUser(UserRequest request) {
        User user = userMapper.toUser(request);
        Mono<User> customerMono = userRepository.save(user).log();
        return customerMono.map(userMapper::toUserResponse);
    }

    public Flux<UserResponse> retrieveUsers() {
        return userRepository.findAll().log().map(userMapper::toUserResponse);
    }

}