//package sk.umb.fpv.dain142demo.config;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//
//// TODO - je potrebne?
//public class StandardRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
//
//    private static final String REALM_ACCESS = "realm_access";
//
//    private static final String ROLES = "roles";
//
//    private static final String PREFIX_ROLE = "ROLE_";
//
//    @Override
//    public Collection<GrantedAuthority> convert(Jwt jwt) {
//        final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get(REALM_ACCESS);
//        return ((List<String>) realmAccess.get(ROLES)).stream()
//                .map(roleName -> PREFIX_ROLE + roleName)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
//
//}