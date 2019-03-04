package datkt.fs.test

import datkt.tape.test

fun write(argv: Array<String>) {
  test("write(fd: Int, buffer: ByteArray, offset: Int, length: Int, position: Int, callback: Callback)") { t ->
    val filename = "tmp/fs-write-test-file"
    datkt.fs.open(filename, "w+") { err, fd ->
      t.error(err)
      t.ok(fd)

      var buffer = "hello".toUtf8() // len=5

      if (null != fd) {
        datkt.fs.write(fd, buffer) { err ->
          t.error(err)
          datkt.fs.readFile(filename) { err, buf ->
            t.error(err)
            t.equal(
              buf?.stringFromUtf8(),
              buffer.stringFromUtf8(),
              "contents written correctly to new file")

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
