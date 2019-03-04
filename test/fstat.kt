package datkt.fs.test

import datkt.tape.test

private const val FILENAME = "test/main.kt"

fun fstat(argv: Array<String>) {
  var uid: Int = 0
  var gid: Int = 0

  if (2 == argv.count()) {
    uid = argv[0].toInt()
    gid = argv[1].toInt()
  } else if (1 == argv.count()) {
    uid = argv[0].toInt()
  }

  test("fstat(fd, callback)") { t ->
    datkt.fs.open(FILENAME) { err, fd ->
      t.error(err)

      if (null != fd) {
        datkt.fs.fstat(fd) { err, stats ->
          t.ok(null != stats)
          t.error(err)

          if (null == stats) {
            t.fail("Failed to stat file")
          } else {
            t.ok(stats.dev > 0, "stats.dev > 0")
            t.ok(stats.mode > 0, "stats.mode > 0")
            t.ok(stats.nlink > 0, "stats.nlink > 0")
            t.ok(stats.uid > 0, "stats.uid > 0")
            t.ok(stats.gid > 0, "stats.gid > 0")
            t.ok(stats.ino > 0, "stats.info > 0")
            t.ok(stats.size > 0, "stats.size > 0")
            t.ok(stats.atime > 0, "stats.atime > 0")
            t.ok(stats.mtime > 0, "stats.mtime > 0")
            t.ok(stats.ctime > 0, "stats.ctime > 0")
            t.ok(stats.birthtime > 0, "stats.birthtime > 0")
            t.ok(stats.isFile(), "true == stats.isFile()")
          }
        }

        datkt.fs.close(fd) { err ->
          t.error(err)
          t.end()
        }
      }
    }
  }
}
