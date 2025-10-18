package com.example.reactiveprog1;

import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {
    UserResponse toUserResponse(User destination);
    User toUser(UserRequest request);
}
