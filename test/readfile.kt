package datkt.fs.test

import datkt.tape.test

fun readFile(argv: Array<String>) {
  test("readFile(path[, opts], callback)") { t ->
    datkt.fs.readFile("does not exist") { err, buf ->
      t.ok(err is Error, "Fails for files that don't exist")

      datkt.fs.readFile("./test/fixtures/file.txt") { err, buf ->
        t.equal(err, null)
        t.ok(null != buf, "buffer is not null")

        if (null != buf) {
          t.equal(
            buf.sliceArray(0..4).stringFromUtf8(),
            "hello",
            "reads 'hello' from test/fixtures/file.txt")
        }

        t.end()
      }
    }
  }
}
