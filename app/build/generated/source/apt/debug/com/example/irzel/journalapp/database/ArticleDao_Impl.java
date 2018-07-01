package com.example.irzel.journalapp.database;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ArticleDao_Impl implements ArticleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfJornalArticle;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfJornalArticle;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfJornalArticle;

  public ArticleDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfJornalArticle = new EntityInsertionAdapter<JornalArticle>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `article`(`id`,`title`,`info`,`owner`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, JornalArticle value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getInfo() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getInfo());
        }
        if (value.getOwner() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getOwner());
        }
        final Long _tmp;
        _tmp = DateConversor.toTimestamp(value.getUpdateDate());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
      }
    };
    this.__deletionAdapterOfJornalArticle = new EntityDeletionOrUpdateAdapter<JornalArticle>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `article` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, JornalArticle value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfJornalArticle = new EntityDeletionOrUpdateAdapter<JornalArticle>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `article` SET `id` = ?,`title` = ?,`info` = ?,`owner` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, JornalArticle value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getInfo() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getInfo());
        }
        if (value.getOwner() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getOwner());
        }
        final Long _tmp;
        _tmp = DateConversor.toTimestamp(value.getUpdateDate());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        stmt.bindLong(6, value.getId());
      }
    };
  }

  @Override
  public void insertArticle(JornalArticle jornalArticle) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfJornalArticle.insert(jornalArticle);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteArticle(JornalArticle jornalArticle) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfJornalArticle.handle(jornalArticle);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateArticle(JornalArticle jornalArticle) {
    __db.beginTransaction();
    try {
      __updateAdapterOfJornalArticle.handle(jornalArticle);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<JornalArticle>> loadAllArticles() {
    final String _sql = "SELECT * FROM article ORDER BY updated_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<JornalArticle>>() {
      private Observer _observer;

      @Override
      protected List<JornalArticle> compute() {
        if (_observer == null) {
          _observer = new Observer("article") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfInfo = _cursor.getColumnIndexOrThrow("info");
          final int _cursorIndexOfOwner = _cursor.getColumnIndexOrThrow("owner");
          final int _cursorIndexOfUpdateDate = _cursor.getColumnIndexOrThrow("updated_at");
          final List<JornalArticle> _result = new ArrayList<JornalArticle>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final JornalArticle _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpInfo;
            _tmpInfo = _cursor.getString(_cursorIndexOfInfo);
            final String _tmpOwner;
            _tmpOwner = _cursor.getString(_cursorIndexOfOwner);
            final Date _tmpUpdateDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfUpdateDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfUpdateDate);
            }
            _tmpUpdateDate = DateConversor.toDate(_tmp);
            _item = new JornalArticle(_tmpId,_tmpTitle,_tmpInfo,_tmpOwner,_tmpUpdateDate);
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
    }.getLiveData();
  }

  @Override
  public LiveData<JornalArticle> loadArticleById(int id) {
    final String _sql = "SELECT * FROM article WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return new ComputableLiveData<JornalArticle>() {
      private Observer _observer;

      @Override
      protected JornalArticle compute() {
        if (_observer == null) {
          _observer = new Observer("article") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfInfo = _cursor.getColumnIndexOrThrow("info");
          final int _cursorIndexOfOwner = _cursor.getColumnIndexOrThrow("owner");
          final int _cursorIndexOfUpdateDate = _cursor.getColumnIndexOrThrow("updated_at");
          final JornalArticle _result;
          if(_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpInfo;
            _tmpInfo = _cursor.getString(_cursorIndexOfInfo);
            final String _tmpOwner;
            _tmpOwner = _cursor.getString(_cursorIndexOfOwner);
            final Date _tmpUpdateDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfUpdateDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfUpdateDate);
            }
            _tmpUpdateDate = DateConversor.toDate(_tmp);
            _result = new JornalArticle(_tmpId,_tmpTitle,_tmpInfo,_tmpOwner,_tmpUpdateDate);
          } else {
            _result = null;
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
    }.getLiveData();
  }
}
