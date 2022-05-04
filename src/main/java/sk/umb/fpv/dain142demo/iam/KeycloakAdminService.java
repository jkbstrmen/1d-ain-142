package sk.umb.fpv.dain142demo.iam;

import java.util.List;

public interface KeycloakAdminService {

    String createUser(UserCreate request) throws KeycloakException;

    void enableUser(String userId) throws KeycloakException;

    void disableUser(String userId) throws KeycloakException;

    void changePassword(String userId, String password) throws KeycloakException;

    void editRoles(String userId, List<String> roles) throws KeycloakException;

    List<String> getRolesByUserId(String userId) throws KeycloakException;

    List<String> getAllRoles() throws KeycloakException;

    List<String> getUserIdsByRole(String role) throws KeycloakException;

    void deleteUser(String userId) throws KeycloakException;

    boolean matchesPassword(String userName, String password);
}
