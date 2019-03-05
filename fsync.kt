package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.uv.uv_fs_fsync
import datkt.uv.uv_fs_t

import datkt.fs.EmptyCallback as Callback

fun fsync(fd: Int, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_fsync(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    fd,
    staticCFunction(::onfsync))
}

private fun onfsync(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
