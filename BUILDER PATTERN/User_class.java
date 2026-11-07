// Non-public class so it can stay in the same file
class User {
    private final String name;
    private final String email;
    private final int age;
    private final String address;

    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.address = builder.address;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public String getAddress() { return address; }

    public static class Builder {
        private final String name;
        private final String email;
        private int age;~
        private String address;

        public Builder(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name is required");
            }
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email is required");
            }
            if (age < 0) {
                throw new IllegalArgumentException("Invalid age");
            }
            return new User(this);
        }
    }
}


public class Main {
    public static void main(String[] args) {
        User user = new User.Builder("Sowmi", "sowmi@gmail.com")
                .age(22)
                .address("Chennai")
                .build();

        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Age: " + user.getAge());
        System.out.println("Address: " + user.getAddress());
    }
}
