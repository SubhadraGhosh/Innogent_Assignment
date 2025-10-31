
LOW_STOCK = 5
import itertools

class Product:
    _id_counter = itertools.count(1)
    def __init__(self, name, stock, price, location, tags=None):
        self.id = next(Product._id_counter)
        self.name = name
        self.stock = stock
        self.price = price
        self.location = location
        self.tags = set(tags) if tags else set()

    def is_low_stock(self):
        return self.stock < LOW_STOCK


    def __str__(self):
        tags = ', '.join(self.tags) if self.tags else 'None'
        return (
            f"   Id       : {self.id}\n"
            f"   Name     : {self.name}\n"
            f"   Stock    : {self.stock}\n"
            f"   Price    : ₹{self.price:.2f}\n"
            f"   Location : {self.location}\n"
            f"   Tags     : {tags}\n"
            f""
            "---------------------------"
        )


    def set_id(self):
        self.id = Product.id_generator
        Product.id_generator += 1


class Inventory:
    def __init__(self):
        self.products = []

    #Listing all the products
    def list_products(self):
        if self.products:
            for p in self.products:
                print(p)
        else:
            print("No products in inventory.")

    #Low on stock warnings. (LOW_STOCK = 5)
    def low_stack_warning(self):
        flag = False
        for p in self.products:
            if p.is_Low_stock():
                print(f"Warning: Low stock for product '{p.name}'. Only {p.stock} left.")
                flag = True
        if not flag:
            print("All products are sufficiently stocked.")

    #Add product.
    def add_product(self, product):
        self.products.append(product)
        print(f"Product '{product.name}' added successfully.")

    #Update stock.
    def update_stock(self, id, new_stock):
        for p in self.products:
            if p.id == id:
                p.stock = new_stock
                print(f"Stock for product '{p.name}' updated to {new_stock}.")
                return
        print(f"Product '{p.id}' not found in inventory.")

    #Delete product
    def delete_product(self, id):
        for p in self.products:
            if p.id == id:
                self.products.remove(p)
                print(f"Product '{p.name}' deleted successfully.")
                return
        print(f"Product with ID '{id}' not found in inventory.")

    #Print Total value of all products in stock.
    def total_inventory_value(self):
        total_value = sum(p.price * p.stock for p in self.products)
        print(f"Total inventory value: ₹{total_value:.2f}")
        return total_value

    #Applying discount
    def apply_discount(self, tags, percent=0.5):
        for p in self.products:
            if ('clearance' in p.tags) and (tags & p.tags):  # check for matching tags
                original_price = p.price
                p.price *= (1 - percent)
                print(f"Discounted '{p.name}' from ₹{original_price:.2f} to ₹{p.price:.2f}")


def product_input():
    name = input("Enter product name: ")
    stock = int(input("Enter product stock: "))
    price = float(input("Enter product price: "))
    location = input("Enter product location: ")
    tags_input = input("Enter product tags (comma-separated): ")
    tags = {tag.strip() for tag in tags_input.split(',')} if tags_input else set()
    return Product(name, stock, price, location, tags)

def main():
    inventory = Inventory()
    inventory.products = [
        Product("Amul Milk", 20, 50.0, "Shelf-1", {"dairy", "fresh"}),
        Product("Mango", 15, 30.0, "Shelf-2", {"fruit", "seasonal", "clearance"}),
        Product("Maggi", 50, 12.0, "Shelf-3", {"grocery", "instant"}),
        Product("Britannia Biscuits", 40, 25.0, "Shelf-4", {"snack", "grocery", "clearance"}),
        Product("Tata Tea", 30, 150.0, "Shelf-5", {"beverage", "grocery"}),
        Product("Spinach", 10, 20.0, "Shelf-1", {"vegetable", "fresh"}),
        Product("Parle-G", 60, 5.0, "Shelf-4", {"snack", "grocery"}),
        Product("Dettol Soap", 25, 40.0, "Shelf-6", {"hygiene", "essential", "clearance"}),
        Product("Coca-Cola", 45, 60.0, "Shelf-5", {"beverage", "refreshing"}),]

    #User Input Loop
    while True:
        choice = input("""
        Inventory Management System
        1. List all products
        2. Low stock warnings
        3. Add a new product
        4. Update product stock
        5. Delete a product
        6. Total inventory value
        7. Apply discount to products by tags
        8. Exit
                       
        Enter your choice: """)

        if choice == '1':
            inventory.list_products()
        elif choice == '2':
            inventory.low_stack_warning()
        elif choice == '3':
            product = product_input()
            inventory.add_product(product)
        elif choice == '4':
            id = int(input("Enter product ID to update stock: "))
            new_stock = int(input("Enter new stock value: "))
            inventory.update_stock(id, new_stock)
        elif choice == '5':
            id = int(input("Enter product ID to delete: "))
            inventory.delete_product(id)
        elif choice == '6':
            inventory.total_inventory_value()
        elif choice == '7':
            tags_input = input("Enter tags for discount (comma-separated): ")
            tags = {tag.strip() for tag in tags_input.split(',')} if tags_input else set()
            percent = float(input("Enter discount percentage (default 50%): ") or 50)
            inventory.apply_discount(tags, percent/100)
        elif choice == '8':
            print("Exiting Inventory Management System.")
            break
        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()

