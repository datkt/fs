package datkt.fs.test

import datkt.tape.test

fun writeFile(argv: Array<String>) {
  test("writeFile(path: String, buffer: ByteArray, callback: Callback)") { t ->
    val filename = "tmp/fs-writeFile-test-file"
    var buffer = "hello".toUtf8() // len=5

    datkt.fs.writeFile(filename, buffer) { err ->
      t.error(err)
      datkt.fs.readFile(filename) { err, buf ->
        t.error(err)
        t.equal(
          buf?.stringFromUtf8(),
          buffer.stringFromUtf8(),
          "contents written correctly to new file")

        datkt.fs.unlink(filename) { err ->
          t.error(err)
          t.end()
        }
      }
    }
  }
}
