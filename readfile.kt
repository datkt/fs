package datkt.fs

import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.alloc

import datkt.fs.ReadDirectoryCallback as Callback

//data class ReadFileOptions { }

//fun readFile(path: String, opts: ReadFileOptions? = null, callback) {
//}
//
//private fun onread(req: CPointer<uv_fs_t>?) {
//}
