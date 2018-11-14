package datkt.fs.test

import datkt.tape.test

private const val FILENAME = "test.kexe"
private const val LINK = "test.kexe.link"

fun link(argv: Array<String>) {
  var uid: Int = 0
  var gid: Int = 0

  if (2 == argv.count()) {
    uid = argv[0].toInt()
    gid = argv[1].toInt()
  } else if (1 == argv.count()) {
    uid = argv[0].toInt()
  }

  test("link(source, path, callback)") { t ->
    datkt.fs.link(FILENAME, LINK) { err ->
      t.equal(err, null, "link succeeds")
      t.end(err)
    }
  }
}
