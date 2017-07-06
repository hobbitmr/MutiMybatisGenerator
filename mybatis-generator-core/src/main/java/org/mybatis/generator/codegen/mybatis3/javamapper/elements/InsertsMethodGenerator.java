/**
 *    Copyright 2006-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author hobbit
 * 
 */
public class InsertsMethodGenerator extends
        AbstractJavaMapperMethodGenerator {

    public InsertsMethodGenerator() {
        super();
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();

        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(introspectedTable.getInsertSelectiveStatementId());
        FullyQualifiedJavaType parameterType_model = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());

        FullyQualifiedJavaType parameterType =new FullyQualifiedJavaType(" java.util.List<"+parameterType_model.getShortName()+">");
        importedTypes.add(parameterType);

        method.addParameter(new Parameter(parameterType, "records")); //$NON-NLS-1$
        context.getCommentGenerator().addGeneralMethodComment(method,
                "批量插入记录,如果字段为NUL,则被过滤掉");

        addMapperAnnotations(interfaze, method);
        
        if (context.getPlugins().clientInsertSelectiveMethodGenerated(
                method, interfaze, introspectedTable)) {
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {
    }
}
