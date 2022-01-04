package com.scheduled.data.table

import org.jetbrains.exposed.sql.Table

object NoteTable: Table() {
    val id = varchar("id",512)
    val userEmail = varchar("userEmail", 512).references(UserTable.email)
    val title = text("title")
    val content = text("content")
    val timeStamp = long("date")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}