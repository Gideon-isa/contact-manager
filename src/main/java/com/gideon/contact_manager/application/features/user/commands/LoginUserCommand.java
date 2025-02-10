package com.gideon.contact_manager.application.features.user.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginUserCommand {
    private String email;
    private String password;
}
