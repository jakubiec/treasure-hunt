# Treasure Hunt

The implementation provides 2 approaches:
* object oriented
* functional (mostly inspired by the book [Domain Modeling Made Functional](https://www.amazon.com/Domain-Modeling-Made-Functional-Domain-Driven/dp/1680502549))

Those are represented in the codebase in dedicated packages like `object_oriented` or `functional`.

## Use cases

1. Read input from standard input and explore starting at `1,1`
    It also stores valid input in the memory.
2. Query endpoint, i.e. `curl localhost:8080/treasure/11`, to find treasure.
    The valid input needs to be previously set by Standard Input.

## Build&Test&Run

1. Test with: `./gradlew check`
2. Build with: `./gradlew shadowjar`
3. Run with: `java -jar build/libs/treasure-hunt.jar`
4. Enjoy ;)

