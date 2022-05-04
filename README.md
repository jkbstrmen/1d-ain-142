# Keycloak as provider of Identity and Access Management

## Setup
Setup consists of three main steps:
1. Run and configure Keycloak service instance
2. Setup and configure Spring application
3. Setup and configure Angular application

### 1. Run and configure Keycloak service instance
1. Go to directory **keycloak** and run command (for more information see [here](#keycloak))  
   ```docker-compose up -d```
2. Visit keycloak UI at [http://localhost:9900](http://localhost:9900)
3. Login to **Administration console** using username: keycloak, password: keycloak
4. Select realm **your-project**
5. Create your first user e.g. **admin** in **Users** view
6. Set user's password in **Credentials** tab

### 2. Setup and configure Spring application
1. Add to pom.xml (for more information see [here](#spring-app))

```xml
                <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.keycloak</groupId>
			<artifactId>keycloak-spring-boot-starter</artifactId>
			<version>15.0.1</version>
		</dependency>
```

2. Add config files to your project:
    1. Add or replace your current CorsConfig.java with config/CorsConfig.java
    2. Add config/KeycloakConfig.java
    3. Add config/SecurityConfig.java

3. Add to your **application.properties**:

```yaml
keycloak.auth-server-url=http://localhost:9900/auth
keycloak.realm=your-project
keycloak.resource=be-app
keycloak.public-client=true
```

5. Run your Spring Boot project. Now any request to your REST endpoints should produce HTTP status code 401 - Unauthorized.
6. Verify *keycloak<->spring* setup using Postman (for more information see [here](#postman))


### 3. Setup and configure Angular application
Official documentation of [angular-oauth2-oidc](https://www.npmjs.com/package/angular-oauth2-oidc).

1. In project root run command: ``npm i angular-oauth2-oidc --save``
2. Add AuthConfig to project:
```ts
import { AuthConfig } from 'angular-oauth2-oidc';

export const authCodeFlowConfig: AuthConfig = {
  issuer: 'http://localhost:9900/auth/realms/your-project',
  redirectUri: 'http://localhost:4200',
  clientId: 'fe-app',
  scope: 'openid profile email',
  requireHttps: false,
  showDebugInformation: true,
};
```
3. Add AuthInterceptor to project:
```ts
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {OAuthService} from "angular-oauth2-oidc";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {

  constructor(private oauthService: OAuthService, private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    request = request.clone({
      setHeaders: {
        Authorization: 'Bearer ' + this.oauthService.getAccessToken()
      }
    });

    return next.handle(request);
  }
}

```
4. Add providers to root AppModule:
```ts
function init_app(oauthService: OAuthService) {
    return () => configureWithNewConfigApi(oauthService);
}

function configureWithNewConfigApi(oauthService: OAuthService) {
    oauthService.configure(authCodeFlowConfig);
    oauthService.tokenValidationHandler = new NullValidationHandler();
    oauthService.setupAutomaticSilentRefresh();
    oauthService.events.subscribe(e => { });
    return oauthService.loadDiscoveryDocumentAndTryLogin();
}

...

@NgModule({
    declarations: [
        ...
    ],
    imports: [
        ...
    ],
    providers: [
        
        ...
            
        {
          provide: APP_INITIALIZER,
          useFactory: init_app,
          deps: [
            OAuthService
          ],
          multi: true
        },
        {
          provide: HTTP_INTERCEPTORS,
          useClass: AuthInterceptor,
          multi: true
        },
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
```
5. Call to login:
```ts
this.oauthService.loadDiscoveryDocumentAndLogin();
```
6. Call to logout:
```ts
this.oauthService.logOut();
```


## Additional information

### Keycloak
[Keycloak](https://www.keycloak.org/) is open source product for Identity and Access Management. By running given docker compose file docker will pull and run two docker images. One for database where keycloak's data will be persisted and second for keycloak itself. This docker compose file is configured using **.env** file located in same directory as docker-compose.yml and keycloak at startup imports realm from **realm-export.json** file also located at the same directory. Imported realm name is **your-project** and contains basic configuration of required Clients and Roles.

### Spring app
... TODO ...

```xml
                <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<dependency>
			<groupId>org.keycloak</groupId>
			<artifactId>keycloak-spring-boot-starter</artifactId>
			<version>15.0.1</version>
		</dependency>
```

For more information try [another tutorial](https://www.baeldung.com/spring-boot-keycloak).

To be able to manage users through your application - keycloak admin client setup is needed. That is another chapter.

```xml
        <dependency>
			<groupId>org.keycloak</groupId>
			<artifactId>keycloak-admin-client</artifactId>
			<version>15.0.1</version>
		</dependency>
```

### Postman
1. Set Auth to OAuth 2.0.
2. Configure **Auth URL** to *http://localhost:9900/auth/realms/your-project/protocol/openid-connect/auth*
3. Configure **Access Token URL** to *http://localhost:9900/auth/realms/your-project/protocol/openid-connect/token*
4. Configure **Client ID** to *be-app*
5. Try to **Get New Access Token**

Or try [this](https://paulbares.medium.com/quick-tip-oauth2-with-keycloak-and-postman-cc7211b693a5).

### Angular app
... TODO ...


## Keycloak Admin Client

Add to pom.xml

```xml
        <dependency>
			<groupId>org.keycloak</groupId>
			<artifactId>keycloak-admin-client</artifactId>
			<version>15.0.1</version>
		</dependency>
```


