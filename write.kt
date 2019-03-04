package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr

import datkt.fs.WriteFileCallback as Callback

import datkt.uv.uv_fs_write
import datkt.uv.uv_buf_t
import datkt.uv.uv_fs_t

fun write(
  fd: Int,
  buffer: ByteArray,
  offset: Int = 0,
  length: Int = buffer.size,
  position: Int = 0,
  callback: Callback
) {
  val cb = staticCFunction(::onwrite)
  val req = uv.init<Callback>(callback, buffer)
  val ref = uv.toCValuesRef<uv_fs_t>(req)
  val len = length.toLong().toULong()
  val pos = position.toLong()
  memScoped {
    val buf = alloc<uv_buf_t>()
    buffer.usePinned { pinned ->
      buf.base = pinned.addressOf(offset)
      buf.len = len

      uv_fs_write(loop.default, ref, fd, buf.ptr, 1, pos, cb)
    }
  }
}

private fun onwrite(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.cleanup()
    uv.done(err)
  }
}
