package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.alloc
import kotlinx.cinterop.ptr

import datkt.uv.uv_fs_scandir_next
import datkt.uv.uv_fs_scandir
import datkt.uv.uv_dirent_t
import datkt.uv.uv_fs_t
import datkt.uv.UV_EOF

import datkt.fs.ReadDirectoryCallback as Callback
import datkt.fs.uv.uv

fun readdir(path: String, callback: Callback) {
  val cb = staticCFunction(::onscandir)
  val req = uv.init<Callback>(callback)
  val ref = uv.toCValuesRef<uv_fs_t>(req)
  val loop = datkt.fs.loop.default
  val errno = uv_fs_scandir(loop, ref, path, 0, cb)

  if (errno < 0) {
    callback(createError(errno), null)
  }
}

private fun onscandir(req: CPointer<uv_fs_t>?) {
  uv.request<Callback>(req) { err, (fs, done, cleanup) ->
    var files: Array<String>? = null

    if (null != fs && null == err) {
      files = emptyArray()
      memScoped {
        val dirent = alloc<uv_dirent_t>()
        val ref = uv.toCValuesRef<uv_dirent_t>(dirent)

        while (true) {
          val errno = uv_fs_scandir_next(req, ref)
          val name = dirent.name?.toKString()

          if (UV_EOF == errno) {
            break
          }

          if (errno < 0) {
            done(createError(errno), null)
            cleanup()
            break
          }

          if (null != name) {
            files += name
          }
        }
      }
    }

    done(err, files)
    cleanup()
  }
}
