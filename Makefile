all: Markov.class

Markov.class: Markov.java Prefix.class Chain.class
	javac Markov.java

Chain.class: Chain.java
	javac Chain.java

Prefix.class: Prefix.java
	javac Prefix.java

clean:
	rm *.class
