# Tasks

## Features

- list type
- lazy sequence builder
- hash type
- modules - رزمة (rezmuh, .rez)
  - dylan style modules with external file descriptor
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
- user types: record, algebraic, union, protocol
- user types w/ versioning, persistence, immutability, ref [coherence] (http://coherence-lang.org/EmergingLangs.pdf)
- wiki
- web repl w/ ANTLR JavaScript target & jquery.console, jquery
  - ref: [amber smalltalk] (http://amber-lang.net/)
  - ref: [try-clojure] (https://github.com/Raynes/tryclojure)
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
- atom optimizer (atomizer)
- rundjinn tool for shebang scripts
- Djinn to JavaScript compiler
  - Djinn on V8?
- standalone LLVM port with C FFI
- ANTLR Djinn target

## Enhancements

- list indices start at 1 or 0?
- what does nth do if the index is out-of-bounds?
- runtime startup much much slower! is it loading by annotation, or reflective c'tor calls? both? try ctors first.
- allow method definition to be after function definitions, create placeholder methods and replace them later when actual definition is found
- type predicate functions
- is there a use for ocl pre and post conditions on synthetic slots? normal slots?
- refactor type atoms into single simple type atom and create type classes.  combine decimal and int into number ala javascript
- fix function_type_declaration to allow functions with multiple inputs and outputs, then correct definition of reduce to reduce :: [list<a> (a b -> a)] -> [a]
- warning when a declaration shadows a function name
- utf-8 identifiers
  - using AntlrInputStream, charVocabulary grammar option, '\u00c0' .. '\uFEFC' | <ascii range>
- track sealed methods in method builder and seal at the end of a translation unit
- mapping of type declaration literals to builtin types ("bool" -> BooleanAtom)
- move the primitive factory code entirely inside the runtime class, expose only a getPrimitive method
- upgrade jline to standalone version (not scala version)
- gather additional features from dylan, cat, factor, joy, clojure, plot
- model
  - add inputs and outputs to action class
  - symbols as messages?
  - tags should be implemented as properties in the metamodel
  - refactor and simplify metamodel
  - modules should have protocols and versioned dependency system
- primitives
  - defvar defun, defunp - reimplement declaration, method, function syntax as macrosx
  - roll up, down
  - full numeric intrinsics primitive impls
  - remove impls for pow, etc, wait for jdk iterop and build on calls to those
  - review for moving unnecessary impls to prelude
  - dup should not clone
  - seq protocol: cons, nth, length, remove, subseq
  - implement Lock-free extensible hash tables back by split-ordered lists, compare with UnifiedMap?
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
  - placeholder values inside lambdas
- parser
  - split type and property grammars into separate files
  - source file name is stored with meta object
  - ast to markdown transformer with string template
  - model to source transformer
- fix terminal problem on mac os
- apache commons cli or http://code.google.com/p/java-cli-api/
- apache commons launcher
- logging

## Examples

- fizzbuzz
- http://blog.carbonfive.com/2011/11/17/explorations-in-go-solving-the-instagram-engineering-challenge/
- http://en.wikipedia.org/wiki/Forth_(programming_language)#A_complete_RC4_cipher_program
- http://www.reddit.com/r/Python/comments/mdoro/whats_your_shortest_fizzbuzz/

## Other languages

- Cat - http://www.cat-language.com/
- Factor - http://factorcode.org/
- Joy - http://www.latrobe.edu.au/phimvt/joy.html
- Otuo - http://www.acooke.org/otuto.html
- Niue - http://vmathew.in/niue/index.html
- FIF - http://www.mailsend-online.com/blog/fif-isnt-forth.html
- Clojure - http://clojure.org/
