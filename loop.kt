package datkt.fs

import datkt.uv.uv_default_loop
import datkt.uv.UV_RUN_DEFAULT
import datkt.uv.uv_loop_t
import datkt.uv.uv_stop
import datkt.uv.uv_run

object loop {
  val default get() = uv_default_loop()
  fun run(): Int {
    return uv_run(loop.default, UV_RUN_DEFAULT)
  }

  fun stop() {
    uv_stop(loop.default)
  }
}
