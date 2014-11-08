package com.soundsofpolaris.timeline.event;

import com.soundsofpolaris.timeline.timeline.Timeline;

import java.text.SimpleDateFormat;

public class Event {
	private long mId;
	private int mYear;
	private int mMonth;
	private long mDate;
	private String mTitle;
	private String mDesc;
	private Timeline mParentTimeline;
	private Boolean mIsAllYear;
	private Boolean mIsAllMonth;
	
	public Event(long id, int year, int month, long date, String title, String desc, boolean isAllYear, boolean isAllMonth, Timeline parentTimeline){
		mId = id;
		mYear = year;
		mMonth = month;
		mDate = date;
		mTitle = title;
		mDesc = desc;
		mIsAllYear = isAllYear;
		mIsAllMonth = isAllMonth;
		mParentTimeline = parentTimeline;
	}
	
	public long getId(){
		return mId;
	}

	public String getTitle(){
		return mTitle;
	}
	
	public String getDescription(){
		return mDesc;
	}
	
	public int getYear(){
		return mYear;
	}

    public String getMonth(){
        SimpleDateFormat sf = new SimpleDateFormat("MMM");
        return sf.format(mDate);
    }

    public String getDay(){
        SimpleDateFormat sf = new SimpleDateFormat("d");
        return sf.format(mDate);
    }
	
	public String getPrettyDate(){
		SimpleDateFormat sf;
		if(mIsAllYear){
			sf = new SimpleDateFormat("y");
		} else if (mIsAllMonth) {
			sf = new SimpleDateFormat("MMMMM");
		} else {
			sf = new SimpleDateFormat("MMMMM dd");
		}
		return sf.format(mDate);
	}
	
	public long getUnixDate(){
		return mDate;
	}
	
	public Timeline getParentTimeline(){
		return mParentTimeline;
	}

	public Boolean isAllYear(){
		return mIsAllYear;
	}
	
	public Boolean isAllMonth(){
		return mIsAllMonth;
	}
	
	@Override
	public String toString(){
		return mTitle + ", " + getYear() + ", " + getPrettyDate() + ", " + ", allyear?" + mIsAllYear + ", allmonth?" + mIsAllMonth;
	}
}
