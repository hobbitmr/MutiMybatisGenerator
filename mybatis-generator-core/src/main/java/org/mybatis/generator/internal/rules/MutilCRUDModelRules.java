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
package org.mybatis.generator.internal.rules;

import org.mybatis.generator.api.IntrospectedTable;

/**
 *增加批次插入,批次删除,等操作
 *作者: hobbit
 *时间 2017-05-25 11:06
 */
public class MutilCRUDModelRules extends BaseRules {

    /**
     * Instantiates a new conditional model rules.
     *
     * @param introspectedTable
     *            the introspected table
     */
    public MutilCRUDModelRules(IntrospectedTable introspectedTable) {
        super(introspectedTable);
    }

    @Override
    public boolean generatePrimaryKeyClass() {
        return false;
    }

    @Override
    public boolean generateSQLExampleWhereClause() {
        return true;
    }


    @Override
    public boolean generateBaseRecordClass() {
        return true;
    }

    @Override
    public boolean generateRecordWithBLOBsClass() {
        return false;
    }

    @Override
    public boolean generateInsertNotCheck() {
        return true;
    }

    @Override
    public boolean generateExampleClass() {
        return true;
    }


    @Override
    public boolean generateInsert() {
        return true;
    }

    @Override
    public boolean generateInserts() {
        return true;
    }

    @Override
    public boolean generateBaseResultMap() {
        return true;
    }

    @Override
    public boolean generateDeleteByWhere() {
        return true;
    }


    @Override
    public boolean generateSelectByWhere() {
        return true;
    }

    @Override
    public boolean generateMyBatis3UpdateByExampleWhereClause() {
        return true;
    }

    @Override
    public boolean generateSelectByPrimaryKey() {
        return true;
    }

    @Override
    public boolean generateCountByExample() {
        return true;
    }

    @Override
    public boolean generateUpdateByExampleSelective() {
        return true;
    }

    @Override
    public boolean generateUpdateByPrimaryKeyWithoutBLOBs() {
        return false;
    }

    @Override
    public boolean generateDeleteByPrimaryKey() {
        return super.generateDeleteByPrimaryKey();
    }

    @Override
    public boolean generateBaseColumnList() {
        return true;
    }

}
