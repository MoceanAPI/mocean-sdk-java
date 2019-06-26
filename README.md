MoceanAPI Client Library for Java 
============================
[![Maven Release](https://img.shields.io/maven-central/v/com.github.moceanapi/moceanapisdk.svg)](https://mvnrepository.com/artifact/com.github.moceanapi/moceanapisdk)
[![Build Status](https://img.shields.io/travis/com/MoceanAPI/mocean-sdk-java.svg)](https://travis-ci.com/MoceanAPI/mocean-sdk-java)
[![codecov](https://img.shields.io/codecov/c/github/MoceanAPI/mocean-sdk-java.svg)](https://codecov.io/gh/MoceanAPI/mocean-sdk-java)
[![codacy](https://img.shields.io/codacy/grade/c51a73cd91c74edfa1eca25d02497aa9.svg)](https://app.codacy.com/project/MoceanAPI/mocean-sdk-java/dashboard)
[![license](https://img.shields.io/npm/l/mocean-sdk.svg)](https://www.npmjs.com/package/mocean-sdk)

This is the Java client library for use Mocean's API. To use this, you'll need a Mocean account. Sign up [for free at 
moceanapi.com][signup].

 * [Installation](#installation)
 * [Usage](#usage)
 * [Example](#example)

## Installation

To use the client library you'll need to have [created a Mocean account][signup]. 

### Maven

Add the following to the correct place in your project's POM file:
```xml
<dependency>
      <groupId>com.github.moceanapi</groupId>
      <artifactId>moceanapisdk</artifactId>
      <version>1.x.x</version>
</dependency>
```

### Gradle

Add the following to `build.gradle`

```groovy
repositories {
    mavenCentral()
}
```

For Gradle 3.4 or Higher:

```groovy
dependencies {
    implementation 'com.github.moceanapi:moceanapisdk:1.x.x'
}
```

For older versions:

```groovy
dependencies {
    compile 'com.github.moceanapi:moceanapisdk:1.x.x'
}
```

## Usage

Create a client with your API key and secret:

```java
import com.mocean.system.Mocean;
import com.mocean.system.auth.Basic;

Basic credential = new Basic("API_KEY_HERE","API_SECRET_HERE");
Mocean mocean = new Mocean(credential);
```

## Example

To use [Mocean's SMS API][doc_sms] to send an SMS message, call the `mocean.sms.send()` method.

The API can be called directly, using a simple array of parameters, the keys match the [parameters of the API][doc_sms].

```java
SmsResponse res = mocean.sms()
            .setFrom("MOCEAN")
            .setTo("60123456789")
            .setText("Hello World")
            .send();
            
System.out.println(res);
```

### Responses

For your convenient, the API response has been parse to specified object.

```java
System.out.println(res);         //show full response string
System.out.println(res.status);  //show response status, "0" in this case
```

## Documentation

Kindly visit [MoceanApi Docs][doc_main] for more usage

License
-------

This library is released under the [MIT License][license]

[signup]: https://dashboard.moceanapi.com/register?medium=github&campaign=sdk-java
[doc_main]: https://moceanapi.com/docs/?java
[doc_sms]: https://moceanapi.com/docs/?java#send-sms
[license]: LICENSE.txt
