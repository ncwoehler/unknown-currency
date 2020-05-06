run:
	./gradlew bootJar
	docker build . -t unknown-currency
	docker run unknown-currency

standalone:
	./gradlew bootJar
	docker build . -t unknown-currency
	docker run --env STANDALONE=STANDALONE unknown-currency