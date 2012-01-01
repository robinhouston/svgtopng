BATIK_LIBDIR = /usr/local/batik-1.7/lib
INSTALLDIR = /usr/local/svgtopng
INSTALLBINSYMLINK = /usr/local/bin

JAVACFLAGS = -Xlint

BATIK_JARS = $(shell ./jars.sh $(BATIK_LIBDIR))
CLASSPATH := $(BATIK_JARS):.

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
	ln -sf $(INSTALLDIR)/svgtopng $(INSTALLBINSYMLINK)
