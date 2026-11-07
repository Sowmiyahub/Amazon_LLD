//pizza Builder
class Pizza{
    //private final fiels
    private final String size;
    private final String crust;
    private final String cheese;
    private final String olives;

    //private constructors - bcz we only want to create Pizza objects only through the Builder, not directly using new Pizza()
    private Pizza(Builder builder){
        this.size = builder.size;
        this.crust = builder.crust;
        this.cheese = builder.cheese;
        this.olives = builder.olives;
    }

    public String getSize(){
        return size;
        
    }
    public String getCrust(){
        return crust;
        
    }
    public String getCheese(){
        return cheese;
        
    }
    public String getOlives(){
        return olives;
        
    }
    //nested static builder
    public static class Builder{
        //mandatory fields
        private final String size;
        private final String crust;
        //optional fields
        private String cheese;
        private String olives;

        public Builder(String size, String crust){
            this.size = size;
            this.crust = crust;
        }
        public Builder cheese(String cheese){
            this.cheese = cheese;
            return this;
        }
        public Builder olives(String olives){
            this.olives = olives;
            return this;
        }
        public Pizza build(){
            if (size == null || size.isBlank()) {
                throw new IllegalArgumentException("Size is required");
            }
            
            if (crust == null || crust.isBlank()) {
                throw new IllegalArgumentException("Crust is required");
            }
            return new Pizza(this);
        }
    }
}

public class Main{
    public static void main(String[] args){
        Pizza pizza = new Pizza.Builder("Medium", "Thick")
            .cheese("Mozzarella")
            .olives("Black Olives")
            .build();
        System.out.println("Size of Pizza : " + pizza.getSize());
        System.out.println("Crust of Pizza : " + pizza.getCrust());
        System.out.println("Cheese of Pizza : " + pizza.getCheese());
        System.out.println("Olives of Pizza : " + pizza.getOlives());
    }
}