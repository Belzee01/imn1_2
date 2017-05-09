PACKAGES = fileproc poisson

JAVAC = javac
JVM = 1.8

BIN = ./bin/

SRC = ./src/

JAVAFLAGS = -g -d $(BIN) -cp $(SRC) -target $(JVM)


COMPILE = $(JAVAC) $(JAVAFLAGS)

EMPTY = 

JAVA_FILES = $(subst $(SRC), $(EMPTY), $(wildcard $(SRC)*.java))

ifdef PACKAGES
PACKAGEDIRS = $(addprefix $(SRC), S(PACKAGES))
PACKAGEFILES = $(subst $(SRC), $(EMPTY), $(foreach DIR, $(PACKAGEDIRS), $(wildcard $(DIR)/*.java)))
ALL_FILES = $(PACKAGEFILES) $(JAVA_FILES)
else
ALL_FILES = $(JAVA_FILES)
endif

CLASS_FILES = $(ALL_FILES:.java=.class)

all: $(addprefix $(BIN), $(CLASS_FILES))

clean : 
	rm -rf $(BIN)*

run : 
	java -cp $(BIN) Main

$(BIN)%.class : $(SRC)%.java
	$(COMPILE) $<
