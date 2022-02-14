import Annotations.*;

@Entity(tableName = "TestTable")
public class TestClass {
    @PrimaryKey
    @Property(fieldName = "id")
    private Integer id;

    @Property(fieldName = "name")
    private String name;

    public TestClass() {
    }

    public TestClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Getter(fieldName = "id")
    public Integer getId() {
        return id;
    }

    @Getter(fieldName = "name")
    public String getName() {
        return name;
    }

    @Setter(fieldName = "id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Setter(fieldName = "name")
    public void setName(String name) {
        this.name = name;
    }
}
