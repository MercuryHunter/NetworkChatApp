# Makefile for Chat App

SRCDIR = src
BINDIR = bin

JAVAC = javac
JFLAGS = -g -d $(BINDIR) -cp $(BINDIR)

OBJECTS=

vpath %.java $(SRCDIR)
vpath %.class $(BINDIR)

# define general build rule for java sources
.SUFFIXES:  .java  .class

.java.class:
	$(JAVAC)  $(JFLAGS)  $<

#default rule - will be invoked by make
all: $(OBJECTS)

run_server: all
	java -cp $(BINDIR) SERVER ARGS

run_client: all
	java -cp $(BINDIR) CLIENT ARGS
				
clean:
	@rm -f $(BINDIR)/*.class
