package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.EmptyCallback as Callback

import datkt.uv.uv_fs_ftruncate
import datkt.uv.uv_fs_t

fun ftruncate(fd: Int, length: Long = 0, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_ftruncate(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    fd,
    length,
    staticCFunction(::onftruncate))
}

private fun onftruncate(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
