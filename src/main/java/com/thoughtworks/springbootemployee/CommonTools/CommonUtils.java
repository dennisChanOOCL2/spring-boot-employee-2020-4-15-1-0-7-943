package com.thoughtworks.springbootemployee.CommonTools;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    public static final String MALE = "male";
    public static final String FEMALE = "female";

    public List pagingForList(List inputList, Integer page, Integer pageSize) {

        List returnList = new ArrayList<>(inputList);

        if (page != null && pageSize != null) {
            try {
                returnList = returnList.subList((page-1) * pageSize, (page-1) * pageSize + pageSize);
                return returnList;
            } catch (IndexOutOfBoundsException expcetion) {
                return null;
            }
        }

        return returnList;
    }



}
