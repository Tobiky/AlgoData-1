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