package sk.umb.fpv.dain142demo.iam;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="keycloak-admin")
@Data
public class KeycloakSettings {

    private String realm;

    private String resource;

    private String authServerUrl;

    private String credentialsSecret;
}