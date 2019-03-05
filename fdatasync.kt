package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.uv.uv_fs_fdatasync
import datkt.uv.uv_fs_t

import datkt.fs.EmptyCallback as Callback

fun fdatasync(fd: Int, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_fdatasync(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    fd,
    staticCFunction(::onfdatasync))
}

private fun onfdatasync(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
