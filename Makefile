JFLAGS = -d
JC = javac
OUTFILE = class
SRC = src
PACKAGES = insurance
CLASSES = Main.java

all:
	mkdir -p $(OUTFILE)
	$(JC) $(JFLAGS) $(OUTFILE) $(SRC)/*.java $(SRC)/*/*.java
	jar xf postgresql-42.2.23.jar

jar:
	jar -cfmv bank.jar Manifest.txt META-INF org -C $(OUTFILE) Main.class */*.class $(OUTFILE)

clean:
	rm -rf $(OUTFILE)
	rm -rf META-INF org bank.jar
