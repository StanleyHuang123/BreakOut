JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
				$(JC) $(JFLAGS) $*.java

CLASSES = \
        Breakout.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
				$(RM) *.class

run:
				javac Breakout.java
				java Breakout 25 6