This example demonstrates how to support multiple authentication methods to secure Spring Boot rest endpoints. It uses Spring profiles to switch between Azure Active Directory authentication and basic auth. It's inspired by [this example](https://github.com/microsoft/azure-spring-boot/tree/master/azure-spring-boot-samples/azure-active-directory-spring-boot-backend-sample) that secures Spring Boot REST API with Azure AD.

### To run the basic auth profile:
1. Set the default user in [application-basicauth.properties](src/main/resources/application-basicauth.properties).
2. To run the application:
  *  to run in command line
```bash
cd $project_root
mvn package
cd target
java -jar multiauth-1.0-SNAPSHOT.jar --spring.profiles.active=basicauth
```
  *  To run in IntelliJ, create a maven run configuration as following:
```bash
-DskipTests -Dspring-boot.run.profiles=basicauth spring-boot:run 
```

![Alt text](IntelliJRunConfig.PNG?raw=true "IntelliJ Maven Run Config") 

### To run Azure AD profile:
1. [configure an Azure application](https://docs.microsoft.com/en-us/azure/active-directory/develop/v1-protocols-oauth-code#register-your-application-with-your-ad-tenant). Set the values in [application-aad.properties](src/main/resources/application-aad.properties).
2. Create groups, users, and add users to groups as documented [here](https://docs.microsoft.com/en-us/azure/active-directory/fundamentals/active-directory-groups-create-azure-portal).
3. To run the application:
  *  to run in command line:
```bash
cd $project_root
mvn package
cd target
java -jar multiauth-1.0-SNAPSHOT.jar --spring.profiles.active=aad
```
  *  to run in IntelliJ, create a maven run configuration as following:
```bash
-DskipTests -Dspring-boot.run.profiles=aad spring-boot:run 
```

### To verify authentication and authorization are set up properly
Go to http://localhost:8080, you will see the swagger UI for the HelloController.  If you log in as a user belonging to the ADMIN group in Azure AD, you can run the ```calc``` api that adds two numbers. If you only belong to the VIEWER group in Azure AD, you can run the ```hello``` api, but the ```calc``` api will return 403 forbidden. 

Access http://localhost:8080/whoami to see the user principal information.

### To run tests 
Test runs only with ```basicauth``` profile. The [application.properties](src/test/resources/application.properties) specifies the profile, so you can just run ```mvn test```.

### To logout
Logout controller isn't implemented yet. The default Spring Boot ```/logout``` doesn't work. 
* To log out of Azure AD, hit the URL https://login.microsoftonline.com/common/oauth2/logout.  
* To log out of basic auth, close the browser.

### Troubleshooting
* If you get an error ```AADSTS240002: Input id_token cannot be used as 'urn:ietf:params:oauth:grant-type:jwt-bearer' grant```, make sure to set ```oauth2AllowIdTokenImplicitFlow``` in the registered Azure AD app manifest to true.
* If you get 403 even when signed in as a user belonging to the ADMIN group, make sure csrf is disabled in your ```WebSecurityConfigurerAdapter``` - ```http.csrf().disable()```
* If your application-${profile}.properties file is in a custom location, you can specify in the java command line ```--spring.config.location=/path/to/configdir/```. Or in maven run config ```-Dspring.config.location=file:///C:/path/to/configdir/```.
* If the application can't find the ```application-${profile}.properties```, you may encounter many strange errors.  