//package sk.umb.fpv.dain142demo.config;
//
//import java.util.Collections;
//import java.util.Map;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtDecoders;
//import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//
//public class OidcSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
//
//        http.antMatcher("/**")
//                .authorizeRequests()
//                .antMatchers("/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs")
//                .permitAll()
//                .anyRequest()
//                .authenticated();
//
//        http.headers().frameOptions().sameOrigin();
//    }
//
//    private JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        // Convert realm_access.roles claims to granted authorities, for use in access decisions
//        converter.setJwtGrantedAuthoritiesConverter(new StandardRealmRoleConverter());
//        return converter;
//    }
//
//    /**
//     * Creates Jwt decoder
//     *
//     * @param properties properties with issuer uri
//     * @return
//     */
//    @Bean
//    public JwtDecoder jwtDecoderByIssuerUri(OAuth2ResourceServerProperties properties) {
//        String issuerUri = properties.getJwt().getIssuerUri();
//        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);
//        // Use preferred_username from claims as authentication name, instead of UUID subject
//        jwtDecoder.setClaimSetConverter(new UsernameSubClaimAdapter());
//        return jwtDecoder;
//    }
//
//    class UsernameSubClaimAdapter implements Converter<Map<String, Object>, Map<String, Object>> {
//
//        private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter
//                .withDefaults(Collections.emptyMap());
//
//        @Override
//        public Map<String, Object> convert(Map<String, Object> claims) {
//            Map<String, Object> convertedClaims = this.delegate.convert(claims);
//            String username = (String) convertedClaims.get("preferred_username");
//            convertedClaims.put("sub", username);
//            return convertedClaims;
//        }
//    }
//}
