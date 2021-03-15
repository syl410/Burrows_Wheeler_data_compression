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


More details about Burrows–Wheeler transform:\
i     Original Suffixes          Sorted Suffixes       t    index[i]\
--    -----------------------     -----------------------    --------\
 0    A B R A C A D A B R A !     ! A B R A C A D A B R A    11\
 1    B R A C A D A B R A ! A     A ! A B R A C A D A B R    10\
 2    R A C A D A B R A ! A B     A B R A ! A B R A C A D    7\
*3    A C A D A B R A ! A B R     A B R A C A D A B R A !   *0\
 4    C A D A B R A ! A B R A     A C A D A B R A ! A B R    3\
 5    A D A B R A ! A B R A C     A D A B R A ! A B R A C    5\
 6    D A B R A ! A B R A C A     B R A ! A B R A C A D A    8\
 7    A B R A ! A B R A C A D     B R A C A D A B R A ! A    1\
 8    B R A ! A B R A C A D A     C A D A B R A ! A B R A    4\
 9    R A ! A B R A C A D A B     D A B R A ! A B R A C A    6\
10    A ! A B R A C A D A B R     R A ! A B R A C A D A B    9\
11    ! A B R A C A D A B R A     R A C A D A B R A ! A B    2\
result:\
3\
ARD!RCAAAABB\
