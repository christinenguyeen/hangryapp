package com.example.hangryapp.data;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PreferenceDao_Impl implements PreferenceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PreferenceData> __insertionAdapterOfPreferenceData;

  private final EntityDeletionOrUpdateAdapter<PreferenceData> __deletionAdapterOfPreferenceData;

  private final EntityDeletionOrUpdateAdapter<PreferenceData> __updateAdapterOfPreferenceData;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public PreferenceDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPreferenceData = new EntityInsertionAdapter<PreferenceData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `preferences` (`id`,`name`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PreferenceData value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
      }
    };
    this.__deletionAdapterOfPreferenceData = new EntityDeletionOrUpdateAdapter<PreferenceData>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `preferences` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PreferenceData value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfPreferenceData = new EntityDeletionOrUpdateAdapter<PreferenceData>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `preferences` SET `id` = ?,`name` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PreferenceData value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM preferences";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final PreferenceData... preferenceData) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPreferenceData.insert(preferenceData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertPreference(final PreferenceData preferenceData) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPreferenceData.insert(preferenceData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletePreference(final PreferenceData preferenceData) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfPreferenceData.handle(preferenceData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updatePreference(final PreferenceData preferenceData) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfPreferenceData.handle(preferenceData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<PreferenceData>> getAllPreferences() {
    final String _sql = "Select * from preferences";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"preferences"}, false, new Callable<List<PreferenceData>>() {
      @Override
      public List<PreferenceData> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final List<PreferenceData> _result = new ArrayList<PreferenceData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PreferenceData _item;
            _item = new PreferenceData();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
