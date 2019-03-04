package datkt.fs

import datkt.fs.WriteFileCallback as Callback

data class WriteFileOptions(
  val mode: Long = DEFAULT_OPEN_MODE,
  val flags: String = "w"
)

fun writeFile(path: String, buffer: ByteArray, callback: Callback) {
  return writeFile(path, buffer, WriteFileOptions(), callback)
}

fun writeFile(
  path: String,
  buffer: ByteArray,
  opts: WriteFileOptions,
  callback: Callback
) {
  var fd: Int = -1

  fun onopen(err: Error?, result: Int?) {
    if (null != err) {
      callback(err)
    } else if (null != result && result > -1) {
      fd = result
      write(fd, buffer) { err -> callback(err) }
    }
  }

  open(path, opts?.flags, opts?.mode, ::onopen)
}
