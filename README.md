# Burrows_Wheeler_data_compression

This is a bzip2 like data compression program. It implements primary aglorithms used in the bzip2.
The compression ratio is close to bzip2.




Circular suffix sort\
Manber-Myers MSD algorithm\
・ Phase 0: sort on first character using key-indexed counting sort.\
・ Phase i: given array of suffixes sorted on first 2i-1 characters,\
            create array of suffixes sorted on first 2i characters.\

