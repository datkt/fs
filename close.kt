package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.EmptyCallback as Callback

import datkt.uv.uv_fs_close
import datkt.uv.uv_fs_t

fun close(fd: Int, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_close(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    fd,
    staticCFunction(::onclose))
}

private fun onclose(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
