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

import datkt.fs.EmptyCallback as Callback

import datkt.uv.uv_fs_req_cleanup
import datkt.uv.uv_fs_access
import datkt.uv.uv_fs_t

fun access(path: String, callback: Callback) {
  return access(path, F_OK, callback)
}

fun access(path: String, mode: Long = F_OK, callback: Callback) {
  val ref = StableRef.create(callback)
  val req = nativeHeap.alloc<uv_fs_t>()
  req.data = ref.asCPointer()
  uv_fs_access(
    datkt.fs.loop.default,
    (req as CStructVar).ptr as CValuesRef<uv_fs_t>,
    path,
    mode.toInt(),
    staticCFunction(::onaccess))
}

private fun onaccess(req: CPointer<uv_fs_t>?) {
  uv_fs_req_cleanup(req)

  val fs: uv_fs_t? = req?.pointed
  val ref = fs?.data?.asStableRef<Callback>()
  val callback = ref?.get()
  val err = datkt.fs.createError(fs)

  callback?.invoke(err)

  ref?.dispose()
}
