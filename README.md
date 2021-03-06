# Burrows_Wheeler_data_compression

This is a bzip2 like data compression program.\
It implements primary aglorithms used in the bzip2.\
The compression ratio is close to bzip2.\
This originates from one [project](https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php) of courses of Algorithms Part 1 and Part2 with more implementation on algorithm and bash script to make it a real compression program.


**Workflow of the data compression and decompression:**\
Compression:\
Burrows–Wheeler transform -> Move-to-front encoding -> Huffman compression\
Decompression:\
Burrows–Wheeler inverse transform -> Move-to-front decoding -> Huffman decompression

**Main algorithm used in this data compression program**:\
***Circular suffix sort:*** sort array of the n circular suffixes of a string of length n.\
            *Manber-Myers MSD(Most-Significant-Digit) first string sort algorithm*\
                        - Phase 0: sort on first character using *key-indexed counting sort*.\
                        - Phase i: given array of suffixes sorted on first 2i-1 characters, create array of suffixes sorted on first 2i characters using *3way quick-sort*.
            
***Burrows–Wheeler transform:*** transform typical English text file into a text file in which sequences of the same character occur near each other many times.

***Move-to-front encoding:*** convert string into a string in which certain characters appear much more frequently than others.

***Huffman compression:*** compress text by encoding frequently occurring characters with short codewords and infrequently occurring characters with long codewords.


------------

1, More details about Burrows–Wheeler transform ([reference](https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php)):\
*Burrows–Wheeler transform*\
![image](https://user-images.githubusercontent.com/16233638/111111050-9e3fc780-852b-11eb-9d3a-365e874b6867.png)\
result:\
3\
ARD!RCAAAABB

*Burrows–Wheeler inverse transform*
- Inverting the message given t[], first, and the next[] array. Sort last column to get first column and search from first to each next line:\
![image](https://user-images.githubusercontent.com/16233638/111111355-2aea8580-852c-11eb-9ba2-dbbb65918253.png)
- Constructing the next[] array from t[] and first. It is easy to deduce a next[] value for a character that appears exactly once in the input string. For char appears twice or more, they match the relative order in the last column.\
![image](https://user-images.githubusercontent.com/16233638/111110943-73557380-852b-11eb-8f50-3e6a630bdbac.png)


2, More details about Huffman compression\
*Compression*
- read string from input
- get frequency of each radix (freArr)
- write length
- construct trie from freArr
- write trie
- build symbol table from trie       
- encode inputStr using the symbol table

*Decompression*
- read length
- read flat trie and and re-build trie
- decode inputStr using trie
