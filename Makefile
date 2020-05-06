MONETA_VERSION?=1.3

run:
	./gradlew bootJar -DmonetaVersion=${MONETA_VERSION}
	docker build . -t unknown-currency
	docker run unknown-currency

standalone:
	./gradlew bootJar -DmonetaVersion=${MONETA_VERSION}
	docker build . -t unknown-currency
	docker run --env STANDALONE=STANDALONE unknown-currency