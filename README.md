# Sensor Data

## Users Guide

Check out `src/main/java/App.java` to change what operation to perform on your data.
The file name of the data needs to be passed in as a command-line argument.
There is an example of how to use my code to process your own data, 
but to use it, one need only edit a few lines.

## Problem Statement

This problem involves designing a data structure and accompanying algorithms to read and process sensor data.
This data will be given in the form of 7-column data points, one column at a time.
We are then tasked with collecting this data effieciently which also providing adequate information for later processing, the details of which will be decribed later.

Our data is read by an embedded sensor, and stored in some data structure of our design.
Because of the nature of this device, space and time are a major constraint on the sensing side.
Most embedded devices are going to have perhaps megabytes of memory all told, and
taking too long to make a measurement will mean losing the opportunity to procure more data.
The data is read in 7 columns, or signals, and they are `timestamp, gx, gy, gz, ax, ay, az`, where those prefixed by `g` are from a gyroscope, and those prefixed by `a` were read from an accelerometer; `timestamp` in integer, and the rest in floating point (call it `double`).

But the interpretation of these columns is not very important here.
What is important is performing four key functions on it as quickly as possible.

1. `searchContinuityAboveValue(? data, int indexBegin, int indexEnd, double threshold, int winLength)`
    Finds the first index in `data` (which is one column of the overall dataset) which begins a run of length at least `winLength` in which all elements are greater than `threshold`. This search will only occur between `indexBegin` and `indexEnd`.
2. `backSearchContinuityWithinRange(? data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength)` 
    Finds the first index in `data` which begins a run of length `winLength` in which all elements are greater than `thresholdLo`, and less than `thresholdLo`.
3. `searchContinuityAboveValueTwoSignals(? data1, ? data2, int indexBegin, int indexEnd, double threshold1, double threshold2, winLength)` 
    Finds the first index at both `data1` and `data2` have runs on length `winLength` greater than `threshold1` and `threshold2`, respectively.
4. `searchMultiContinuityWithinRange(? data, int indexBegin, int indexEnd, double thresholdLo, double thresholdHi, int winLength)`
    Finds the begining and ending index of all regions in `data` of length at least `winLength` and where all elements are between `thresholdLo` and `thresholdHi`.

## Approach

We see here that there are 3 critical figures to optimize.
These will be used to compare some alternative implemenations.

1. Insert time. This will be done on a small embedded device, with minimal processing power.
    Every additional microsecond it takes to add a new element to our data structure means that we can take few readings in the same amount of time, and therefore we are left with less precise data.
    Our goal is O(1) or O(log n).
2. Space. Again, because of the nature of the sensor, there are hard limits to the amount of available memory.
    Goal is O(n), maybe upto O(n<sup>2</sup>) if we can get a big improvement somewhere else.
3. Processing time.
    If we squint, we can see that the operations listed above are fundamentally similar:
    finding contiguous regions of numbers which meet some numerical bound, either searching forward or backward.
    For this reason, I will demonstrate an algorithm for the first without mentioning the others in this document.
    Although I wasn't told this, I believe that this operation will occur either on a server, or at least a more powerful user device, and that it will be called many fewer times than insert.
    Goal is to beat naive search (described later), which approaches O(n<sup>2</sup>).


## Naive

In this case, `data` is simply an array of the relevant values, ordered by index.
The simplest search in this case follows.

```
func searchContinuityAboveValue(data, indexBegin, indexEnd, threshold, winLength):
    for i = indexBegin to (indexEnd - winLength) do
        for j = 0 to winLength do
            if data[i+j] < threshold then
                continue to next i
            end
        end
        return i
    end
end
```

Nice and simple, but if we say that the difference between `indexBegin` and `indexEnd` is a constant multiple of n, and that `winLength` is a constant multiple of n (both of which seem like reasonable assumptions), then this is O(n<sup>2</sup>).
Not very good.

The benefit of this approach, however, is that on-device processing is as good as it can get.
We have O(1) insert time, amortized if we're dealing with dynamic arrays like `java.util.ArrayList`, and O(n) space.
This really is the benchmark against which we will measure other solutions.


## Partial Min Matrix

Perhaps if we knew the minimum element between indices `i` and `j`, then processing would be very quick.
If the minimum element is greater than theshold, then all elements within some span will be as well.
Say `data` took two indices and returned this information instead.

```
func searchContinuityAboveValue(data, indexBegin, indexEnd, threshold, winLength):
    for i = indexBegin to (indexEnd - winLength) do
        if data[i, i + winLength] > threshold then
            return i
        end
    end
end
```

Now we're only considering linear work.
A big upgrade, right?

Well, in order to have this matrix, we need to use O(n<sup>2</sup>) space, and in order to populate it O(n<sup>2</sup>) time overall, and O(n) time on each element.
Based on our earlier discussion, those figures are unacceptable.


