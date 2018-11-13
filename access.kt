package datkt.fs

import kotlinx.cinterop.*

import datkt.uv.uv_fs_t
import datkt.uv.uv_strerror
import datkt.uv.uv_fs_access
import datkt.uv.uv_fs_req_cleanup

fun access(path: String, mode: Long = F_OK, callback: AccessCallback) {
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

fun onaccess(req: CPointer<uv_fs_t>?) {
  uv_fs_req_cleanup(req)

  val fs: uv_fs_t? = req?.pointed
  val ref = fs?.data?.asStableRef<AccessCallback>()
  val callback = ref?.get()
  val err = datkt.fs.createError(fs)

  callback?.invoke(err)

  ref?.dispose()
}
