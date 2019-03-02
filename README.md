datkt.fs
========

Asynchronous file system operations for Kotlin built on libuv and based
on the [file system API for Node.js](https://nodejs.org/api/fs.html).

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
  mkdir("./directory") { err ->
    if (null != err) {
      println("Failed to make directory: ${err.message}")
    } else {
      writeFile("./directory/file", "hello world") { err ->
        if (null != err) {
          println("Failed to write file: ${err.message}")
        }
      }
    }
  }

  // kick off file system event loop
  datkt.fs.loop.run()
}
```

## API

* [object loop](#loop)
* [fun access(path, mode, callback)](#access)
  * [Access Modes](#access-modes)
* [fun chmod(path, mode, callback)](#chmod)
  * [File Modes](#chmod-modes)
* [fun chown(path, uid, gid, callback)](#chown)
* [fun lchown(path, uid, gid, callback)](#lchown)
* [fun link(source, path, callback)](#link)
* [fun symlink(source, path, type, callback)](#symlink)
* [fun stat(path, callback)](#stat)
* [fun lstat(path, callback)](#lstat)
* [fun mkdir(path, mode, callback)](#mkdir)
* [fun readdir(path, callback)](#readdir)
* [fun open(path, flags, mode, callback)](#open)
  * [File System Flags](#file-system-flags)
* [class Stats(...)](#stats)
  * [Stats.hasMode(mode)](#stats-hasMode)
  * [Stats.isCharacterDevice()](#stats-isCharacterDevice)
  * [Stats.isSymbolicLink()](#stats-isSymbolicLink)
  * [Stats.isBlockDevice()](#stats-isBlockDevice)
  * [Stats.isDirectory()](#stats-isDirectory)
  * [Stats.isSocket()](#stats-isSocket)
  * [Stats.isFIFO()](#stats-isFIFO)
  * [Stats.isFile()](#stats-isFile)

### `object loop { ... }`
<a name="loop" />

An `object` that represents an interface into the
[uv](https://github.com/datkt/uv) event loop used internally for
asynchronous work done by the functions exposed in this package.
`datkt.fs.loop.run()` will invoke any queued work for the event loop.

```kotlin
object loop {
  fun run(): Int
  fun stop()
}
```

#### `loop.run()`

Invoke the uv event loop. Calls
[uv_run](http://docs.libuv.org/en/v1.x/loop.html#c.uv_run) internally.

#### `loop.stop()`

Stop the uv event loop. Calls
[uv_stop](http://docs.libuv.org/en/v1.x/loop.html#c.uv_stop) internally.

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

#### Access Modes
<a name="access-modes" />

The possible values for the `mode` argument to test file access
permissions can be seen below.

* `F_OK` - Test for the existence of a file
* `R_OK` - Test for read permissions on a file
* `W_OK` - Test for write permissions on a file
* `X_OK` - Test for execution permissions on a file

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

#### File Modes
<a name="chmod-modes" />

The possible values for the `mode` argument can be seen below. They can
be grouped into a bit mask by the logical OR (`or`) operator.

* `S_IRWXU` - Read, write, and execute by owner (`700`)
* `S_IRUSR` - Read by owner (`400`)
* `S_IWUSR` - Write by owner (`200`)
* `S_IXUSR` - Execute by owner (`100`)
* `S_IRWXG` - Read, write, and Execute by group (`070`)
* `S_IRGRP` - Read by group (`040`)
* `S_IWGRP` - Write by group (`020`)
* `S_IXGRP` - Execute by group (`010`)
* `S_IRWXO` - Read, write, and execute by others (`007`)
* `S_IROTH` - Read by others (`004`)
* `S_IWOTH` - Write by others (`002`)
* `S_IXOTH` - Execute by others (`001`)

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

### `lchown(path: String, uid: Long, gid: Long, callback: Callback)`
<a name="lchown" />

Change user and group ownership of a symbolic link specified at `path` for
user id `uid`, and group id `gid` calling `callback` with an `Error`, if one
occurs.

```kotlin
symlink("/home/file", "/home/link") { err ->
  lchown("/home/link", 1000, 10) { err ->
    if (null != err) {
      println("Something went wrong changing link ownership: ${err.message}")
    }
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

### `mkdir(path: String, mode: Long = DEFAULT_MKDIR_MODE, callback: Callback)`
<a name="mkdir" />

Make a directory specified at `path` with with `mode` calling `calling`
with an `Error`, if one occurs. The `mode` defaults to
`DEFAULT_MKDIR_MODE` (see below) if not specified.

```kotlin
mkdir("/path/to/directory") { err ->
  if (null != err) {
    println(err.message)
  }
}
```

#### Modes
<a name="mkdir-modes" />

See [File Modes](#chmod-modes) for a list of all file modes.
The default mode is defined by the `DEFAULT_MKDIR_MODE` constant.

```kotlin
val DEFAULT_MKDIR_MODE = (S_IRWXU or S_IRWXG or S_IRWXO)
```

### `readdir(path: String, callback: Callback)`
<a name="readdir" />

Read a directory specified at `path` for files entries calling
`callback` with an `Error`, if one occurs, otherwise an `Array<String>`
with file names.

```kotlin
readdir("/home") { err, entries, ->
  for (entry in entries) {
    println(entry)
  }
}
```

### `open(path: String, flags: String = "r", mode: Long = DEFAULT_OPEN_MODE, callback: Callback)
<a name="open" />

Open a file specified at `path` with optional flags and mode calling
`callback` with an `Error`, if one occurs, otherwise with a valid file
descriptor.

```kotlin
open("/path/to/file") { err, fd ->
  if (null != err) {
    println(err.message)
  } else {
    // do something with fd
  }
}
```

By default, all files will be opened in `DEFAULT_OPEN_MODE` where:

```kotlin
// equivalent to (0666)
val DEFAULT_OPEN_MODE = (
  S_IRUSR or S_IWUSR or
  S_IRGRP or S_IWGRP or
  S_IROTH or S_IWOTH
)
```

### File System Flags
<a name="file-system-flags" />

* `"r"` - Open file for reading. An error occurs if the file does not exist.
* `"r+"` - Open file for reading and writing. An error occurs if the file does not exist.
* `"w"` - Open file for writing. The file is created if it does not
  exist, otherwise it is truncated.
* `"w+"` - Open file for reading and writing. The file is created if it does not
  exist, otherwise it is truncated.
* `"a"` - Open file for appending. Writes start at the end of the file.
  The file is created if it does not exist, otherwise it is truncated.

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

## License

MIT
