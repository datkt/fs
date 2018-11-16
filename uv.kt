package datkt.fs.uv

import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.CStructVar
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr

import datkt.uv.uv_fs_req_cleanup
import datkt.uv.uv_fs_t

typealias UVRequest = CPointer<uv_fs_t>
typealias UVBlock<T> = (Error?, uv.request<T>) -> Any?

object uv {
  fun <T> init(callback: T): uv_fs_t {
    val ref = StableRef.create<Function<*>>(callback as Function<*>)
    val req = nativeHeap.alloc<uv_fs_t>()
    req.data = ref.asCPointer()
    return req
  }

  fun toCValuesRef(req: uv_fs_t): CValuesRef<uv_fs_t>? {
    return (req as CStructVar).ptr as CValuesRef<uv_fs_t>
  }

  class request<T>(req: UVRequest?, block: UVBlock<T>) {
    val cleanup: () -> Any?
    val done: T
    val ref: StableRef<Function<*>>?
    val err: Error?
    val fs: uv_fs_t?

    operator fun component1() = this.fs
    operator fun component2() = this.done
    operator fun component3() = this.cleanup
    operator fun component4() = this.ref

    init {
      uv_fs_req_cleanup(req)

      this.fs = req?.pointed
      this.ref = this.fs?.data?.asStableRef<Function<*>>()
      this.err = datkt.fs.createError(fs)

      this.done = ref?.get() as T
      this.cleanup = {
        ref.dispose()
        if (null != req) {
          nativeHeap.free(req.rawValue)
        }
      }

      block(err, this)
    }
  }
}
