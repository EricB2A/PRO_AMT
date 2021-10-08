# Commandes

Pour créer un fichier sql et lancer le docker : 
```bash
touch shit.sql
docker compose up
```

Pour accéder à la main au serveur mysql
```bash
docker exec -it docker-mysql_db_1 bash
mysql -uroot -proot

```