package datkt.fs.test

import datkt.tape.test

fun copyFile(argv: Array<String>) {
  test("copyFile(src: String, dst: String, callback: Callback)") { t ->
    val filename = "tmp/fs-copyFile-test-file"
    var buffer = "hello".toUtf8() // len=5

    datkt.fs.writeFile(filename, buffer) { err ->
      t.error(err)

      datkt.fs.copyFile(filename, filename + "-copy") { err ->
        t.error(err)

        datkt.fs.readFile(filename + "-copy") { err, buf ->
          t.equal(
            buf?.stringFromUtf8(),
            buffer.stringFromUtf8(),
            "contents written correctly to copied file")

          datkt.fs.unlink(filename) { err ->
            t.error(err)

            datkt.fs.unlink(filename + "-copy") { err ->
              t.error(err)
              t.end()
            }
          }
        }
      }
    }
  }
}
