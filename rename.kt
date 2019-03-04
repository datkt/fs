package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.EmptyCallback as Callback

import datkt.uv.uv_fs_rename
import datkt.uv.uv_fs_t

fun rename(src: String, dst: String, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_rename(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    src,
    dst,
    staticCFunction(::onrename))
}

private fun onrename(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
