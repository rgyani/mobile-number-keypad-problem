# mobile-number-keypad-problem

Given the mobile numeric keypad. 
You can only press buttons that are up, left, right or down to the current button. You are not allowed to press bottom row corner buttons (i.e. * and # ).

![img](imgs/phone-5494681_640.png)

Given a number N, find out the number of possible numbers of given length.

**For example:** <br>
For N=1, number of possible numbers would be 10 (0, 1, 2, 3, …., 9)<br>
For N=2, number of possible numbers would be 26<br>
Possible numbers: 08, 12, 14, 21, 23, 25 and so on.<br>
………………………………<br>
………………………………<br>


### Let's solve this using Dynamic Programming

Dynamic Programming is an algorithmic paradigm that solves a given complex problem by breaking it into subproblems and stores the results of subproblems to avoid computing the same results again.<br>
The two main properties of a problem that suggests that it can be solved using Dynamic programming are:
1. Overlapping subproblems
2. Optimal Substructure

#### Overlapping subproblems
For example, in Binary search there are no subproblems, whereas in Fibonacci Numbers, there are many subproblems which are solved again and again

```
/* simple recursive program for Fibonacci numbers */
int fib(int n)
{
    if ( n <= 1 )
    return n;
    return fib(n-1) + fib(n-2);
}
```

Here is the recursion tree for fib(5)

```
    
                         fib(5)
                     /             \
               fib(4)                fib(3)
             /      \                /     \
         fib(3)      fib(2)         fib(2)    fib(1)
        /     \        /    \       /    \
  fib(2)   fib(1)  fib(1) fib(0) fib(1) fib(0)
  /    \
fib(1) fib(0)
```

We can see that the function fib(3) is being called 2 times. 
If we had stored the value of fib(3), then instead of computing it again, we could have reused the old stored value.

In dynamic programming, computed solutions to subproblems are stored in a table so that these don’t have to be recomputed.

There are following two different ways to store the values so that these values can be reused:
* Memoization (Top Down)
* Tabulation (Bottom Up)

###### Memoization (Top Down)
The memoized program is similar to the recursive version, wherein, it uses a lookup table before computing solutions. 
We initialize a lookup Count with all initial values as NIL. 
Whenever we need the solution to a subproblem, we first look into the lookup table. 
If the precomputed value is there then we return that value, otherwise, we calculate the value and put the result in the lookup table so that it can be reused later.

```
  int fib(int n) 
  { 
    if (lookup[n] == NIL) 
    { 
      if (n <= 1) 
          lookup[n] = n; 
      else
          lookup[n] = fib(n-1) + fib(n-2); 
    } 
    return lookup[n]; 
  } 
```

###### Tabulation (Bottom Up)
The tabulated program for a given problem builds a table in bottom up fashion and returns the last entry from table. 
For example, for the same Fibonacci number, we first calculate fib(0) then fib(1) then fib(2) then fib(3) and so on. 
So literally, we are building the solutions of subproblems bottom-up.

```
  int fib(int n) 
  { 
    int f[] = new int[n+1]; 
    f[0] = 0; 
    f[1] = 1; 
    for (int i = 2; i <= n; i++) 
          f[i] = f[i-1] + f[i-2]; 
    return f[n]; 
  } 
```

#### Optimal Substructure
A given problem has Optimal Substructure Property if optimal solution of the given problem can be obtained by using optimal solutions of its subproblems.
For example, the Shortest Path problem has following optimal substructure property:
If a node x lies in the shortest path from a source node u to destination node v then the shortest path from u to v is combination of shortest path from u to x and shortest path from x to v.


Coming back to the Mobile number Keypad Problem above, we see that it has both properties, Optimal Substructure and Overlapping Subproblems, so lets solve it using dynamic programming efficiently,


For N=1, its a trivial case, lets put it in our table like so

| Start\N |  1  |
|:-------:|:---:|
| 0       |   1 |
| 1       |   1 |
| 2       |   1 |
| 3       |   1 |
| 4       |   1 |
| 5       |   1 |
| 6       |   1 |
| 7       |   1 |
| 8       |   1 |
| 9       |   1 |

For N=2, we see that,<br>
For Start=0, we press 0 and now, we can only press 8, hence<br>
**Count[0,2] = Count[8,1]**<br>
For Start=1, we press 1 and now, we can press either 2 or 4, hence<br>
**Count[1,2] = Count[2,1] + Count[4,1]**<br>
For Start=2, we press 2 and now, we can press either 1 or 5 or 3, hence<br>
**Count[2,2] = Count[1,1] + Count[5,1] + Count[3,1]**<br>
and so on...


| Start\N |   1 |   2 |
|:-------:|:---:|:---:|
| 0       |   1 |   1 |
| 1       |   1 |   2 |
| 2       |   1 |   3 |
| 3       |   1 |   2 |
| 4       |   1 |   3 |
| 5       |   1 |   4 |
| 6       |   1 |   3 |
| 7       |   1 |   2 |
| 8       |   1 |   4 |
| 9       |   1 |   2 |


Now things get interesting, For N=3, we see that<br>
For Start=0, we pressed 0, now we are the same situation above when we are starting from 8 with 2 presses left, hence<br>
***Count[0,3] = Count[8, 2]*** <br>
For Start=1, we pressed 1 and then either 2 or 4, with 1 press left, so <br>
***Count[1,3] = Count[2, 2] + Count[4,2]*** <br>
For Start=2, we pressed 2 and then either 1 or 5 or 3, with 1 press left, so <br>
***Count[2,3] = Count[1, 2] + Count[5,2] + Count[3,2]*** <br>
and so on...

| Start\N |   1 |   2 |   3  |
|:-------:|:---:|:---:|:----:|
| 0       |   1 |   1 |   4  |
| 1       |   1 |   2 |   6  |
| 2       |   1 |   3 |   8  |
| 3       |   1 |   2 |   6  |
| 4       |   1 |   3 |   8  |
| 5       |   1 |   4 |   13 |
| 6       |   1 |   3 |   8  |
| 7       |   1 |   2 |   7  |
| 8       |   1 |   4 |   9  |
| 9       |   1 |   2 |   7  |

So now, we have a clear understanding of how to find the count, for each start for each possible N<br>
***Count[0,N] = Count[8, N-1]***<br>
***Count[1,N] = Count[2, N-1] + Count[4, N-1]***<br>
***Count[2,N] = Count[1, N-1] + Count[5, N-1] + Count[3, N-1]***<br>
***Count[3,N] = Count[2, N-1] + Count[6, N-1]***<br>
***Count[4,N] = Count[1, N-1] + Count[5, N-1] + Count[7, N-1]***<br>
***Count[5,N] = Count[2, N-1] + Count[4, N-1] + Count[8, N-1] + Count[6, N-1]***<br>
***Count[6,N] = Count[3, N-1] + Count[5, N-1] + Count[9, N-1]***<br>
***Count[7,N] = Count[4, N-1] + Count[8, N-1]***<br>
***Count[8,N] = Count[7, N-1] + Count[5, N-1] + Count[9, N-1]***<br>
***Count[9,N] = Count[8, N-1] + Count[6, N-1]***<br>


### Lets talk about the implementation now

Its is obvious, we can solve this in O(N) time, since the main loop runs N time, while the auxilary loops for finding the movement using
```
// left, up, right, down move from current location
int row[] = {0, 0, -1, 0, 1};
int col[] = {0, -1, 0, 1, 0}; 
```  
will run in constant time.<br>
The Space complexity is however O(N), however, if we hardcode the formula above, we can use just two arrays of size 10

Both the solutions are presented in the attached Solution.java file
