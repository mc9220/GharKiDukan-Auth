package com.gkd.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AuthResponse {

    private String accessToken;
}
