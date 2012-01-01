A command-line program for rendering SVG files into PNG format.

Uses the Apache Batik library: http://xmlgraphics.apache.org/batik/

Install by running "make install".

Assumes the Batik jar files are in /usr/local/batik-1.7/lib, and
installs into /usr/local/svgtopng. Also creates a symbolic link to
the svgtopng script from /usr/local/bin. All these can be changed by
passing variables BATIK_LIBDIR, INSTALLDIR and INSTALLBINSYMLINK
to make, or by editing the default values at the beginning of
the Makefile.
