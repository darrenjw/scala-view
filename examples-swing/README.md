# scala-view examples - Swing examples

## A collection of examples illustrating use of the scala-view library - legacy Swing examples

This directory contains a collection of examples illustrating the use of the [scala-view](https://github.com/darrenjw/scala-view) library. You can run these examples by running `sbt` from this directory and typing `run` at the `sbt` prompt. Then select the example you want to run.

Current examples:

* [Langton's Ant](https://en.wikipedia.org/wiki/Langton%27s_ant)
* [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)
* [Sierpinski carpet](https://en.wikipedia.org/wiki/Sierpinski_carpet) - animated construction
* [Julia set](https://en.wikipedia.org/wiki/Julia_set) animation (parallelised)
* [Ising model](https://en.wikipedia.org/wiki/Ising_model) Gibbs sampling MCMC simulation (parallelised)
* A finite-difference solution of an [SPDE](https://en.wikipedia.org/wiki/Stochastic_partial_differential_equation) representing a 2d [reaction-diffusion system](https://en.wikipedia.org/wiki/Reaction%E2%80%93diffusion_system) for the [Lotka-Volterra](https://en.wikipedia.org/wiki/Lotka%E2%80%93Volterra_equations) predator-prey model, derived as a continuum limit of a spatial CLE approximation to a RD-SSA model

Note that you should click on the "Stop" button before clicking on the "close window" button if you want the application to quit, even for finite streams.

These are examples for the legacy Swing-based API. Examples for the ScalaFX API are in a separate [examples directory](../examples-sfx/).

