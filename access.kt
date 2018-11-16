package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.EmptyCallback as Callback
import datkt.fs.uv.uv

import datkt.uv.uv_fs_access
import datkt.uv.uv_fs_t

fun access(path: String, callback: Callback) {
  return access(path, F_OK, callback)
}

fun access(path: String, mode: Long = F_OK, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_access(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    mode.toInt(),
    staticCFunction(::onaccess))
}

private fun onaccess(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
