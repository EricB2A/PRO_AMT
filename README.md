# Silky Road 🐪
## Silky Road 
Dans le cadre de notre cours d'AMT, nous réalisons un **site d'e-commerce** de vente de tapis (😉).
Avant de contribuer, nous vous recommandons de commencer par lire le [**Wiki (si vous êtes assistant, cliquez ici)**](../../wiki) qui vous donnera une bonne vision du produit, de comment contribuer ainsi que des détails technologiques et techniques utiles à une bonne collaboration 🔥

## Structure
- Le dossier `src` contient le code source du projet.
- Le dossier `docs` contient tous les éléments utiles à la documentation (fichiers sources, images).
- Le dossier `docker` contient les images Docker. Chaque image différente est dans un sous-dossier (exemple: une image Docker `couchebase` serait dans `docker/docker-couchebase`).

## Commencer 🏁
Cette section a pour but de mettre en place un **environnement de développement** similaire à celui de l'équipe de développement.
Pour en savoir plus sur la stack technologique, référez-vous à la section [choix technologique](../../wiki/Choix-technologiques) du Wiki !
### Prérequis
- [Java](https://www.java.com/fr/download/) : *1.8*
- [Maven](https://maven.apache.org/download.cgi) :  *3.8.**
- [Docker engine](https://docs.docker.com/engine/install/) : *20.10.**
- [Docker compose](https://docs.docker.com/compose/install/) 
  - *NOTE: docker compose est inclus dans les installation de l'engine sur Windows et MacOS*.
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

### Installation 
1. Cloner le repository.
```bash 
git clone git@github.com:EricB2A/PRO_AMT.git
```
2. Ouvrir le projet avec IntelliJ
3. Lancer le conteneur Docker
```bash
cd docker/docker-mysql && touch silkyRoad.sql && docker compose up
```
4. Lancer Spring boot et le Tomcat Server *(^R)*.
5. Allez à l'adresse [localhost:8080](http://localhost:8080/) et vous voilà prêt à contribuer :cool:. 

### Variables d'enrivonnement
Afin de pouvoir utiliser notre application en local, il faut ajouter plusieurs variables d'environnement :

<ul>
<li>AUTH_SERVICE_SECRET</li> 
<li>AUTH_SERVICE_IP</li>
</ul>

Pour ajouter des variables d'environnement, aller sous Run->Edit Configuration

Dans la configuration Spring Boot correspondant à l'application, sous Environment -> environment variables, cliquez sur l'icône de bloc note
et ajoutez les deux variables ci-dessus avec leur valeurs respectives

## SGBD 🐋
Notre application utilise le SGBD relationnel `mySQL`, que nous avons Dockerisé via l'image [mysql-server](https://hub.docker.com/r/mysql/mysql-server/).
Par défaut, le conteneur Docker sera accessible depuis l'hôte sur le ``port 3307`` avec le mot de passe ``root``. Les données sont stockées dans le fichier `silkyRoad.sql`.  
Vous pouvez bien évidemment configurer ces paramètres en modifiant le [docker compose](docker/docker-mysql/docker-compose.yml).

Pour accéder à l'instance mySQL *(en ayant un container up & running)* : 
```bash
docker exec -it docker-mysql_db_1 bash
mysql -uroot -proot
```

## Modèle de domaine 💡
![model](docs/uml/model_images/model.png)

L'ORM [Hibernate](https://hibernate.org/) va faire un mapping 1-1 entre ce(s) classe(s) et ses(leurs) attributs et une table éponyme et ses champs associés en base de données.
Veuillez noter que les méthodes de types `setter` et `getter` ne sont pas présents sur ce schéma afin de simplifier la lisibilité de ce dernier.

## Authentification
Documentation du login se trouve [ici](docs/Authentification.md) 
## Routes 👀
| Verbe HTTP | Route                         | Contrôleur            |
|------------|-------------------------------|-----------------------|
| GET        | `/accueil`                    | `HomeController`      |
| GET        | `/admin/articles`             | `ArticleController`   |
| GET        | `/admin/articles/add`         | `ArticleController`   |
| POST       | `/admin/articles/add/post`    | `ArticleController`   |
| POST       | `/admin/articles/edit/post`   | `ArticleController`   |
| GET        | `/admin/articles/edit/{id}`   | `ArticleController`   |
| GET        | `/admin/articles/delete/{id}` | `ArticleController`   |
| GET        | `/login`                      | `LoginController`     |
| POST       | `/login`                      | `LoginController`     |
| POST       | `/signup`                     | `LoginController`     |
| GET        | `/signup`                     | `LoginController`     |
| POST       | `/logout`                     | `LoginController`     |
| GET        | `/cart`                       | `CartController`      |
| PUT        | `/cart/{id}`                  | `CartController`      |
| POST       | `/cart/{id}`                  | `CartController`      |
| DELETE     | `/cart/{id}`                  | `CartController`      |



## L'équipe 🦍
| Développeur       | Github                                                  |
|-------------------|---------------------------------------------------------|
| Eric Bousbaa      | [EricB2A](https://github.com/EricB2A)                   |
| Ilias Goujgali    | [Double-i](https://github.com/Double-i)                 |
| Noah Fusi         | [noahfusi](https://github.com/noahfusi)                 |
| Dalia Maillefer   | [AliceThunderWind](https://github.com/AliceThunderWind) |
| Stéfan Teofanovic | [OvichHeigVD](https://github.com/OvichHeigVD)           |

### Discord
Vous êtes les bienvenus à nous rejoindre sur [Discord](https://discord.gg/bwNER8rU) où nous organisons des sessions de travail hebdomadaires.

Bonne contribution 😎
