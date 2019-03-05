package datkt.fs

import datkt.fs.EmptyCallback as Callback

fun truncate(path: String, length: Long = 0, callback: Callback) {
  open(path, "r+") { err, fd ->
    if (null != err) {
      callback(err)
    } else if (null != fd && fd > -1) {
      ftruncate(fd, length, callback)
    } else {
      callback(Error("An unknown error occured."))
    }
  }
}
