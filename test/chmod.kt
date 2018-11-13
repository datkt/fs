package datkt.fs.test

import datkt.tape.test

fun chmod() {
  test("chmod(path: String, mode: Long, callback: (Error?) -> Any?)") { t ->
    var i = 0
    fun testMode(name: String, mode: Long) {
      i++
      datkt.fs.chmod("test.kexe", mode) { err ->
        t.equal(err, null, "Setting 'test.kexe' permission '${name}'")

        if (0 == --i) {
          t.end(err)
        }
      }
    }

    testMode("S_IRUSR", datkt.fs.S_IRUSR)
    testMode("S_IWUSR", datkt.fs.S_IWUSR)
    testMode("S_IXUSR", datkt.fs.S_IXUSR)

    testMode("S_IWGRP", datkt.fs.S_IWGRP)
    testMode("S_IRGRP", datkt.fs.S_IRGRP)
    testMode("S_IXGRP", datkt.fs.S_IXGRP)

    testMode("S_IWOTH", datkt.fs.S_IWOTH)
    testMode("S_IROTH", datkt.fs.S_IROTH)
    testMode("S_IXOTH", datkt.fs.S_IXOTH)

    testMode(
      "S_IRUSR | S_IWUSR",
      datkt.fs.S_IRUSR or
      datkt.fs.S_IWUSR)

    testMode(
      "S_IRUSR | S_IWUSR | S_IXOTH",
      datkt.fs.S_IRUSR or
      datkt.fs.S_IWUSR or
      datkt.fs.S_IXOTH)

    testMode(
      "S_IRUSR | S_IWUSR | S_IXOTH | S_IWGRP",
      datkt.fs.S_IRUSR or
      datkt.fs.S_IWUSR or
      datkt.fs.S_IXOTH or
      datkt.fs.S_IWGRP)

    testMode(
      "S_IRUSR | S_IWUSR | S_IXOTH | S_IWGRP | S_IRGRP",
      datkt.fs.S_IRUSR or
      datkt.fs.S_IWUSR or
      datkt.fs.S_IXOTH or
      datkt.fs.S_IWGRP or
      datkt.fs.S_IRGRP)

    testMode(
      "S_IRUSR | S_IWUSR | S_IXOTH | S_IRGRP | S_IWGRP | S_IXGRP",
      datkt.fs.S_IRUSR or
      datkt.fs.S_IWUSR or
      datkt.fs.S_IXOTH or
      datkt.fs.S_IRGRP or
      datkt.fs.S_IWGRP or
      datkt.fs.S_IXGRP)

    testMode(
      "S_IRUSR | S_IWUSR | S_IXOTH | S_IRGRP | S_IWGRP | S_IXGRP | S_IROTH",
      datkt.fs.S_IRUSR or
      datkt.fs.S_IWUSR or
      datkt.fs.S_IXOTH or
      datkt.fs.S_IRGRP or
      datkt.fs.S_IWGRP or
      datkt.fs.S_IXGRP or
      datkt.fs.S_IROTH)

    testMode(
      "S_IRUSR | S_IWUSR | S_IXOTH | S_IRGRP | S_IWGRP | S_IXGRP | S_IROTH |" +
      "S_IWOTH",
      datkt.fs.S_IRUSR or
      datkt.fs.S_IWUSR or
      datkt.fs.S_IXOTH or
      datkt.fs.S_IRGRP or
      datkt.fs.S_IWGRP or
      datkt.fs.S_IXGRP or
      datkt.fs.S_IROTH or
      datkt.fs.S_IWOTH)

    testMode(
      "S_IRUSR | S_IWUSR | S_IXOTH | S_IRGRP | S_IWGRP | S_IXGRP | S_IROTH |" +
      "S_IWOTH | S_IXOTH",
      datkt.fs.S_IRUSR or
      datkt.fs.S_IWUSR or
      datkt.fs.S_IXOTH or
      datkt.fs.S_IRGRP or
      datkt.fs.S_IWGRP or
      datkt.fs.S_IXGRP or
      datkt.fs.S_IROTH or
      datkt.fs.S_IWOTH or
      datkt.fs.S_IXOTH)
  }
}
