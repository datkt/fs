package datkt.fs

typealias EmptyCallback = (Error?) -> Any?
typealias BufferCallback = (Error?, ByteArray?) -> Any?
typealias StatCallback = (Error? /*,Stats? */) -> Any?
typealias ReadDirectoryCallback = (Error?, Array<String>?) -> Any?
