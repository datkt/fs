package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.OpenCallback as Callback
import datkt.fs.util.stringToFlags

import datkt.uv.uv_fs_open
import datkt.uv.uv_fs_t

val DEFAULT_OPEN_MODE = (
  S_IRUSR or S_IWUSR or
  S_IRGRP or S_IWGRP or
  S_IROTH or S_IWOTH
)

fun open(
  path: String,
  flags: String = "r",
  mode: Long = DEFAULT_OPEN_MODE,
  callback: Callback
) {
  val req = uv.init<Callback>(callback)
  uv_fs_open(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    stringToFlags(flags),
    mode.toInt(),
    staticCFunction(::onopen))
}

private fun onopen(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    val result = uv.fs?.result?.toInt()

    if (null != result && result >= 0) {
      uv.done(err, result)
    } else {
      uv.done(err, null)
    }

    uv.cleanup()
  }
}
