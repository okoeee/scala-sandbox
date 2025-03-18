package readingfiletest

import java.io.{BufferedInputStream, ByteArrayOutputStream}

object MemoryTestApp {
  def execute(): Unit = {
    // ダウンロード前のメモリ使用量を出力
    printMemoryUsage("ダウンロード前")

    val resource = getClass.getResource("/1GB.png.zip")
    val inputStream = resource.openStream()

    // 必要に応じてタイムアウトやヘッダの設定を追加できます
    val in = new BufferedInputStream(inputStream)
    val baos = new ByteArrayOutputStream()
    val buffer = new Array[Byte](8192) // 8KB バッファ

    Iterator
      .continually(in.read(buffer))
      .takeWhile(_ != -1)
      .foreach { bytesRead =>
        printMemoryUsage("ダウンロード中")
        baos.write(buffer, 0, bytesRead)
      }

    in.close()
    baos.close()

    // 読み込んだデータ（zip ファイルのバイト列）を取得
    val fileBytes = baos.toByteArray
    println(s"ダウンロード完了。ファイルサイズ: ${fileBytes.length} バイト")

    // ダウンロード後のメモリ使用量を出力
    printMemoryUsage("ダウンロード後")

    // ※ここで fileBytes を API のレスポンスとしてストリーミングするなどの処理を行うことも可能
  }

  // 現在のメモリ使用量を MB 単位で表示するヘルパー関数
  private def printMemoryUsage(stage: String): Unit = {
    val runtime = Runtime.getRuntime
    val usedMemory = (runtime.totalMemory - runtime.freeMemory) / (1024 * 1024)
    println(s"$stage: 総メモリ = ${runtime.totalMemory / (1024 * 1024)} MB")
    println(s"$stage: 使用中のメモリ = $usedMemory MB")
  }

}
