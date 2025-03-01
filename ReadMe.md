# LZ78 Compression and Decompression Tool

This project implements the **LZ78 compression and decompression algorithm** in Java. The LZ78 algorithm is a lossless data compression method that builds a dictionary of substrings encountered in the input data and replaces repeated occurrences with references to the dictionary.

## Table of Contents

- [LZ78 Compression and Decompression Tool](#lz78-compression-and-decompression-tool)
  - [Table of Contents](#table-of-contents)
  - [Features](#features)
  - [Technical Overview](#technical-overview)
    - [Algorithm](#algorithm)
    - [Data Structures](#data-structures)
    - [File Handling](#file-handling)
  - [Prerequisites](#prerequisites)
  - [Setup](#setup)
  - [Usage](#usage)
    - [Steps to Use](#steps-to-use)
  - [Example](#example)
    - [Compression](#compression)
    - [Decompression](#decompression)
  - [Testing](#testing)
    - [Example Test Case](#example-test-case)
  - [Contributing](#contributing)
  - [Acknowledgments](#acknowledgments)

---

## Features

- **Compression**: Compresses a text file into a binary file using the LZ78 algorithm.
- **Decompression**: Decompresses a binary file back into the original text file.
- **User-Friendly Interface**: A simple command-line interface for easy interaction.
- **Efficient Implementation**: Uses a dictionary to store and retrieve substrings efficiently.

---

## Technical Overview

### Algorithm

The LZ78 algorithm works as follows:

1. **Compression**:
   - Initialize an empty dictionary and a buffer for the current substring.
   - Read the input text character by character.
   - For each character, append it to the current substring and check if the combined substring exists in the dictionary.
   - If it exists, continue extending the substring.
   - If it does not exist, output the dictionary index of the current substring and the new character, then add the combined substring to the dictionary.
   - Repeat until the entire input is processed.

2. **Decompression**:
   - Initialize an empty dictionary.
   - Read the compressed binary data and reconstruct the original text using the dictionary.
   - For each tag (dictionary index + character), retrieve the corresponding substring from the dictionary and append the new character.
   - Add the new substring to the dictionary for future reference.

### Data Structures

- **Dictionary**: A `HashMap<String, Integer>` is used to store substrings and their corresponding indices during compression.
- **Binary String**: A `StringBuilder` is used to concatenate binary representations of tags during compression.
- **Byte Array**: A `byte[]` array is used to write binary data to the output file during compression and read binary data during decompression.

### File Handling

- **Compression**:
  - The input text file is read using `Files.readAllBytes()`.
  - The compressed binary data is written to a `.bin` file using `FileOutputStream`.
- **Decompression**:
  - The compressed binary file is read using `FileInputStream`.
  - The decompressed text is written to a `.txt` file using `FileOutputStream`.

---

## Prerequisites

To run this project, you need:

- **Java Development Kit (JDK)**: Ensure you have JDK 8 or later installed.

---

## Setup

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/your-username/lz78-compression.git
   cd lz78-compression
   ```

2. **Compile the Code**:
   If you are using a terminal, navigate to the project directory and compile the Java files:

   ```bash
   javac LZ78Main.java
   ```

3. **Run the Program**:
   After compiling, run the program:

   ```bash
   java LZ78Main
   ```

---

## Usage

The program provides a command-line interface with the following options:

1. **Compress**: Compress a text file into a binary file.
2. **Decompress**: Decompress a binary file back into a text file.
3. **Exit**: Exit the program.

### Steps to Use

1. Run the program:

   ```bash
   java LZ78Main
   ```

2. Choose an option:
   - Enter `1` to compress a file.
   - Enter `2` to decompress a file.
   - Enter `3` to exit.

3. Provide the input and output file paths when prompted.

---

## Example

### Compression

1. Input text file (`input.txt`):

    `TOBEORNOTTOBEORTOBEORNOT`

2. Compress the file:
   - Choose option `1`.
   - Enter the input file path: `input.txt`.
   - Enter the output file path: `compressed.bin`.

3. Output:
   - A binary file `compressed.bin` is created.

### Decompression

1. Decompress the file:

   - Choose option `2`.
   - Enter the input file path: `compressed.bin`.
   - Enter the output file path: `decompressed.txt`.

2. Output:
   - A text file `decompressed.txt` is created with the original content:

     `TOBEORNOTTOBEORTOBEORNOT`

---

## Testing

To test the program, you can use the following steps:

1. Create a text file (`test.txt`) with some sample data.
2. Compress the file using the program.
3. Decompress the file and verify that the output matches the original input.

### Example Test Case

- **Input File**: `test.txt` with content `ABABABA`.
- **Compressed File**: `compressed.bin`.
- **Decompressed File**: `decompressed.txt` (should match `test.txt`).

---

## Contributing

Contributions are welcome! If you'd like to contribute, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Commit your changes.
4. Submit a pull request.

## Acknowledgments

- This project was developed as part of the **DSAI 325: Introduction to Information Theory** course at the University of Science and Technology.
- Special thanks to the teaching assistants and instructors for their guidance.
