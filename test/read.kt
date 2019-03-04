package datkt.fs.test

import datkt.tape.test

fun read(argv: Array<String>) {
  test("read(fd: Int, buffer: ByteArray, offset: Int, length: Int, position: Int, callback: Callback)") { t ->
    datkt.fs.open("./read.kt") { err, fd ->
      t.equal(err, null)
      t.ok(fd)

      var buffer = ByteArray(13) // "package datkt"

      // space out
      buffer.forEachIndexed { i, _ -> buffer[i] = 0 }

      if (null != fd) {
        datkt.fs.read(fd, buffer, 0, 7, 0) { err, buf ->
          t.equal(err, null)
          t.equal(
              buffer.sliceArray(0..7).stringFromUtf8(),
              "package",
              "correctly reads 'package' from file")

          datkt.fs.read(fd, buffer, 7, 6, 7) { err, buf ->
            t.equal(err, null)
            t.equal(" ", buffer.sliceArray(7..7).stringFromUtf8())
            t.equal(
                buffer.sliceArray(8..12).stringFromUtf8(),
                "datkt",
                "correctly reads 'datkt' from file")

            t.equal(
              buffer.stringFromUtf8(),
              "package datkt",
              "correctly reads 'package datkt' in buffer"
            )

            datkt.fs.close(fd) {
              t.equal(err, null)
              t.end()
            }
          }
        }
      }
    }
  }
}
