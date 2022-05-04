package sk.umb.fpv.dain142demo.iam;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    private static final Pattern USER_ID_PATTERN = Pattern.compile("([^\\/]+$)");
    private static final String CLIENT_ID_PROPERTY = "client_id";
    private static final String USERNAME_PROPERTY = "username";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String GRANT_TYPE_PROPERTY = "grant_type";
    private static final String ACCESS_TOKEN_PROPERTY = "access_token";
    private static final String CLIENT_ID = "eduq-rcapp";
    private static final String GRANT_TYPE = "password";
    private static final String GET_TOKEN_URI_TEMPLATE = "/auth/realms/%s/protocol/openid-connect/token";

    private RestTemplate restTemplate;

    private String keycloakUri;

    private String realm;

    private String getTokenUri;

    private Keycloak keycloak;

    public KeycloakAdminServiceImpl(Keycloak keycloak, @Value("${keycloak.realm}") String realm,
            @Value("${uservice.oidc-uri}") String keycloakUri) {
        this.keycloak = keycloak;
        this.realm = realm;
        this.keycloakUri = keycloakUri;
        this.getTokenUri = String.format(GET_TOKEN_URI_TEMPLATE, realm);
    }

    public String createUser(UserCreate request) throws KeycloakException {
        if (log.isDebugEnabled()) {
            log.debug("createUser {}", request);
        }

        CredentialRepresentation password = preparePasswordRepresentation(request.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setCredentials(Arrays.asList(password));
        user.setEnabled(false);

        try {
            Response res = realmResource().users().create(user);
            if (res.getStatus() == 201) {
                String userId = parseUserId(res.getHeaderString(HttpHeaders.LOCATION));
                if (request.getRoles() != null && request.getRoles().size() > 0) {
                    assignRoles(userId, request.getRoles());
                }
                return userId;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new KeycloakException("Error occurred while creating user " + request.getUsername(), e);
        }
        throw new KeycloakException("Error occurred while creating user " + request.getUsername());
    }

    public void editRoles(String userId, List<String> roles) throws KeycloakException {
        log.debug("editRoles: userId: {}, roles: {}", userId, roles);
        try {
            List<RoleRepresentation> rolesToDelete = realmResource()
                    .users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .listAll()
                    .stream()
                    .filter(r -> !roles.contains(r.getName()))
                    .collect(Collectors.toList());

            if (!rolesToDelete.isEmpty()) {
                realmResource()
                        .users()
                        .get(userId)
                        .roles()
                        .realmLevel()
                        .remove(rolesToDelete);
            }

            assignRoles(userId, roles);
        } catch (Exception e) {
            throw new KeycloakException("Error occurred while editing roles for user " + userId, e);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void deleteUser(String userId) throws KeycloakException {
        log.debug("deleteUser: userId: {}", userId);
        try {
            realmResource()
                    .users()
                    .delete(userId);
        } catch (Exception e) {
            throw new KeycloakException("Error occurred when deleting user with id: " + userId, e);
        }
    }

    @Override
    public boolean matchesPassword(String userName, String password) {
        log.debug("matchesPassword: userName: {}", userName);

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(CLIENT_ID_PROPERTY, CLIENT_ID);
        map.add(PASSWORD_PROPERTY, password);
        map.add(USERNAME_PROPERTY, userName);
        map.add(GRANT_TYPE_PROPERTY, GRANT_TYPE);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            Map<String, String> response = rest().postForObject(keycloakUri + getTokenUri, request, Map.class);
            return response != null && StringUtils.hasLength(response.get(ACCESS_TOKEN_PROPERTY));
        } catch (Unauthorized e) {
            return false;
        }
    }

    public List<String> getRolesByUserId(String userId) throws KeycloakException {
        log.debug("getRolesByUserId: {}", userId);
        try {
            return getUser(userId).roles().getAll().getRealmMappings()
                    .stream()
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new KeycloakException("Error occurred while getting roles for user " + userId, e);
        }
    }

    public void enableUser(String userId) throws KeycloakException {
        if (log.isDebugEnabled()) {
            log.debug("enableUser {}", userId);
        }

        try {
            UserResource userResource = getUser(userId);
            UserRepresentation user = userResource.toRepresentation();
            user.setEnabled(true);
            userResource.update(user);
        } catch (Exception e) {
            throw new KeycloakException("Error occurred while enabling user " + userId, e);
        }
    }

    public void disableUser(String userId) throws KeycloakException {
        if (log.isDebugEnabled()) {
            log.debug("disableUser {}", userId);
        }

        try {
            UserResource userResource = getUser(userId);
            UserRepresentation user = userResource.toRepresentation();
            user.setEnabled(false);
            userResource.update(user);
        } catch (Exception e) {
            throw new KeycloakException("Error occurred while disabling user " + userId, e);
        }
    }

    public void changePassword(String userId, String password) throws KeycloakException {
        if (log.isDebugEnabled()) {
            log.debug("changePassword {}", userId);
        }

        try {
            CredentialRepresentation cR = preparePasswordRepresentation(password);
            UserResource userResource = getUser(userId);
            userResource.resetPassword(cR);
        } catch (Exception e) {
            throw new KeycloakException("Error occurred while changing password of user " + userId, e);
        }
    }

    public List<String> getAllRoles() throws KeycloakException {
        log.debug("getAllRoles");
        try {
            return realmResource().roles().list().stream().map(RoleRepresentation::getName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new KeycloakException("Error occurred while getting roles", e);
        }
    }

    public List<String> getUserIdsByRole(String role) throws KeycloakException {
        log.debug("getUserIdsByRole: {}", role);
        try {
            return rolesResource()
                    .get(role)
                    .getRoleUserMembers()
                    .stream()
                    .map(UserRepresentation::getId)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new KeycloakException(String.format("Error occurred while getting users by role: %s", role), e);
        }
    }

    private UserResource getUser(String userId) {
        return realmResource().users().get(userId);
    }

    private void assignRoles(String userId, List<String> roles) {
        realmResource()
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(roles.stream().map(
                        roleName -> realmResource().roles().get(roleName).toRepresentation()
                ).collect(Collectors.toList()));
    }

    private RealmResource realmResource() {
        return keycloak.realm(realm);
    }

    private RolesResource rolesResource() {
        return keycloak.realm(realm).roles();
    }

    private String parseUserId(String locationUri) {
        Matcher regexMatcher = USER_ID_PATTERN.matcher(locationUri);
        if (regexMatcher.find()) {
            String userId = StringUtils.trimAllWhitespace(regexMatcher.group(1));
            if (StringUtils.hasText(userId)) {
                return userId;
            }
        }
        throw new IllegalArgumentException("Cannot parse user id from " + locationUri);
    }

    private CredentialRepresentation preparePasswordRepresentation(String password) {
        CredentialRepresentation cR = new CredentialRepresentation();
        cR.setTemporary(false);
        cR.setType(CredentialRepresentation.PASSWORD);
        cR.setValue(password);
        return cR;
    }

    private RestTemplate rest() {
        synchronized (this) {
            if (restTemplate == null) {
                restTemplate = new RestTemplate();
            }
        }
        return restTemplate;
    }

}
