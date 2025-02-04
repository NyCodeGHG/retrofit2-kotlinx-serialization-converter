Change Log
==========

Version 0.11.0 *(2022-01-11*
----------------------------
* Kotlin updated to 1.6.10
* kotlinx-serialization updated to 1.3.2
* Dokka updated to 1.6.10

Version 0.10.0 *(2021-11-18)*
----------------------------
* Kotlin updated to 1.6.0
* kotlinx-serialization updated to 1.3.1

Version 0.9.4 *(2021-09-20)*
----------------------------
* Kotlin updated to 1.5.31
* kotlinx-serialization test dependencies updated to 1.2.2
* Dokka updated to 1.5.30

Version 0.9.3 *(2021-08-25)*
----------------------------

* Kotlin updated to 1.5.30
* kotlinx-serialization-core updated to 1.2.2

Version 0.9.2 *(2021-07-08)*
----------------------------

* Dokka updated to 1.5.0

Version 0.9.1 *(2021-06-24)*
----------------------------

* Kotlin updated to 1.5.20
* Updated Code Style

Version 0.9.0 *(2021-06-10)*
----------------------------

* Kotlin updated to 1.5.10
* Serialization updated to 1.2.1
* Migrated Tests to JUnit Jupiter from JUnit 4
* Migrated to Gradle Kotlin DSL

Version 0.8.0 *(2020-10-09)*
----------------------------

* Kotlin updated to 1.4.10
* Serialization updated to 1.0.0

  Note that the APIs used by this converter are marked as experimental. As such, the experimental annotation has been
  propagated to the public API such that you will need to opt-in to using it or receive a warning. It is also why this
  library is not a 1.0.0 since those APIs may have backwards-incompatible changes in the future requiring an update.

Version 0.7.0 *(2020-08-31)*
----------------------------

* New: Add support for contextual serializers which are registered with the supplied format. No new API or action is
  required for this to work–it's all implementation detail. Since it's a potentially incompatible behavior change, the
  version was bumped.

Version 0.6.0 *(2020-08-19)*
----------------------------

* Kotlin updated to 1.4.0
* Serialization updated to 1.0.0-rc

  Note that the APIs used by this converter are marked as experimental. As such, the experimental annotation has been
  propagated to the public API such that you will need to opt-in to using it or receive a warning.

Version 0.5.0 *(2020-03-06)*
----------------------------

* Kotlin updated to 1.3.70
* Serialization updated to 0.20.0
* Retrofit updated to 2.6.4

Version 0.4.0 *(2019-04-12)*
----------------------------

* Serialization updated to 0.11.0

Version 0.3.0 *(2019-02-25)*
----------------------------

* New: `asConverterFactory()` extension API on `StringFormat` and `BinaryFormat`. This deprecates the
  old `serializationConverterFactory` top-level function.
* Kotlin updated to 1.3.20
* Serialization updated to 0.10.0

Version 0.2.0 *(2018-10-29)*
----------------------------

* New: `stringBased` and `bytesBased` have been removed in favor of a single
  `serializationConverterFactory` function with overloads for `StringFormat` and `BinaryFormat`
  instances (like `JSON` and `ProtoBuf`). For those configuring Retrofit from Java the API is now
  `KotlinSerializationConverterFactory.create`.
* Kotlin updated to 1.3.0
* Serialization updated to 0.9.0

Version 0.1.0 *(2018-09-22)*
----------------------------

Update to Kotlin 1.3.0-rc-57 and serialization 0.8.0-rc13.


Version 0.0.1 *(2018-01-15)*
----------------------------

Initial release.
