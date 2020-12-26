import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

enum Color {
    RED, GREEN, YELLOW
}

enum Family {
    LIVERWORTS, HORSETAILS, CONIFERS,FLOWERING
}

class Plant {
    String name;
    Color color;
    Family family;

    public Plant(String name, Color color, Family family) {
        this.name = name;
        this.color = color;
        this.family = family;
    }

    @Override
    public String toString() {
        return new StringJoiner(",")
                .add("name='" + name + "'")
                .add("color=" + color)
                .add("family=" + family)
                .toString();
    }
}

// With this approach, whenever we need a new filter this class needs to be changed
class PlantFilter {

    public Stream<Plant> filterByColor(List<Plant> plants, Color color){
        return plants.stream().filter(p -> p.color == color);
    }

    public Stream<Plant> filterByFamily(List<Plant> plants, Family family){
        return plants.stream().filter(p -> p.family == family);
    }

    public Stream<Plant> filterByColorAndFamily(List<Plant> plants, Color color, Family family){
        return plants.stream().filter(p -> p.color == color && p.family == family);
    }

}

interface Specification<T> {
    boolean isSatisfied(T item);
}

interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> spec);
}

class ColorSpecification implements Specification<Plant> {

    private Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Plant item) {
        return item.color == color;
    }
}

class FamilySpecification implements Specification<Plant> {
    private Family family;

    public FamilySpecification(Family family) {
        this.family = family;
    }

    @Override
    public boolean isSatisfied(Plant item) {
        return item.family == family;
    }
}

class AndSpecification<T> implements Specification<T> {

    private Specification<T> first,second;

    public AndSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}

class BetterFilter implements Filter<Plant> {

    @Override
    public Stream<Plant> filter(List<Plant> items, Specification<Plant> spec) {
        return items.stream().filter(p -> spec.isSatisfied(p));
    }
}

public class OpenClosedPrincipleDemo {
    public static void main(String[] args) {
        List<Plant> plants = new ArrayList<>();
        Plant daisy = new Plant("Daisy", Color.YELLOW, Family.FLOWERING);
        Plant spruce = new Plant("Spruce", Color.GREEN, Family.CONIFERS);
        Plant pincushion = new Plant("Pincushion Moss", Color.GREEN, Family.LIVERWORTS);
        plants.addAll(Arrays.asList(daisy,spruce,pincushion));

        PlantFilter pf = new PlantFilter();
        System.out.println("# Filtering with PlantFilter (old)");
        System.out.println("Green plants are:");
        pf.filterByColor(plants, Color.GREEN).forEach(p -> System.out.println("- " + p.name));
        System.out.println("Green and Conifers are:");
        pf.filterByColorAndFamily(plants, Color.GREEN, Family.CONIFERS).forEach( p -> System.out.println("- " + p.name));

        System.out.println("# Filtering with BetterFilter (new)");

        BetterFilter bf = new BetterFilter();
        System.out.println("Green plants are:");
        bf.filter(plants, new ColorSpecification(Color.GREEN)).forEach(p -> System.out.println("- " + p.name));
        System.out.println("Green and Conifers are:");
        bf.filter(plants, new AndSpecification<>(
                new ColorSpecification(Color.GREEN),
                new FamilySpecification(Family.CONIFERS)
                )).forEach(p -> System.out.println("- " + p.name));
    }
}
