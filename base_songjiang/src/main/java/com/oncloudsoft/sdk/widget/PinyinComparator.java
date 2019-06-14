package com.oncloudsoft.sdk.widget;


import com.oncloudsoft.sdk.entity.GroupDetailData;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<GroupDetailData> {

	@Override
	public int compare(GroupDetailData o1, GroupDetailData o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
