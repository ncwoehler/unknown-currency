MONETA_VERSION?=1.4

run:
	./gradlew clean bootJar -DmonetaVersion=${MONETA_VERSION}
	docker build . -t unknown-currency
	docker run unknown-currency