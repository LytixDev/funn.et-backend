run:
	make mvn args="spring-boot:run -Dspring-boot.run.fork=false $(args)"

compose:
	docker-compose -f docker/docker-compose.yml --env-file .env -p spring $(args)

mvn:
	env $$(cat .env|xargs) mvn $(args)

database:
	make compose args="up -d"

database-down:
	make compose args="down"

test:
	make mvn args="test $(args)"

