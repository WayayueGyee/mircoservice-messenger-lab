package com.messenger.authandprofile.application.profile.command;

import an.awesome.pipelinr.Command;
import com.messenger.authandprofile.application.profile.dto.UserDto;
import io.vavr.control.Either;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class EditUserProfileCommand implements Command<Either<Exception, UserDto>> {
    private UUID id;
    private String firstName;
    @Nullable private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String city;
    private UUID avatar;
}
