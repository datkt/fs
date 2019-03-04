package datkt.fs

import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.pointed
import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr

import datkt.uv.uv_fs_req_cleanup
import datkt.uv.uv_fs_t

typealias UVRequest = CPointer<uv_fs_t>
typealias UVBlock<T> = (Error?, uv.request<T>) -> Any?

class UVRequestData<T>(
  val callback: T,
  val buffer: Any? = null
)

object uv {
  fun <T> init(callback: T, buffer: Any? = null): uv_fs_t {
    val data = UVRequestData<T>(callback, buffer)
    val ref = StableRef.create<UVRequestData<T>>(data)
    //val ref = StableRef.create<Function<*>>(callback as Function<*>)
    val req = nativeHeap.alloc<uv_fs_t>()
    req.data = ref.asCPointer()
    return req
  }

  fun <T : CPointed> toCValuesRef(req: T): CValuesRef<T> {
    return req.ptr
  }

  class request<T>(req: UVRequest?, block: UVBlock<T>) {
    var cleanup: () -> Any?
    val block: UVBlock<T>
    var done: T
    var data: UVRequestData<T>
    //var ref: StableRef<Function<*>>? = null
    var ref: StableRef<UVRequestData<T>>? = null
    var err: Error? = null
    var req: UVRequest? = null
    var fs: uv_fs_t? = null

    operator fun component1() = this.fs
    operator fun component2() = this.done
    operator fun component3() = this.cleanup
    operator fun component4() = this.ref

    init {
      this.fs = req?.pointed
      this.req = req
      //this.ref = this.fs?.data?.asStableRef<Function<*>>()
      this.ref = this.fs?.data?.asStableRef<UVRequestData<T>>()
      this.err = datkt.fs.createError(fs)
      this.data = this.ref?.get() as UVRequestData<T>
      this.done = this.data.callback
      this.block = block

      this.cleanup = {
        val ref = this.ref

        if (null != ref) {
          ref.dispose()
          this.ref = null
        }

        if (null != req) {
          uv_fs_req_cleanup(req)
          nativeHeap.free(req.rawValue)
          this.req = null
        }
      }

      this.block(err, this)
    }
  }
}
