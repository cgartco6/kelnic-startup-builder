import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object UsersTable : Table("users") {
    val id = varchar("id", 50)
    val email = varchar("email", 100)
    val tier = varchar("tier", 20)
    val businessStateJson = text("business_state_json")
    override val primaryKey = PrimaryKey(id)
}

object DatabaseFactory {
    fun init() {
        val dbFile = File("kelnic.db")
        Database.connect("jdbc:sqlite:${dbFile.absolutePath}", "org.sqlite.JDBC")

        transaction {
            SchemaUtils.createMissingTablesAndColumns(UsersTable)
        }
    }

    fun saveUser(user: User) {
        transaction {
            UsersTable.insert {
                it[id] = user.id
                it[email] = user.email
                it[tier] = user.tier.name
                it[businessStateJson] = "{}" // Simplified - expand with kotlinx.serialization
            }
        }
    }
}
