#!/bin/bash
cat 'docker/docker-mysql/mockdata/'*'.sql' | mysql -h 127.0.0.1 -P $1 -p $2 -u $3 -p$4 