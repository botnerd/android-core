[ ![Download](https://api.bintray.com/packages/botnerd/android-core/com.botnerd.core/images/download.svg) ](https://bintray.com/botnerd/android-core/com.botnerd.core/_latestVersion)

# Android Core Libraries

This library provides a set of common utilities and classes. The library is broken into four modules:

## Util Module

A set of utility classes with common methods for comparison, date/time, IO, and string manipulation.

Gradle

```groovy
implementation 'com.botnerd.core:util:1.0.0'
```

## Network Module

A set of classes use with Retrofit for networking. This include some interceptors for added the user agent or managing cookies, http constants, and utilities for connectivity and device information

Gradle

```groovy
implementation 'com.botnerd.core:network:1.0.0'
```

## UI Module

The largest part of this library is the UI module. It has many classes that simplify UI code via data binding. In particular, the `MultiTypeDataBoundAdapter` provides a single adapter that can be used for all `RecyclerView`s within an app. For details on this adapter and several of the bindings, see [Top 5 Reasons to Use Android Data Binding](https://www.captechconsulting.com/blogs/top-5-reason-to-use-android-data-binding).

This module also contains code for simplifying Chrome Custom Tab integration, font management, and a few other goodies.

Gradle

```groovy
implementation 'com.botnerd.core:ui:1.0.0'
```

## Barcode Module

This provides some data binding adapters for adding QR codes via the zxing library to a layout. It is in it's own library because the zxing library adds the Camera permission to the AndroidManifest.xml even though it is not used

Gradle

```groovy
implementation 'com.botnerd.core:barcode:1.0.0'
```



