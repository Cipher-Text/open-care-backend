package com.ciphertext.opencarebackend.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeycloakAdminService {

    private final String serverUrl;
    private final String realm;
    private final String adminRealm;
    private final String adminClientId;
    private final String adminUsername;
    private final String adminPassword;


    public KeycloakAdminService(
            @Value("${app.keycloak.server-url}") String serverUrl,
            @Value("${app.keycloak.realm}") String realm,
            @Value("${app.keycloak.admin.realm}") String adminRealm,
            @Value("${app.keycloak.admin.client-id}") String adminClientId,
            @Value("${app.keycloak.admin.username}") String adminUsername,
            @Value("${app.keycloak.admin.password}") String adminPassword) {
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.adminRealm = adminRealm;
        this.adminClientId = adminClientId;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    private Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(adminRealm)  // The realm to log in to (always 'master' for admin operations)
                .clientId(adminClientId)
                .username(adminUsername)  // Admin username
                .password(adminPassword)  // Admin password
                .build();
    }

    public List<UserRepresentation> getUsers() {
        Keycloak keycloak = getKeycloakInstance();
        return keycloak.realm(realm).users().list();
    }

    public UserRepresentation getUserById(String userId) {
        Keycloak keycloak = getKeycloakInstance();
        return keycloak.realm(realm).users().get(userId).toRepresentation();
    }

    public void updateUser(String userId, UserRepresentation userRepresentation) {
        Keycloak keycloak = getKeycloakInstance();
        keycloak.realm(realm).users().get(userId).update(userRepresentation);
    }

    public void deleteUser(String userId) {
        Keycloak keycloak = getKeycloakInstance();
        keycloak.realm(realm).users().get(userId).remove();
    }

    public void resetUserPassword(String userId, String newPassword, boolean temporary) {
        Keycloak keycloak = getKeycloakInstance();
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(temporary);
        keycloak.realm(realm).users().get(userId).resetPassword(credential);
    }

    // Add roles to user
    public void addRolesToUser(String userId, List<String> roleNames) {
        Keycloak keycloak = getKeycloakInstance();
        UserResource userResource = keycloak.realm(realm).users().get(userId);

        List<RoleRepresentation> realmRoles = roleNames.stream()
                .map(roleName -> keycloak.realm(realm).roles().get(roleName).toRepresentation())
                .collect(Collectors.toList());

        userResource.roles().realmLevel().add(realmRoles);
    }
}