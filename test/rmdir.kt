package datkt.fs.test

import datkt.tape.test

private const val DIRECTORY = "tmp/test-rmdir-directory"

fun rmdir(argv: Array<String>) {
  test("rmdir(path, callback)") { t ->
    datkt.fs.mkdir(DIRECTORY) { err ->
      t.equal(err, null, "Directory created without error")

      datkt.fs.rmdir(DIRECTORY) { err ->
        t.equal(err, null, "Directory removed without error")

        datkt.fs.access(DIRECTORY) { err ->
          t.ok(err is Error)
          t.end()
        }
      }
    }
  }
}
