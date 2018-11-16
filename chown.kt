package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.convert

import datkt.fs.EmptyCallback as Callback
import datkt.fs.uv.uv

import datkt.uv.uv_fs_chown
import datkt.uv.uv_fs_t

fun chown(path: String, uid: Int, gid: Int, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_chown(
    datkt.fs.loop.default,
    uv.toCValuesRef(req),
    path,
    uid.convert(),
    gid.convert(),
    staticCFunction(::onchown))
}

private fun onchown(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
