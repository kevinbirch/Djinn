# Welcome to the Djinn Programming Language

Djinn (جن) are supernatural creatures in Arab folklore and Islamic teachings that occupy a parallel world to that of mankind.

Djinn is a concatenative, functional, stack-based programming language.

## Getting Started

1. Build the project

    $ mvn package

2. Run the REPL

    $ java -jar target/djinn-1.0-SNAPSHOT-uber.jar

3. Enjoy

## The Canonical "Hello World" Example

    $ java -jar target/djinn-1.0-SNAPSHOT-uber.jar
    Welcome to Djinn.
    Type ":quit" or ":exit" to end your session.  Type ":help" for instructions.

    User> "hello, world" println
    hello, world
    stack:

## Syntax and Semantics

The REPL supports three forms of statements: declarations, functions and immediate statements.

### Declarations

To bind a name to the result of a lambda, use a declaration:

    User> \name "Djinn"
    => <<Declaration>> "name" = ["Djinn"]

The name "name" is now bound to a lambda containing the string "Djinn".  When this name is referenced, the lambda will be
evaluated  at that time.  This is an example of the syntax sugar provided when you want to simply bind a single immediate
value to a name.  Immediate values can be integers, decimals or strings.  Otherwise if you want to bind the result of a
complete lambda to a name, do this:

    User> \value [1 1 add]
    => <<Declaration>> "value" = [1 1 add]

### Functions

To create a new function, do this:

    User> \[increment 1 add]
    => <<Function>> "increment"

The syntax for functions takes the form of:

    '\[' NAME (lambda '?')? atom+ function* declaration* ']'

The name of the function comes first, followed by an optional pattern lambda.  This is a special lambda that will be
executed before the body of the function to determine if the function should be activated.  The pattern lambda definition
MUST be suffixed by a question mark ('?') to be considered as the pattern.

In this way, multiple function definitions can be grouped together into a "method" sharing the same name, but different
patterns and bodies.  If a function is a member of a method family, then the members will be evaluated one-at-a-time to
determine which one should be activated. If a member does not have a pattern defined and none of the pattern-bearing
members were suitable then that default function will be activated.

For example, the definition for the method "ifel" (from the Djinn prelude):

    \[ifel [id]? drop [drop] dip apply]
    \[ifel drop drop apply]

### Immediate Statements

Any terms entered into the REPL that are not defining declarations or functions will be executed immediately.

    User> name
    stack: "Djinn"

    User> 5 increment
    stack: "Djinn" 6

    User> value
    stack: "Djinn" 6 2

## License

Copyright (c) 2011 Kevin Birch

Distributed under an [MIT-style](http://www.opensource.org/licenses/mit-license.php) license.

> Permission is hereby granted, free of charge, to any person obtaining a copy of
> this software and associated documentation files (the "Software"), to deal in
> the Software without restriction, including without limitation the rights to
> use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
> of the Software, and to permit persons to whom the Software is furnished to do
> so, subject to the following conditions:

> The above copyright notice and this permission notice shall be included in all
> copies or substantial portions of the Software.

> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
> IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
> FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
> AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
> LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
> OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
> SOFTWARE.

