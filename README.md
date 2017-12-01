# Problem Statement

This 

This is a small project to solve a simple problem.
It's like this: you are writing code for an embedded sensor,
and this sensor has six columns of data (let's just call them 0-5),
as well as a seventh column, `timestamp`.
One goal is that aquisition of this data not be slowed down by your data structure choice, so as not to lose capturing data you otherwise would.
For that reason, we expect `insert()` to be constant or logarithmic time at worst.
