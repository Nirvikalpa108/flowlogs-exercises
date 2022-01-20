package com.gu.flowlogs

import io.javalin.Javalin
import io.javalin.http.{Context, Handler}

object Server {
  def main(args: Array[String]): Unit = {
    val app = Javalin.create().start(7070)
    app.get("/", new Handler {
      override def handle(ctx: Context): Unit = {
        println(s"GET: ${ctx.path()}")
        ctx.result("OK")
      }
    })
    app.post("/", new Handler {
      override def handle(ctx: Context): Unit = {
        println(s"Received data: ${ctx.body()}")
        ctx.result("OK")
      }
    })
  }
}
