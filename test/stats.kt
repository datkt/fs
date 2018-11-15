package datkt.fs.test

import datkt.tape.test
import datkt.fs.Stats

fun stats(argv: Array<String>) {
  test("Stats()") { t ->
    var stat = Stats()

    t.equal(stat.dev, 0L, "0L == stat.dev")
    t.equal(stat.mode, 0L, "0L == stat.mode")
    t.equal(stat.nlink, 0L, "0L == stat.nlink")
    t.equal(stat.uid, 0L, "0L == stat.uid")
    t.equal(stat.gid, 0L, "0L == stat.gid")
    t.equal(stat.rdev, 0L, "0L == stat.rdev")
    t.equal(stat.ino, 0L, "0L == stat.ino")
    t.equal(stat.size, 0L, "0L == stat.size")
    t.equal(stat.blksize, 0L, "0L == stat.blksize")
    t.equal(stat.blocks, 0L, "0L == stat.blocks")
    t.equal(stat.atime, 0L, "0L == stat.atime")
    t.equal(stat.mtime, 0L, "0L == stat.mtime")
    t.equal(stat.ctime, 0L, "0L == stat.ctime")
    t.equal(stat.birthtime, 0L, "0L == stat.birthtime")

    t.equal(stat.isCharacterDevice(), false, "false == stat.isCharacterDevice()")
    t.equal(stat.isSymbolicLink(), false, "false == stat.isSymbolicLink()")
    t.equal(stat.isBlockDevice(), false, "false == stat.isBlockDevice()")
    t.equal(stat.isDirectory(), false, "false == stat.isDirectory()")
    t.equal(stat.isSocket(), false, "false == stat.isSocket()")
    t.equal(stat.isFIFO(), false, "false == stat.isFIFO()")
    t.equal(stat.isFile(), false, "false == stat.isFile()")

    stat = Stats(mode = datkt.fs.S_IFREG)
    t.equal(stat.isFile(), true, "true == stat.isFile()")

    stat = Stats(mode = datkt.fs.S_IFIFO)
    t.equal(stat.isFIFO(), true, "true == stat.isFIFO()")

    stat = Stats(mode = datkt.fs.S_IFSOCK)
    t.equal(stat.isSocket(), true, "true == stat.isSocket()")

    stat = Stats(mode = datkt.fs.S_IFDIR)
    t.equal(stat.isDirectory(), true, "true == stat.isDirectory()")

    stat = Stats(mode = datkt.fs.S_IFBLK)
    t.equal(stat.isBlockDevice(), true, "true == stat.isBlockDevice()")

    stat = Stats(mode = datkt.fs.S_IFLNK)
    t.equal(stat.isSymbolicLink(), true, "true == stat.isSymbolicLink()")

    stat = Stats(mode = datkt.fs.S_IFCHR)
    t.equal(stat.isCharacterDevice(), true, "true == stat.isCharacterDevice()")

    t.end()
  }
}
