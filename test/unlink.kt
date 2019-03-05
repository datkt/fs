package datkt.fs.test

import datkt.tape.test

fun unlink(argv: Array<String>) {
  test("unlink(path, callback)") { t ->
    val path = "tmp/a-test-file"
    datkt.fs.open(path, "w+") { err, fd ->
      t.ok(null == err)
      t.ok(null != fd, "file descriptor is not null")
      if (null != fd) {
        datkt.fs.close(fd) { err ->
          t.ok(null == err)
          datkt.fs.unlink(path) { err ->
            t.ok(null == err)
            t.end()
          }
        }
      }
    }
  }
}
