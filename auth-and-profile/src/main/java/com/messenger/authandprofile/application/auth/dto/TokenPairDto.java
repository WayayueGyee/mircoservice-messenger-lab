package com.messenger.authandprofile.application.auth.dto;

import lombok.Value;

@Value
public class TokenPairDto {
    String accessToken;
    String refreshToken;
}
