# N-Queens Problem Solver

## Introduction

The N-Queens Problem Solver is a Java-based application that aims to solve the classic N-Queens puzzle using various hill climbing algorithms. The N-Queens problem is a well-known puzzle that involves placing N chess queens on an N×N chessboard so that no two queens can threaten each other, i.e., no two queens share the same row, column, or diagonal. This project provides an efficient and interactive tool to explore different algorithms for solving this intriguing puzzle.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Algorithms Implemented](#algorithms-implemented)
- [How to Run](#how-to-run)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)



## Features

- **Variety of Algorithms**: The project implements different hill climbing algorithms to solve the N-Queens problem, allowing you to compare their performance and efficiency.
- **Interactive Interface**: The program provides an interactive command-line interface that guides you through the process, making it easy to set up experiments and view results.
- **Customizable**: You can choose the number of queens for your puzzle and select the algorithm that best suits your needs.
- **Statistics**: The application keeps track of the number of successful and failed attempts, displaying statistics on success rates and move counts.
- **Random Restart**: The project includes a random restart feature, which is often used to escape local minima and find better solutions.

## Algorithms Implemented

The project implements the following hill climbing algorithms for solving the N-Queens puzzle:

1. **Hill Climbing Search**: A basic hill climbing algorithm that iteratively explores neighboring solutions and selects the one with the lowest heuristic cost.
2. **Hill Climbing Search with Sideways Move**: A variation of hill climbing that allows sideways moves, expanding the search space and potentially escaping local optima.
3. **Random-Restart Hill Climbing with Sideways Move**: A random-restart strategy that restarts the hill climbing process if a local minimum is encountered. It also includes sideways moves for exploration.
4. **Random-Restart Hill Climbing without Sideways Move**: Similar to the previous algorithm, but without sideways moves.

## How to Run

Follow these steps to run the N-Queens Problem Solver:

1. **Compile**: Compile the Java code using any Java compiler.

   ```shell
   javac NQueensProblem.java

2. **Run th Program**: Execute the program using the following command.

    ```shell
    java NQueensProblem

3. **User Input:** Follow the on-screen prompts to input the number of queens you want to place on the chessboard and select the algorithm you'd like to use for solving the puzzle.

4. **View Results:** The program will execute the chosen algorithm and display the results, including the final board configuration and statistics.

## Usage

- Choose the number of queens (N) to place on the chessboard.
- Select an algorithm to solve the N-Queens problem (options are explained in the program).
- Observe the algorithm's progress, including successful and failed attempts.
- Analyze the final solution or experiment with different configurations.

## Contributing
If you would like to contribute to the banking system project, please follow these steps:

Fork this repository to your own GitHub account and then clone it to your local machine.

Create a new branch for your feature or bug fix: git checkout -b my-feature-branch.

Make changes to the code and commit them: git commit -am 'Add some feature'.

Push the changes to your GitHub account: git push origin my-feature-branch.

Create a pull request to merge your changes into the main branch of this repository.

### License
This project is licensed under the MIT License. See the LICENSE file for more information.