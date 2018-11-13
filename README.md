datkt.fs
=======

Async file system for Kotlin built on libuv

## Installation

The `datkt.fs` package an be installed with NPM.

```sh
$ npm install @datkt/fs
```

## Prerequisites

* [Kotlin/Native](https://github.com/JetBrains/kotlin-native) and the
  `konanc` command line program.

## Usage

```sh
## Compile a program in 'main.kt' and link fs.klib found in `node_modules/`
$ konanc main.kt $(konanc-config -clr node_modules/@datkt/fs)
```

where `main.kt` might be

```kotlin
import datkt.fs.*

fun main(args: Array<String>) {
  // @TODO
}
```

## API

### `access(path: String, mode: Long = F_OK, callback: (Error?) -> Unit?)`

Test user permissions for a file specified at `path` and `mode` calling
`callback` with an `Error` if one errors.


```kotlin
datkt.fs.access("/home") { err ->
  if (null != err) {
    println("Something went wrong checking access to /home")
  }
}
```

## License

MIT
