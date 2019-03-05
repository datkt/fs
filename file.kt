package datkt.fs

class File(
  val path: String,
  val flags: String = "r",
  val mode: Long = DEFAULT_OPEN_MODE
) {
  private var fd: Int = -1
  val opened: Boolean get() = this.fd > -1

  init {
    open(path, flags, mode) { err, fd ->
      if (null != err) {
        throw Error(err.message)
      } else if (null != fd) {
        this.fd = fd
      }
    }
  }
}
