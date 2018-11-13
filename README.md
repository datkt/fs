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

* [access(path, mode, callback)][access]
* [chmod(path, mode, callback)][chmod]

### `access(path: String, mode: Long = F_OK, callback: (Error?) -> Unit?)`
<a name="access" />

Test user permissions for a file specified at `path` and `mode` calling
`callback` with an `Error` if one errors.

```kotlin
access("/home") { err ->
  if (null != err) {
    println("Something went wrong checking access to /home")
  }
}
```

### `chmod(path: String, mode: Long, callback: (Error?) -> Any?)`
<a name="chmod" />

Change user permissions of a file specified at `path` and `mode` calling
`callback` with an `Error` if one occurs.

```kotlin
val mode = (
  S_IRUSR or S_IWUSR or S_IXUSR or
  S_IRGRP or S_IWGRP or S_IXGRP or
  S_IROTH or S_IWOTH or S_IXOTH
)

chmod("/home/program", mode) { err ->
  if (null != err) {
    println("Something changing program permissions: ${err.message}")
  }
}
```

## License

MIT
