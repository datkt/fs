package datkt.fs.test

import datkt.tape.test

fun rename(argv: Array<String>) {
  test("rename(src: String, dst: String, callback: Callback)") { t ->
    val filename = "tmp/fs-rename-test-file"
    val renamed = filename + "-new"
    var buffer = "hello".toUtf8() // len=5

    datkt.fs.writeFile(filename, buffer) { err ->
      t.error(err)

      datkt.fs.rename(filename, renamed) { err ->
        t.error(err)

        datkt.fs.readFile(renamed) { err, buf ->
          t.equal(
            buf?.stringFromUtf8(),
            buffer.stringFromUtf8(),
            "contents written correctly to copied file")

          datkt.fs.access(filename) { err ->
            t.ok(err is Error)

            datkt.fs.unlink(renamed) { err ->
              t.error(err)
              t.end()
            }
          }
        }
      }
    }
  }
}
