import datkt.fs.test.constants
import datkt.fs.test.writeFile
import datkt.fs.test.copyFile
import datkt.fs.test.readFile
import datkt.fs.test.realpath
import datkt.fs.test.readlink
import datkt.fs.test.readdir
import datkt.fs.test.symlink
import datkt.fs.test.rename
import datkt.fs.test.access
import datkt.fs.test.lchown
import datkt.fs.test.unlink
import datkt.fs.test.chown
import datkt.fs.test.close
import datkt.fs.test.chmod
import datkt.fs.test.mkdir
import datkt.fs.test.write
import datkt.fs.test.fstat
import datkt.fs.test.lstat
import datkt.fs.test.stats
import datkt.fs.test.stat
import datkt.fs.test.link
import datkt.fs.test.read
import datkt.fs.test.open

import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

fun main(argv: Array<String>) {
  suspend fun call(runner: (argv: Array<String>) -> Any?) {
    return suspendCoroutine { cont ->
      runner.invoke(argv)
      datkt.fs.loop.run()
      cont.resume(Unit)
    }
  }

  suspend fun run() {
    call(::constants)
    call(::open)
    call(::close)
    call(::read)
    call(::write)
    call(::unlink)
    call(::access)
    call(::chmod)
    call(::chown)
    call(::lchown)
    call(::stats)
    call(::lstat)
    call(::fstat)
    call(::stat)
    call(::link)
    call(::symlink)
    call(::mkdir)
    call(::readdir)
    call(::readFile)
    call(::writeFile)
    call(::copyFile)
    call(::realpath)
    call(::readlink)
    call(::rename)
  }

  fun launch(block: suspend () -> Unit) {
    return block.startCoroutine(Continuation(EmptyCoroutineContext) { r ->
      r.onFailure { err ->
        println("Failure: Error: ${err.message}")
      }

      r.onSuccess {
        datkt.tape.collect()
      }
    })
  }

  launch { run() }
}
