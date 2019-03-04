package datkt.fs.test

import datkt.tape.test

fun realpath(argv: Array<String>) {
  test("realpath(path[, opts], callback)") { t ->
    datkt.fs.realpath("../..") { err, path ->
      t.error(err)
      t.ok(
        null != path && "/home" == path.sliceArray(0..4).stringFromUtf8(),
        "Returns real path")
      t.end()
    }
  }
}
