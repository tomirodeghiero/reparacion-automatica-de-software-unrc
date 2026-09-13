.PHONY: compile
.PHONY: test
.PHONY: clean

compile:
	javac -cp "lib/*" *.java

test:
	java -cp ".:lib/*" org.junit.runner.JUnitCore VectorTest

clean:
	find . -name '*.class' -delete
