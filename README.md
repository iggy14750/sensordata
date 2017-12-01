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

