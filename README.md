> [This is a prototype](https://github.com/camunda/cloud-connectors/issues/36#issuecomment-1170444587) and subject to breaking changes. Use at your own risk!

# Camunda Connector SDK

[![CI](https://github.com/camunda/connector-sdk/actions/workflows/CI.yml/badge.svg)](https://github.com/camunda/connector-sdk/actions/workflows/CI.yml)

[Connector Core](#create-a-connector) and [run-times](#start-a-connector).


## Create a Connector

Include the [connector core](./core) via maven:

```xml
<dependency>
  <groupId>io.camunda.connector</groupId>
  <artifactId>connector-core</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

Define your connector logic through the [`ConnectorFunction`](./core/src/main/java/io/camunda/connector/api/ConnectorFunction.java) interface:

```java
public class PingConnector implements ConnectorFunction {

  @Override
  public Object execute(ConnectorContext context) throws Exception {

    var request = context.getVariablesAsType(PingRequest.class);

    var validator = new Validator();
    request.validateWith(validator);
    validator.evaluate();

    request.replaceSecrets(context.getSecretStore());

    var caller = request.getCaller();

    return new PingResponse("Pong to " + caller);
  }
}
```

Expose your connector as a [`ConnectorFunction` SPI implementation](https://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html).


## Start a Connector

Spin up your connector as a [job worker](./runtime-job-worker#readme) or build your own run-time, tailored towards your environment.


## Build

```bash
mvn clean package
```

## Build a release

Checkout the repo and branch to build the release from. Run the release script with the version to release and the next
development version. The release script requires git and maven to be setup correctly, and that the user has push rights
to the repository.

```bash
./release.sh 0.3.0 0.4.0
```
