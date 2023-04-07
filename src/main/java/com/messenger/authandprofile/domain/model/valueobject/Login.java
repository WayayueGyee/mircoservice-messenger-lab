package com.messenger.authandprofile.domain.model.valueobject;

import com.messenger.authandprofile.domain.exception.login.InvalidLoginException;
import lombok.NonNull;
import lombok.Value;

import java.util.regex.Pattern;

@Value
public class Login {
    @NonNull String value;

    public Login(@NonNull String value) throws InvalidLoginException {
        final var isMatch = LoginPattern.PATTERN.matcher(value).matches();
        if (!isMatch) throw new InvalidLoginException();
        this.value = value;
    }

    private static final class LoginPattern {
        private static final String REGEX = "^[0-9a-zA-Z]\\w{4,32}$";
        private static final Pattern PATTERN = Pattern.compile(REGEX);
    }
}
