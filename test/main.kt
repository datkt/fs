import datkt.fs.test.constants
import datkt.fs.test.access
import datkt.fs.test.chmod
import datkt.fs.test.chown

fun main(argv: Array<String>) {
  fun call(runner: (argv: Array<String>) -> Any?) {
    runner.invoke(argv)
    datkt.fs.loop.run()
  }

  call(::constants)
  call(::access)
  call(::chmod)
  call(::chown)

  datkt.tape.collect()
}
