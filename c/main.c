#include <stdio.h>

void recursiveInput()
{
    // h e \n
    char c = getchar();

    if (c != '\n') {
        recursiveInput();
    }
    
    putchar(c);
}

void iterativeInput(int count) 
{
    char characters[count];
    int index = 0;

    while (index < count)
    {
        characters[index] = getchar();
        index++;
    }
    
    while (index >= 0)
    {
        putchar(characters[index]);
        index--;
    }
}

int main() 
{
    #if r
    recursiveInput();
    #elif i
    iterativeInput(6);
    #else
    printf("No function selecion defined.");
    #endif
    putchar('\n');
}