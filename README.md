# Problem Statement

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

# Approach

We see here that there are 3 critical figures to optimize.
These will be used to compare some alternative implemenations.

1. Insert time. This will be done on a small embedded device, with minimal processing power.
    Every additional microsecond it takes to add a new element to our data structure means that we can take few readings in the same amount of time, and therefore we are left with less precise data.
    Our goal is O(1) or O(log n).
2. Space. Again, because of the nature of the sensor, there are hard limits to the amount of available memory.
    Goal is O(n), maybe upto O(n<sup>2</sup>) if we can get a big impovement somewhere else.
3. Processing time.
    If we squint, we can see that the operations listed above are fundamentally similar:
    finding contiguous regions of numbers which meet some numerical bound, either searching forward or backward.
    For this reason, I will demonstrate an algorithm for the first without mentioning the others in this document.
    Although I wasn't told this, I believe that this operation will occur either on a server, or at least a more powerful user device, and that it will be called many fewer times than insert.
    Goal is to beat naive search (described later), which approaches O(n<sup>2</sup>).


# Naive

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
We have O(1) insert time, amortized if we're dealing with dynamic arrays like `std::vector`, and O(n) space.
This really is the benchmark against which we will measure other solutions.


# Partial Min Matrix

Perhaps if we knew the minimum element between indices `i` and `j`, then processing would be very quick.
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