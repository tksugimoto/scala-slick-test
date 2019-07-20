package io.github.tksugimoto.slick.test

import io.github.tksugimoto.slick.test.models.Tables

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Main {
  def main(args: Array[String]): Unit = {
    import Tables.profile.api._

    val db =
      Database.forConfig("io.github.tksugimoto.slick.test.persistence.db")

    val query = for {
      user <- Tables.Users
    } yield (
      user.id,
      user.name,
      user.age,
    )

    val action: DBIO[Seq[(Long, String, Option[Int])]] = query.result

    val resultFuture: Future[Seq[(Long, String, Option[Int])]] = db.run(action)

    // 本番で使うべからず
    import scala.concurrent.ExecutionContext.Implicits.global

    resultFuture
      .andThen {
        case Success(users) => users.foreach(println)
        case Failure(ex)    => ex.printStackTrace()
      }
      .andThen {
        case _ => db.close()
      }
  }
}
