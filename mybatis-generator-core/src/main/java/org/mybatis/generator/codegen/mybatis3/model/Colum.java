package org.mybatis.generator.codegen.mybatis3.model;

/**
 * 描述:
 * 创建者: hobbit(欧鹏程)
 * 时间:2017/6/30
 */
public class Colum {
    private String[] columnName={"1","2"};
    private int[] columnIndex={0,0};

    public Colum setA(boolean a) {
        columnIndex[0]=1;
        return this;
    }


}
