package restaurant.service;

import restaurant.dto.request.SignInRequest;
import restaurant.dto.request.SignUpRequest;
import restaurant.dto.response.SignResponse;
import restaurant.dto.response.SimpleResponse;
import restaurant.exceptions.NotFoundException;

public interface UserService {
    SignResponse signIn(SignInRequest signInRequest) throws NotFoundException;

    SimpleResponse signUp(Long resId, SignUpRequest signUpRequest);
}
