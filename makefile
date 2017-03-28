# Makefile for Chat App

SRCDIR = src
BINDIR = bin

JAVAC = javac
JFLAGS = -g -d $(BINDIR) -cp $(BINDIR)

IP = 127.0.0.1
NAME = DefaultName
PORT = 1050
MAXCLIENTS = 10
MAXROOMS = 10

OBJECTS=ConnectedClient.class Connector.class \
		RoomHandler.class Room.class Server.class \
		Receiver.class Sender.class Client.class

vpath %.java $(SRCDIR):$(SRCDIR)/client:$(SRCDIR)/server
vpath %.class $(BINDIR):$(BINDIR)/client:$(BINDIR)/server

# define general build rule for java sources
.SUFFIXES: .java .class

.java.class:
	@$(JAVAC) $(JFLAGS) $<

#default rule - will be invoked by make
all: $(OBJECTS)

# Massive dependency cluster
ConnectedClient.class: ConnectedClient.java
	@rm -rf $(BINDIR)/server/Server.class $(BINDIR)/server/Connector.class $(BINDIR)/server/ConnectedClient.class $(BINDIR)/server/RoomHandler.class $(BINDIR)/server/Room.class
	@javac $(JFLAGS) $(SRCDIR)/server/Server.java $(SRCDIR)/server/Connector.java $(SRCDIR)/server/ConnectedClient.java $(SRCDIR)/server/RoomHandler.java $(SRCDIR)/server/Room.java

RoomHandler.class: RoomHandler.java
	@rm -rf $(BINDIR)/server/RoomHandler.class $(BINDIR)/server/Room.class
	@javac $(JFLAGS) $(SRCDIR)/server/RoomHandler.java $(SRCDIR)/server/Room.java

run_server: all
	@echo "Variables: PORT MAXCLIENTS MAXROOMS"
	@java -cp $(BINDIR) server.Server $(PORT) $(MAXCLIENTS) $(MAXROOMS)

run_client: all
	@echo "Variables: IP PORT NAME"
	@java -cp $(BINDIR) client.Client $(IP) $(PORT) $(NAME) 
				
clean:
	@rm -f $(BINDIR)/*.class
	@rm -f $(BINDIR)/*/*.class
