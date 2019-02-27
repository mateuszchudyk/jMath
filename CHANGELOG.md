# Change Log

## 1.0.0 (2019-02-27)

**jMath** is a mathematical library to evaluate complicated expression written as a `string`. Main features:
  - Expressions are stored as `Abstract Syntax Tree` so the expression can be evaluated many times without re-parsing.
  - Expressions can contains variables.
  - Support for custom function and operators.
  - Functions can be overloaded (resolver chooses function by number of arguments).
  - Functions can takes variable number of arguments (variadic functions).
  - Expressions can be optimized by optimization passes.
  - Support for custom optimization passes.

Library contains set of built-in:
  - Constants:
    - numeric: `pi`, `e`, `inf`,
    - logical: `true`, `false`,
  - Operators:
    - arithmetic: `+`, `-`, `*`, `/`, `^` (power), `-` (negation),
    - comparators: `=`, `<>`, `<`, `>`, `<=`, `>=`,
    - logical: `not`, `or`, `and`, `xor`, `nor`, `nand`, `<=>` (iff), `=>` (consequence),
    - other: `%` (percentage), `mod` (modulo),
  - Functions:
    - basic: `pow`, `sqrt`, `root`, `exp`, `log`, `log2`, `log10`, `abs`, `floor`, `ceil`, `round`, `clamp`, `sgn`, `indicator`,
    - trigonometric: `sin`, `cos`, `tan`, `asin`, `acos`, `atan`, `atan2`, `sinh`, `cosh`, `tanh`,
    - statistical: `min`, `max`, `mean`, `stddev`, `median`,
    - random: `rand` (uniform), `nrand` (normal),
    - conversion: `to_degrees`, `to_radians`, `to_logical`,
    - compound: `gcd`, `lcm`, `factorial`, `fib`, `lagrange`, `poly`,
  - Optimization passes:
    - BinaryOperatorSimplifying,
    - ConstantFolding,
