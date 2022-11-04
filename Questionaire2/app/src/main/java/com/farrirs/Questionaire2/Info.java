package com.farrirs.Questionaire2;

public class Info {
    private static int[] index=new int[6];
    private static String gender;
    private static String age;
    private static String name;
    public static int getIndex(int idx)
    {
        return index[idx];
    }
    public static void setIndex(int idx, int val)
    {
        index[idx]=val;
    }
    public static String getGender()
    {
        return gender;
    }
    public static void setGender(String val)
    {
        gender=val;
    }
    public static String getAge()
    {
        return age;
    }
    public static void setAge(String val)
    {
        age=val;
    }
    public static String getName()
    {
        return name;
    }
    public static void setName(String val)
    {
        name=val;
    }
}
