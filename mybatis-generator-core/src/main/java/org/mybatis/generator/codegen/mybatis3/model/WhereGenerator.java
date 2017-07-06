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

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

/**
 *
 * @author hobbit
 *
 */
public class WhereGenerator extends AbstractJavaGenerator {

    public WhereGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString(
                "Progress.6", table.toString())); //$NON-NLS-1$
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType whereClasstype = new FullyQualifiedJavaType(
                introspectedTable.getWhereType());
        TopLevelClass topLevelClass = new TopLevelClass(whereClasstype);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);

        //生产条件内部类
        InnerClass criterion = getCriterionInnerClass();


        // add default constructor
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(whereClasstype.getShortName());
        method.addBodyLine("this.criterions = new ArrayList<>();"); //$NON-NLS-1$
        topLevelClass.addMethod(method);
        topLevelClass.addImportedType(FullyQualifiedJavaType.getNewArrayListInstance());

        // add field, getter, setter for orderby clause
        Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(FullyQualifiedJavaType.getStringInstance());
        field.setName("orderBy"); //$NON-NLS-1$
        commentGenerator.addFieldComment(field, "排序条件");
        topLevelClass.addField(field);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("setOrderBy"); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType
                .getStringInstance(), "orderBy")); //$NON-NLS-1$
        method.addBodyLine("this.orderBy = orderBy;"); //$NON-NLS-1$
        method.addBodyLine("return this;"); //$NON-NLS-1$
        method.setReturnType(whereClasstype);
        commentGenerator.addGeneralMethodComment(method, "设置排序条件");
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setName("getOrderBy"); //$NON-NLS-1$
        method.addBodyLine("return orderBy;"); //$NON-NLS-1$
        commentGenerator.addGeneralMethodComment(method, "获取排序条件");
        topLevelClass.addMethod(method);

        // add field, getter, setter for distinct
        field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(FullyQualifiedJavaType.getBooleanPrimitiveInstance());
        field.setName("distinct"); //$NON-NLS-1$
        commentGenerator.addFieldComment(field, "是否会设置 distinct 属性");
        topLevelClass.addField(field);


        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("setDistinct"); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType
                .getBooleanPrimitiveInstance(), "distinct")); //$NON-NLS-1$
        method.addBodyLine("this.distinct = distinct;"); //$NON-NLS-1$
        method.addBodyLine("return this;"); //$NON-NLS-1$
        method.setReturnType(whereClasstype);
        commentGenerator.addGeneralMethodComment(method, "是设置 是否distinct");
        topLevelClass.addMethod(method);


        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType
                .getBooleanPrimitiveInstance());
        method.setName("isDistinct"); //$NON-NLS-1$
        method.addBodyLine("return distinct;"); //$NON-NLS-1$
        commentGenerator.addGeneralMethodComment(method, "是否会设置 distinct 属性");
        topLevelClass.addMethod(method);

        // add field and methods for the list of ored criteria
        field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);


        FullyQualifiedJavaType criterionsJavaType = new FullyQualifiedJavaType(
                "List<" + criterion.getType().getFullyQualifiedName() + ">"); //$NON-NLS-1$
        field.setType(criterionsJavaType);
        field.setName("criterions"); //$NON-NLS-1$
        commentGenerator.addFieldComment(field, "条件列表");
        topLevelClass.addField(field);
        topLevelClass.addImportedType(FullyQualifiedJavaType.getNewListInstance());

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(criterionsJavaType);
        method.setName("getCriterion"); //$NON-NLS-1$
        method.addBodyLine("return criterions;"); //$NON-NLS-1$
        commentGenerator.addGeneralMethodComment(method, "获取条件列表");
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("and"); //$NON-NLS-1$
        method.addParameter(new Parameter(criterion.getType(), "criterion")); //$NON-NLS-1$
        method.addBodyLine("criterions.add(criterion);"); //$NON-NLS-1$
        method.addBodyLine("return this;"); //$NON-NLS-1$
        method.setReturnType(whereClasstype);
        commentGenerator.addGeneralMethodComment(method, "添加条件对象");
        topLevelClass.addMethod(method);

        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("clear"); //$NON-NLS-1$
        method.addBodyLine("criterions.clear();"); //$NON-NLS-1$
        method.addBodyLine("orderBy = null;"); //$NON-NLS-1$
        method.addBodyLine("distinct = false;"); //$NON-NLS-1$
        commentGenerator.addGeneralMethodComment(method, "清除所有参数");
        topLevelClass.addMethod(method);

        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            builderColumnCriterion(introspectedColumn, topLevelClass, commentGenerator, criterion);
        }

        topLevelClass.addInnerClass(criterion);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelExampleClassGenerated(
                topLevelClass, introspectedTable)) {
            answer.add(topLevelClass);
        }
        return answer;
    }

    private void builderColumnCriterion(IntrospectedColumn introspectedColumn, TopLevelClass topLevelClass,
                                        CommentGenerator commentGenerator, InnerClass criterionClass) {

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append(introspectedColumn.getJavaProperty());
        if (Character.isLowerCase(sb.charAt(0))) {
            if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            }
        }
        method.setName("builder" + sb.toString()); //$NON-NLS-1$
        method.setStatic(true);
        FullyQualifiedJavaType paramType = introspectedColumn
                .getFullyQualifiedJavaType();

        if (paramType.compareTo(FullyQualifiedJavaType.getDateInstance()) == 0) {
            paramType = FullyQualifiedJavaType.getStringInstance();
        }
        method.addParameter(new Parameter(paramType, "value")); //$NON-NLS-1$
        method.setReturnType(criterionClass.getType());
        method.addBodyLine("return new "
                + criterionClass.getType() + "(\"" + introspectedColumn.getActualColumnName() + "\",value,\"" + introspectedColumn.getJdbcTypeName() + "\");"); //$NON-NLS-1$
        commentGenerator.addGeneralMethodComment(method, "添加条件--" + introspectedColumn.getRemarks());
        topLevelClass.addMethod(method);

    }

    private InnerClass getCriterionInnerClass() {
        Field field;
        Method method;

        InnerClass answer = new InnerClass(new FullyQualifiedJavaType(
                "Criterion")); //$NON-NLS-1$
        answer.setVisibility(JavaVisibility.PUBLIC);
        answer.setStatic(true);
        context.getCommentGenerator().addClassComment(answer,
                "查询条件类");

        field = new Field();
        field.setName("param"); //$NON-NLS-1$
        field.setType(FullyQualifiedJavaType.getStringInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));

        field = new Field();
        field.setName("value"); //$NON-NLS-1$
        field.setType(FullyQualifiedJavaType.getObjectInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));

        field = new Field();
        field.setName("jdbcType"); //$NON-NLS-1$
        field.setType(FullyQualifiedJavaType.getStringInstance());
        field.setVisibility(JavaVisibility.PRIVATE);
        answer.addField(field);
        answer.addMethod(getGetter(field));


        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("Criterion"); //$NON-NLS-1$
        method.setConstructor(true);
        method.addParameter(new Parameter(FullyQualifiedJavaType
                .getStringInstance(), "param")); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType
                .getObjectInstance(), "value")); //$NON-NLS-1$
        method.addParameter(new Parameter(FullyQualifiedJavaType
                .getStringInstance(), "jdbcType")); //$NON-NLS-1$
        method.addBodyLine("this.param=param;");
        method.addBodyLine("this.value=value;");
        method.addBodyLine("this.jdbcType=jdbcType;");
        answer.addMethod(method);
        return answer;
    }

}
