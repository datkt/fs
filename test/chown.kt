package datkt.fs.test

import datkt.tape.test

private const val FILENAME = "test/fixtures/file.txt"

fun chown(argv: Array<String>) {
  var uid: Int = 0
  var gid: Int = 0

  if (2 == argv.count()) {
    uid = argv[0].toInt()
    gid = argv[1].toInt()
  } else if (1 == argv.count()) {
    uid = argv[0].toInt()
  }

  test("chown(path, uid, gid, callback)") {
    t -> datkt.fs.chown(FILENAME, uid, gid) { err ->
      t.equal(err, null, "chown succeeds")
      t.end(err)
    }
  }
}
