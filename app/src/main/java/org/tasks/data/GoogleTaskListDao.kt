package org.tasks.data

import androidx.room.*
import org.tasks.data.CaldavAccount.Companion.TYPE_GOOGLE_TASKS
import org.tasks.filters.GoogleTaskFilters
import org.tasks.time.DateTimeUtils.currentTimeMillis

@Dao
interface GoogleTaskListDao {
    @Query("SELECT * FROM caldav_accounts WHERE cda_account_type = $TYPE_GOOGLE_TASKS")
    suspend fun getAccounts(): List<CaldavAccount>

    @Query("SELECT * FROM caldav_lists WHERE cdl_id = :id")
    suspend fun getById(id: Long): CaldavCalendar?

    @Query("SELECT * FROM caldav_lists WHERE cdl_account = :account ORDER BY cdl_name ASC")
    suspend fun getLists(account: String): List<CaldavCalendar>

    @Query("SELECT * FROM caldav_lists WHERE cdl_uuid = :remoteId LIMIT 1")
    suspend fun getByRemoteId(remoteId: String): CaldavCalendar?

    @Query("SELECT * FROM caldav_lists WHERE cdl_uuid IN (:remoteIds)")
    suspend fun getByRemoteId(remoteIds: List<String>): List<CaldavCalendar>

    @Query("UPDATE caldav_lists SET cdl_last_sync = 0 WHERE cdl_account = :account")
    suspend fun resetLastSync(account: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(googleTaskList: CaldavCalendar): Long

    @Query("SELECT caldav_lists.*, COUNT(tasks._id) AS count"
            + " FROM caldav_lists "
            + " LEFT JOIN google_tasks ON google_tasks.gt_list_id = caldav_lists.cdl_uuid"
            + " LEFT JOIN tasks ON google_tasks.gt_task = tasks._id AND tasks.deleted = 0 AND tasks.completed = 0 AND tasks.hideUntil < :now AND gt_deleted = 0"
            + " WHERE caldav_lists.cdl_account = :account"
            + " GROUP BY caldav_lists.cdl_uuid")
    suspend fun getGoogleTaskFilters(account: String, now: Long = currentTimeMillis()): List<GoogleTaskFilters>
}