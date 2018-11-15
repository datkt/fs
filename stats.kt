package datkt.fs

import datkt.uv.uv_stat_t

class Stats(
  val dev: Long = 0,
  val mode: Long = 0,
  val nlink: Long = 0,
  val uid: Long = 0,
  val gid: Long = 0,
  val rdev: Long = 0,
  val ino: Long = 0,
  val size: Long = 0,
  val blksize: Long = 0,
  val blocks: Long = 0,
  val atime: Long = 0,
  val mtime: Long = 0,
  val ctime: Long = 0,
  val birthtime: Long = 0
) {
  override fun toString(): String {
    return """
Stats {
  dev=${this.dev},
  mode=${this.mode},
  nlink=${this.nlink},
  uid=${this.uid},
  gid=${this.gid},
  rdev=${this.rdev},
  ino=${this.ino},
  size=${this.size},
  blksize=${this.blksize},
  blocks=${this.blocks},
  atime=${this.atime},
  mtime=${this.mtime},
  ctime=${this.ctime},
  birthtime=${this.birthtime}
}
""".trim()
  }

  fun hasMode(mode: Long): Boolean {
    return if (-1 == mode.toInt()) false else (mode == (this.mode and S_IFMT))
  }

  fun isCharacterDevice() = this.hasMode(S_IFCHR)
  fun isSymbolicLink() = this.hasMode(S_IFLNK)
  fun isBlockDevice() = this.hasMode(S_IFBLK)
  fun isDirectory() = this.hasMode(S_IFDIR)
  fun isSocket() = this.hasMode(S_IFSOCK)
  fun isFIFO() = this.hasMode(S_IFIFO)
  fun isFile() = this.hasMode(S_IFREG)
}

fun uv_stat_t.toStats() = Stats(
  dev = this.st_dev.toLong(),
  mode = this.st_mode.toLong(),
  nlink = this.st_nlink.toLong(),
  uid = this.st_uid.toLong(),
  gid = this.st_gid.toLong(),
  rdev = this.st_rdev.toLong(),
  ino = this.st_ino.toLong(),
  size = this.st_size.toLong(),
  blksize = this.st_blksize.toLong(),
  blocks = this.st_blocks.toLong(),
  atime = this.st_atim.tv_sec,
  mtime = this.st_mtim.tv_sec,
  ctime = this.st_ctim.tv_sec,
  birthtime = this.st_birthtim.tv_sec
)
