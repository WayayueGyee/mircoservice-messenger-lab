package com.messenger.authandprofile.infra.auth.jwt.validator;

import com.messenger.authandprofile.infra.auth.jwt.JwtBearerTokenParameters;

@FunctionalInterface
public interface SignatureValidator {
    boolean validate(String token, JwtBearerTokenParameters jwtBearerTokenParameters);
}
