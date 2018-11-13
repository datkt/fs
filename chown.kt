package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.CStructVar
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.convert
import kotlinx.cinterop.pointed
import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr

import datkt.uv.uv_fs_req_cleanup
import datkt.uv.uv_fs_chown
import datkt.uv.uv_fs_t

fun chown(path: String, uid: Int, gid: Int, callback: (Error?) -> Any?) {
  val ref = StableRef.create(callback)
  val req = nativeHeap.alloc<uv_fs_t>()
  req.data = ref.asCPointer()
  uv_fs_chown(
    datkt.fs.loop.default,
    (req as CStructVar).ptr as CValuesRef<uv_fs_t>,
    path,
    uid.convert(),
    gid.convert(),
    staticCFunction(::onchown))
}

private fun onchown(req: CPointer<uv_fs_t>?) {
  uv_fs_req_cleanup(req)

  val fs: uv_fs_t? = req?.pointed
  val ref = fs?.data?.asStableRef<(Error?) -> Any?>()
  val callback = ref?.get()
  val err = datkt.fs.createError(fs)

  callback?.invoke(err)

  ref?.dispose()
}
