all: clean compile run

compile: 
	@echo "-------------------------------------------"
	@echo "Compiling..."
	@javac Main.java

run:
	@echo "-------------------------------------------"
	@echo "Running the tests...."
	@echo "================================================================================="
	java Main
	@echo "================================================================================="
	@echo "Completed tests...."


clean:
	@echo "-------------------------------------------"
	@echo "Removing compiled files..."
	@rm -f *.class
