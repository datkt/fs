import datkt.fs.test.constants
import datkt.fs.test.readdir
import datkt.fs.test.symlink
import datkt.fs.test.access
import datkt.fs.test.lchown
import datkt.fs.test.chown
import datkt.fs.test.chmod
import datkt.fs.test.mkdir
import datkt.fs.test.lstat
import datkt.fs.test.stats
import datkt.fs.test.stat
import datkt.fs.test.link

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
    call(::access)
    call(::chmod)
    call(::chown)
    call(::lchown)
    call(::stats)
    call(::lstat)
    call(::stat)
    call(::link)
    call(::symlink)
    call(::mkdir)
    call(::readdir)
  }

  fun launch(block: suspend () -> Unit) {
    return block.startCoroutine(Continuation(EmptyCoroutineContext) { r ->
      r.onFailure {
        datkt.tape.collect()
      }

      r.onSuccess {
        datkt.tape.collect()
      }
    })
  }

  launch { run() }
}
