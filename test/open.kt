package datkt.fs.test

import datkt.tape.test

fun open(argv: Array<String>) {
  test("open(path[, flags[, mode]] callback)") { t ->

    datkt.fs.open("not a file") { err, fd ->
      t.ok(err is Error, "fails for non file")
      t.ok(null == fd, "invalid file descriptor is null")

      datkt.fs.open("./open.kt") { err, fd1 ->
        t.ok(null != fd1, "file descriptor is not null")
        t.ok(null != fd1 && fd1 > 0, "file descriptor is in not stdin")
        t.equal(err, null, "default (0o666) for './open.kt' file path")

        datkt.fs.open("./open.kt", "r", 0) { err, fd2 ->
          t.ok(null != fd2, "file descriptor is not null")
          t.ok(null != fd2 && fd2 > 0, "file descriptor is in not stdin")
          t.equal(err, null, "default (0o666) for './open.kt' file path")
          t.ok(
              null != fd2 && null != fd1 && fd1 != fd2 && fd2 > fd1,
              "creates unique file descriptor")
          t.end()
        }
      }
    }
  }
}
