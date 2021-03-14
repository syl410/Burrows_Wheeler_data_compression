# Burrows_Wheeler_data_compression

Circular suffix sort
Manber-Myers MSD algorithm
・ Phase 0: sort on first character using key-indexed counting sort.
・ Phase i: given array of suffixes sorted on first 2i-1 characters,
            create array of suffixes sorted on first 2i characters.
        
