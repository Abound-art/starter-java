# ABOUND Starter Repo - Java

This is a starter repository for an ABOUND algorithm written in Java, with a basic Gradle setup.

* Unsure on what ABOUND is? Check out https://abound.art
* Looking for another language? Check out our other options for starter repos [here](https://abound.art/artists)
* New to Gradle? Get started [here](https://docs.gradle.org/current/userguide/what_is_gradle.html)

## What is in this repo

This repo includes all of the scaffolding to build an algorithm for ABOUND. That means:

1. Reading in the JSON configuration for a run
2. Generating art (using a [Lorenz attractor](https://en.wikipedia.org/wiki/Lorenz_system) as an example)
3. Writing the output to a file

In short, this repo does everything except implement your art algorithm, which
will generally look like this:

```java
// Config is all the parameters your algorithm takes as input.
public class MyAlgorithm {
    // Your arguments here - they'll be deserialized from JSON.
    String myArg;
    Int anotherArg;
    
    public RenderedImage run() {
        // Your code here which generates the image from the config.
    }
}

```

## Run locally + Testing your code

```bash
export ABOUND_CONFIG_PATH=example_input.json
export ABOUND_OUTPUT_PATH=output.png
./gradlew run
```

Will generate a piece of art at `output.png` that looks like this:

![An example output of the Lorenz attractor algorithm, a blue and green spiral](/example_output.png)

It's also worth noting
that the example algorithm produces raster images, meaning they're made of
pixels and are output as [PNG](https://en.wikipedia.org/wiki/PNG) files, but
you can also write algorithms that produce vector images, meaning they're made
of geometric shapes and are output as
[SVG](https://en.wikipedia.org/wiki/SVG) files.

### As a Docker container

TODO - Write this after packaging

## Packaging for Deployment

TODO - Write this after packaging

## Deploying on ABOUND 

Head to https://abound.art/artists for the most recent instructions on how to upload
your algorithm once it is written. Make sure to read through the constraints carefully
to make sure that your algorithm conforms to them prior to submission.

Once you're ready to upload, tag and push the image with:

```bash
docker tag <local tag> <image name given by ABOUND>
docker push <image name given by ABOUND>
```

If you haven't changed the example configuration in this repo, the `<local
tag>` defaults to `abound-starter-java`.

The `docker push` command will fail if you aren't already authenticated with
your ABOUND credentials.