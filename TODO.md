# Tasks

## Features

- list type
- module support
- user types
- static type support

## Enhancements

- move stack depth requirements up from function to method
    - for user functions this requires finding depth requirements for the actual initial atom
    - for built-ins binary, unary and nullary abstract classes can be created
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
