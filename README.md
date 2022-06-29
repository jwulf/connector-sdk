# Camunda Connectors Framework

Libraries that support the Camunda connectors framework, including run-times and the [connector SDK](./connector-sdk).

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