## Sorted Data

Alright, but what if the data came to us sorted, maximum to minimum? 
Or more specifically, the data is in order, 
but we have a list of indices which refer to elements largest to smallest?
Imagine we had some mystery algorithm which detects when the addition of an index creates a run of the size we're looking for, in constant time per element, no less.
I suspect we could use a method somewhat similar to [Disjoint Set (Union-Find)](https://en.wikipedia.org/wiki/Disjoint-set_data_structure) to do this, but for the sake of brevity, we'll call it magic.

Let `magic(index)` be a function which

* Returns -1 if the addition of `index` does not create a run of length `winLength`.
* Returns the earliest index of such a run.

Then we can do the following.

```
func searchContinuityAboveValue(data, indexBegin, indexEnd, threshold, winLength):
    int best = infinity
    for i in data.maxElements do
        if i < indexBegin or i > indexEnd then
            continue to next i
        end
        if data[i] < threshold then
            return best
        end
        k = magic(i)
        if k != -1 and k < best then
            best = k
        end
    end
    return best
end
```

Linear time for processing, right?
Well, the question is how to get these sorted indices.
I can think of a few methods.

1. Construct it entirely in processing. 
    The general rule is O(n log n) to sort lists. Constructing this seems no different.
2. Construct sorted list during capture.
    Then we're looking at O(n) capture to insert each element into the sorted array. No good.
3. Use a binary search tree. 
    Insert (during capture) is O(log n), and finding an arbitrary element (during processing) is as well.
    Then the processing shown above is O(n log n).
4. Use a max heap.
    Basically the same as a binary search tree.

The biggest downsides of 2, 3, and 4 is that, while asymptotic space remains linear, they each require about 3x the space of only keeping the data itself around during capture, which may still be too much for our device.
So, with all considered, the best we can do 1: O(1) insert, O(n) space, O(n log n) processing. Not bad over all.


## Rolling Minimum

Inspired by the [rolling hash](https://en.wikipedia.org/wiki/Rolling_hash) for substring searching, 
let us construct a rolling minimum of `winLength` size windows in `data`.
This will need three operations.

1. `insert(elem)` will add `elem` to the collection.
2. `getMin()` will return the minimum of the collection.
3. `delete(elem)` will remove `elem` from the collection.

Then suppose `data` is a simple array of measured data again.

```
func searchContinuityAboveValue(data, indexBegin, indexEnd, threshold, winLength):
    let rm = RollingMinimum()
    add the first winLength elements to rm.

    for i = indexBegin to (indexEnd - winLength) do
        if rm.getMin() > threshold then
            return i
        end
        rm.insert(data[i + winLength])
        rm.delete(data[i])
    end
    return -1 // no such runs exist
end
```

So processing time depends upon the runtime of those operations of `RollingMinimum`.
But first, notice, that we are back to the original scheme of data acquisition, O(1) insert, and O(n) space.

We have some options for what kind of data structure backs `RollingMinimum`.

<table>
    <tr>
        <th></th>
        <th>Sorted Array</th>
        <th>Unsorted Array</th>
        <th>Min Heap</th>
        <th>Binary Search Tree</th>
    </tr><tr>
        <td><code>insert()</code></td>
        <td>O(n)</td>
        <td>O(1)</td>
        <td>O(log n)</td>
        <td>O(log n)</td>
    </tr><tr>
        <td><code>getMin()</code></td>
        <td>O(1)</td>
        <td>O(n)</td>
        <td>O(1)</td>
        <td>O(log n)</td>
    </tr><tr>
        <td><code>delete()</code></td>
        <td>O(n)</td>
        <td>O(n)</td>
        <td>O(log n)</td>
        <td>O(log n)</td>
    </tr>
</table>

It seems then that we want to choose between either a Min Heap or Binary Search Tree.
Both give us O(n log n) processing time, but we have a few tradeoffs to consider.

<table>
    <tr>
        <th>Min Heap</th>
        <th>Binary Search Tree</th>
    </tr><tr>
        <td>Faster getMin()</td>
        <td>Doesn't affect overall performance</td>
    </tr><tr>
        <td>Arbitrary delete not always supported</td>
        <td>All operations always supported</td>
    </tr><tr>
        <td>Better cache coherency</td>
        <td>Only need one for max and min</td>
    </tr>
</table>


## Conclusion

For these reasons, I believe the overall simplest without giving any performance is the Binary Search Tree backed Rolling Minumum.
This means the data structure used for collecting data will simply be a few arrrays.
While this is the same as naive, it is the best we can get for data collection on the embedded sensor, which is where every byte and cycle matter.
Moreover, we have still dramatically reduced the processing time over naive.
So, in all, I belive this is the best tradoff given the application.