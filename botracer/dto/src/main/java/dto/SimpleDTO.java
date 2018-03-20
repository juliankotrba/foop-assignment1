package dto;

import java.io.Serializable;

public class SimpleDTO  {
    private String name;
    private Integer count;

    public SimpleDTO(String name, Integer count){
        this.name=name;
        this.count=count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "SimpleDTO{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
