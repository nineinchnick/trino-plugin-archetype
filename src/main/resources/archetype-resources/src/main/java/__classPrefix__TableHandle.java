/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package $package;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.trino.spi.connector.ColumnHandle;
import io.trino.spi.connector.ConnectorTableHandle;
import io.trino.spi.connector.SchemaTableName;
import io.trino.spi.connector.SortItem;
import io.trino.spi.predicate.TupleDomain;

import java.util.List;
import java.util.Optional;

public class ${classPrefix}TableHandle
        implements ConnectorTableHandle, Cloneable
{
    private final SchemaTableName schemaTableName;
    private TupleDomain<ColumnHandle> constraint;
    private int offset;
    private int limit;
    private int pageIncrement;
    private List<SortItem> sortOrder;

    @JsonCreator
    public ${classPrefix}TableHandle(
            @JsonProperty("schemaTableName") SchemaTableName schemaTableName,
            @JsonProperty("constraint") TupleDomain<ColumnHandle> constraint,
            @JsonProperty("offset") int offset,
            @JsonProperty("limit") int limit,
            @JsonProperty("pageIncrement") int pageIncrement,
            @JsonProperty("sortOrder") List<SortItem> sortOrder)
    {
        this.schemaTableName = schemaTableName;
        this.constraint = constraint;
        this.offset = offset;
        this.limit = limit;
        this.pageIncrement = pageIncrement;
        this.sortOrder = sortOrder;
    }

    public String toString()
    {
        return schemaTableName.getTableName();
    }

    @JsonProperty("schemaTableName")
    public SchemaTableName getSchemaTableName()
    {
        return schemaTableName;
    }

    @JsonProperty("constraint")
    public TupleDomain<ColumnHandle> getConstraint()
    {
        return constraint;
    }

    @JsonProperty("offset")
    public int getOffset()
    {
        return offset;
    }

    @JsonProperty("limit")
    public int getLimit()
    {
        return limit;
    }

    @JsonProperty("pageIncrement")
    public int getPageIncrement()
    {
        return pageIncrement;
    }

    @JsonProperty("sortOrder")
    public Optional<List<SortItem>> getSortOrder()
    {
        return sortOrder == null ? Optional.empty() : Optional.of(sortOrder);
    }

    @Override
    public ${classPrefix}TableHandle clone()
    {
        try {
            return (${classPrefix}TableHandle) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public ${classPrefix}TableHandle cloneWithLimit(int limit)
    {
        ${classPrefix}TableHandle tableHandle = this.clone();
        tableHandle.limit = limit;
        return tableHandle;
    }

    public ${classPrefix}TableHandle cloneWithOffset(int offset, int pageIncrement)
    {
        ${classPrefix}TableHandle tableHandle = this.clone();
        tableHandle.offset = offset;
        tableHandle.pageIncrement = pageIncrement;
        return tableHandle;
    }

    public ${classPrefix}TableHandle cloneWithConstraint(TupleDomain<ColumnHandle> constraint)
    {
        ${classPrefix}TableHandle tableHandle = this.clone();
        tableHandle.constraint = constraint;
        return tableHandle;
    }

    public ${classPrefix}TableHandle cloneWithSortOrder(List<SortItem> sortOrder)
    {
        ${classPrefix}TableHandle tableHandle = this.clone();
        tableHandle.sortOrder = sortOrder;
        return tableHandle;
    }
}