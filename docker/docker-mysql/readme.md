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