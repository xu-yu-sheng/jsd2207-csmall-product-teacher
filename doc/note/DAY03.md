# 20. 关于`<resultMap>`与`<sql>`标签

Mybatis框架在处理查询时，会自动的将**列名**与**属性名**完全一致的数据进行封装（例如查询结果集中列名为`name`的值会自动封装到返回值对象的`name`属性中），如果名称不一致，则不会自动封装！

通常，建议通过`<resultMap>`标签来配置列名与属性名的对应关系，以指导Mybatis如何处理结果集。

另外，还建议使用`<sql>`标签来封装查询的字段列表，并通过`<include>`标签来引用封装的查询字段列表，例如：

```xml
<!-- CategoryStandardVO getStandardById(Long id); -->
<select id="getStandardById" resultMap="StandardResultMap">
    SELECT
        <include refid="StandardQueryFields"/>
    FROM
        pms_category
    WHERE
        id=#{id}
</select>

<sql id="StandardQueryFields">
    <if test="true">
        id, name, parent_id, depth, keywords, sort, icon, enable, is_parent, is_display
    </if>
</sql>

<resultMap id="StandardResultMap" type="cn.tedu.csmall.product.pojo.vo.CategoryStandardVO">
    <id column="id" property="id"/>
    <result column="name" property="name"/>
    <result column="parent_id" property="parentId"/>
    <result column="depth" property="depth"/>
    <result column="keywords" property="keywords"/>
    <result column="sort" property="sort"/>
    <result column="icon" property="icon"/>
    <result column="enable" property="enable"/>
    <result column="is_parent" property="isParent"/>
    <result column="is_display" property="isDisplay"/>
</resultMap>
```

注意：配置`<select>`标签时，如果使用`resultMap`属性，则此属性的值必须是`<resultMap>`的`id`值！如果使用`resultMap`属性，则此属性的值必须是返回结果类型的全限定名！

如果在`<select>`配置的`resultMap`的值有误，则会出现如下错误（错误示例）：

```
java.lang.IllegalArgumentException: Result Maps collection does not contain value for cn.tedu.csmall.product.pojo.vo.CategoryStandardVO
```

如果在`<select>`配置的`resultType`的值有误，则会出现如下错误（错误示例）：

```
Caused by: org.apache.ibatis.builder.BuilderException: Error parsing Mapper XML. The XML location is 'file [D:\IdeaProjects\jsd2207-csmall-product-teacher\target\classes\mapper\CategoryMapper.xml]'. Cause: org.apache.ibatis.builder.BuilderException: Error resolving class. Cause: org.apache.ibatis.type.TypeException: Could not resolve type alias 'StandardResultMap'.  Cause: java.lang.ClassNotFoundException: Cannot find class: StandardResultMap

Caused by: org.apache.ibatis.builder.BuilderException: Error resolving class. Cause: org.apache.ibatis.type.TypeException: Could not resolve type alias 'StandardResultMap'.  Cause: java.lang.ClassNotFoundException: Cannot find class: StandardResultMap

Caused by: org.apache.ibatis.type.TypeException: Could not resolve type alias 'StandardResultMap'.  Cause: java.lang.ClassNotFoundException: Cannot find class: StandardResultMap

Caused by: java.lang.ClassNotFoundException: Cannot find class: StandardResultMap
```

如果同一个XML文件中有2个`<resultMap>`的`id`完全相同，则会出现以下错误（错误示例），或者，如果存在多个XML文件，但`<mapper>`标签的`namespace`值相同，且存在相同`id`的`<resultMap>`，也会出现此错误：

```
Caused by: org.apache.ibatis.builder.BuilderException: Error parsing Mapper XML. The XML location is 'file [D:\IdeaProjects\jsd2207-csmall-product-teacher\target\classes\mapper\CategoryMapper.xml]'. Cause: java.lang.IllegalArgumentException: Result Maps collection already contains value for cn.tedu.csmall.product.mapper.CategoryMapper.StandardResultMap

Caused by: java.lang.IllegalArgumentException: Result Maps collection already contains value for cn.tedu.csmall.product.mapper.CategoryMapper.StandardResultMap
```



