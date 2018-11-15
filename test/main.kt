import datkt.fs.test.constants
import datkt.fs.test.symlink
import datkt.fs.test.access
import datkt.fs.test.chmod
import datkt.fs.test.chown
import datkt.fs.test.lstat
import datkt.fs.test.stat
import datkt.fs.test.link

fun main(argv: Array<String>) {
  fun call(runner: (argv: Array<String>) -> Any?) {
    runner.invoke(argv)
    datkt.fs.loop.run()
  }

  call(::constants)
  call(::access)
  call(::chmod)
  call(::chown)
  call(::link)
  call(::symlink)
  call(::lstat)
  call(::stat)

  datkt.tape.collect()
}
