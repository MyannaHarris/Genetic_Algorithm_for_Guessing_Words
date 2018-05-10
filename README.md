# Genetic_Algorithm_for_Guessing_Words
Genetic algorithm that tries to guess a word

Some files were given

To Compile (either Windows or Unix):
1. Copy all eight files to the same directory
2. Be sure that JDK 7 is installed and accessible from the directory where the WordGuess files are found
3. Type: javac SetParams.java
4. Type: javac WordGuessTst.java

To create the parameter file.
1. Decide upon a parameter set, for example:
```
   --128 intitial population members
   --64 actural population members
   --5 size of the word to be guessed
   --.1 mutation factor
   --1000 maximum number of generations
```
2. Type: java SetParams params.dat 128 64 5 .1 1000

To run WordGuess:
1. Type: java WordGuessTst <parameter file> <word to be guessed>
    For example, if the parameter file is "params.dat" and the word to be guessed is "genetic"
    java WordGuessTst params.dat genetic
2. WordGuess displays the best guess from each generation until it guesses the word itself or 
   until the maximum number of generations is reached.
