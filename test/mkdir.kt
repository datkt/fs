package datkt.fs.test

import datkt.tape.test

private const val DIRECTORY = "tmp/test-mkdir-directory"

fun mkdir(argv: Array<String>) {
  test("mkdir(path, mode, callback)") { t ->
    datkt.fs.mkdir(DIRECTORY) { err ->
      t.equal(err, null, "Directory created without error")

      datkt.fs.mkdir(DIRECTORY) { err ->
        t.ok(err is Error && null != err, "Directory already exists")
        datkt.fs.stat(DIRECTORY) { err, stat ->
          t.equal(err, null, "stat directory successfully")
          if (null != stat) {
            t.ok(stat.isDirectory())
          } else {
            t.fail("Failed to stat directory")
          }
          t.end()
        }
      }
    }
  }
}
