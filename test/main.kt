import datkt.fs.test.constants
import datkt.fs.test.access

fun main(args: Array<String>) {
  call(::constants)
  call(::access)

  datkt.tape.collect()
}

fun call(runner: () -> Any?) {
  runner()
  datkt.fs.loop.run()
}
