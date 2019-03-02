package datkt.fs.util

import datkt.fs.O_WRONLY
import datkt.fs.O_RDONLY
import datkt.fs.O_SYNC
import datkt.fs.O_RDWR
import datkt.fs.O_CREAT
import datkt.fs.O_APPEND
import datkt.fs.O_EXCL
import datkt.fs.O_TRUNC

private fun int(long: Long) = long.toInt()

// ported from:
// https://github.com/nodejs/node/blob/51d20b6a8f695947a700e7fed8f0e5b33b79cefb/lib/internal/fs/utils.js#L325
fun stringToFlags(flags: String): Int {
  when (flags.toLowerCase()) {
    "r" -> return int(O_RDONLY)
    "r+" -> return int(O_RDWR)
    "rs", "sr" -> return int(O_RDONLY) or int(O_SYNC)
    "sr+", "rs+" -> return int(O_RDWR) or int(O_SYNC)

    "w" -> return int(O_TRUNC) or int(O_CREAT) or int(O_WRONLY)
    "w+" -> return int(O_TRUNC) or int(O_CREAT) or int(O_RDWR)
    "wx", "xw" ->
      return int(O_TRUNC) or int(O_CREAT) or int(O_WRONLY) or int(O_EXCL)
    "wx+", "xw+" ->
      return int(O_TRUNC) or int(O_CREAT) or int(O_RDWR) or int(O_EXCL)

    "a" -> return int(O_APPEND) or int(O_CREAT) or int(O_WRONLY)
    "a+" -> return int(O_APPEND) or int(O_CREAT) or int(O_RDWR)
    "ax", "xa" ->
      return int(O_APPEND) or int(O_CREAT) or int(O_WRONLY) or int(O_EXCL)
    "as", "sa" ->
      return int(O_APPEND) or int(O_CREAT) or int(O_WRONLY) or int(O_SYNC)
    "ax+", "xa+" ->
      return int(O_APPEND) or int(O_CREAT) or int(O_RDWR) or int(O_EXCL)
    "as+", "sa+" ->
      return int(O_APPEND) or int(O_CREAT) or int(O_RDWR) or int(O_SYNC)

    else -> return 0
  }
}
