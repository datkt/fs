package datkt.fs

import datkt.fs.ReadFileCallback as Callback

data class ReadFileOptions(
  val mode: Long = DEFAULT_OPEN_MODE,
  val flags: String = "r"
)

fun readFile(path: String, callback: Callback) {
  return readFile(path, ReadFileOptions(), callback)
}

fun readFile(path: String, opts: ReadFileOptions, callback: Callback) {
  var buffer: ByteArray
  var fd: Int = -1

  fun onstat(err: Error?, stats: Stats?) {
    if (null != err) {
      callback(err, null)
    } else if (null != stats && fd > -1) {
      val size = stats.size.toInt()
      buffer = ByteArray(size)
      read(fd, buffer, 0, size, 0, callback)
    }
  }

  fun onopen(err: Error?, result: Int?) {
    if (null != err) {
      callback(err, null)
    } else if (null != result && result > -1) {
      fd = result
      fstat(result, ::onstat)
    }
  }

  open(path, opts?.flags, opts?.mode, ::onopen)
}
