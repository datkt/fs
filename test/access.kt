package datkt.fs.test

import datkt.tape.test

fun access(argv: Array<String>) {
  test("access(path: String, mode: Int, callback: (Error?) -> Any?)") { t ->
    datkt.fs.access(".", fun (err: Error?) {
      t.equal(err, null, "default (F_OK) for './' file path")
    })

    datkt.fs.access("not a file") { err ->
      t.ok(err is Error, "fails for non file")
    }

    datkt.fs.access(".", datkt.fs.F_OK) { err ->
      t.equal(err, null, "F_OK for './' file path")
    }

    datkt.fs.access(".", datkt.fs.R_OK) { err ->
      t.equal(err, null, "R_OK for './' file path")
    }

    datkt.fs.access(".", datkt.fs.W_OK) { err ->
      t.equal(err, null, "W_OK for './' file path")
    }

    datkt.fs.access(".", datkt.fs.X_OK) { err ->
      t.equal(err, null, "X_OK for './' file path")
      t.end()
    }
  }
}
