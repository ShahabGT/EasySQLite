package ir.shahabazimi.easysqlite;

public class ColumnModel {

    String name;
    String type;
    String constraint,constraint2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public String getConstraint2() {
        return constraint2;
    }

    public void setConstraint2(String constraint2) {
        this.constraint2 = constraint2;
    }
}
