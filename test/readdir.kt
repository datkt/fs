package datkt.fs.test

import datkt.tape.test

private const val DIRECTORY = "."
private const val FILENAME = "test.kexe"

fun readdir(argv: Array<String>) {
  test("readdir(path, callback)") { t ->
    datkt.fs.readdir(DIRECTORY) { err, files ->
      t.equal(err, null, "readdir did not fail")
      t.ok(null != files, "files array is not null")

      if (null != files) {
        t.ok(files.count() > 0, "files array has items")
        t.ok(files.contains(FILENAME), "contains this file")
      }
    }

    datkt.fs.readdir(FILENAME) { err, files ->
      t.ok(err is Error, "fails for non directory")
      t.equal(files, null, "files is null when an error occurs")
      t.end()
    }
  }
}
