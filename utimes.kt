package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.EmptyCallback as Callback

import datkt.uv.uv_fs_utime
import datkt.uv.uv_fs_t

fun utime(path: String, atime: Double, mtime: Double, callback: Callback) {
  return utimes(path, atime, mtime, callback)
}

fun utimes(path: String, atime: Double, mtime: Double, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_utime(
    loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    atime,
    mtime,
    staticCFunction(::onutime))
}

private fun onutime(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
