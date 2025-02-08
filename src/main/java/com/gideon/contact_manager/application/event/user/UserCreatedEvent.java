package com.gideon.contact_manager.application.event.user;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreatedEvent extends ApplicationEvent {
    private final String username;
    private final String email;

    public UserCreatedEvent(Object source, String username, String email) {
        super(source);
        this.username = username;
        this.email = email;
    }
}
