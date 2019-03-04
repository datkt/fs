package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.fs.StatCallback as Callback

import datkt.uv.uv_fs_fstat
import datkt.uv.uv_fs_t

fun fstat(
  fd: Int,
  callback: Callback
) {
  val req = uv.init<Callback>(callback)
  uv_fs_fstat(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    fd,
    staticCFunction(::onfstat))
}

private fun onfstat(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, (fs, done, cleanup) ->
    val stats = if (null != err) null else fs?.statbuf?.toStats()
    done(err, stats)
    cleanup()
  }
}
