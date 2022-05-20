# CollatzFilter
* Apply the collatz procedure to image pixels.

Each pixel in an image is an integer between 0 and 255. Divide even-valued pixels (either the rounded mean or each channel) by two, and Multiply odd-valued pixels by 3 plus one.
```java
(pxmean % 2 == 0) ? pxmean/2 : 3 * pxmean + 1;

// OR

(rpx % 2 == 0) ? rpx/2 : 3 * rpx + 1;
(gpx % 2 == 0) ? gpx/2 : 3 * gpx + 1;
(bpx % 2 == 0) ? bpx/2 : 3 * bpx + 1;
```
