package com.gideon.contact_manager.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ContactGroup {
    FAMILY("Family"),
    FRIENDS("Friends"),
    WORK("Work"),
    OTHER("Others");


    private final String displayName;

    ContactGroup(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static ContactGroup forValue(String displayName) {
        for (ContactGroup group : ContactGroup.values()) {
            if (group.getDisplayName().equalsIgnoreCase(displayName)) {
                return group;
            }
        }
        throw new IllegalArgumentException("Unknown group: " + displayName);
    }
}

