    package model;

    import java.time.LocalDateTime;

    public class Product {
        private int id;
        private String name;
        private double price;
        private String description;
        private int stock;
        private LocalDateTime importDate;
        private boolean status;

        public Product(int id, String name, double price, String description, int stock,LocalDateTime importDate, boolean status) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.description = description;
            this.stock = stock;
            this.importDate=importDate;
            this.status=status;
        }

        public Product(String name, double price, String description, int stock,LocalDateTime importDate,boolean status) {
            this.name = name;
            this.price = price;
            this.description = description;
            this.stock = stock;
            this.importDate=importDate;
            this.status=status;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public LocalDateTime getImportDate() {
            return importDate;
        }

        public void setImportDate(LocalDateTime importDate) {
            this.importDate = importDate;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    ", description='" + description + '\'' +
                    ", stock=" + stock +
                    ", importDate=" + importDate +

                    '}';
        }
    }
