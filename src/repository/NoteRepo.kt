package com.scheduled.repository

import org.jetbrains.exposed.sql.*
import com.scheduled.data.model.Note
import com.scheduled.data.table.NoteTable

class noteRepo {

    suspend fun addnote(note: Note, email:String){
        DatabaseFactory.dbQuery {
            NoteTable.insert { table->
                table[NoteTable.id] = note.id
                table[NoteTable.title] = note.title
                table[NoteTable.timeStamp] = note.timeStamp
                table[NoteTable.userEmail] = email
            }
        }
    }

    suspend fun getAllnotes(email: String):List<Note>  =
        DatabaseFactory.dbQuery {
            NoteTable.select{
                NoteTable.userEmail.eq(email)
            }.mapNotNull {
                rowTonote(it)
            }
        }

    suspend fun udpatenote(note: Note, email: String){
        DatabaseFactory.dbQuery {
            NoteTable.update(
                where = {
                    NoteTable.userEmail.eq(email) and NoteTable.id.eq(note.id)
                },
                body = { table ->
                    table[NoteTable.title] = note.title
                    table[NoteTable.timeStamp] = note.timeStamp
                }
            )
        }
    }

    suspend fun deletenote(noteId:String){
        DatabaseFactory.dbQuery {
            NoteTable.deleteWhere {
                NoteTable.id.eq(noteId)
            }
        }
    }


    private fun rowTonote(resultRow: ResultRow?): Note? {
        if(resultRow == null){
            return null
        }
        else{
            return Note(
                id = resultRow[NoteTable.id],
                timeStamp = resultRow[NoteTable.timeStamp],
                title = resultRow[NoteTable.title]
            )
        }
    }

}