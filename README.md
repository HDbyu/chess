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

https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIei0azF5vDgHYsgwr5ks9K+KDvvorxLAC5wFrKaooOUCAHjysL7oeqLorE2IJoYLphm6ZIUgatLlqOJpEuGFocjA3K8gagrCjAQGhqRbqdph5RGtojrOsmkElshPLZrmmD-iC0ElFcAxEaMC6Tn006zs246tn035XiJhTZD2MD9oOvQSSOUmKdWU5BvJ86GW2y6rt4fiBF4KDoHuB6+Mwx7pJkmAaReRTUNe0gAKK7n59R+c0LQPqoT7dHJjboO2v5nECJbRXOQk8fFrEwPB9jOUhTm+qhGIYXKWEEjhLLlBwKDcJkpkBiZMVoCRTLMZGFGRMMEA0DAkBzjAABm3jDEYHGutKSaXjBCpKiqmGdsJ5TIc5AkIHmaUsapVwwCpPlqV2ORgH2A5DltZicFZ66BJCtq7tCMAAOKjqyrmnh557MKJ153UFoX2KOUX1SlP5svNMDJbFwlshlCCqA9oyqLCyCxLDagFehnGJqVTHlTA5JUjSYONYxzVjYUlqUTyto0doQoipJYijea43eU68r0faHGzdhWOkjjFLI-DTVmhGpMUZCYA+LcOOPUTQvrZNUtw+jWHcYlabQ-zy2rarctiaWUy-XD4yVP0BsoAAktIRsAIy9gAzAALE8J6ZAaFYTF8OgIKADau6OKxfKbAByo7u3sMCNNtxxM55B1aUdvT649RsVCbo4W9bduO1Mzv6nT-tPJ73u+1J7tPEHId9Mp4dLmdnjWRu2AS9g3DwLqmT3aOKRuWe+2QxtlS1A0P1-cEAPoEO5ejJHHYq6m5QExPo7B1PqXa6J4IwJ6erI7CW+ZMjqNYkrGplTzuMEQTgtka15RUZT7PyDTbP6fTp8RuvrPsfIx9c8TPPADaHepsQwM2Fuyco0YFaGGSBkVIUD4zFTmmlcoe8UAHzUIJCGTMfybT6KbdO5QbYOxOkDaOb1Do6R6InUYBCYBEPtidSydcLoBEsFVeCyQYAACkIA8g7qMQIhcQANler3D6aZqiUjvC0U2-06wNSHM3YAbCoBwAgPBKAsx8HSGnvFEGC9ehKJUWojRWi046NXqmPu8sABWvC0A7zsTydBaJCo-0xn-d0vM8aUDqvIucV8Woi1vhTO0ApqZ0QYqAnWG8v7AHcTIN+bFsBaH3qOWEwDtCBJJuA9MSj1R+kMKk4AMAYF+hgGgFAnDik-1nsCcoTiHGjk1pYqC4jNq6M7DHChx0a4rmYTZAIXhlFdi9LAYA2Bm6EHiIkLuL0Y7WN1hUfygVgqhWMHFYGyCYCtJnhNFmsFN7cDwLCIqMET7cy8ZVaqKBGSnJltfYJ6YOo0FZMAZUw1v6czqSWEAxyswYJWrs3aOC9adLIftHpCc+mYCAA

hi sorry need to fix