run:
	# start database if it is down and give it some time to spin up
	(docker ps | grep -q "spring-db-1") || (make database && sleep 15)
	make mvn args="spring-boot:run -Dspring-boot.run.fork=false $(args)"

compose:
	docker-compose -f docker/docker-compose.yml --env-file .env -p spring $(args)

mvn:
	mvn $(args)

database:
	make compose args="up -d"

database-down:
	make compose args="down"

test:
	make mvn args="test $(args)"

prettier:
	make mvn args="prettier:write"

install:
	make mvn args="clean install"

compile:
	make mvn args="clean compile"
