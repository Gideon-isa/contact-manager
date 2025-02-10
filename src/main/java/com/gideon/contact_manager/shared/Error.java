package com.gideon.contact_manager.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Error {
    @Nullable
    private String code;

    @Nullable
    private String description;

    public static final Error none = new Error("", "");
}
