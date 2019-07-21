package io.github.tksugimoto.slick.test.models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Users
    *  @param id Database column id SqlType(BIGINT), AutoInc, PrimaryKey
    *  @param name Database column name SqlType(VARCHAR), Length(50,true)
    *  @param age Database column age SqlType(INT), Default(None)
    *  @param address Database column address SqlType(VARCHAR), Length(50,true), Default(Some(NULL)) */
  case class UsersRow(
      id: Long,
      name: String,
      age: Option[Int] = None,
      address: Option[String] = Some("NULL"),
  )

  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(
      implicit e0: GR[Long],
      e1: GR[String],
      e2: GR[Option[Int]],
      e3: GR[Option[String]],
  ): GR[UsersRow] = GR { prs =>
    import prs._
    UsersRow.tupled((<<[Long], <<[String], <<?[Int], <<?[String]))
  }

  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag)
      extends profile.api.Table[UsersRow](_tableTag, Some("test"), "users") {
    def * = (id, name, age, address) <> (UsersRow.tupled, UsersRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? =
      ((Rep.Some(id), Rep.Some(name), age, address)).shaped.<>(
        { r =>
          import r._; _1.map(_ => UsersRow.tupled((_1.get, _2.get, _3, _4)))
        },
        (_: Any) =>
          throw new Exception("Inserting into ? projection not supported."),
      )

    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)

    /** Database column name SqlType(VARCHAR), Length(50,true) */
    val name: Rep[String] = column[String]("name", O.Length(50, varying = true))

    /** Database column age SqlType(INT), Default(None) */
    val age: Rep[Option[Int]] = column[Option[Int]]("age", O.Default(None))

    /** Database column address SqlType(VARCHAR), Length(50,true), Default(Some(NULL)) */
    val address: Rep[Option[String]] = column[Option[String]](
      "address",
      O.Length(50, varying = true),
      O.Default(Some("NULL")),
    )
  }

  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
