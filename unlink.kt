package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.EmptyCallback as Callback

import datkt.uv.uv_fs_unlink
import datkt.uv.uv_fs_t

fun unlink(path: String, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_unlink(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    staticCFunction(::onunlink))
}

private fun onunlink(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
