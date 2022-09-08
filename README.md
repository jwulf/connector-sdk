> This is a preview and subject to breaking changes. Use at your own risk!

# Camunda Connector SDK

[![CI](https://github.com/camunda/connector-sdk/actions/workflows/CI.yml/badge.svg)](https://github.com/camunda/connector-sdk/actions/workflows/CI.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.camunda.connector/connector-core/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/io.camunda.connector/connector-core)


[Connector Core](#create-a-connector) and [run-times](#start-a-connector).


## Create a Connector

Head over to our [Connector Template](https://github.com/camunda/connector-template) for a head start. It provides the following setup.

Include the [connector core](./core) via maven:

```xml
<dependency>
  <groupId>io.camunda.connector</groupId>
  <artifactId>connector-core</artifactId>
  <version>0.1.0</version>
</dependency>
```

### Create an Outbound Connector

Define your connector logic through the [`ConnectorFunction`](./core/src/main/java/io/camunda/connector/api/ConnectorFunction.java) interface:

```java
public class PingConnector implements ConnectorFunction {

  @Override
  public Object execute(ConnectorContext context) throws Exception {

    var request = context.getVariablesAsType(PingRequest.class);

    context.replaceSecrets(request);

    var caller = request.getCaller();

    return new PingResponse("Pong to " + caller);
  }
}
```

Expose your connector as a [`ConnectorFunction` SPI implementation](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/ServiceLoader.html).

## Start a Connector

Spin up your connector as a [job worker](./runtime-job-worker#readme) or build your own run-time, tailored towards your environment.


## Build

```bash
mvn clean package
```

## Build a release

Trigger the [release action](https://github.com/camunda/connector-sdk/actions/workflows/RELEASE.yml) manually with the version `x.y.z` you want to release.
This can be done on the `main` branch as well as `stable/.x.y` maintenance branches. You can choose the branch to execute the action on as described in the
[GitHub documentation](https://docs.github.com/en/actions/managing-workflow-runs/manually-running-a-workflow).

When triggered from the `main` branch, a maintenance branch `stable/x.y` will be created based on the release version `x.y.z` that you specified.

**Note**: This currently also applies for further classifiers like `x.y.z-rc1` or `x.y.z-alpha1`, i.e. a new branch `stable/x.y` will be created for that release. If the branch to create already exists, the release will fail.
