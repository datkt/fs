package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.EmptyCallback as Callback
import datkt.fs.uv.uv

import datkt.uv.uv_fs_link
import datkt.uv.uv_fs_t

fun link(source: String, path: String, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_link(
    datkt.fs.loop.default,
    uv.toCValuesRef(req),
    source,
    path,
    staticCFunction(::onlink))
}

private fun onlink(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
