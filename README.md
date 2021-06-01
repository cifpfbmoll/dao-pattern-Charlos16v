# Active-Record-Pattern

## Apuntes:
### Contenedor Docker a partir de Dockerfile.postgresql

- docker build -f Dockerfile.postgresql --no-cache -t my-postgresql-container .
- docker run -d --name fruits-db -p 5555:5432 my-postgresql-container  