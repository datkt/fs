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
* [lstat(path, callback)](#lstat)
* [Stats(...)](#stats)
  * [Stats.hasMode(mode)](#stats-hasMode)
  * [Stats.isCharacterDevice()](#stats-isCharacterDevice)
  * [Stats.isSymbolicLink()](#stats-isSymbolicLink)
  * [Stats.isBlockDevice()](#stats-isBlockDevice)
  * [Stats.isDirectory()](#stats-isDirectory)
  * [Stats.isSocket()](#stats-isSocket)
  * [Stats.isFIFO()](#stats-isFIFO)
  * [Stats.isFile()](#stats-isFile)

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

### `class Stats(...)`

<a name="stats" />

Represents a data class containing stat properties of a file.

```kotlin
class Stats(
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

#### `Stats.hasMode(mode: Long): Boolean`
<a name="stats-hasMode" />

Check if a given mode is present in the stat's mode bit field.

```kotlin
if (stat.hasMode(S_IFREG)) {
  // stat points to a regular file
}
```

#### `Stats.isCharacterDevice(): Boolean`
<a name="stats-isCharacterDevice" />

Check if the stat points to a [character
device](https://en.wikipedia.org/wiki/Device_file#Character_devices).
Equivalent to `stat.hasMode(S_IFCHR)`.

```kotlin
if (stat.isCharacterDevice()) {
  // stat points to a character device
}
```

#### `Stats.isSymbolicLink(): Boolean`
<a name="stats-isSymbolicLink" />

Check if the stat points to a [symbolic link](
https://en.wikipedia.org/wiki/Symbolic_link).
Equivalent to `stat.hasMode(S_IFLNK)`.

```kotlin
if (stat.isSymbolicLink()) {
  // stat points to a symbolic link
}
```

#### `Stats.isBlockDevice(): Boolean`
<a name="stats-isBlockDevice" />

Check if the stat points to a [block
device](https://en.wikipedia.org/wiki/Device_file#Block_devices).
Equivalent to `stat.hasMode(S_IFBLK)`.

```kotlin
if (stat.isBlockDevice()) {
  // stat points to a block device
}
```

#### `Stats.isDirectory(): Boolean`
<a name="stats-isDirectory" />

Check if the stat points to a directory. Equivalent to `stat.hasMode(S_IFDIR)`.

```kotlin
if (stat.isDirectory()) {
  // stat points to a directory
}
```

#### `Stats.isSocket(): Boolean`
<a name="stats-isSocket" />

Check if the stat points to a socket. Equivalent to `stat.hasMode(S_IFSOCK)`.

```kotlin
if (stat.isSocket()) {
  // stat points to a socket
}
```

#### `Stats.isFIFO(): Boolean`
<a name="stats-isFIFO" />

Check if the stat points to a [FIFO](https://en.wikipedia.org/wiki/Named_pipe).
Equivalent to `stat.hasMode(S_IFIFO)`.

```kotlin
if (stat.isFIFO()) {
  // stat points to a FIFO (named pipe)
}
```

#### `Stats.isFile(): Boolean`
<a name="stats-isFile" />

Check if the stat points to a regular file.
Equivalent to `stat.hasMode(S_IFREG)`.

```kotlin
if (stat.isFile()) {
  // stat points to a file
}
```

### `stat(path: String, callback: Callback)`
<a name="stat" />

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

### `lstat(path: String, callback: Callback)`
<a name="lstat" />

Query the stats of a file specified at `path`, if it is a link or file, calling
`callback` with an `Error`, if one occurs, otherwise an instance of
`Stats` as the second argument.

```kotlin
lstat("/home/file") { err, stats ->
  if (null != err) {
    println(err.message)
  } else {
    println(stats)
  }
}
```

## License

MIT
