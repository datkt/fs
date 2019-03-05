package datkt.fs.test

import datkt.tape.test

fun truncate(argv: Array<String>) {
  test("truncate(path: String, length: Long = 0, callback: Callback)") { t ->
    val filename = "tmp/fs-truncate-test-file"
    var buffer = "hello".toUtf8() // len=5

    datkt.fs.writeFile(filename, buffer) { err ->
      t.error(err)
      datkt.fs.truncate(filename) { err ->
        t.error(err)

        datkt.fs.readFile(filename) { err, buf ->
          t.error(err)
          t.ok(null != buf && 0 == buf.size, "file truncated")

          datkt.fs.unlink(filename) { err ->
            t.error(err)
            t.end()
          }
        }
      }
    }
  }
}
