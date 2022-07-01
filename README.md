> [This is a prototype](https://github.com/camunda/cloud-connectors/issues/36#issuecomment-1170444587) and subject to breaking changes. Use at your own risk!

# Camunda Connector Framework

[![CI](https://github.com/camunda/connector-framework/actions/workflows/CI.yml/badge.svg)](https://github.com/camunda/connector-framework/actions/workflows/CI.yml)

[Connector SDK](#create-a-connector) and [run-times](#connector-run-times).


## Creating a Connector

Include the [connector SDK](./connector-sdk) via maven: 

```xml
<dependency>
  <groupId>io.camunda.connectors</groupId>
  <artifactId>connector-sdk</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Define your connector logic through the [`ConnectorFunction`](https://github.com/camunda/connectors-framework/blob/main/connector-sdk/src/main/java/io/camunda/connector/sdk/ConnectorFunction.java) interface:

```java
public class PingConnector implements ConnectorFunction {

  @Override
  public Object execute(ConnectorContext context) {

    var request = context.getVariablesAsType(PingRequest.class);

    var validator = new Validator();
    request.validate(validator);
    validator.validate();

    request.replaceSecrets(context.getSecretStore());

    try {
      var name = request.getCaller();

      return new PingResponse("Pong to " + caller);
    } catch (Exception e) {
      throw new ConnectorFailedException(e);
    }
  }
}
```

Expose your connector as a [`ConnectorFunction` SPI implementation](https://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html).

## Connector Run-Times

Run your connector as a [job worker](https://github.com/camunda/connectors-framework/tree/main/connector-runtime-job-worker#readme) or build your own run-time based on your environment.


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
