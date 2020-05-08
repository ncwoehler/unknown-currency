MONETA_VERSION?=1.4

boot-java11:
	./gradlew clean bootJar -DmonetaVersion=${MONETA_VERSION} -DjavaVersion=VERSION_11
	cp build/libs/unknown-currency.jar docker/
	docker build docker/ -f docker/Dockerfile.jre11-boot -t unknown-currency-bootjar-jre11
	docker run unknown-currency-bootjar-jre11

boot-java8:
	./gradlew clean bootJar -DmonetaVersion=${MONETA_VERSION} -DjavaVersion=VERSION_1_8
	cp build/libs/unknown-currency.jar docker/
	docker build docker/ -f docker/Dockerfile.jre8-boot -t unknown-currency-bootjar-jre8
	docker run unknown-currency-bootjar-jre8

shadow-java11:
	./gradlew clean shadowJar -DmonetaVersion=${MONETA_VERSION} -DjavaVersion=VERSION_11
	cp build/libs/unknown-currency-all.jar docker/
	docker build docker/ -f docker/Dockerfile.jre11-shadow -t unknown-currency-shadowjar-jre11
	docker run unknown-currency-shadowjar-jre11

shadow-java8:
	./gradlew clean shadowJar -DmonetaVersion=${MONETA_VERSION} -DjavaVersion=VERSION_1_8
	cp build/libs/unknown-currency-all.jar docker/
	docker build docker/ -f docker/Dockerfile.jre8-shadow -t unknown-currency-shadowjar-jre8
	docker run unknown-currency-shadowjar-jre8