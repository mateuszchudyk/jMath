# jMath

[![Release](https://img.shields.io/github/release/mateuszchudyk/jmath.svg?colorB=blue&style=for-the-badge)](https://github.com/mateuszchudyk/jmath/releases)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?colorB=blue&style=for-the-badge)](./LICENSE)

## Overview

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
    - conversion: `to_degrees`, `to_radians`, `to_logical`
    - compound: `gcd`, `lcm`, `factorial`, `fib`, `lagrange`, `poly`
  - Optimization passes:
    - BinaryOperatorSimplifying
    - ConstantFolding

## Built-in operators

| Symbol | Description | Priority | Position | Associative |
|---|---|---|---|---|
| ! | see `Factorial` function| 1 | Right | Left |
| ^ | see `Exponentiation` function | 2 | Middle | Right |
| - | Arithmetic negation | 3 | Left | Right |
| not | Logical not | 3 | Left | Right |
| * | Multiplication | 4 | Middle | Both |
| mod | Modulo | 4 | Middle | Left |
| % | Percentage | 4 | Right | Left |
| / | Division. Divider cannot be 0 | 4 | Middle | Left |
| + | Addition | 5 | Middle | Both |
| - | Subtraction | 5 | Middle | Left |
| =, <>, <, >, <=, >= | Comparators | 7 | Middle | Left |
| <=> | Logical if and only if | 7 | Middle | Left |
| => | Logical consequence | 7 | Middle | Left |
| or, and, xor | Logical or, and, xor | 8 | Middle | Both |
| nor, nand | Logical nor, nand | 8 | Middle | Left |

## Built-in functions

| Name | Description | Number of arguments |
|---|---|---|
| abs | Abs(x) = absolute value (if x < 0 then -x else x) | n = 1 |
| acos | Acos(x) = arccosine. Domain x: [-1, 1] | n = 1 |
| asin | Asin(x) = arcsine. Domain x: [-1, 1] | n = 1 |
| atan | Atan(x) = arctangent | n = 1 |
| atan2 | Atan2(y, x) = theta from polar coordinate (r, theta) of point (x, y). Domain: any (x, y) without (0, 0) | n = 2 |
| ceil | Ceil(x) = the smallest integer greater then x | n = 1 |
| clamp | Clamp(x, a, b) = if x < a then return x, if x > b then return b, else return x | n = 3 |
| cos | Cos(x) = cosine of x (in radian) | n = 1 |
| cosh | Cosh(x) = hyperbolic cosine of x | n = 1 |
| exp | Exp(x) = e^x | n = 1 |
| factorial | Factorial(n) = the product of the numbers from 1 to n. Domain n: {0, 1, 2, ...} | n = 1 |
| fib | Fib(n) = the n-th element of Fibonacci's sequence (F_0 = 0, F_1 = 1). Domain n: {0, 1, 2, ...} | n = 1 |
| floor | Floor(x) = the largest integer lower then x | n = 1 |
| gcd | Gcd(a, ...) = the greatest common divisor of the numbers. Domain for all numbers: {1, 2, ...} | n >= 1 |
| indicator | Indicator(x, a, b) = return true (1.0) if and only if a < x < b else return false (0.0) | n = 3 |
| lagrange | Lagrange(x, x1, y1, ...) = value of lagrange interpolation polynomial in point x. First argument is x (where calculate value of interpolation polynomial) next there are pairs (x, y) of check points. Number of arguments must be odd | n >= 3 && n % 2 = 1 |
| lcm | Lcm(a, ...) = the least common multiple of the numbers. Domain for all number: {1, 2, ...} | n >= 1 |
| log | Log(b, x) = natural logarithm of x with base b. Domain b: (0, +inf)/{1}, x: (0, +inf) | n = 2 |
| log | Log(x) = natural logarithm of x. Domain x: (0, +inf) | n = 1 |
| log10 | Log10(x) = logarithm of x to base 10. Domain x: (0, +inf) | n = 1 |
| log2 | Log2(x) = logarithm of x to base 2. Domain x: (0, +inf) | n = 1 |
| max | Max(a, ...) = the largest out of the given numbers | n >= 1 |
| mean | Mean(a, ...) = the mean of the given numbers | n >= 1 |
| median | Median(a, ...) = the median of the given numbers | n >= 1 |
| min | Min(a, ...) = the smallest out of the given numbers | n >= 1 |
| nrand | Nrand() = the random number with a standard normal distribution (avg: 0, stddev: 1) | n = 0 |
| nrand | Nrand(mean, stddev) = the random number with a normal distribution. Domain stddev: (0, +inf) | n = 2 |
| poly | Poly(x, a0, a1, ...) = value of polynomial in point x. First argument is x (where calculate value of polynomial) next there are coefficients of polynomial (a0+a1*x+a2*x^2+...) | n >= 2 |
| pow | Pow(a, b) = a to the power b. Domain a: if abs(b) < 1 then [0, +inf) else any real number | n = 2 |
| rand | Rand() = the random number with a uniform distribution [0, 1) | n = 0 |
| rand | Rand(a, b) = the random number with a uniform distribution [a, b). Domain: b > a | n = 2 |
| root | Root(x, n) = the nth root of x. Domain x: if n is even then [0, +inf) else any real number | n = 2 |
| round | Round(x) = the nearest integer number x | n = 1 |
| sgn | Signum(x) = sign of x. Result is: 1 for x > 0, 0 for x = 0 and -1 for x < 0 | n = 1 |
| sin | Sin(x) = sine of x (in radian) | n = 1 |
| sinh | Sinh(x) = hyperbolic sine of x | n = 1 |
| sqrt | Sqrt(x) = the square root of x. Domain x: [0, +inf) | n = 1 |
| stddev | Stddev(a, ...) = the standard deviation of the given numbers | n >= 1 |
| tan | Tan(x) = tangent of x (in radian) | n = 1 |
| tanh | Tanh(x) = hyperbolic tangent of x | n = 1 |
| to_degrees | To_Degrees(x) = convert x from radian to degrees | n = 1 |
| to_logical | To_Logical(x) = if x < 1 then 0 else 1 | n = 1 |
| to_radians | To_Radians(x) = convert x from degrees to radians | n = 1 |

## Built-in optimization passes

| Name | Description |
|---|---|
| BinaryOperatorSimplifying | Simplify a binary operator if it's possible, e.g.: `x + 0 = x` or `x^1 = x`|
| ConstantFolding | Fold `AST` subtree to constant if it contains only constans |

## License

[MIT]



[MIT]: LICENSE
