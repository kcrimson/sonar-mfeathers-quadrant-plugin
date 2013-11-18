sonar-mfeathers-quadrant-plugin
===============================

Sonar plugin which generates quadrant based on code complexity and amount of changes in code, based on idea proposed by Michael Feathers in one of his blog posts

Desired features
----------------

We want to:

* have stats how many files are there at all, how many per quadrant
* be able to move tresholds as is more suitable for our use case
* show trends in time (Sonar time machine?)
* give users other views, like:

  1. detailed view per file
  2. connections between files
  3. tabular view of a quadrant, sorted by file names 

Would have to find out how many files it is sensible to show in a quadrant. That will be revisited once treshold moving logic will be tackled.

Sonar
-----
Sonar integration will be kept separately, most likely as another Maven module. 

Sonar sees files as reasources, same as measures. We will need SCM Sensor that will give us change rate and should make a Batch that would take it and McCabe Sensor and gives us new measure (Quadrant). 

*Let's take a look at Sonar TreeMap plug-in*.
