A command-line program for rendering SVG files into PNG format.

Uses the Apache Batik library: http://xmlgraphics.apache.org/batik/

Install by running "make install".

Assumes the Batik jar files are in /usr/local/batik-1.7/lib, and
installs into /usr/local/svgtopng. Both these can be changed by
passing BATIK_LIBDIR and INSTALLDIR variables to make, or by editing
the default values at the beginning of the Makefile.
