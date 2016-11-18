package com.ccma.itri.org.tw.carpediem.SorttingList;

import com.ccma.itri.org.tw.carpediem.EventObject.BackpackItem;

import java.util.Comparator;

/**
 * Created by A40503 on 2016/11/17.
 */

public class SorttingComarator {

    public static class BackpackItemSortleft implements Comparator<BackpackItem>{
        @Override
        public int compare(BackpackItem o1, BackpackItem o2) {
            return ((Integer)o1.getDayLeft()).compareTo(o2.getDayLeft());
        }
    }

    public static class BackpackItemSortCreatedAt implements Comparator<BackpackItem>{
        @Override
        public int compare(BackpackItem o1, BackpackItem o2) {
            return o1.getCreatedAt().compareTo(o2.getCreatedAt());
        }
    }

    public static class BackpackItemSortOwner implements Comparator<BackpackItem>{
        @Override
        public int compare(BackpackItem o1, BackpackItem o2) {
            return o1.getAdvertiser().getId().compareTo(o2.getAdvertiser().getId());
        }
    }
}
