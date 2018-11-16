package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.uv.uv_fs_lstat
import datkt.uv.uv_fs_t

import datkt.fs.StatCallback as Callback
import datkt.fs.uv.uv

fun lstat(path: String, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_lstat(
    datkt.fs.loop.default,
    uv.toCValuesRef<uv_fs_t>(req),
    path,
    staticCFunction(::onlstat))
}

private fun onlstat(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, (fs, done, cleanup) ->
    val stats = if (null != err) null else fs?.statbuf?.toStats()
    done(err, stats)
    cleanup()
  }
}
