# scala-view examples - ScalaFX examples

## A collection of examples illustrating use of the scala-view library - using the new, recommended ScalaFX API

This directory contains a collection of examples illustrating the use of the [scala-view](https://github.com/darrenjw/scala-view) library. You can run these examples by running `sbt` from this directory and typing `run` at the `sbt` prompt. Then select the example you want to run.

Current examples:

* [Heat equation](https://en.wikipedia.org/wiki/Heat_equation): Numerical solution of the heat equation, or equivalently, Gaussian blurring of a noisy image
* [Ising model](https://en.wikipedia.org/wiki/Ising_model): Gibbs sampling MCMC simulation
* [GMRF](https://en.wikipedia.org/wiki/Markov_random_field): Gibbs sampling MCMC simulation for a Gaussian Markov random field
* STAR(1): Simulation of a space-time auto-regressive model of order 1, with tobit-style thresholding. eg. for a cloud/rainfall prior

All of the algorithms run in parallel on all available cores.

The heat equation example is also the subject of the [interactive tutorial](../docs/Tutorial.md).

These are examples for the new ScalaFX-based API. Examples for the legacy Swing API are in a separate [examples directory](../examples-swing/).

