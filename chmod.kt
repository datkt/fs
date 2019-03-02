package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.uv.uv_fs_chmod
import datkt.uv.uv_fs_t

import datkt.fs.EmptyCallback as Callback

fun chmod(path: String, mode: Long, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_chmod(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    mode.toInt(),
    staticCFunction(::onchmod))
}

private fun onchmod(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
