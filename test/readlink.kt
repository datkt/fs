package datkt.fs.test

import datkt.tape.test

fun readlink(argv: Array<String>) {
  test("readlink(path[, opts], callback)") { t ->
    val filename = "test.kexe"
    val symlink = "somelink"
    datkt.fs.symlink(filename, symlink) { err ->
      t.error(err)
      datkt.fs.readlink(symlink) { err, path ->
        t.error(err)
        t.ok(
          null != path && filename == path.stringFromUtf8(),
          "Returns real path for symlink")
        t.end()
      }
    }
  }
}
