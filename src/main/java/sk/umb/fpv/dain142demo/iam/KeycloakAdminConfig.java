package sk.umb.fpv.dain142demo.iam;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KeycloakSettings.class)
public class KeycloakAdminConfig {

    @Bean
    public Keycloak keycloak(final KeycloakSettings settings) {
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .serverUrl(settings.getAuthServerUrl())
                .realm(settings.getRealm())
                .clientId(settings.getResource())
                .clientSecret(settings.getCredentialsSecret())
                .build();
    }

}
