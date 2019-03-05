package datkt.fs.test

import datkt.tape.test

fun ftruncate(argv: Array<String>) {
  test("ftruncate(fd: Int, length: Long = 0, callback: Callback)") { t ->
    val filename = "tmp/fs-ftruncate-test-file"
    var buffer = "hello".toUtf8() // len=5

    datkt.fs.writeFile(filename, buffer) { err ->
      t.error(err)

      datkt.fs.open(filename, "r+") { err, fd ->
        t.error(err)

        if (null != fd) {
          datkt.fs.ftruncate(fd) { err ->
            t.error(err)

            datkt.fs.readFile(filename) { err, buf ->
              t.error(err)
              t.ok(null != buf && 0 == buf.size, "file truncated")

              datkt.fs.close(fd) { err ->
                t.error(err)

                datkt.fs.unlink(filename) { err ->
                  t.error(err)
                  t.end()
                }
              }
            }
          }
        }
      }
    }
  }
}
