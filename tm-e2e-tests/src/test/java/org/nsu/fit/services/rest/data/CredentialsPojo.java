package org.nsu.fit.services.rest.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CredentialsPojo {
    private final String login;

    private final String pass;
}
