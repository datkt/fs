package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKString
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ByteVar

import datkt.fs.BufferCallback as Callback

import datkt.uv.uv_fs_mkdtemp
import datkt.uv.uv_fs_t

fun mkdtemp(path: String, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_mkdtemp(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    staticCFunction(::onmkdtemp))
}

private fun onmkdtemp(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    val path = uv?.fs?.path

    if (null != err) {
      uv.done(err, null)
    } else if (null != path) {
      val bytes: CPointer<ByteVar> = path as CPointer<ByteVar>
      val string = bytes.toKString()
      val byteArray = string.toUtf8()
      uv.done(null, byteArray)
    } else {
      uv.done(Error("An unknown error occured."), null)
    }

    uv.cleanup()
  }
}
