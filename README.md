# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

## Phase 2 diagram url

https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYsgwr5ks9K+KDvvorxLAC5wFrKaooOUCAHjysL7oeqLorE2IJoYLphm6ZIUgatLlqOJpEuGFocjA3K8gagrCjAQGhqRbqdph5RGtojrOgSOEsh6uqZLG-rTiGrrSpGHLRjAgnxnKWHJpBJbITy2a5pg-4gtBJRXAMRGjAuk59NOs7NuOrZ9N+V4aYU2Q9jA-aDr0OkjnppnVlOQbGfOrltsuq7eH4gReCg6B7gevjMMe6SZJgNkXkU1DXtIACiu5JfUSXNC0D6qE+3RGY26Dtr+ZxAiW+VzmpCnFaxMDwfY4VIWFvqoRiGGyRqPGkjA5JgIJAYeQVaAkUyzHieUVExkGtFhOVhWieaSaXjB5TCRxmEdUxvFGCg3CZJ5-V1oNw1mhGhSWumwwQDQMCQHOMAAGbeMMRgcfNEaaeCMCKsqnGJvJpVpsh4UqQgeZVSxllXDAFkJVZXY5GAfYDkO0NmJwfnroEkK2ru0IwAA4qOrKRaeMXnswmnXvjaWZfYo55QNFU-my6krYzhXqWyNUIKohOjKosLILEfNqC16G-Vh3GbV1PV9bNQ2MSNYlnRRE1SVN2hCjN7NoIrJ0Q8tMCrfIEsbUrMsUiLAvHWRY0wJCYA+Lc3VE3rtsffKLv86bi0lamcG80TINgwDBtaaWUx0-z4yVP0UcoAAktIMcAIy9gAzAALE8J6ZAaFYTF8OgIKADb56OKxfPHAByo6F3sMCNDDxy+7FiN2cjvSR0TMcVHHo5J6nGfZ1Muf6rp9x9EXJcgGXE+V08Nd11PDdN2jK6eP5G7YE72DcPA-GGCLKRRWeCNc5DlS1A0tP08EOtDkvozNx2-3+0bD+9E-KAQaHHuwTAT0eoRawiAZkEWYssQ+ylubd03UKRyx1jbUaKtxo8kmnGTWdF5Zu1Gv-NmmCTbrWwtLOBwAbQgPjiJTqp12TlEkvHGAyQMipC9igGSMFOys0AYfCBahVKc19j+KGfR46D3KGnLOqNmat3JkjByPRu6jHETASRmdUa+U3pjAIlgdrwWSDAAAUhAHkBNRyBGLqXMm59KZpmqJSO8LR44M0OnOIce9gC6KgHACA8EoCzDEdIF+xVuHy3cSXLxPi-EBIHkEyqf8lpOk9gAKxMWgEBqSeR8LRK1aBMgaF4V6kGA6M4jq4OVnQyi6D1aEOAFrD+ri5o0LDp9Y2wA8lvUKZQ2JyCKnnTViLaabCk7lIWvgthHCkmS3yaQti2AtDgNHLCKh2hekLVQemDx6o-SGAWXU5hfovooAMXsn2b9gTlEyek0cwd4mpjDgBaRllOxt3kSjJc6MtEBQCF4TxXYvSwGANgPehB4iJBPqTNuF9YblAqMlVK6VMrGCKizKq5Q7lQUSZ9EA3A8CwjapwkhsDyg4oBdJbQ+LRm0POpES6NBWTAGVA04Ma12pEpOiS3FUAaIUrWdSiitKrpqCNky9iRC2UwI5YArlgzeVUvIuUQV9KRUIAmaywl5ySykrwLcwRtiobBJeXIjuCiNHoyAA