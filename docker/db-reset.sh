#!/bin/bash

COMPOSE_FILE="../docker/docker-compose.yml"

export $(grep -v '^#' $ENV_FILE | xargs)

docker compose -f $COMPOSE_FILE down -v
docker compose -f $COMPOSE_FILE up -d

read -p "Press Enter to finish the script and exit..."

echo "Database has been reset!"