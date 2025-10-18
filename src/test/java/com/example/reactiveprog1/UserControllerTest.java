package com.example.reactiveprog1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController controller;

    @Test
    void testRetrieveUsersBackpressureAndDelay() {
        // Create a mock Flux of UserResponse objects with 1000 elements
        // Not considering other values like address, email etc for test case
        Flux<UserResponse> mockFlux = Flux.range(1, 1000)
                .map(i -> UserResponse.builder()
                        .id(String.valueOf(i))
                        .build()
                );

        // When the retrieveUsers method is called on the userService mock,
        // return the mockFlux we created above
        when(userService.retrieveUsers()).thenReturn(mockFlux);

        // Subscribe to the Flux returned by the controller's retrieveUsers method
        Flux<UserResponse> result = controller.retrieveUsers();

        // Verify that the backpressure and delay are working as expected
        StepVerifier.create(result)
                .expectSubscription()
                .thenRequest(5) // Request the first 5 elements
                .expectNextCount(5) // Expect to receive 5 elements
                .thenAwait(Duration.ofMillis(100)) // Wait for 100ms before requesting more elements
                .thenRequest(5) // Request the next 5 elements
                .expectNextCount(5) // Expect to receive 5 more elements
                .thenCancel()
                .verify();
    }

    @Test
    void testRetrieveUsers_BufferOverflow() {
        // Given
        int numUsers = 100;
        when(userService.retrieveUsers()).thenReturn(Flux.range(1, numUsers)
                .map(i -> UserResponse.builder().id(String.valueOf(i)).name("User "+ i).build()));
        // When
        Flux<UserResponse> userResponseFlux = controller.retrieveUsers();

        // Then
        StepVerifier.create(userResponseFlux)
                .expectSubscription()
                .expectNextCount(10) // With a backpressure buffer size of 10, we expect to receive the first 10 users
                .thenAwait(Duration.ofMillis(1000)) // Wait for the buffer to overflow
                .expectNextCount(0) // We should not receive any more users after the buffer overflows
                .thenCancel()
                .verify();
    }


}