# Tasks

## Features

- list type
- module support
- user types
- full prelude
- java interop
- static type support

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
- more syntax tests for methods, comments, tags, etc
- roll up, down primitives
- declarations inside methods don't cache
- version info properties file from build

