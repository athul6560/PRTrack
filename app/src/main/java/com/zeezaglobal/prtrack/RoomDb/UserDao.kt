package com.zeezaglobal.prtrack.RoomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zeezaglobal.prtrack.Entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    @Insert
    suspend fun insertUser(user: User)
}