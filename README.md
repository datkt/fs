datkt.fs
========

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

* [access(path, mode, callback)](#access)
* [chmod(path, mode, callback)](#chmod)
* [chown(path, uid, gid, callback)](#chown)
* [link(source, path, callback)](#link)
* [symlink(source, path, type, callback)](#symlink)
* [stat(path, callback)](#stat)

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

### `chmod(path: String, mode: Long, callback: Callback)`
<a name="chmod" />

Change user permissions of a file specified at `path` and `mode` calling
`callback` with an `Error` if one occurs.

```kotlin
// make read, write, execute permissions for everyone
val mode = (
  S_IRUSR or S_IWUSR or S_IXUSR or
  S_IRGRP or S_IWGRP or S_IXGRP or
  S_IROTH or S_IWOTH or S_IXOTH
)

// modify program permissions
chmod("/home/program", mode) { err ->
  if (null != err) {
    println("Something went wrong changing program permissions: ${err.message}")
  }
}
```

### `chown(path: String, uid: Long, gid: Long, callback: Callback)`
<a name="chown" />

Change user and group ownership of a file specified at `path` for user
id `uid`, and group id `gid` calling `callback` with an `Error`, if one
occurs.

```kotlin
chown("/home/file", 1000, 10) { err ->
  if (null != err) {
    println("Something went wrong changing file ownership: ${err.message}")
  }
}
```

### `link(source: String, path: String, callback: Callback)`
<a name="link" />

Create a new hard link at `path` for a file specified at `source`
calling `callback` with an `Error`, if one occurs.

```kotlin
link("/home/file", "/home/link") { err ->
  if (null != err) {
    println("Something went creating hard link: ${err.message}")
  }
}
```

### `symlink(source: String, path: String, type: Int = 0, callback: Callback)`
<a name="symlink" />

```kotlin
symlink("/home/file", "/home/symlink") { err ->
  if (null != err) {
    println("Something went creating soft link: ${err.message}")
  }
}
```

### `data class Stats(...)`

Represents a data class containing stat properties of a file.

```kotlin
data class Stats(
  val dev: Long = 0,
  val mode: Long = 0,
  val nlink: Long = 0,
  val uid: Long = 0,
  val gid: Long = 0,
  val rdev: Long = 0,
  val ino: Long = 0,
  val size: Long = 0,
  val blksize: Long = 0,
  val blocks: Long = 0,
  val atime: Long = 0,
  val mtime: Long = 0,
  val ctime: Long = 0,
  val birthtime: Long = 0
)
```

### `stat(path: String, callback: Callback)`

Query the stats of a file specified at `path`, if it exists, calling
`callback` with an `Error`, if one occurs, otherwise an instance of
`Stats` as the second argument.

```kotlin
stat("/home/file") { err, stats ->
  if (null != err) {
    println(err.message)
  } else {
    println(stats)
  }
}
```

## License

MIT
