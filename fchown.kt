package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.convert

import datkt.fs.EmptyCallback as Callback

import datkt.uv.uv_fs_fchown
import datkt.uv.uv_fs_t

fun fchown(fd: Int, uid: Int, gid: Int, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_fchown(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    fd,
    uid.convert(),
    gid.convert(),
    staticCFunction(::onfchown))
}

private fun onfchown(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
