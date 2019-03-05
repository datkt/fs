package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.EmptyCallback as Callback

import datkt.uv.uv_fs_futime
import datkt.uv.uv_fs_t

fun futime(fd: Int, atime: Double, mtime: Double, callback: Callback) {
  return futimes(fd, atime, mtime, callback)
}

fun futimes(fd: Int, atime: Double, mtime: Double, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_futime(
    loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    fd,
    atime,
    mtime,
    staticCFunction(::onfutime))
}

private fun onfutime(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
