# Welcome to the Djinn Programming Language

Djinn (جن) are supernatural creatures in Arab folklore and Islamic teachings that occupy a parallel world to that of mankind.

Djinn is a concatenative, functional, stack-based programming language.

## Getting Started

1. Build the project

    $ mvn package

2. Run the REPL

    $ java -jar target/djinn-1.0-SNAPSHOT-uber.jar

3. Enjoy

## Syntax and Semantics

The REPL supports three forms of statements: declarations, functions and immediate statements.

### Declarations

To bind a name to the result of a lambda, use a declaration:

    System> \name "Djinn"
    => <<Declaration>> "name" = ["Djinn"]

The name "name" is now bound to a lambda containing the string "Djinn".  When this name is referenced, the lambda will be
evaluated  at that time.  This is an example of the syntax sugar provided when you want to simply bind a single immediate
value to a name.  Immediate values can be integers, decimals or strings.  Otherwise if you want to bind the result of a
complete lambda to a name, do this:

    System> \value [1 1 +]
    => <<Declaration>> "value" = [1 1 +]

### Functions

To create a new function, do this:

    System> \[increment 1 +]
    => <<Function>> "increment"

The syntax for functions takes the form of:

    \[<<name>> <<pattern lambda>> <<atoms>>]

The <<name>> of the function comes first, followed by an optional pattern lambda.  This is a special lambda that will be
executed before the body of the function to determine if the function should be activated.  The pattern lambda definition
MUST be suffixed by a question mark ('?') to be considered as the pattern.

In this way, multiple function definitions can be grouped together into a "method" sharing the same name, but different
patterns and bodies.  If a function is a member of a method family, then the members will be evaluated one-at-a-time to
determine which one should be activated. If a member does not have a pattern defined and none of the pattern-bearing
members were suitable then that default function will be activated.

For example, the definition for the method "ifel" (from the Djinn prelude):

    \[ifel [dig]? [swap] dip drop2 apply]
    \[ifel drop swap drop apply]

### Immediate Statements

Any terms entered into the REPL that are not defining declarations or functions will be executed immediately.

## License

Copyright (c) 2011 Kevin Birch

Distributed under an MIT-style license.
