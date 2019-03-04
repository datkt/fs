package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.CVariable
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import kotlinx.cinterop.ByteVar

import datkt.fs.BufferCallback as Callback

import datkt.uv.uv_fs_realpath
import datkt.uv.uv_fs_t

fun realpath(path: String, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_realpath(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    staticCFunction(::onrealpath))
}

private fun onrealpath(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    val ptr = uv.fs?.ptr
    if (null != err) {
      uv.done(err, null)
    } else if (null != ptr) {
      val bytes: CPointer<ByteVar> = ptr as CPointer<ByteVar>
      val string = bytes.toKString()
      val byteArray = string.toUtf8()
      uv.done(err, byteArray)
    }

    uv.cleanup()
  }
}
