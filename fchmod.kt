package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.uv.uv_fs_fchmod
import datkt.uv.uv_fs_t

import datkt.fs.EmptyCallback as Callback

fun fchmod(fd: Int, mode: Long, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_fchmod(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    fd,
    mode.toInt(),
    staticCFunction(::onfchmod))
}

private fun onfchmod(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
