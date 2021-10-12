# Commandes utiles

Pour créer un fichier sql et lancer le Docker : 
```bash
touch silkyRoad.sql
docker compose up
```

Pour accéder en ligne de commandes au serveur mysql :
```bash
docker exec -it docker-mysql_db_1 bash
mysql -uroot -proot

```

En cas de problème ou de changement de configuration, il peut s'avérer utile de supprimer le container et d'en recréer un.
```bash
docker container ls -la                 # Permet de trouver l'id du container
docker container rm <id_du_container>   # Suppression du container
docker compose up                       # Le container peut être maintenant recréer
```