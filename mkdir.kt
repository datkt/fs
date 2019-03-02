package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.EmptyCallback as Callback

import datkt.uv.uv_fs_mkdir
import datkt.uv.uv_fs_t

val DEFAULT_MKDIR_MODE = (S_IRWXU or S_IRWXG or S_IRWXO)

fun mkdir(path: String, callback: Callback) {
  return mkdir(path, DEFAULT_MKDIR_MODE, callback)
}

fun mkdir(path: String, mode: Long = DEFAULT_MKDIR_MODE, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_mkdir(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    mode.toInt(),
    staticCFunction(::onmkdir))
}

private fun onmkdir(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
