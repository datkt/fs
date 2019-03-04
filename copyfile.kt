package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.EmptyCallback as Callback

import datkt.uv.uv_fs_copyfile
import datkt.uv.uv_fs_t

fun copyFile(src: String, dst: String, flags: Int = 0, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_copyfile(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    src,
    dst,
    flags.toInt(),
    staticCFunction(::oncopyfile))
}

private fun oncopyfile(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
