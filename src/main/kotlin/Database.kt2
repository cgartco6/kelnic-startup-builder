import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object BusinessTable : Table("business_state") {
    val id = integer("id").autoIncrement()
    val stateJson = text("state_json")
    override val primaryKey = PrimaryKey(id)
}

object DatabaseFactory {
    fun init() {
        val dbFile = File("kelnic.db")
        Database.connect("jdbc:sqlite:${dbFile.absolutePath}", "org.sqlite.JDBC")

        transaction {
            SchemaUtils.createMissingTablesAndColumns(BusinessTable)
        }
    }

    fun saveState(json: String) {
        transaction {
            BusinessTable.deleteAll()
            BusinessTable.insert { it[stateJson] = json }
        }
    }

    fun loadState(): String? = transaction {
        BusinessTable.selectAll().firstOrNull()?.get(BusinessTable.stateJson)
    }
}
