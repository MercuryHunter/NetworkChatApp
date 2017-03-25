# Makefile for Chat App

SRCDIR = src
BINDIR = bin

JAVAC = javac
JFLAGS = -g -d $(BINDIR) -cp $(BINDIR)

IP=127.0.0.1
NAME=DefaultName
PORT= 1050

OBJECTS=Connector.class ConnectedClient.class MessageHandler.class Server.class \
		Receiver.class Sender.class Client.class

vpath %.java $(SRCDIR):$(SRCDIR)/client:$(SRCDIR)/server
vpath %.class $(BINDIR):$(BINDIR)/client:$(BINDIR)/server

# define general build rule for java sources
.SUFFIXES:  .java  .class

.java.class:
	@$(JAVAC)  $(JFLAGS)  $<

#default rule - will be invoked by make
all: $(OBJECTS)

Connector.class: Connector.java
	@rm -rf $(BINDIR)/server/Server.class $(BINDIR)/server/Connector.class $(BINDIR)/server/ConnectedClient.class
	@javac $(JFLAGS) $(SRCDIR)/server/Server.java $(SRCDIR)/server/Connector.java $(SRCDIR)/server/ConnectedClient.java

run_server: all
	@java -cp $(BINDIR) server.Server $(PORT) 10 10

run_client: all
	@java -cp $(BINDIR) client.Client $(IP) $(PORT) $(NAME) 
				
clean:
	@rm -f $(BINDIR)/*.class
	@rm -f $(BINDIR)/*/*.class
