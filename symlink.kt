package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.CPointer

import datkt.uv.UV_FS_SYMLINK_JUNCTION
import datkt.uv.UV_FS_SYMLINK_DIR
import datkt.uv.uv_fs_symlink
import datkt.uv.uv_fs_t

import datkt.fs.EmptyCallback as Callback
import datkt.fs.uv.uv

val SYMLINK_FILE = 0
val SYMLINK_DIRECTORY = UV_FS_SYMLINK_DIR // 1
val SYMLINK_JUNCTION = UV_FS_SYMLINK_JUNCTION // 2

fun symlink(source: String, path: String, type: Int = 0, callback: Callback) {
  val req = uv.init<Callback>(callback)
  uv_fs_symlink(
    datkt.fs.loop.default,
    uv.toCValuesRef(req),
    source,
    path,
    type,
    staticCFunction(::onsymlink))
}

private fun onsymlink(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, uv ->
    uv.done(err)
    uv.cleanup()
  }
}
