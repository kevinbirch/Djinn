# Tasks

## Features

- list type
- lazy sequence builder
- hash type
- modules - رزمة (rezmuh, .rez)
  - split type and property grammars into separate files
  - dylan style modules with external file descriptor
  - source file name is stored with meta object
  - add module repository to runtime class
  - add module parser to runtime class
  - replace dictionary with modules
  - should dictionary/module be c'tor arg for functions et al?
- regex type
- runtime image storage format
- standalone runtime mode
- repl server & client
- type and predicate based method dispatch
- pre/post method functions, next-method
- user types w/ versioning, persistence, immutability
- java interop
- bytecode compiler
- static type support
- macros
- MOP
- IntelliJ plugin
- eclipse plugin
- MDE: XMI, OCF support
- live object graph across associations for calculated slots
- concurrency model (STM)

## Enhancements

- track sealed methods in method builder and seal at the end of a translation unit
- mapping of type declaration literals to builtin types ("bool" -> BooleanAtom)
- move the primitive factory code entirely inside the runtime class, expose only a getPrimitive method
- upgrade jline to standalone version (not scala version)
- model
  - add inputs and outputs to action class
  - symbols as messages?
  - tags should be implemented as properties in the metamodel
- primitives
  - roll up, down
  - full numeric intrinsics primitive impls
- prelude
  - additional predicates: odd?, even?, etc
- repl
  - file loading
  - fatal error in parsing should not leave partial definitions, all or nothing
  - restarts, fix and continue
  - refactor commands into separate classes
- types
  - type declaration parser support for type variable qualifiers (partially implemented)
  - representing type variables in the model and metamodel
- syntax
  - more syntax tests for tags, etc
  - exceptions?
  - escape sequences in strings
- parser
  - ast to markdown transformer with string template
  - model to source transformer
- fix terminal problem on mac os
- apache commons cli
- apache commons launcher
- logging
