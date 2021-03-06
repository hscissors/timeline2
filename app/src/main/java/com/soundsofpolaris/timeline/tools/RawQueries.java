package com.soundsofpolaris.timeline.tools;

import android.provider.ContactsContract;

public class RawQueries {
    public static String CREATE_EVENTS_TABLE() {
        return "CREATE TABLE " + DatabaseHelper.EVENTS_TABLE + " (" +
                DatabaseHelper.ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseHelper.YEAR_COL + " INTEGER NOT NULL, " +
                DatabaseHelper.DATE_COL + " INTEGER NOT NULL, " +
                DatabaseHelper.TITLE_COL + " TEXT NOT NULL, " +
                DatabaseHelper.DESC_COL + " TEXT NOT NULL, " +
                DatabaseHelper.TIMELINE_ID_COL + " INTERGER NOT NULL);";
    }

    public static String CREATE_TIMELINE_TABLE() {
        return "CREATE TABLE " + DatabaseHelper.TIMELINE_TABLE + " (" +
                DatabaseHelper.TIMELINE_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseHelper.TIMELINE_NAME_COL + " TEXT NOT NULL, " +
                DatabaseHelper.TIMELINE_COLOR_COL + " INTEGER NOT NULL);";
    }

    public static String CREATE_TIMELINE_TO_TIMELINE_TABLE() {
        return "CREATE TABLE " + DatabaseHelper.TIMELINE_TO_TIMELINE_TABLE + " (" +
                DatabaseHelper.TT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseHelper.TT_PARENT_ID + " INTEGER NOT NULL, " +
                DatabaseHelper.TT_LINKED_ID + " INTEGER NOT NULL);";
    }

    public static String DROP_EVENTS_TABLE() {
        return "DROP TABLE IF EXISTS " + DatabaseHelper.EVENTS_TABLE;
    }

    public static String DROP_TIMELINES_TABLE() {
        return "DROP TABLE IF EXISTS " + DatabaseHelper.TIMELINE_TABLE;
    }

    public static String ALL_EVENTS_BY_TIMELINE(long timelineId) {
        return "SELECT * FROM " + DatabaseHelper.EVENTS_TABLE +" INNER JOIN " + DatabaseHelper.TIMELINE_TABLE + " ON eventstable.gid = groupstable.gid WHERE id = " + timelineId + " ORDER BY " + DatabaseHelper.YEAR_COL + " ASC, " + DatabaseHelper.ALLYEAR_COL + " DESC, " + DatabaseHelper.MONTH_COL + " ASC, " + DatabaseHelper.ALLMONTH_COL + " DESC," + DatabaseHelper.DATE_COL + " ASC";
    }

    public static String ALL_TIMELINES() {
        return "SELECT * FROM " + DatabaseHelper.TIMELINE_TABLE + " ORDER BY gname ASC";
    }

    public static String TOTAL_EVENTS(long timelineId){
        return "SELECT COUNT(*) FROM " + DatabaseHelper.EVENTS_TABLE + " WHERE " + DatabaseHelper.TIMELINE_ID_COL + " = " + timelineId;
    }
//    public static String GET_LINKED_TIMELINE_IDS(ArrayList<Integer> parentGroupIds) {
//        StringBuilder parents = new StringBuilder();
//        for (int i = 0; i < parentGroupIds.size(); i++) {
//            if (i != parentGroupIds.size() - 1) {
//                parents.append(DatabaseHelper.GTG_PARENT_ID + " = " + parentGroupIds.get(i) + " or ");
//            } else {
//                parents.append(DatabaseHelper.GTG_PARENT_ID + " = " + parentGroupIds.get(parentGroupIds.size() - 1));
//
//            }
//        }
//
//        return "SELECT * FROM " + DatabaseHelper.TIMELINE_TO_TIMELINE_TABLE + " WHERE " + parents.toString();
//    }

//For debugging
//    public static final String ALL_EVENTS(){
//        return "SELECT * FROM eventstable INNER JOIN groupstable ON eventstable.gid = groupstable.gid ORDER BY " + DatabaseHelper.YEAR_COL + " ASC, " + DatabaseHelper.DATE_COL + " ASC, " + DatabaseHelper.ALLYEAR_COL + " DESC, " + DatabaseHelper.ALLMONTH_COL + " DESC";
//    }
}
