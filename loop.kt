package datkt.fs

import datkt.uv.uv_default_loop
import datkt.uv.UV_RUN_DEFAULT
import datkt.uv.uv_stop
import datkt.uv.uv_run

object loop {
  var factory = ::uv_default_loop
  val default get() = this.factory()

  fun run(): Int {
    return uv_run(loop.default, UV_RUN_DEFAULT)
  }

  fun stop() {
    uv_stop(loop.default)
  }
}
