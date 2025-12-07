package designpattern

import cats.implicits._

object ChainOfResponsibilityPattern {

  /*
   要求を処理するオブジェクトを1本のチェーン状に並べて順に処理を委譲していくデザインパターン
   Webフレームワークのミドルウェア、バリデーション処理などで応用が可能
   */

  trait Handler {
    def handle(req: String): Option[String]
  }

  class AuthHandler(next: Handler) extends Handler {
    def handle(req: String): Option[String] =
      if (req.contains("auth")) "Authorized".some
      else next.handle(req)
  }

  class LogHandler(next: Handler) extends Handler {
    def handle(req: String): Option[String] = {
      println(s"log: $req")
      next.handle(req)
    }
  }

}
