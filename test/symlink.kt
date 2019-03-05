package datkt.fs.test

import datkt.tape.test

private const val FILENAME = "test.kexe"
private const val LINK = "tmp/symlink-test.kexe"

fun symlink(argv: Array<String>) {
  var uid: Int = 0
  var gid: Int = 0

  if (2 == argv.count()) {
    uid = argv[0].toInt()
    gid = argv[1].toInt()
  } else if (1 == argv.count()) {
    uid = argv[0].toInt()
  }

  test("symlink(source, path, type, callback)") { t ->
    var i = 0
    fun queue(type: Int) {
      i++
      val link = "${LINK}-${type}-${i}"
      datkt.fs.symlink(FILENAME, link, type) { err ->
        t.equal(err, null, "symlink succeeds")
        datkt.fs.lstat(link) { err, stat ->
          t.equal(err, null, "Faild to stat")
          t.ok(null != stat, "stats are null")
          t.ok(stat?.isSymbolicLink(), "Failed to stat correctly symbolic link")

          if (0 == --i) {
            t.end(err)
          }
        }
      }
    }

    queue(datkt.fs.SYMLINK_FILE)
    queue(datkt.fs.SYMLINK_DIRECTORY)
    queue(datkt.fs.SYMLINK_JUNCTION)
  }
}
