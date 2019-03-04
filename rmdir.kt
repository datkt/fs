package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.EmptyCallback as Callback

import datkt.uv.uv_fs_rmdir
import datkt.uv.uv_fs_t

fun rmdir(path: String, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_rmdir(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    staticCFunction(::onrmdir))
}

private fun onrmdir(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
