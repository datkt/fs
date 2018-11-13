package datkt.fs.test

import datkt.tape.test

fun constants() {
  test("datkt.fs.constants") { t ->
    t.ok(datkt.fs.F_OK >= -1, "F_OK")
    t.ok(datkt.fs.R_OK >= -1, "R_OK")
    t.ok(datkt.fs.W_OK >= -1, "W_OK")
    t.ok(datkt.fs.X_OK >= -1, "X_OK")

    t.ok(datkt.fs.O_RDONLY >= -1, "O_RDONLY")
    t.ok(datkt.fs.O_WRONLY >= -1, "O_WRONLY")
    t.ok(datkt.fs.O_RDWR >= -1, "O_RDWR")
    t.ok(datkt.fs.O_CREAT >= -1, "O_CREAT")
    t.ok(datkt.fs.O_EXCL >= -1, "O_EXCL")
    t.ok(datkt.fs.O_NOCTTY >= -1, "O_NOCTTY")
    t.ok(datkt.fs.O_TRUNC >= -1, "O_TRUNC")
    t.ok(datkt.fs.O_APPEND >= -1, "O_APPEND")
    t.ok(datkt.fs.O_DIRECTORY >= -1, "O_DIRECTORY")
    t.ok(datkt.fs.O_NOATIME >= -1, "O_NOATIME")
    t.ok(datkt.fs.O_NOFOLLOW >= -1, "O_NOFOLLOW")
    t.ok(datkt.fs.O_SYNC >= -1, "O_SYNC")
    t.ok(datkt.fs.O_DSYNC >= -1, "O_DSYNC")
    t.ok(datkt.fs.O_SYMLINK >= -1, "O_SYMLINK")
    t.ok(datkt.fs.O_DIRECT >= -1, "O_DIRECT")
    t.ok(datkt.fs.O_NONBLOCK >= -1, "O_NONBLOCK")

    t.ok(datkt.fs.S_IFMT >= -1, "S_IFMT")
    t.ok(datkt.fs.S_IFREG >= -1, "S_IFREG")
    t.ok(datkt.fs.S_IFDIR >= -1, "S_IFDIR")
    t.ok(datkt.fs.S_IFCHR >= -1, "S_IFCHR")
    t.ok(datkt.fs.S_IFBLK >= -1, "S_IFBLK")
    t.ok(datkt.fs.S_IFIFO >= -1, "S_IFIFO")
    t.ok(datkt.fs.S_IFLNK >= -1, "S_IFLNK")
    t.ok(datkt.fs.S_IFSOCK >= -1, "S_IFSOCK")
    t.ok(datkt.fs.S_IRWXU >= -1, "S_IRWXU")
    t.ok(datkt.fs.S_IRUSR >= -1, "S_IRUSR")
    t.ok(datkt.fs.S_IWUSR >= -1, "S_IWUSR")
    t.ok(datkt.fs.S_IXUSR >= -1, "S_IXUSR")
    t.ok(datkt.fs.S_IRWXG >= -1, "S_IRWXG")
    t.ok(datkt.fs.S_IRGRP >= -1, "S_IRGRP")
    t.ok(datkt.fs.S_IWGRP >= -1, "S_IWGRP")
    t.ok(datkt.fs.S_IXGRP >= -1, "S_IXGRP")
    t.ok(datkt.fs.S_IRWXO >= -1, "S_IRWXO")
    t.ok(datkt.fs.S_IROTH >= -1, "S_IROTH")
    t.ok(datkt.fs.S_IWOTH >= -1, "S_IWOTH")
    t.ok(datkt.fs.S_IXOTH >= -1, "S_IXOTH")

    t.ok(datkt.fs.UV_FS_SYMLINK_DIR >= -1, "UV_FS_SYMLINK_DIR")
    t.ok(datkt.fs.UV_FS_SYMLINK_JUNCTION >= -1, "UV_FS_SYMLINK_JUNCTION")
    t.ok(datkt.fs.UV_DIRENT_UNKNOWN >= -1, "UV_DIRENT_UNKNOWN")
    t.ok(datkt.fs.UV_DIRENT_FILE >= -1, "UV_DIRENT_FILE")
    t.ok(datkt.fs.UV_DIRENT_DIR >= -1, "UV_DIRENT_DIR")
    t.ok(datkt.fs.UV_DIRENT_LINK >= -1, "UV_DIRENT_LINK")
    t.ok(datkt.fs.UV_DIRENT_FIFO >= -1, "UV_DIRENT_FIFO")
    t.ok(datkt.fs.UV_DIRENT_SOCKET >= -1, "UV_DIRENT_SOCKET")
    t.ok(datkt.fs.UV_DIRENT_CHAR >= -1, "UV_DIRENT_CHAR")
    t.ok(datkt.fs.UV_DIRENT_BLOCK >= -1, "UV_DIRENT_BLOCK")

    t.end()
  }
}
