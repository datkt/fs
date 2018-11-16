package datkt.fs

import kotlinx.cinterop.toKString
import datkt.uv.uv_strerror
import datkt.uv.uv_fs_s

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

fun createError(errno: Int): Error? {
  if (errno < 0) {
    val message = uv_strerror(errno)?.toKString()
    return Error(message)
  }

  return null
}

fun createError(errno: Long): Error? {
  return createError(errno.toInt())
}
