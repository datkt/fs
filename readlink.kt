package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toKString
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ByteVar

import datkt.fs.BufferCallback as Callback

import datkt.uv.uv_fs_readlink
import datkt.uv.uv_fs_t

fun readlink(path: String, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_readlink(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    staticCFunction(::onreadlink))
}

private fun onreadlink(req: CPointer<uv_fs_t>?) {
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
