/**
 * Copyright 2006-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.model;

import com.sun.deploy.util.ArrayUtil;
import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.RootClassInfo;
import org.mybatis.generator.config.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mybatis.generator.internal.util.JavaBeansUtil.*;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 列自定义生成器
 * 作者: hobbit
 * 时间 2017-06-30 04:41
 */
public class ColumnCustomerGenerator extends AbstractJavaGenerator {

    public ColumnCustomerGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString(
                "Progress.8", table.toString())); //$NON-NLS-1$
        Plugin plugins = context.getPlugins();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getColumnCustomName());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        List<IntrospectedColumn> introspectedColumns = getColumnsInThisClass();

        Field columNameMap = new Field();
        FullyQualifiedJavaType fqjtColumnName = new FullyQualifiedJavaType("Map<String,Boolean>");
        columNameMap.setVisibility(JavaVisibility.PRIVATE);
        columNameMap.setType(fqjtColumnName);
        columNameMap.setName("columNameMap");
        columNameMap.setInitializationString("new HashMap<>()");
        topLevelClass.addField(columNameMap);
        topLevelClass.addImportedType(columNameMap.getType());
        topLevelClass.addImportedType(FullyQualifiedJavaType.getNewMapInstance());
        topLevelClass.addImportedType(FullyQualifiedJavaType.getNewHashMapInstance());
        List<String> bodylineList = new ArrayList<>();
        String rootClass = getRootClass();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            if (RootClassInfo.getInstance(rootClass, warnings)
                    .containsProperty(introspectedColumn)) {
                continue;
            }
            bodylineList.add(introspectedColumn.getActualColumnName());
            Method method = getJavaBeansGetter(introspectedColumn, context, introspectedTable);
            topLevelClass.addMethod(method);

            method = getJavaBeansSetter(introspectedColumn, context, introspectedTable);
            topLevelClass.addMethod(method);

        }
        addDefaultConstructor(topLevelClass,bodylineList);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelExampleClassGenerated(
                topLevelClass, introspectedTable)) {
            answer.add(topLevelClass);
        }
        return answer;
    }

    public Method getJavaBeansSetter(IntrospectedColumn introspectedColumn,
                                     Context context,
                                     IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fqjt = FullyQualifiedJavaType
                .getBooleanPrimitiveInstance();
        String property = introspectedColumn.getActualColumnName();

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(getSetterMethodName("Had"+introspectedColumn.getJavaProperty()));
        method.addParameter(new Parameter(fqjt, "isHad"));
        context.getCommentGenerator().addSetterComment(method,
                introspectedTable, introspectedColumn);

        StringBuilder sb = new StringBuilder();
        sb.append("columNameMap.put(\"" + introspectedColumn.getActualColumnName() + "\",isHad);"); //$NON-NLS-1$
        method.addBodyLine(sb.toString());
        return method;
    }

    public Method getJavaBeansGetter(IntrospectedColumn introspectedColumn,
                                     Context context,
                                     IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType fqjt = FullyQualifiedJavaType
                .getBooleanPrimitiveInstance();

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(getGetterMethodName("Had"+introspectedColumn.getJavaProperty(), fqjt));
        method.setReturnType(fqjt);
        context.getCommentGenerator().addGetterComment(method,
                introspectedTable, introspectedColumn);
        StringBuilder sb = new StringBuilder();
        sb.append("return columNameMap.get(\"" + introspectedColumn.getActualColumnName() + "\");"); //$NON-NLS-1$
        method.addBodyLine(sb.toString());
        return method;
    }

    protected void addDefaultConstructor(TopLevelClass topLevelClass,List<String> bodyList) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(topLevelClass.getType().getShortName());
        for(String bodyLine:bodyList){
            method.addBodyLine("columNameMap.put(\"" + bodyLine + "\",false);"); //$NON-NLS-1$
        }
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }



    private boolean includePrimaryKeyColumns() {
        return !introspectedTable.getRules().generatePrimaryKeyClass()
                && introspectedTable.hasPrimaryKeyColumns();
    }

    private boolean includeBLOBColumns() {
        return !introspectedTable.getRules().generateRecordWithBLOBsClass()
                && introspectedTable.hasBLOBColumns();
    }

    private List<IntrospectedColumn> getColumnsInThisClass() {
        List<IntrospectedColumn> introspectedColumns;
        if (includePrimaryKeyColumns()) {
            if (includeBLOBColumns()) {
                introspectedColumns = introspectedTable.getAllColumns();
            } else {
                introspectedColumns = introspectedTable.getNonBLOBColumns();
            }
        } else {
            if (includeBLOBColumns()) {
                introspectedColumns = introspectedTable
                        .getNonPrimaryKeyColumns();
            } else {
                introspectedColumns = introspectedTable.getBaseColumns();
            }
        }
        return introspectedColumns;
    }
}
