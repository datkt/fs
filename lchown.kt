package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.convert

import datkt.fs.EmptyCallback as Callback
import datkt.fs.uv.uv

import datkt.uv.uv_fs_lchown
import datkt.uv.uv_fs_t

fun lchown(path: String, uid: Int, gid: Int, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_lchown(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    uid.convert(),
    gid.convert(),
    staticCFunction(::onlchown))
}

private fun onlchown(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
