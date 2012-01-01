BATIK_LIBDIR = /usr/local/batik-1.7/lib
INSTALLDIR = /usr/local/svgtopng


JAVACFLAGS = -Xlint
CLASSPATH = ${BATIK_JARS}:.

BATIK_JARS = $(shell ./jars.sh $(BATIK_LIBDIR))

export BATIK_JARS CLASSPATH INSTALLDIR

%.class: %.java
	javac $(JAVACFLAGS) $<

all: SVGToPNG.class svgtopng

clean:
	rm -f *.class svgtopng

svgtopng: generate-script.sh Makefile
	@./generate-script.sh $@

install: all
	mkdir -p $(INSTALLDIR)
	cp SVGToPNG.class svgtopng $(INSTALLDIR)
