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
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.Iterator;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getGetterMethodName;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class CustomColumnListElementGenerator extends AbstractXmlElementGenerator {


    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$
        parentElement.addElement(answer);
        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                introspectedTable.getCustomColumnListId()));

        StringBuilder sb = new StringBuilder();
        FullyQualifiedJavaType fqjtBoolean = FullyQualifiedJavaType
                .getBooleanPrimitiveInstance();

        XmlElement insertTrimElement = new XmlElement("trim"); //$NON-NLS-1$
        insertTrimElement.addAttribute(new Attribute("suffixOverrides", ",")); //$NON-NLS-1$ //$NON-NLS-2$
        answer.addElement(insertTrimElement);
        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getAllColumns()) {
            XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append("column." + getGetterMethodName("Had" + introspectedColumn.getJavaProperty()+"()", fqjtBoolean));
            ifElement.addAttribute(new Attribute(
                    "test", sb.toString())); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getActualColumnName());
            sb.append(',');
            ifElement.addElement(new TextElement(sb.toString()));
            insertTrimElement.addElement(ifElement);
        }
    }
}
