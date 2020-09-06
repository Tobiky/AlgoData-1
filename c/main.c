/*
    Author: Andreas Hammarstrand
    Written: 2020/08/25
    Updated: 2020/09/06
    Purpose:
        `main.c` has been written to illustrate how to reverse an input,
        through the means of either a recursive function or a in-function
        array-based stack implementation. For recursive see
        `void recursiveInput()` and for iterative see `void iterativeInput()`.
    Usage:
        Compile with any C99 or later C compiler with `-D r` for recursive
        or `-D i` for iterative. User is expected to input ASCII-limited
        characters at the start of the program.
*/

#include <stdio.h>
#define LIMIT 6

// recursively takes an input from stdin, then outputs it to stdout
void recursiveInput()
{
    // input from user
    char c = getchar();

    // if input was not new line, take in another input recursively
    if (c != '\n') {
        recursiveInput();
    }
    
    // output raw input to stdout
    putchar(c);
}

// iteratively takes an input from stdin, then outputs it to stdout
void iterativeInput() 
{
    // input storage and stack pointer initialization 
    char characters[LIMIT];
    int index = 0;

    // fill `characters` stack with input from user through stdin
    while (index < LIMIT)
    {
        characters[index] = getchar();
        index++;
    }
    
    // output all of `characters` stack to stdout
    while (index >= 0)
    {
        putchar(characters[index]);
        index--;
    }
}

int main() 
{
    // Lets us use one file for both methods, also possible to use arguments 
    #if r
    recursiveInput();
    #elif i
    iterativeInput();
    #else
    printf("No function selecion defined.");
    #endif
    putchar('\n');
}