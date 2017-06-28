/**
 *    Copyright ${license.git.copyrightYears} the original author or authors.
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

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * @author Jeff Butler
 */
public class WhereClauseElementGenerator extends
        AbstractXmlElementGenerator {

    private boolean isForUpdateByExample;

    public WhereClauseElementGenerator(boolean isForUpdateByExample) {
        super();
        this.isForUpdateByExample = isForUpdateByExample;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getWhereClauseId())); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        XmlElement whereElement = new XmlElement("where"); //$NON-NLS-1$
        answer.addElement(whereElement);

        XmlElement outerForEachElement = new XmlElement("foreach"); //$NON-NLS-1$
        outerForEachElement.addAttribute(new Attribute(
                "collection", "where.criterion")); //$NON-NLS-1$ //$NON-NLS-2$
        outerForEachElement.addAttribute(new Attribute("item", "criteria")); //$NON-NLS-1$ //$NON-NLS-2$
        outerForEachElement.addAttribute(new Attribute("separator", " and ")); //$NON-NLS-1$ //$NON-NLS-2$
        whereElement.addElement(outerForEachElement);

        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "criteria.param!= null")); //$NON-NLS-1$ //$NON-NLS-2$

        StringBuilder sb = new StringBuilder();

        sb.append("#{"); //$NON-NLS-1$
        sb.append("criteria.param");
        sb.append(",jdbcType="); //$NON-NLS-1$
        sb.append(FullyQualifiedJavaType.getStringInstance());
        sb.append("}");
        sb.append("=");
        sb.append("#{"); //$NON-NLS-1$
        sb.append("criteria.value");
        sb.append(",jdbcType="); //$NON-NLS-1$
        sb.append(FullyQualifiedJavaType.getStringInstance());
        sb.append("}");

        ifElement.addElement(new TextElement(sb.toString()));
        outerForEachElement.addElement(ifElement);
        if (context.getPlugins()
                .sqlMapExampleWhereClauseElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }


}
