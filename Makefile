compose:
	docker-compose -f docker/docker-compose.yml --env-file .env -p spring $(args)

database:
	make compose args="up -d"

database-down:
	make compose args="down"

spring:
	mvn spring-boot:run

prettier:
	mvn prettier:write
