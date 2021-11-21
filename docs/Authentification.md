# Authentification
Afin de profiter d'une base solide pour notre application, nous avons décider d'intégrer le service d'authentification fourni avec Spring Security.
![UML login](https://github.com/EricB2A/PRO_AMT/blob/d2d98cb79094e5b049cb52143d4af0fe67d8c71c/docs/uml/model_images/login.png)

## Login / connexion
Spring Security laisse la possiblité de déléguer l'authentification à un `AuthentificationProvider` qui se chargera d'authentifier l'utilisateur selon nos besoins.

Dans notre cas, notre `AuthenticationProvider` (la classe `AuthProvider`), appelle la méthode login du `LoginService`.

`LoginService` se charge d'interroger le service d'authentification récupérant ainsi le token JWT qui sera par la suite retourner (via `LoginController`) à l'utilisateur comme cookie (httpOnly).

## Registre / inscription
Pour l'inscription, nous utilisons la méthode `register` de notre `LoginService` qui, comme pour `login` demandera l'inscription au service d'authentication fourni, il enregistra ensuite une copie de l'utilisateur dans notre database locale et retournera le résultat à `LoginController`.  Nous avons décidé d'avoir une copie dans notre db afin d'avoir la flexibilité d'avoir plus d'information sur notre utilisateur (e-mail, nom, prénom etc.)


## Authentification d'une requête
Afin de pouvoir, vérifier le JWT fourni par le client, notre application utilise un filtre Spring `JwtRequestFilter`. Ce dernier se chargera de vérifier l'authenticité du token et le cas échant logguer l'utilisateur dans l'application.

## Filtre des routes
Spring Security permet de configurer les droits des routes. Les règles sont définis dans `src/main/java/com/example/amt_demo/config/SpringSecurityConfig.java`, Pour le moment, les règles sont les suivantes:

- Les routes matchant `/admin/**` sont accessibles avec le role `admin`
- `/signout` nécessite d'être authentifié sans role spécifique
- Les routes `/login` & `/signup` sont accessibles lorsqu'on n'est pas connecté.
- Les routes matchant `/`, `/carpets`, `/accueil`, `/images/**`, `/css/**`, `/js/**`, `/catalog/**`, `/cart/**`,`/carpet-photos/**` sont accessibles par tout le monde.
- Toutes les autres requêtes sont refusées sans autres conditions


