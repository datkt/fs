package datkt.fs

import kotlinx.cinterop.*
import datkt.uv.uv_fs_s
import datkt.uv.uv_strerror

fun createError(fs: uv_fs_s?): Error? {
  if (null == fs) {
    return Error("Unable to read state from 'uv_fs_s'.")
  }

  if (fs.result < 0L) {
    val message = uv_strerror(fs.result.toInt())?.toKString()
    return Error(message)
  }

  return null
}
