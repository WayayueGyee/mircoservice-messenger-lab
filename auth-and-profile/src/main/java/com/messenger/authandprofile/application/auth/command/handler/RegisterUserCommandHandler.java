package com.messenger.authandprofile.application.auth.command.handler;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.auth.command.RegisterUserCommand;
import com.messenger.authandprofile.application.auth.dto.UserWithTokenDto;
import com.messenger.authandprofile.application.auth.service.TokenService;
import com.messenger.authandprofile.application.shared.mapper.UserMapper;
import com.messenger.authandprofile.domain.exception.user.UserAlreadyExistsException;
import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.model.valueobject.*;
import com.messenger.authandprofile.domain.repository.UserRepository;
import io.vavr.control.Either;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class RegisterUserCommandHandler implements
        Command.Handler<RegisterUserCommand, Either<Exception, UserWithTokenDto>> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    public RegisterUserCommandHandler(
            UserRepository userRepository, UserMapper userMapper, TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    @Override
    public Either<Exception, UserWithTokenDto> handle(@NonNull RegisterUserCommand command) {
        if (userRepository.isExistsByLogin(command.getLogin())) {
            return Either.left(UserAlreadyExistsException.createUserIsAlreadyExistsByLogin(command.getLogin()));
        } else if (userRepository.isExistsByEmail(command.getEmail())) {
            return Either.left(UserAlreadyExistsException.createUserIsAlreadyExistsByEmail(command.getEmail()));
        }

        var user = User.builder(new Login(command.getLogin()), new Email(command.getEmail()),
                        new FullName(command.getFirstName(), command.getMiddleName(), command.getLastName()),
                        new RegexPassword(command.getPassword())
                )
                .withBirthDate(new BirthDate(command.getBirthDate()))
                .withPhoneNumber(new PhoneNumber(command.getPhoneNumber()))
                .withCity(command.getCity())
                .registerUser();
        userRepository.addUser(user);

        var token = tokenService.generateTokens(user);

        return Either.right(userMapper.mapToUserWithTokenDto(user, token));
    }
}