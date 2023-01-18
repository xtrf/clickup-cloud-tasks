## Micronaut 3.8.1 Documentation

- [User Guide](https://docs.micronaut.io/3.8.1/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.8.1/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.8.1/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

# Micronaut and Google Cloud Function

## Running the function locally

```cmd
./mvnw function:run
```

And visit http://localhost:8080/clickupCloudFunctions
## Deploying the function

First build the function with:

```bash
$ ./mvnw clean package
```

Then `cd` into the `target` directory (deployment has to be done from the location where the JAR lives):

```bash
$ cd target
```

Now run:

```bash
$ gcloud functions deploy clickup-cloud-functions --entry-point io.micronaut.gcp.function.http.HttpFunction --runtime java11 --trigger-http
```

Choose unauthenticated access if you don't need auth.

To obtain the trigger URL do the following:

```bash
$ YOUR_HTTP_TRIGGER_URL=$(gcloud functions describe clickup-cloud-functions --format='value(httpsTrigger.url)')
```

You can then use this variable to test the function invocation:

```bash
$ curl -i $YOUR_HTTP_TRIGGER_URL/clickupCloudFunctions
```

## Feature google-cloud-function documentation

- [Micronaut Google Cloud Function documentation](https://micronaut-projects.github.io/micronaut-gcp/latest/guide/index.html#simpleFunctions)


## Feature google-cloud-function-http documentation

- [Micronaut Google Cloud Function documentation](https://micronaut-projects.github.io/micronaut-gcp/latest/guide/index.html#httpFunctions)


