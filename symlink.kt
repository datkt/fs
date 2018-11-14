package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.CStructVar
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr

import datkt.uv.UV_FS_SYMLINK_JUNCTION
import datkt.uv.UV_FS_SYMLINK_DIR
import datkt.uv.uv_fs_req_cleanup
import datkt.uv.uv_fs_symlink
import datkt.uv.uv_fs_t

private typealias Callback = (Error?) -> Any?

val SYMLINK_FILE = 0
val SYMLINK_DIRECTORY = UV_FS_SYMLINK_DIR // 1
val SYMLINK_JUNCTION = UV_FS_SYMLINK_JUNCTION // 2

fun symlink(source: String, path: String, type: Int = 0, callback: Callback) {
  val ref = StableRef.create(callback)
  val req = nativeHeap.alloc<uv_fs_t>()
  req.data = ref.asCPointer()
  uv_fs_symlink(
    datkt.fs.loop.default,
    (req as CStructVar).ptr as CValuesRef<uv_fs_t>,
    source,
    path,
    type,
    staticCFunction(::onsymlink))
}

private fun onsymlink(req: CPointer<uv_fs_t>?) {
  uv_fs_req_cleanup(req)

  val fs: uv_fs_t? = req?.pointed
  val ref = fs?.data?.asStableRef<(Error?) -> Any?>()
  val callback = ref?.get()
  val err = datkt.fs.createError(fs)

  callback?.invoke(err)

  ref?.dispose()
}
