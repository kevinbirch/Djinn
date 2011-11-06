# Tasks

## Features

- list type
- hash type
- module support
- user types
- full prelude
- java interop
- static type support
- macros

## Enhancements

- implement method multiple dispatch execution
- should dictionary be replaced by modules?
    - should dictionary/module be c'tor arg for functions et al?
- tags should be implemented as properties in the metamodel
    - model xformer should populate properties
- track sealed methods in method builder and seal at the end of a xlation unit
- type declaration parser support for type variable qualifiers (partially implemented)
- mapping of type declaration literals to builtin types ("bool" -> BooleanAtom)
- representing type variables in the model and metamodel
- fix compound declaration name visibility
- tests
- implement rest of built ins
- fatal error in parsing should not leave partial definitions, all or nothing
- escape sequences in strings
- regex literals
- need to differentiate between a lambda in an immediate statement that must be pushed and one that must be executed
- I/O
- hello world example
- repl restarts, fix and continue
- symbols as messages?
- gensym is uuid from tag url
- more syntax tests for tags, etc
- roll up, down primitives
- declarations inside methods don't cache
- version info properties file from build
- full numeric intrinsics primitive impls
- eliminate context?
- ast to markdown transformer with string template
- split type and property grammars into separate files
- dylan style modules with external file descriptor
- source file name is stored with meta object
- model to source transformer
- move the primitive factory code entirely inside the runtime class, expose only a getPrimitive method
- add module repository to runtime class
- add module parser to runtime class
- add inputs and outputs to action class
- exceptions?
- additional predicates: odd?, even?, etc
- refactor binary function to have type variable for stack item types
- should compose throw if the lambdas have more than one atom? no?
- fix terminal problem on mac os
- apache commons cli
- apache commons launcher
- logging


