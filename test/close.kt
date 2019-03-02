package datkt.fs.test

import datkt.tape.test

fun close(argv: Array<String>) {
  test("close(fd, callback)") { t ->
    datkt.fs.open("close.kt") { err, fd ->
      t.ok(null == err)
      t.ok(null != fd, "file descriptor is not null")
      if (null != fd) {
        datkt.fs.close(fd) { err ->
          t.ok(null == err)
          t.end()
        }
      }
    }
  }
}
