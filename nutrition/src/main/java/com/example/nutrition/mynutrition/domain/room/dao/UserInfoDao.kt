package com.example.nutrition.mynutrition.domain.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.nutrition.mynutrition.domain.room.entity.UserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userInfo: UserInfoEntity)

    @Update()
    fun update(userInfo: UserInfoEntity)

    @Query("SELECT * FROM user_info LIMIT 1")
    fun getUserInfo(): UserInfoEntity?
}