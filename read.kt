package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr

import datkt.fs.ReadFileCallback as Callback

import datkt.uv.uv_fs_read
import datkt.uv.uv_buf_t
import datkt.uv.uv_fs_t

fun read(
  fd: Int,
  buffer: ByteArray,
  offset: Int,
  length: Int,
  position: Int,
  callback: Callback
) {
  val cb = staticCFunction(::onread)
  val req = uv.init<Callback>(callback, buffer)
  val ref = uv.toCValuesRef<uv_fs_t>(req)
  val len = length.toLong().toULong()
  val pos = position.toLong()
  memScoped {
    val buf = alloc<uv_buf_t>()
    buffer.usePinned { pinned ->
      buf.base = pinned.addressOf(offset)
      buf.len = len

      uv_fs_read(loop.default, ref, fd, buf.ptr, 1, pos, cb)
    }
  }
}

private fun onread(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.cleanup()
    if (null != err) {
      uv.done(err, null)
    } else if (null != uv.data.buffer) {
      uv.done(null, uv.data.buffer as ByteArray)
    } else {
      uv.done(Error("Failed to acquire read buffer"), null)
    }
  }
}
